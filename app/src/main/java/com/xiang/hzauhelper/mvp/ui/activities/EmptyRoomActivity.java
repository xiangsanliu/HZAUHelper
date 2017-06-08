package com.xiang.hzauhelper.mvp.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.mvp.presenter.EmptyRoomPresenter;
import com.xiang.hzauhelper.mvp.view.EmptyRoomView;

import butterknife.BindView;
import butterknife.OnClick;

public class EmptyRoomActivity extends BaseActivity implements EmptyRoomView {


    EmptyRoomPresenter presenter = null;
    @BindView(R.id.select_date)
    Spinner selectDate;
    @BindView(R.id.select_lesson_num)
    Spinner selectLessonNum;
    @BindView(R.id.query)
    Button query;
    @BindView(R.id.empty_room_list)
    RecyclerView emptyRoomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EmptyRoomPresenter(this);
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_empty_room;
    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {

    }

    @Override
    public void dismissProgress(ProgressDialog progressDialog) {

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
                        finish();
                        presenter.dismissProgress();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String checkCode = ((EditText) view.findViewById(R.id.code_edit)).getText().toString();
                        hideSoftInput(view.findViewById(R.id.code_edit));
                        presenter.showProgress();
                        presenter.setSpinner(checkCode);
                    }
                });
    }

    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void loadCheckCode(Bitmap bitmap) {
        createCheckCodeDialog(bitmap).create().show();
    }

    @Override
    public void initSpinner(String[] startDate, String[] lessonNum) {
        ArrayAdapter<String>  adapter;
        adapter = new ArrayAdapter<>(this, R.layout.item_my_spinner, startDate);
        adapter.setDropDownViewResource(R.layout.my_dropdown_spinner_item);
        selectDate.setAdapter(adapter);
        adapter = new ArrayAdapter<>(this, R.layout.item_my_spinner, lessonNum);
        adapter.setDropDownViewResource(R.layout.my_dropdown_spinner_item);
        selectLessonNum.setAdapter(adapter);
        presenter.dismissProgress();
    }

    @OnClick(R.id.query)
    public void onClick() {
        presenter.getEmptyRoom(selectDate.getSelectedItemPosition(), selectLessonNum.getSelectedItemPosition());
    }
}
