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
    private String nextPageUrl;
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
        progressDialog.setMessage("请稍候");
        progressDialog.create();
    }

    private void solveDocument(Document document) {
        Elements elements = document.getElementsByAttributeValue("class", "items");
        for (int i=0; i<elements.size(); i++) {
            String bookName = elements.get(i).getElementsByClass("itemTitle").text();
            String status = elements.get(i).getElementsByAttribute("onmouseout").text();
            String author, askNumber, publisher, year, coverUrl;
            coverUrl = elements.get(i).getElementsByTag("img").get(0).attr("src");
            Elements contentElements =  elements.get(i).getElementsByClass("content");
            author = contentElements.get(0).text();
            askNumber = contentElements.get(1).text();
            publisher = contentElements.get(2).text();
            year = contentElements.get(3).text();
            bookList.add(new Book(bookName, publisher, askNumber, year, coverUrl, status, author));
        }
        nextPageUrl = document.getElementsByAttributeValue("id","nav").toString();
        nextPageUrl = nextPageUrl.substring(nextPageUrl.lastIndexOf(',')+2, nextPageUrl.lastIndexOf('\"')-1);
    }

}

