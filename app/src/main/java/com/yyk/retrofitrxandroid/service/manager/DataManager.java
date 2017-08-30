package com.yyk.retrofitrxandroid.service.manager;

import android.content.Context;

import com.yyk.retrofitrxandroid.service.RetrofitHelper;
import com.yyk.retrofitrxandroid.service.RetrofitService;
import com.yyk.retrofitrxandroid.service.bean.Android;

import rx.Observable;

/**
 * Created by YYK on 2017/8/28.
 */

public class DataManager {

    private RetrofitService mRetrofitService;

    public DataManager(Context context) {
        mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<Android> getAndroid(String page) {
        return mRetrofitService.getAndroid(page);
    }
}
