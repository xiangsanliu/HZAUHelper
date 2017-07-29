package com.xiang.hzauhelper.network;

import com.xiang.hzauhelper.mvp.model.PEGetter;

import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiang on 2017/7/17.
 *
 */

public class HttpPEGetter {

    private static HttpPEGetter httpPEGetter;
    private PEGetter pEGetter;

    private HttpPEGetter() {
        pEGetter = PEGetter.newInstance();
    }

    public static HttpPEGetter newInstance() {
        if (httpPEGetter == null) {
            return new HttpPEGetter();
        } else {
            return httpPEGetter;
        }
    }

    public Observable<String> getSouthLake(final String account, final String password) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(pEGetter.getPEScore(account, password));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public Observable<String> getScore(final String account, final String password) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(pEGetter.getPEScore(account, password));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

}
