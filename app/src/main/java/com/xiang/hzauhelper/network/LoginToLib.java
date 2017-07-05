package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.xiang.hzauhelper.entities.Book;
import com.xiang.hzauhelper.entities.BookHistory;
import com.xiang.hzauhelper.mvp.presenter.LibHistoryPresenter;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiang on 2017/6/7.
 *
 */
class LoginToLib {
    LoginToLib() {
        bookHistories = new ArrayList<>();
    }

    private final String LOGIN_URL = "https://sso.hzau.edu.cn:7002/cas/login";
    private final String HOST_URL = "http://218.199.76.6:8991/F/";
    private final String CHECK_CODE_URL = "https://sso.hzau.edu.cn:7002/cas/captcha.htm";
    private String unReturnedUrl, returnedUrl;
    private String s;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Request request;
    private Response response;
    private String cookie;
    private String lt;
    private String execution;
    private String _eventId;
    private List<BookHistory> bookHistories;

    List<BookHistory> getDocument(String userName, String password
            , String checkCode) throws Exception {
        String url = cookie.substring(10, cookie.indexOf(';'));
        url = LOGIN_URL + ';'+ "jsessionid" +url + s;
        FormBody formBody = new FormBody.Builder()
                .add("lt", lt)
                .add("execution", execution)
                .add("_eventId", _eventId)
                .add("username", userName)
                .add("password", password)
                .add("authCode", checkCode)
                .build();
        request = new Request.Builder()
                .post(formBody)
                .url(url)
                .addHeader("cookie", cookie)
                .addHeader("Referer", LOGIN_URL + s)
                .build();
        response = okHttpClient.newCall(request).execute();
        cookie = response.header("Set-Cookie");
        String psd_handle = response.body().string();
        Document document = Jsoup.parse(psd_handle);
        url = document.getElementsByTag("a").get(0).attr("href");
        url = url.substring(12);
        psd_handle = psd_handle.substring(psd_handle.indexOf("pds_handle="), psd_handle.indexOf("&calling"));

        request = new Request.Builder()
                .url(url)
//                .addHeader("Cookie", cookie)
//                .addHeader("Cookie", psd_handle)
                .build();
        response = okHttpClient.newCall(request).execute();

        document = Jsoup.parse(response.body().string());
        Elements elements = document.getElementsByTag("a");
        url = elements.get(2).attr("href");

        request = new Request.Builder()
                .url(url)
                .build();
        response = okHttpClient.newCall(request).execute();
        document = Jsoup.parse(response.body().string());

        elements = document.getElementsByClass("indent1").get(0).getElementsByTag("a");
        unReturnedUrl = elements.get(0).attr("href");
        returnedUrl = elements.get(1).attr("href");

        unReturnedUrl = unReturnedUrl.substring(24, unReturnedUrl.length()-3);
        returnedUrl = returnedUrl.substring(24, returnedUrl.length()-3);

        return getBookHistories();
    }

    Bitmap getCheckCodeImage() throws IOException {     //设置验证码，并保证验证码的cookie和post的cookie是一样的。
        setCookie();
        request = new Request.Builder()
                .addHeader("Cookie", cookie)
                .url(CHECK_CODE_URL)
                .build();
        response = okHttpClient.newCall(request).execute();
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

    private void setCookie() throws IOException {
        String url ;
        Request request = new Request.Builder()
                .url(HOST_URL)
                .build();
        okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        String content = response.body().string();
        content = content.substring(content.lastIndexOf("http"));
        url = content.substring(0, content.indexOf('\''));
        request = new Request.Builder()
                .url(url)
                .build();
        response = okHttpClient.newCall(request).execute();
        Document document = Jsoup.parse(response.body().string());
        String referer = document.getElementsByTag("a").get(0).attr("href");
        url = referer.substring(url.lastIndexOf("http"));
        String s1 = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('-'));
        String s2 = url.substring(url.lastIndexOf('-') + 1, url.lastIndexOf('?'));
        s= "?service=http%3a%2f%2f218%2e199%2e76%2e6%3a8991%2fcas%2fpds%5fmain%3ffunc%3dload%2dl" +
                "ogin%26calling%5fsystem%3daleph%26institute%3dHZA50%26PDS%5fHANDLE%3d%26url%3dhttp%3a%2f%2f" +
                "218%2e199%2e76%2e6%3a8991%2fF%2f"+ s1 +"%2d"+ s1 +"%3ffunc%3d%26filer%3d";

        //到这里为止，就抓取到了登陆的URL地址，并且获取到了Cookie，和一些关键数据
        request = new Request.Builder()
                .url(LOGIN_URL+s)
                .build();
        response = okHttpClient.newCall(request).execute();
        document = Jsoup.parse(response.body().string());
        Elements elements = document.getElementsByAttributeValue("type", "hidden");
        lt = elements.get(0).attr("value");
        execution = elements.get(1).attr("value");
        _eventId = elements.get(2).attr("value");
        cookie =  response.header("Set-Cookie");
    }

    String continuedBook(String url) throws IOException {
        request = new Request.Builder()
                .url(url)
                .build();
        response = okHttpClient.newCall(request).execute();
        return response.code()+"";

    }

    List<BookHistory> getBookHistories() throws IOException {   //登陆成功，并返回数据
        Document document;
        Elements elements;
        request = new Request.Builder()
                .url(unReturnedUrl)
                .build();
        response = okHttpClient.newCall(request).execute();
        document = Jsoup.parse(response.body().string());
        elements = document.getElementsByClass("td1");
        elements.remove(0);
        for (int i=0; i<elements.size(); i+=11) {
            String bookName = elements.get(i+3).text();
            String author = elements.get(i+2).text();
            String year = elements.get(i+4).text();
            String continueUrl = elements.get(i).getAllElements().attr("href");
            request = new Request.Builder()
                    .url(continueUrl)
                    .build();
            response = okHttpClient.newCall(request).execute();
            document = Jsoup.parse(response.body().string());
            elements = document.getElementsByClass("td1");
            String borrowTime = "借出日期：" + elements.get(1).text();
            continueUrl = elements.get(5).getAllElements().attr("HREF");
            String fine = elements.get(7).text();
            String returnTime = "应还日期：" + elements.get(3).text().substring(0, 8);
            bookHistories.add(new BookHistory(bookName, year, author, borrowTime
                    , continueUrl, fine, returnTime));
        }

        request = new Request.Builder()
                .url(returnedUrl)
                .build();
        response = okHttpClient.newCall(request).execute();
        document = Jsoup.parse(response.body().string());
        elements = document.getElementsByClass("td1");
        elements.remove(0);
        for (int i=0; i<elements.size(); i+=10) {
            String bookName = elements.get(i+2).text();
            String author = elements.get(i+1).text();
            String year = elements.get(i+3).text();
            String borrowTime = "应还日期：" + elements.get(i+4).text();
            String retrunTime = "归还日期：" + elements.get(i+6).text();
            String fine = elements.get(i+8).text();
            bookHistories.add(new BookHistory(bookName, year, author, retrunTime, fine, borrowTime));
        }
        return bookHistories;
    }

}
