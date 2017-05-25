package com.xiang.hzauhelper.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.xiang.hzauhelper.mvp.model.AccountGetter;
import com.xiang.hzauhelper.mvp.view.MainView;
import com.xiang.hzauhelper.network.HttpMethodGet;
import com.xiang.hzauhelper.network.JwGetter;

import org.reactivestreams.Subscriber;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableRange;

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
        httpMethodGet = HttpMethodGet.newInstance(new JwGetter());
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
