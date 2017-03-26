package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.xiang.hzauhelper.entities.JwUrls;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiang on 2017/3/25.
 *
 */

class JwGetter {

    private static JwGetter jwGetter;
    private OkHttpClient okHttpClient;
    private static String cookie;

    private static final String MAIN_URL = "http://jw.hzau.edu.cn/";
    private static final String POST_URL = "http://jw.hzau.edu.cn/default2.aspx";
    private static final String GET_CODE_URL = "http://jw.hzau.edu.cn/CheckCode.aspx";
    private static String examPlanUrl;
    private static String courseTableUrl;

    private JwGetter(){
        okHttpClient = new OkHttpClient();
    }

    static JwGetter newInstance() {
        if (jwGetter == null) {
            return new JwGetter();
        } else {
            return jwGetter;
        }
    }

    private String getViewState() throws IOException {
        Document document = Jsoup.connect(MAIN_URL).get();
        return document.getElementsByTag("input").attr("value");
    }

    Bitmap getCodeBitmap() throws IOException {
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(GET_CODE_URL).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        cookie =  response.header("Set-Cookie");
        cookie = cookie.substring(0, cookie.indexOf(';'));
        byte[] bytes = response.body().bytes();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void setUrl(String account, String password, String code) throws IOException {
        Log.d("JwGetter", getViewState());
        if (cookie != null ) {
            Log.d("Cookie", cookie);
        } else {
            Log.d("Cookie", "null");
        }
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
        Log.d("JwGetter", "HAO");
        Log.d("cookie", cookie);
        Request request = new Request.Builder()
                .url(POST_URL)
                .post(body)
                .addHeader("Cookie", cookie)
                .build();
        Response urlResponse = okHttpClient.newCall(request).execute();
        Document urlDocument = Jsoup.parse(urlResponse.body().string());
        Elements urlElements = urlDocument.getElementsByAttributeValue("target", "zhuti");
        examPlanUrl = MAIN_URL + urlElements.get(10).attr("href");
        courseTableUrl = MAIN_URL + urlElements.get(11).attr("href");
    }


    Document getExamPlanDoc(String account, String password, String code) throws IOException {
        if (examPlanUrl == null) {
            setUrl(account, password, code);
        }

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

    private Document getCourseDocument(String account, String password, String code) throws IOException {
        if (courseTableUrl == null) {
            setUrl(account, password, code);
        }
        Request courseRequest = new Request.Builder()
                .url(courseTableUrl)
                .addHeader("Cookie", cookie)
                .addHeader("Referer", "http://jw.hzau.edu.cn/xs_main.aspx?xh="+account)
                .build();
        Response courseResponse = okHttpClient.newCall(courseRequest).execute();
        return Jsoup.parse(courseResponse.body().string());
    }

}
