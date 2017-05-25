package com.xiang.hzauhelper.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

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

    public void showProgress(){

    }
    public void dismissProgress(){
    }

    @SuppressLint("ShowToast")
    public void showToast(String content) {
        Toast.makeText((Context) view, content, Toast.LENGTH_SHORT).show();
    }

}
