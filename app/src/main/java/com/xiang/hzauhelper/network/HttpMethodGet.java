package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;

import com.xiang.hzauhelper.entities.BookHistory;

import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;

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
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public Observable<Document> getNextBookListDocument(final String nextPageUrl) {
        return Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
                e.onNext(documentGetter.getNextBookListDocument(nextPageUrl));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }


    public Observable<List<BookHistory>> getBookHistory(final String userName, final String password, final String checkCode) {
        return Observable.create(new ObservableOnSubscribe<List<BookHistory>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookHistory>> e) throws Exception {
                e.onNext(documentGetter.getBookHistoryDoc(userName, password, checkCode));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public Observable<Bitmap> getLibCheckCode() {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                e.onNext(documentGetter.getLibCheckCode());
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public Observable<String> continueBook(final String url) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(documentGetter.continuedBook(url));
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public Observable<List<BookHistory>> refreshtBookHistory() {
        return Observable.create(new ObservableOnSubscribe<List<BookHistory>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookHistory>> e) throws Exception {
                e.onNext(documentGetter.refreshBookHistory());
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

}
