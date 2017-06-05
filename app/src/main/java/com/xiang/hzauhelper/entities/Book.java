package com.xiang.hzauhelper.entities;

/**
 * Created by xiang on 17-6-5.
 *
 */

public class Book {

    private String name;
    private String publisher;
    private String askNumber;
    private String year;
    private String coverUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAskNumber() {
        return askNumber;
    }

    public void setAskNumber(String askNumber) {
        this.askNumber = askNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
