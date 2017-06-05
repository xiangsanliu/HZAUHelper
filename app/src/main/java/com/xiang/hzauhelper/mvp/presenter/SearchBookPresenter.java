package com.xiang.hzauhelper.mvp.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.xiang.hzauhelper.entities.Book;
import com.xiang.hzauhelper.mvp.view.AccountSettingView;
import com.xiang.hzauhelper.mvp.view.SearchBookView;
import com.xiang.hzauhelper.network.DocumentGetter;
import com.xiang.hzauhelper.network.HttpMethodGet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiang on 2017/5/29.
 *
 */

public class SearchBookPresenter extends BasePresenter<SearchBookView> {

    private HttpMethodGet httpMethodGet;
    private ProgressDialog progressDialog;
    private Activity activity;
    private List<Book> bookList;
    public SearchBookPresenter(Activity activity) {
        httpMethodGet = HttpMethodGet.newInstance(new DocumentGetter());
        this.activity = activity;
    }

    @Override
    public void onCreate() {
        createProgressDialog();
        bookList = new ArrayList<>();
    }



    public void setBookList(String bookName, int type) {
        httpMethodGet.getBookListDocument(bookName, type).subscribe(new Observer<Document>() {
            @Override
            public void onSubscribe(Disposable d) {
                view.showProgress(progressDialog);
            }

            @Override
            public void onNext(Document document) {
                solveDocument(document);
            }

            @Override
            public void onError(Throwable e) {
                showToast("请求数据失败，请重试");
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                view.dismissProgress(progressDialog);
            }
        });
    }


    private void createProgressDialog() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("获取考试安排中");
        progressDialog.create();
    }

    private void solveDocument(Document document) {
        Elements elements = document.getElementsByAttributeValue("class", "items");
        Log.d("size", elements.size()+"");
        for (int i=0; i<elements.size(); i++) {
            String bookName = elements.get(i).getElementsByClass("itemTitle").text();
            Log.d("size", elements.get(i).getElementsByAttribute("onmouseout").text());
            Log.d("bookName", bookName);
            Elements contentElements =  elements.get(i).getElementsByClass("content");
            for (int j=0; j<contentElements.size(); j++) {
                Log.d(j+"", contentElements.get(j).text());
            }
            Book book = new Book();

        }
    }

}

