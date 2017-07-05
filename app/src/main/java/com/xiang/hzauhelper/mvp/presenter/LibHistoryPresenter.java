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
    private BookHistoryAdapter bookHistoryAdapter;
    private List<BookHistory> bookHistories;

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
        bookHistories = DataSupport.findAll(BookHistory.class);
        bookHistoryAdapter = new BookHistoryAdapter(bookHistories, this);
        view.loadBookHistoryList(bookHistoryAdapter);
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
        httpMethodGet.getBookHistory(account, passwordLib, code).subscribe(new Observer<List<BookHistory>>() {
            @Override
            public void onSubscribe(Disposable d) {
                view.showProgress(progressDialog);
            }

            @Override
            public void onNext(List<BookHistory> bookHistoryList) {
                bookHistoryList.clear();
                for (BookHistory bookHistory: bookHistoryList) {
                    bookHistory.save();
                    bookHistories.add(bookHistory);
                }
                bookHistoryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable e) {
                view.dismissProgress(progressDialog);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                view.dismissProgress(progressDialog);
            }
        });
    }

    public void continuedBook(String url) {
        httpMethodGet.continueBook(url).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                view.showProgress(progressDialog);
            }

            @Override
            public void onNext(String s) {
                Log.d("code", s);
                if (s.equals("200")) {
                    showToast("续借成功, 请刷新");
//                    refreshBookHistory();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showProgress(progressDialog);
            }

            @Override
            public void onComplete() {
                view.showProgress(progressDialog);
            }
        });
    }

    private void refreshBookHistory() {
        httpMethodGet.refreshtBookHistory().subscribe(new Observer<List<BookHistory>>() {
            @Override
            public void onSubscribe(Disposable d) {
                view.showProgress(progressDialog);
            }

            @Override
            public void onNext(List<BookHistory> bookHistories) {
                view.loadBookHistoryList(new BookHistoryAdapter(bookHistories, LibHistoryPresenter.this));
            }

            @Override
            public void onError(Throwable e) {
                view.dismissProgress(progressDialog);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                view.dismissProgress(progressDialog);
            }
        });
    }


}
