package com.xiang.hzauhelper.entities;

/**
 * Created by xiang on 2017/3/26.
 */

public class JwUrls {
    public String examPlanUrl;
    public String courseUrl;
    public String cookie;

    public JwUrls(String examPlanUrl, String cookie) {
        this.examPlanUrl = examPlanUrl;
        this.cookie = cookie;
    }

    public JwUrls(String examPlanUrl, String courseUrl, String cookie) {
        this.examPlanUrl = examPlanUrl;
        this.courseUrl = courseUrl;
        this.cookie = cookie;
    }
}
