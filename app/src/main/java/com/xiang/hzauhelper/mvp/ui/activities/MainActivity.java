package com.xiang.hzauhelper.mvp.ui.activities;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.RequestCodes;
import com.xiang.hzauhelper.mvp.presenter.MainPresenter;
import com.xiang.hzauhelper.mvp.view.MainView;

import java.io.IOException;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {


    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    MainPresenter presenter;
    String cookieJw = "";

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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
                try {
                    onExamPlan();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
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
                    login();
                }
                break;
            case RequestCodes.SETCOOKIEJW:
                if (resultCode == RESULT_OK) {
                    cookieJw = data.getStringExtra("cookieJw");
                }
        }
    }

    private void login() {
        Log.d(TAG, "login");
    }

    private void onExamPlan() throws IOException {
        if (cookieJw.length()>0) {
            Intent intent = new Intent(MainActivity.this, ExamPlanActivity.class);
            intent.putExtra("account", presenter.getAccount());
            intent.putExtra("passwordJw", presenter.getPasswordJw());
            intent.putExtra("cookieJw", cookieJw);
            startActivity(intent);
        } else {
            presenter.showCheckCodeInputer();
        }
    }

    @Override
    public void loadBitmap(Bitmap bitmap) {
        createCheckCodeDialog(bitmap).create().show();
    }

    private AlertDialog.Builder createCheckCodeDialog(Bitmap bitmap) {
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_check_code, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.code_image);
        imageView.setImageBitmap(bitmap);
        return new AlertDialog.Builder(this)
                .setTitle("请输入验证码")
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String checkCode = ((EditText)view.findViewById(R.id.code_edit)).getText().toString();
                        Intent intent = new Intent(MainActivity.this, ExamPlanActivity.class);
                        intent.putExtra("account", presenter.getAccount());
                        intent.putExtra("passwordJw", presenter.getPasswordJw());
                        intent.putExtra("checkCode", checkCode);
                        startActivityForResult(intent, RequestCodes.SETCOOKIEJW);
                    }
                });
    }

}
