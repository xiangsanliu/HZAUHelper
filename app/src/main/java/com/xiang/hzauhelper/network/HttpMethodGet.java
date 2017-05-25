package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;
import android.util.Log;
import org.jsoup.nodes.Document;
import java.io.IOException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by xiang on 2017/3/26.
 *
 */

public class HttpMethodGet {

    private static HttpMethodGet httpMethodGet;
    private JwGetter jwGetter;

    private HttpMethodGet(JwGetter jwGetter) {
        this.jwGetter = jwGetter;
    }

    public static HttpMethodGet newInstance(JwGetter jwGetter) {
        if (httpMethodGet != null ) {
            return httpMethodGet;
        } else {
            return new HttpMethodGet(jwGetter);
        }
    }

    public Observable<Bitmap> getCheckCode() throws IOException {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
                    @Override
                    public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                        e.onNext(jwGetter.getCodeBitmap());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Document> getExamPlanDoc(final String account
            , final String password, final String code) {
        return Observable.create(new ObservableOnSubscribe<Document>() {
                    @Override
                    public void subscribe(ObservableEmitter<Document> e) throws Exception {
                        e.onNext(jwGetter.getExamPlanDoc(account, password, code));
                        e.onComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Document> getEmptyRoomDoc(final String account, final String password, final String code) {
        return Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                e.onNext(jwGetter.getEmptyRoomDoc(account, password, code));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public Observable<Document> getEmptyRoomDocument(final String date, final String lessonNum
            , final String __VIEWSTATE, final String xn, final String xq, final String account) {
        return Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                e.onNext(jwGetter.getEmptyRoomDocument(date, lessonNum, __VIEWSTATE, xn, xq,account));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

}
