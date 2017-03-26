package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;

import com.xiang.hzauhelper.entities.JwUrls;

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

    private HttpMethodGet() {

    }

    public static HttpMethodGet newInstance() {
        if (httpMethodGet != null ) {
            return httpMethodGet;
        } else {
            return new HttpMethodGet();
        }
    }

    public Observable<Bitmap> getCheckCode() throws IOException {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
                    @Override
                    public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                        e.onNext(JwGetter.newInstance().getCodeBitmap());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Document> getExamPlanDoc(final String account, final String password, final String code) {
        return Observable.create(new ObservableOnSubscribe<Document>() {
                    @Override
                    public void subscribe(ObservableEmitter<Document> e) throws Exception {
                        e.onNext(JwGetter.newInstance().getExamPlanDoc(account, password, code));
                        e.onComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
