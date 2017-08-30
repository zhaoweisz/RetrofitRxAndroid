package com.yyk.retrofitrxandroid.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yyk.retrofitrxandroid.R;
import com.yyk.retrofitrxandroid.service.bean.Android;
import com.yyk.retrofitrxandroid.service.presenter.AndroidPresenter;
import com.yyk.retrofitrxandroid.service.view.TestActivityView;
import com.yyk.retrofitrxandroid.ui.adapter.AndroidAdapter;
import com.yyk.retrofitrxandroid.widget.MaterialProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YYK on 2017/8/25.
 */

public class TestRetrofitActivity extends AppCompatActivity implements TestActivityView {

    private static final String TAG = "TestRetrofitActivity";
    private TextView mTextView;
    private AndroidPresenter mPresenter;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFAB;
    private List<Android.ResultsBean> beans;
    private AndroidAdapter mAdapter;
    private boolean isLoading;
    private boolean isClear;
    private int lastVisibleItemPosition;
    private int maxLength = 2000;
    private int page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        showProgress();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mFAB = (FloatingActionButton) findViewById(R.id.float_button);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mPresenter = new AndroidPresenter(this);
        mPresenter.onCreate();
        mPresenter.attachView(this);
        page = 1;

        beans = new ArrayList<>();
        mAdapter = new AndroidAdapter(beans);
        mAdapter.setMaxLength(maxLength);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AndroidAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.setBackgroundResource(R.drawable.rectangle_bg_ripples);
                Intent intent = new Intent(TestRetrofitActivity.this,WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",beans.get(position).getUrl());
                intent.putExtras(bundle);
                if(Build.VERSION.SDK_INT >= 21) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TestRetrofitActivity.this).toBundle());
                }
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgress();
                page = 1;
                initData(page);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 5 == mAdapter.getItemCount()) {
                    if (!isLoading) {
                        isLoading = true;
                        page ++;
                        moreData(page);
                    }
                }
            }
        });
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.scrollToPosition(0);
            }
        });
        initData(page);
    }

    private void initData(int mPage) {
        isClear = true;
        mPresenter.getData(mPage + "");
    }

    private void moreData(int mPage) {
        isClear = false;
        mPresenter.getData(mPage + "");
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void showError(final String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        mAdapter.setError(true);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnLoadMoreListener(new AndroidAdapter.onLoadMoreListener() {
            @Override
            public void onLoadMore(final MaterialProgress progressView, final View errorView) {

                errorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressView.setVisibility(View.VISIBLE);
                        progressView.stopProgress();
                        progressView.startProgress();
                        errorView.setVisibility(View.GONE);
                        mPresenter.getData(page + "");
                    }
                });
            }
        });

    }

    @Override
    public void setData(Android android) {
        mAdapter.setError(false);
        if(isClear) {
            beans.clear();
        }
        for(Android.ResultsBean bean : android.getResults()) {
            beans.add(bean);
        }
        if (beans.size() < maxLength) {
            isLoading = false;
        } else {
            isLoading = true;
        }
        if(mSwipeRefreshLayout.isRefreshing()) {
            hideProgress();
        }
//        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        mAdapter.notifyDataSetChanged();
    }
}
