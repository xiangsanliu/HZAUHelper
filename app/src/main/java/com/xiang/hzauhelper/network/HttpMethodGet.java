package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;

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
    private DocumentGetter documentGetter;

    private HttpMethodGet(DocumentGetter documentGetter) {
        this.documentGetter = documentGetter;
    }

    public static HttpMethodGet newInstance(DocumentGetter documentGetter) {
        if (httpMethodGet != null ) {
            return httpMethodGet;
        } else {
            return new HttpMethodGet(documentGetter);
        }
    }

    public Observable<Bitmap> getCheckCode() throws IOException {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
                    @Override
                    public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                        e.onNext(documentGetter.getCodeBitmap());
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
                        e.onNext(documentGetter.getExamPlanDoc(account, password, code));
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
                e.onNext(documentGetter.getEmptyRoomDoc(account, password, code));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public Observable<Document> getEmptyRoomDocument(final String date, final String lessonNum
            , final String __VIEWSTATE, final String xn, final String xq, final String account) {
        return Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                e.onNext(documentGetter.getEmptyRoomDocument(date, lessonNum, __VIEWSTATE, xn, xq,account));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public Observable<Document> getBookListDocument(final String bookName, final int type) {
        return Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                e.onNext(documentGetter.getBookListDocument(bookName, type));
                e.onComplete();
            }
        });
    }

}
