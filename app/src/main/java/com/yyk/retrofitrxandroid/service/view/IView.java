package com.yyk.retrofitrxandroid.service.view;

/**
 * Created by YYK on 2017/8/28.
 */

public interface IView {

    void showProgress();
    void hideProgress();
    void showError(String error);
}
