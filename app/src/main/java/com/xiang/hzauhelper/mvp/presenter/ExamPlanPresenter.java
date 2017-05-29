package com.xiang.hzauhelper.mvp.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;

import com.xiang.hzauhelper.adapter.ExamPlanListAdapter;
import com.xiang.hzauhelper.entities.ExamTerm;
import com.xiang.hzauhelper.mvp.model.AccountGetter;
import com.xiang.hzauhelper.mvp.view.ExamPlanView;
import com.xiang.hzauhelper.network.HttpMethodGet;
import com.xiang.hzauhelper.network.DocumentGetter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiang on 2017/3/25.
 *
 */

public class ExamPlanPresenter extends BasePresenter<ExamPlanView> {

    private HttpMethodGet httpMethodGet;
    private String account, passwordJw;
    private Activity activity;
    private ProgressDialog progressDialog;

    public ExamPlanPresenter(Activity activity) {
        this.activity = activity;
        httpMethodGet = HttpMethodGet.newInstance(new DocumentGetter());
        createProgressDialog();
        initAccount();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initExamPlanList();
    }

    private void loadTerms() {
        view.loadTerms();
    }

    private void loadYears() {
        view.loadYear();
    }

    public void setExamPlanDoc(String code) {
        httpMethodGet.getExamPlanDoc(account, passwordJw, code).subscribe(new Observer<Document>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Document document) {
                deleteAll();
                solveExamPlanDoc(document);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.dismissProgress(progressDialog);
                showToast("获取失败，请重试");
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void solveExamPlanDoc(Document document) {
        List<ExamTerm> examTermList = new ArrayList<>();
//        Elements terms = document.getElementsByTag("option");
//        for (int i=1; i<terms.size()-3; i++) {
//            Log.d("xuenian", terms.get(i).text());
//        }
        Elements examTerms = document.getElementsByTag("table").get(0).getElementsByTag("tr");
        for (int i=1; i<examTerms.size(); i++) {
            Elements elements = examTerms.get(i).getElementsByTag("td");
            ExamTerm examTerm = new ExamTerm();
            for (int j=0; j<elements.size(); j++) {
                examTerm.setAttribute(elements.get(j).text(), j);
            }
            examTerm.save();
            examTermList.add(examTerm);
        }
        loadExamPlanList(examTermList);
        dismissProgress();
    }

    private void loadExamPlanList(List<ExamTerm> examTermList){
        ExamPlanListAdapter adapter = new ExamPlanListAdapter(activity, examTermList);
        view.loadExamPlanList(adapter);
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("获取考试安排中");
        progressDialog.create();
    }

    private void initAccount() {
        AccountGetter accountGetter = AccountGetter.newInstance(activity);
        account = accountGetter.getAccount();
        passwordJw = accountGetter.getPasswordJw();
    }

    public void showCheckCodeInputer() throws IOException {
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

    private void initExamPlanList() {
        List<ExamTerm> examTermList = DataSupport.findAll(ExamTerm.class);
        loadExamPlanList(examTermList);
    }

    private void deleteAll() {
        DataSupport.deleteAll(ExamTerm.class);
    }

    @Override
    public void dismissProgress() {
        view.dismissProgress(progressDialog);
    }
}
