package com.xiang.hzauhelper.mvp.presenter;

import android.content.Context;

import com.xiang.hzauhelper.mvp.model.AccountGetter;
import com.xiang.hzauhelper.mvp.view.AccountSettingView;

/**
 * Created by xiang on 2017/3/23.
 *
 */

public class AccountSettingPresenter extends BasePresenter<AccountSettingView> {

    private AccountGetter accountGetter;

    public AccountSettingPresenter(Context context) {
        accountGetter = AccountGetter.newInstance(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadAccount(accountGetter.getAccount());
        loadPasswordJw(accountGetter.getPasswordJw());
        loadPasswordLib(accountGetter.getPasswrodLib());
        loadPasswordPe(accountGetter.getPasswordPe());
    }

    private void loadAccount(String account) {
        view.loadAccount(account);
    }
    private void loadPasswordJw(String passwordJw) {
        view.loadPasswordJw(passwordJw);
    }
    private void loadPasswordLib(String passwordLib) {
        view.loadPasswordLib(passwordLib);
    }
    private void loadPasswordPe(String passwordPe) {
        view.loadPasswordPe(passwordPe);
    }
    public void setAccount(String accountT, String passwordJwT, String passwordLibT
            , String passwordPeT) {
        accountGetter.setAccount(accountT);
        accountGetter.setPasswordJw(passwordJwT);
        accountGetter.setPasswordLib(passwordLibT);
        accountGetter.setPasswordPe(passwordPeT);
        accountGetter.commit();
    }

}
