package com.yyk.retrofitrxandroid.service.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yyk.retrofitrxandroid.service.bean.Android;
import com.yyk.retrofitrxandroid.service.manager.DataManager;
import com.yyk.retrofitrxandroid.service.view.IView;
import com.yyk.retrofitrxandroid.service.view.TestActivityView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YYK on 2017/8/28.
 */

public class AndroidPresenter implements IPresenter {

    private Context mContext;
    private TestActivityView mTestActivityView;
    private DataManager mDataManager;

    public AndroidPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        mDataManager = new DataManager(mContext);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(IView iView) {
        mTestActivityView = (TestActivityView) iView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getData(String page) {
        mDataManager.getAndroid(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Android>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mTestActivityView.showError("服务器挂了");
                        mTestActivityView.hideProgress();
                    }

                    @Override
                    public void onNext(Android android) {
                        mTestActivityView.setData(android);
                    }
                });
    }
}
