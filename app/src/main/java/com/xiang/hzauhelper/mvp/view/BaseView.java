package com.xiang.hzauhelper.mvp.view;

import android.app.ProgressDialog;

/**
 * Created by xiang on 2017/3/23.
 *
 */

public interface BaseView {
    public void showProgress(ProgressDialog progressDialog);
    public void dismissProgress(ProgressDialog progressDialog);
}
