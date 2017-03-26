package com.xiang.hzauhelper.mvp.ui.activities;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.mvp.presenter.ExamPlanPresenter;
import com.xiang.hzauhelper.mvp.view.ExamPlanView;


public class ExamPlanActivity extends BaseActivity implements ExamPlanView {

    ExamPlanPresenter presenter;
    String account;
    String passwordJw;
    String checkCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStrings(getIntent());
        presenter = new ExamPlanPresenter(account, passwordJw, checkCode);
        presenter.attachView(this);
        presenter.onCreate();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_plan;
    }

    @Override
    public void loadTerms() {

    }

    @Override
    public void loadYear() {

    }

    private void initStrings(Intent intent) {
        account = intent.getStringExtra("account");
        passwordJw = intent.getStringExtra("passwordJw");
        checkCode = intent.getStringExtra("checkCode");
    }

}
