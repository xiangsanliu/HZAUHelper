package com.xiang.hzauhelper.mvp.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.DialogInterface;

import com.xiang.hzauhelper.mvp.model.AccountGetter;
import com.xiang.hzauhelper.mvp.ui.activities.AccountSettingActivity;
import com.xiang.hzauhelper.mvp.ui.activities.MainActivity;
import com.xiang.hzauhelper.mvp.view.MainView;
import com.xiang.hzauhelper.network.HttpMethodGet;
import com.xiang.hzauhelper.network.DocumentGetter;

/**
 * Created by xiang on 2017/3/26.
 *
 */

public class MainPresenter extends BasePresenter<MainView> {

    private Activity activity;
    private AccountGetter accountGetter;

    @Override
    public void onCreate() {
        super.onCreate();
        accountGetter = AccountGetter.newInstance(activity);
        view.loadAccountTv(accountGetter.getAccount());
    }

    public  boolean checkJWAccount() {
        if (accountGetter.getAccount().length()<=0) {
            showAccountDialog("");
            return false;
        } else if (accountGetter.getPasswordJw().length()<=0) {
            showAccountDialog("教务系统");
            return false;
        }
        return true;
    }

    public boolean checkLibAccount() {
        if (accountGetter.getAccount().length()<=0) {
            showAccountDialog("");
            return false;
        } else if (accountGetter.getPasswrodLib().length()<=0) {
            showAccountDialog("图书馆");
            return false;
        }
        return true;
    }

    public boolean checkPEAccount() {
        if (accountGetter.getAccount().length()<=0) {
            showAccountDialog("");
            return false;
        } else if (accountGetter.getPasswordPe().length()<=0) {
            showAccountDialog("体育系统");
            return false;
        }
        return true;
    }
    
    private void showAccountDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false)
                .setTitle("提示")
                .setMessage("还没有添加"+message+"账号或密码")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                })
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivity(new Intent(activity, AccountSettingActivity.class));
                    }
                })
                .create().show();
                
    }

    public MainPresenter(Activity activity) {
        this.activity = activity;
    }

    public String getAccount() {
        return accountGetter.getAccount();
    }

    public String getPasswordJw() {
        return accountGetter.getPasswordJw();
    }

    public String getPasswordLib() {
        return accountGetter.getPasswrodLib();
    }

    public String getPasswordPe() {
        return accountGetter.getPasswordPe();
    }

}
