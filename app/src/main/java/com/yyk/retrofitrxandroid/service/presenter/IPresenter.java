package com.yyk.retrofitrxandroid.service.presenter;

import android.content.Intent;

import com.yyk.retrofitrxandroid.service.view.IView;

/**
 * Created by YYK on 2017/8/28.
 */

public interface IPresenter {
    void onCreate();

    void onStart();//暂时没用到

    void onStop();

    void pause();//暂时没用到

    void attachView(IView iView);

    void attachIncomingIntent(Intent intent);//暂时没用到

}
