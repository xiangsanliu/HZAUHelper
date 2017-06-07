package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

/**
 * Created by xiang on 2017/6/7.
 *
 */
public class LoginToLib {

    private final String LOGIN_URL = "https://sso.hzau.edu.cn:7002/cas/login";
    private final String HOST_URL = "http://218.199.76.6:8991/F/";
    private final String CHECK_CODE_URL = "https://sso.hzau.edu.cn:7002/cas/captcha.htm";
    private String s;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Request request;
    private Response response;
    private String cookie;
    private String lt;
    private String execution;
    private String _eventId;

    Document getDocument(String userName, String password
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
        return Jsoup.parse(response.body().string());
    }

    Bitmap getCheckCodeImage() throws IOException {
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

}
