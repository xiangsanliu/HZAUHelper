package com.xiang.hzauhelper.mvp.model;

import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiang on 2017/7/17.
 *
 */

public class PEGetter {
    
    private final String HOST = "211.69.129.116:8501";
    private String cookie;
    private static PEGetter pEGetter;

    private PEGetter () {

    }

    public static PEGetter newInstance() {
        if (pEGetter == null) {
            return new PEGetter();
        } else {
            return pEGetter;
        }
    }

    private void setPeCookie(String account, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", account)
                .add("password", password)
                .add("btnlogin.x", "40")
                .add("btnlogin.y", "22")
                .build();
        String loginUrl = "http://211.69.129.116:8501/login.do";
        Request request = new Request.Builder()
                .url(loginUrl)
                .post(body)
                .addHeader("Accept","text/html,application" +
                        "/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Connection","keep-alive")
                .addHeader("Host", HOST)
                .addHeader("Referer", loginUrl)
                .build();
        Response response = client.newCall(request).execute();
        cookie = response.header("Set-Cookie");
        cookie = cookie.substring(0, cookie.indexOf(';'));
    }

    public String getSouthLake(String account, String password) throws IOException {
        if (cookie == null) {
            setPeCookie(account, password);
        }
        String result = "";
        String southlakeUrl = "http://211.69.129.116:8501/stu/StudentSportModify.do";
        Document document = Jsoup.connect(southlakeUrl)
                .header("Referer",HOST + "/jsp/menuForwardContent.jsp?url=stu/StudentSportModify.do")
                .header("Host",HOST)
                .header("Cookie",cookie)
                .get();
        Elements elements = document.getElementsByClass("fd");
        Log.d("document", document.text());
        if (elements.size() != 0 ) {
            result = result + "学号： " + elements.get(1).text() + "\n";
            result = result + "姓名：" + elements.get(2).text() + "\n";
            result = result + "已完成圈数：" + elements.get(7).text() + "\n";
        } else {
            result = "fail";
        }
        return result;
    }

    public String getPEScore(String account, String password) throws IOException {
        if (cookie == null) {
            setPeCookie(account, password);
        }
        String result = "";
        String scoreUrl = "http://211.69.129.116:8501/stu/viewresult.do";
        Document document = Jsoup.connect(scoreUrl)
                .header("Cookie", cookie)
                .header("Host", HOST)
                .header("Referer", "http://211.69.129.116:8501/jsp/me" +
                        "nuForwardContent.jsp?url=stu/viewresult.do").get();
        Elements elements = document.getElementsByClass("fd");
        if (elements.size() != 0) {
            result = result + "姓名     :\t" + elements.get(0).text() + "\n"
                    + "学号     :\t" + elements.get(2).text() + "\n"
                    + "班级     :\t" + elements.get(6).text() + "\n"
                    + "教师     :\t" + elements.get(7).text() + "\n"
                    + "成绩     :\t" + elements.get(9).text() + "\n";
        } else  {
            result = "fail";
        }
        return  result;
    }

}
