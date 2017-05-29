package com.xiang.hzauhelper.mvp.presenter;

import android.content.Context;

import com.xiang.hzauhelper.mvp.model.AccountGetter;
import com.xiang.hzauhelper.mvp.view.MainView;
import com.xiang.hzauhelper.network.HttpMethodGet;
import com.xiang.hzauhelper.network.DocumentGetter;

/**
 * Created by xiang on 2017/3/26.
 *
 */

public class MainPresenter extends BasePresenter<MainView> {

    private Context context;
    private HttpMethodGet httpMethodGet;
    private AccountGetter accountGetter;

    @Override
    public void onCreate() {
        super.onCreate();
        accountGetter = AccountGetter.newInstance(context);
        httpMethodGet = HttpMethodGet.newInstance(new DocumentGetter());
        view.loadAccountTv(accountGetter.getAccount());
    }

    public MainPresenter(Context context) {
        this.context = context;
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
