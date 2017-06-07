package com.xiang.hzauhelper.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.xiang.hzauhelper.adapter.BookHistoryAdapter;
import com.xiang.hzauhelper.entities.BookHistory;
import com.xiang.hzauhelper.mvp.model.AccountGetter;
import com.xiang.hzauhelper.mvp.view.LibHistoryView;
import com.xiang.hzauhelper.network.DocumentGetter;
import com.xiang.hzauhelper.network.HttpMethodGet;

import org.jsoup.nodes.Document;
import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiang on 2017/6/7.
 *
 */

public class LibHistoryPresenter extends BasePresenter<LibHistoryView> {

    private Context context;
    private HttpMethodGet httpMethodGet;
    private String account, passwordLib;
    private ProgressDialog progressDialog;

    public LibHistoryPresenter(Context context) {
        this.context = context;
        httpMethodGet = HttpMethodGet.newInstance(new DocumentGetter());
        createProgressDialog();
        initAccount();
    }

    private void initAccount() {
        AccountGetter accountGetter = AccountGetter.newInstance(context);
        account = accountGetter.getAccount();
        passwordLib = accountGetter.getPasswrodLib();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBookHistoryList();
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("请稍候");
        progressDialog.create();
    }

    private void initBookHistoryList() {
//        List<BookHistory> bookHistoryList = DataSupport.findAll(BookHistory.class);
//        loadBookHistory(bookHistoryList);
    }

    private void loadBookHistory(List<BookHistory> bookHistoryList) {
        BookHistoryAdapter adapter = new BookHistoryAdapter(bookHistoryList);
        view.loadBookHistoryList(adapter);
    }

    public void showCheckCodeInputer() {
        view.showProgress(progressDialog);
        httpMethodGet.getLibCheckCode().subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                view.loadCheckCode(bitmap);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                view.dismissProgress(progressDialog);
            }
        });
    }

    public void setBookHistoryDoc(String code) {
        httpMethodGet.getMyLibDoc(account, passwordLib, code).subscribe(new Observer<Document>() {
            @Override
            public void onSubscribe(Disposable d) {
                view.showProgress(progressDialog);
            }

            @Override
            public void onNext(Document document) {
//                DataSupport.deleteAll(BookHistory.class);
                solveDoc(document);
                view.dismissProgress(progressDialog);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void solveDoc(Document document) {
        Log.d("document", document.text());
    }
}
