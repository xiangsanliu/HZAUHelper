package com.xiang.hzauhelper.mvp.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.util.Log;

import com.xiang.hzauhelper.mvp.model.AccountGetter;
import com.xiang.hzauhelper.mvp.view.EmptyRoomView;
import com.xiang.hzauhelper.network.HttpMethodGet;
import com.xiang.hzauhelper.network.DocumentGetter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiang on 2017/5/24.
 *
 */

public class EmptyRoomPresenter extends BasePresenter<EmptyRoomView> {

    private String account, passwordJw, __VIEWSTATE, xn, xq;
    private HttpMethodGet httpMethodGet;
    private ProgressDialog progressDialog;
    private Activity activity;
    private String [] dateValue, lessonNumValue;

    public EmptyRoomPresenter(Activity activity) {
        this.activity = activity;
        createProgressDialog();
        initAccount();
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("请稍候");
        progressDialog.create();
    }

    private void initAccount() {
        AccountGetter accountGetter = AccountGetter.newInstance(activity);
        account = accountGetter.getAccount();
        passwordJw = accountGetter.getPasswordJw();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        httpMethodGet = HttpMethodGet.newInstance(new DocumentGetter());
        try {
            showCheckCodeInputer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCheckCodeInputer() throws IOException {
        view.showProgress(progressDialog);
        httpMethodGet.getCheckCode().subscribe(new Observer<Bitmap>() {
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

            }
        });
    }

    public void setSpinner(String checkCode) {
        showProgress();
        httpMethodGet.getEmptyRoomDoc( account, passwordJw, checkCode).subscribe(new Observer<Document>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Document document) {
                solveSpinnerDoc(document);
                dismissProgress();
            }

            @Override
            public void onError(Throwable e) {
                showToast("请重试");
                activity.finish();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void dismissProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    private void solveSpinnerDoc(Document document) {
        Elements elements = null;
        elements = document.getElementsByAttributeValue("type", "hidden");
        __VIEWSTATE = elements.get(2).attr("value");
        elements = document.getElementsByAttributeValue("name", "kssj");
        elements = elements.get(0).getAllElements();
        String [] startDate = new String[elements.size()-1];
        dateValue = new String[elements.size()-1];
        for (int i=1; i<elements.size(); i++) {
            startDate[i-1] = elements.get(i).text();
            dateValue[i-1] = elements.get(i).attr("value");
        }
        elements = document.getElementsByAttributeValue("name", "sjd");
        elements = elements.get(0).getAllElements();
        String [] lessonNum = new String[elements.size()-1];
        lessonNumValue = new String[elements.size()-1];
        for (int i=1; i<elements.size(); i++) {
            lessonNum[i-1] = elements.get(i).text();
            lessonNumValue[i-1] = elements.get(i).attr("value");
        }
        elements = document.getElementsByAttributeValue("name", "xn");
        elements = elements.get(0).getElementsByAttributeValue("selected", "selected");
        xn = elements.text();
        elements = document.getElementsByAttributeValue("name", "xq");
        elements = elements.get(0).getElementsByAttributeValue("selected", "selected");
        xq = elements.text();
        view.initSpinner(startDate, lessonNum);
    }

    public void getEmptyRoom(int index1, int index2) {
        showProgress();
        httpMethodGet.getEmptyRoomDocument(dateValue[index1], lessonNumValue[index2]
                , __VIEWSTATE, xn, xq, account).subscribe(new Observer<Document>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Document document) {
                Elements elements = document.getElementsByAttributeValue("value", "空教室查询");
                Log.d("elements", elements.size()+"");
                Log.d("elements", elements.text());
                Log.d("elements", document.text());

                dismissProgress();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d("xxx", "xxxx");
                dismissProgress();
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
