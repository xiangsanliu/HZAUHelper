package com.xiang.hzauhelper.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
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

public class DocumentGetter {

    private OkHttpClient okHttpClient;
    private String cookie;
    private LoginToLib loginToLib;

    public DocumentGetter() {
        loginToLib = new LoginToLib();
    }

    private final String MAIN_URL = "http://jw.hzau.edu.cn/";
    private String emptyRoomUrl;
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
        emptyRoomUrl = getEmptyRoomUrl(account, password, code);
        Request request = new Request.Builder()
                .url(emptyRoomUrl)
                .addHeader("Cookie", cookie)
                .addHeader("Referer", "http://jw.hzau.edu.cn/xs_main.aspx?xh="+account)
                .build();
        Response  response = okHttpClient.newCall(request).execute();
        return Jsoup.parse(response.body().string());
    }

    Document getEmptyRoomDocument(String date, String lessonNum, String __VIEWSTATE, String xn
            , String xq, String account) throws IOException {
        Log.d("date", date);
        Log.d("lessonNum", lessonNum);
        FormBody formBody = new FormBody.Builder()
//                .add("__EVENTTARGET", "dpDataGrid1:txtPageSize")
                .add("__ENENTTARGET", "sjd")
                .add("__EVENTARGUMENT", "")
                .add("__VIEWSTATE", __VIEWSTATE)
                .add("xiaoq", "本部")
                .add("jslb", "")
                .add("min_zws", "0")
                .add("max_zws", "")
                .add("kssj", date)
                .add("jssj", date)
                .add("xqj", date.indexOf(0)+"")
                .add("ddlDsz", "")
                .add("sjd", lessonNum)
                .add("Button2", "空教室查询")
//                .add("dpDataGrid1:txtPageSize", "400")
//                .add("dpDataGrid1:txtChoosePage", "1")
                .add("xn", xn)
                .add("xq", xq)
                .add("ddlSyXn", xn)
                .add("ddlSyxq", xq)
                .build();
        Request request = new Request.Builder()
                .url(emptyRoomUrl)
                .post(formBody)
                .addHeader("Host", "jw.hzau.edu.cn")
                .addHeader("Cookie", cookie)
                .addHeader("Referer", "http://jw.hzau.edu.cn/xs_main.aspx?xh="+account)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String content = response.body().string();
        return Jsoup.parse(content);
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
    Document getBookListDocument(String bookName, int type) throws IOException {

        String url = "http://218.199.76.6:8991/F/";
        Request request = new Request.Builder()
                .addHeader("Hosts", "218.199.76.6:8991")
                .url(url)
                .build();
        okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        String content = response.body().string();
        content = content.substring(content.lastIndexOf("http"));
        url = content.substring(0, content.indexOf('\''));
        Log.d("url", url);
        request = new Request.Builder()
                .url(url)
                .build();
        response = okHttpClient.newCall(request).execute();
        Document document = Jsoup.parse(response.body().string());
        Elements elements = document.getElementsByAttributeValue("name", "form1");
        url = elements.get(0).attr("action");
        Log.d("url", url);

        if (type == RequestCodes.LIB_CHINESE) {
            url = url + "?func=find-b&find_code=WRD&request="+bookName+"&local_base=HZA01&filter_code_1=" +
                    "WLN&filter_request_1=&filter_code_2=WYR&filter_request_2=&filter_code_3=WY" +
                    "R&filter_request_3=&filter_code_4=WFM&filter_request_4=&filter_code_5=WSL&" +
                    "filter_request_5=";
        } else {
            url = url + "func=find-b&find_code=WRD&request="+bookName+"&local_base=HZA09&filter_code" +
                    "_1=WLN&filter_request_1=&filter_code_2=WYR&filter_request_2=&filter_cod" +
                    "e_3=WYR&filter_request_3=&filter_code_4=WFM&filter_request_4=&filter_co" +
                    "de_5=WSL&filter_request_5=0";
        }
        request = new Request.Builder()
                .url(url)
                .build();
        response = okHttpClient.newCall(request).execute();
        return Jsoup.parse(response.body().string());
    }

    Document getNextBookListDocument(String nextPageUrl) throws IOException {
        Request request = new Request.Builder()
                .url(nextPageUrl)
                .build();
        okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        return Jsoup.parse(response.body().string());
    }

    Document login(String userName, String password, String checkCode) throws Exception {
        return loginToLib.getDocument(userName, password, checkCode);
    }

    Bitmap getLibCheckCode() throws IOException {
        return loginToLib.getCheckCodeImage();
    }

}
