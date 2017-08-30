package com.yyk.retrofitrxandroid.service;

import com.yyk.retrofitrxandroid.service.bean.Android;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YYK on 2017/8/25.
 */

public interface RetrofitService {

    @GET("data/Android/10/{page}")
    Observable<Android> getAndroid(@Path("page") String page);
}
