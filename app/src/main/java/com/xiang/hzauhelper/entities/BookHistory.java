package com.xiang.hzauhelper.entities;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiang on 2017/6/7.
 *
 */

public class BookHistory extends DataSupport {
    private String name;
    private String year;
    private String author;
    private String returnTime;
    private String continueUrl;
    private String fine;
    private String borrowTime;

    @Override
    public String toString() {
        return "BookHistory{" +
                "name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", author='" + author + '\'' +
                ", returnTime='" + returnTime + '\'' +
                ", continueUrl='" + continueUrl + '\'' +
                ", fine='" + fine + '\'' +
                ", borrowTime='" + borrowTime + '\'' +
                '}';
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public BookHistory(String name, String year, String author, String returnTime, String fine, String borrowTime) {
        this.name = name;
        this.year = year;
        this.author = author;
        this.returnTime = returnTime;
        this.fine = fine;
        this.borrowTime = borrowTime;
        this.continueUrl = "已还";
    }

    public BookHistory(String name, String year, String author, String url) throws IOException {
        this.name = name;
        this.year = year;
        this.author = author;
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        Document document = Jsoup.parse(response.body().string());
        Elements elements = document.getElementsByClass("td1");
        borrowTime = "借出日期：" + elements.get(1).text();
        continueUrl = elements.get(5).getAllElements().attr("HREF");
        fine = elements.get(7).text();
        returnTime = "应还日期：" + elements.get(3).text().substring(0, 8);
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getContinueUrl() {
        return continueUrl;
    }

    public void setContinueUrl(String continueUrl) {
        this.continueUrl = continueUrl;
    }

}
