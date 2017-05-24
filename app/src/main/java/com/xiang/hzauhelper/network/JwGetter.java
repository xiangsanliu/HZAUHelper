package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiang on 2017/3/25.
 *
 */

public class JwGetter {

    private OkHttpClient okHttpClient;
    private String cookie;

    private final String MAIN_URL = "http://jw.hzau.edu.cn/";
//    private String examPlanUrl;
//    private String courseTableUrl;

    private String getViewState() throws IOException {
        Document document = Jsoup.connect(MAIN_URL).get();
        return document.getElementsByTag("input").attr("value");
    }

    Bitmap getCodeBitmap() throws IOException {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        String GET_CODE_URL = "http://jw.hzau.edu.cn/CheckCode.aspx";
        Request request = new Request.Builder().url(GET_CODE_URL).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        cookie =  response.header("Set-Cookie");
        cookie = cookie.substring(0, cookie.indexOf(';'));
        byte[] bytes = response.body().bytes();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String getExamPlanUrl(String account, String password, String code) throws IOException {
        FormBody body = new FormBody.Builder()
                .add("__VIEWSTATE", getViewState())
                .add("txtUserName", account)
                .add("TextBox2", password)
                .add("txtSecretCode", code)
                .add("RadioButtonList1", "学生")
                .add("Button1", "")
                .add("lbLanguage", "")
                .add("hidPdrs", "")
                .add("hidsc", "")
                .build();
        String POST_URL = "http://jw.hzau.edu.cn/default2.aspx";
        Request request = new Request.Builder()
                .url(POST_URL)
                .post(body)
                .addHeader("Cookie", cookie)
                .build();
        Response urlResponse = okHttpClient.newCall(request).execute();
        Document urlDocument = Jsoup.parse(urlResponse.body().string());
        return MAIN_URL
                + urlDocument.getElementsByAttributeValue("onclick","GetMc('学生考试查询');").get(0).attr("href");
    }

    private String getEmptyRoomUrl(String account, String password, String code) throws IOException {
        FormBody body = new FormBody.Builder()
                .add("__VIEWSTATE", getViewState())
                .add("txtUserName", account)
                .add("TextBox2", password)
                .add("txtSecretCode", code)
                .add("RadioButtonList1", "学生")
                .add("Button1", "")
                .add("lbLanguage", "")
                .add("hidPdrs", "")
                .add("hidsc", "")
                .build();
        String POST_URL = "http://jw.hzau.edu.cn/default2.aspx";
        Request request = new Request.Builder()
                .url(POST_URL)
                .post(body)
                .addHeader("Cookie", cookie)
                .build();
        Response urlResponse = okHttpClient.newCall(request).execute();
        Document urlDocument = Jsoup.parse(urlResponse.body().string());
        return MAIN_URL
                + urlDocument.getElementsByAttributeValue("onclick","GetMc('教室查询');").get(0).attr("href");
    }

    Document getExamPlanDoc(String account, String password, String code) throws IOException {
        String examPlanUrl = getExamPlanUrl(account, password, code);
        FormBody formBody = new FormBody.Builder()
                .add("xm", "�\uEF61��")
                .add("xh", account)
                .add("gnmkdm", examPlanUrl.substring(examPlanUrl.lastIndexOf('=')))
                .build();
        Request examPlanRequest = new Request.Builder()
                .url(examPlanUrl)
                .post(formBody)
                .addHeader("Cookie", cookie)
                .addHeader("Referer", "http://jw.hzau.edu.cn/xs_main.aspx?xh="+account)
                .build();
        Response examPlanResponse = okHttpClient.newCall(examPlanRequest).execute();
        return Jsoup.parse(examPlanResponse.body().string());
    }

    Document getEmptyRoomDoc(String account, String password, String code) throws IOException {
        String emptyRoomUrl = getEmptyRoomUrl(account, password, code);
        Request request1 = new Request.Builder()
                .url(emptyRoomUrl)
                .addHeader("Cookie", cookie)
                .addHeader("Referer", "http://jw.hzau.edu.cn/xs_main.aspx?xh="+account)
                .build();
        Response  response = okHttpClient.newCall(request1).execute();
//        Document document = Jsoup.parse(response.body().string());
//        Elements elements = null;
//        elements = document.getElementsByAttributeValue("type", "hidden");
//        String __EVENTTARGET = elements.get(0).attr("value");
//        String __EVENTARGUMENT = elements.get(0).attr("value");
//        String __VIEWSTATE = elements.get(0).attr("value");
//        elements = document.getElementsByAttributeValue("name", "kssj");
        return Jsoup.parse(response.body().string());
    }

//    private Document getCourseDocument(String account, String password, String code) throws IOException {
//        if (courseTableUrl == null) {
//            setUrl(account, password, code);
//        }
//        Request courseRequest = new Request.Builder()
//                .url(courseTableUrl)
//                .addHeader("Cookie", cookie)
//                .addHeader("Referer", "http://jw.hzau.edu.cn/xs_main.aspx?xh="+account)
//                .build();
//        Response courseResponse = okHttpClient.newCall(courseRequest).execute();
//        return Jsoup.parse(courseResponse.body().string());
//    }

}
