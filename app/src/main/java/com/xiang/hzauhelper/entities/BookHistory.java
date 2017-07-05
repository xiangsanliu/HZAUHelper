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

    public BookHistory(String name, String year, String author, String borrowTime
            , String continueUrl, String fine, String returnTime) throws IOException {
        this.name = name;
        this.year = year;
        this.author = author;
        this.borrowTime = borrowTime;
        this.continueUrl = continueUrl;
        this.fine = fine;
        this.returnTime = returnTime;
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
