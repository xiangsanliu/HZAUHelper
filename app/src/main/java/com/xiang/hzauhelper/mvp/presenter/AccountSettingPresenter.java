package com.xiang.hzauhelper.mvp.presenter;

import android.accounts.Account;
import android.content.Context;

import com.xiang.hzauhelper.mvp.model.AccountGetter;
import com.xiang.hzauhelper.mvp.view.AccountSettingView;

/**
 * Created by xiang on 2017/3/23.
 */

public class AccountSettingPresenter extends BasePresenter<AccountSettingView> {

    AccountGetter accountGetter;
    Context context;

    public AccountSettingPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void loadAccount(String account) {

    }
    public void loadPasswordJw(String passwordJw) {

    }
    public void loadPasswordLib(String passwordLib) {

    }

    public void loadPasswordPe(String passwordPe) {

    }

}
