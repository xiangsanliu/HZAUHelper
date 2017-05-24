package com.xiang.hzauhelper.mvp.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.adapter.ExamPlanListAdapter;
import com.xiang.hzauhelper.entities.ExamTerm;
import com.xiang.hzauhelper.mvp.presenter.ExamPlanPresenter;
import com.xiang.hzauhelper.mvp.view.ExamPlanView;

import org.litepal.crud.DataSupport;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;


public class ExamPlanActivity extends BaseActivity implements ExamPlanView {

    ExamPlanPresenter presenter;
    @BindView(R.id.exam_plan_list)
    RecyclerView examPlanList;
    @BindView(R.id.refresh_exam)
    FloatingActionButton refreshExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ExamPlanPresenter(this);
        presenter.attachView(this);
        presenter.onCreate();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public ExamPlanActivity() {
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

    @Override
    public void loadCheckCode(Bitmap bitmap) {
        createCheckCodeDialog(bitmap).create().show();
    }

    @Override
    public void loadExamPlanList(ExamPlanListAdapter adapter) {
        examPlanList.setLayoutManager(new LinearLayoutManager(this));
        examPlanList.setAdapter(adapter);
    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {
        progressDialog.show();
    }

    @Override
    public void hidePregress(ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }

    @OnClick(R.id.refresh_exam)
    public void onClick(){
        try {
            presenter.showCheckCodeInputer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AlertDialog.Builder createCheckCodeDialog(Bitmap bitmap) {
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(this)
                .inflate(R.layout.dialog_check_code, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.code_image);
        imageView.setImageBitmap(bitmap);
        return new AlertDialog.Builder(this)
                .setTitle("请输入验证码")
                .setView(view)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.hidePregress();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String checkCode = ((EditText)view.findViewById(R.id.code_edit)).getText().toString();
                        hideSoftInput(view.findViewById(R.id.code_edit));
                        presenter.setExamPlanDoc(checkCode);
                    }
                });
    }

    @Override
    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
