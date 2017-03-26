package com.xiang.hzauhelper.mvp.presenter;

import android.os.Bundle;

import com.xiang.hzauhelper.mvp.view.BaseView;

/**
 * Created by xiang on 2017/3/23.
 *
 */

public abstract class BasePresenter <T extends BaseView> {

    protected T view;

    public void attachView(T view) {
        this.view = view;
    }

    public void onCreate() {

    }

}
