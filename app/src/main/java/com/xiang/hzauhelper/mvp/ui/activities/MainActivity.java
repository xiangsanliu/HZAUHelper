package com.xiang.hzauhelper.mvp.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.mvp.presenter.LibHistoryPresenter;
import com.xiang.hzauhelper.network.RequestCodes;
import com.xiang.hzauhelper.mvp.presenter.MainPresenter;
import com.xiang.hzauhelper.mvp.view.MainView;
import com.xiang.hzauhelper.network.DocumentGetter;

import java.io.IOException;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {


    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    TextView accountTV;

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this);
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.account_setting:
                startActivityForResult(new Intent(this, AccountSettingActivity.class), RequestCodes.SETACCOUNT);
                break;
            case R.id.exam_plan:
                if (presenter.checkJWAccount()) {
                    onExamPlan();
                }
                break;
            case R.id.empty_room:
                if (presenter.checkJWAccount()) {
                    onEmptyRoom();
                }
                break;
            case R.id.query_book:
                startActivity(new Intent(this, SearchBookActivity.class));
                break;
            case R.id.book_history:
                if (presenter.checkLibAccount()) {
                    startActivity(new Intent(MainActivity.this, LibHistoryActivity.class));
                }
                break;
            case R.id.exit:
                finish();
            default:

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void initViews() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        accountTV = (TextView) navView.getHeaderView(0).findViewById(R.id.account);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCodes.SETACCOUNT:
                if (resultCode == RESULT_OK) {
//                    login();
                }
                break;
            case RequestCodes.SETCOOKIEJW:

        }
    }


    private void onExamPlan() {
        Intent intent = new Intent(MainActivity.this, ExamPlanActivity.class);
        startActivity(intent);
    }

    private void onEmptyRoom() {
        startActivity(new Intent(this, EmptyRoomActivity.class));
    }


    @Override
    public void loadAccountTv(String account) {
        accountTV.setText(account);
    }

    @Override
    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

//    private AlertDialog.Builder createCheckCodeDialog(Bitmap bitmap) {
//        @SuppressLint("InflateParams") final View view = LayoutInflater.from(MainActivity.this)
//                .inflate(R.layout.dialog_check_code, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.code_image);
//        imageView.setImageBitmap(bitmap);
//        return new AlertDialog.Builder(this)
//                .setTitle("请输入验证码")
//                .setView(view)
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String checkCode = ((EditText)view.findViewById(R.id.code_edit)).getText().toString();
//                        Intent intent = new Intent(MainActivity.this, ExamPlanActivity.class);
//                        intent.putExtra("account", presenter.getAccount());
//                        intent.putExtra("passwordJw", presenter.getPasswordJw());
//                        intent.putExtra("checkCode", checkCode);
//                        hideSoftInput(view.findViewById(R.id.code_edit));
//                        startActivityForResult(intent, RequestCodes.SETCOOKIEJW);
//                    }
//                });
//    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {

    }

    @Override
    public void dismissProgress(ProgressDialog progressDialog) {

    }

}
