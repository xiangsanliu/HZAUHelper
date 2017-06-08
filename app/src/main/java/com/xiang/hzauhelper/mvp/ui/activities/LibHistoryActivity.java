package com.xiang.hzauhelper.mvp.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.bumptech.glide.Glide;
import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.adapter.BookHistoryAdapter;
import com.xiang.hzauhelper.mvp.presenter.LibHistoryPresenter;
import com.xiang.hzauhelper.mvp.view.LibHistoryView;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

public class LibHistoryActivity extends BaseActivity implements LibHistoryView {

    LibHistoryPresenter presenter;
    @BindView(R.id.book_history_list)
    RecyclerView bookHistoryList;
    @BindView(R.id.refresh_book)
    FloatingActionButton refreshBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LibHistoryPresenter(this);
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookHistoryList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lib_history;
    }

    private AlertDialog.Builder createCheckCodeDialog(Bitmap bitmap) {
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(this)
                .inflate(R.layout.dialog_check_code, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.code_image);
        imageView.setImageBitmap(bitmap);
//        Glide.with(this).load(bitmap).into(imageView);
        return new AlertDialog.Builder(this)
                .setTitle("请输入验证码")
                .setView(view)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.dismissProgress();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String checkCode = ((EditText) view.findViewById(R.id.code_edit)).getText().toString();
                        hideSoftInput(view.findViewById(R.id.code_edit));
                        presenter.setBookHistoryDoc(checkCode);
                    }
                });
    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {
        progressDialog.show();
    }

    @Override
    public void dismissProgress(ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }

    @Override
    public void loadCheckCode(Bitmap bitmap) {
        createCheckCodeDialog(bitmap).create().show();
    }

    @Override
    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void loadBookHistoryList(BookHistoryAdapter adapter) {
        bookHistoryList.setAdapter(adapter);
    }

    @OnClick(R.id.refresh_book)
    public void onClick() {
        presenter.showCheckCodeInputer();
    }
}
