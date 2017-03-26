package com.xiang.hzauhelper;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by xiang on 2017/3/26.
 */

public class TestRxJava {
    public static Observable<String> getInstance() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        });
    }
}
