package com.xiang.hzauhelper.mvp.ui.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.mvp.view.SearchBookView;

public class SearchBookActivity extends BaseActivity implements SearchBookView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_book;
    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {

    }

    @Override
    public void dismissProgress(ProgressDialog progressDialog) {

    }
}
