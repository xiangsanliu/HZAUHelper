package com.xiang.hzauhelper.mvp.presenter;

import android.util.Log;

import com.xiang.hzauhelper.entities.JwUrls;
import com.xiang.hzauhelper.mvp.view.ExamPlanView;
import com.xiang.hzauhelper.network.HttpMethodGet;

import org.jsoup.nodes.Document;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiang on 2017/3/25.
 */

public class ExamPlanPresenter extends BasePresenter<ExamPlanView> {

    private Document doc;
    private HttpMethodGet httpMethodGet;
    private String account, passwordJw, checkCode;

    public ExamPlanPresenter(String account, String passwordJw, String checkCode) {
        this.account = account;
        this.passwordJw = passwordJw;
        this.checkCode = checkCode;
        httpMethodGet = HttpMethodGet.newInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setExamPlanDoc(account, passwordJw, checkCode);
        Log.d("ExamPlanPresenter", "onCreate");
    }

    private void loadTerms() {
        view.loadTerms();
    }

    private void loadYears() {
        view.loadYear();
    }

    private void setExamPlanDoc(String account, String password, String code) {
        httpMethodGet.getExamPlanDoc(account, password, code).subscribe(new Observer<Document>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("ExamPlanPresenter", "onSubscribe");
            }

            @Override
            public void onNext(Document document) {
                doc = document;
                Log.d("ExamPlanPresenter", document.text());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("ExamPlanPresenter", "OnComplete");
            }
        });
    }

}
