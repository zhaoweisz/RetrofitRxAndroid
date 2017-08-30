package com.yyk.retrofitrxandroid.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yyk.retrofitrxandroid.R;
import com.yyk.retrofitrxandroid.service.bean.Android;
import com.yyk.retrofitrxandroid.widget.MaterialProgress;

import java.util.List;

/**
 * Created by YYK on 2017/7/28.
 */

public class AndroidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Android.ResultsBean> beans;
    private onItemClickListener mOnItemClickListener;
    private onItemLongClickListener mOnItemLongClickListener;
    private onLoadMoreListener mOnLoadMoreListener;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private int maxLength;
    private boolean isError;

    public AndroidAdapter(List<Android.ResultsBean> beans) {
        this.beans = beans;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).descText.setText(beans.get(position).getType());
            ((ItemViewHolder) holder).nameText.setText(beans.get(position).getDesc());
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }

            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        } else if (holder instanceof FootViewHolder) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMore(((FootViewHolder) holder).mProgress, ((FootViewHolder) holder).mRetryText);
            }
            if (isError) {
                ((FootViewHolder) holder).mRetryText.setVisibility(View.VISIBLE);
                ((FootViewHolder) holder).mProgress.setVisibility(View.GONE);
                ((FootViewHolder) holder).mEndText.setVisibility(View.GONE);
            } else {
                if (beans.size() < maxLength) {
                    ((FootViewHolder) holder).mEndText.setVisibility(View.GONE);
                    ((FootViewHolder) holder).mProgress.stopProgress();
                    ((FootViewHolder) holder).mProgress.startProgress();
                    ((FootViewHolder) holder).mRetryText.setVisibility(View.GONE);
                } else {
                    ((FootViewHolder) holder).mProgress.stopProgress();
                    ((FootViewHolder) holder).mProgress.setVisibility(View.GONE);
                    ((FootViewHolder) holder).mEndText.setVisibility(View.VISIBLE);
                    ((FootViewHolder) holder).mRetryText.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return beans.size() == 0 ? 0 : beans.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView descText;
        TextView nameText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            descText = itemView.findViewById(R.id.desc_text);
            nameText = itemView.findViewById(R.id.name_text);
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {

        MaterialProgress mProgress;
        TextView mEndText;
        TextView mRetryText;

        public FootViewHolder(View itemView) {
            super(itemView);
            mProgress = itemView.findViewById(R.id.progress);
            mEndText = itemView.findViewById(R.id.end_text);
            mRetryText = itemView.findViewById(R.id.retry_text);
        }
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(onItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnLoadMoreListener(onLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface onItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public interface onLoadMoreListener {
        void onLoadMore(MaterialProgress progressView, View errorView);
    }
}
