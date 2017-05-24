package com.xiang.hzauhelper.entities;

import org.litepal.crud.DataSupport;

/**
 * Created by xiang on 2017/3/27.
 *
 */

public class ExamTerm extends DataSupport {
    public String courseNum;
    public String courseName;
    public String stuName;
    public String examTime;
    public String examPlace;
    public String examType;
    public String seatNum;
    public String schooReg;

    private void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    private void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    private void setStuName(String stuName) {
        this.stuName = stuName;
    }

    private void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    private void setExamPlace(String examPlace) {
        this.examPlace = examPlace;
    }

    private void setExamType(String examType) {
        this.examType = examType;
    }

    private void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    private void setSchooReg(String schooReg) {
        this.schooReg = schooReg;
    }

    public void setAttribute(String s, int i) {
        switch (i) {
            case 0:
                setCourseNum(s);
                break;
            case 1:
                setCourseName(s);
                break;
            case 2:
                setStuName(s);
                break;
            case 3:
                setExamTime(s);
                break;
            case 4:
                setExamPlace(s);
                break;
            case 5:
                setExamType(s);
                break;
            case 6:
                setSeatNum(s);
                break;
            case 7:
                setSchooReg(s);
                break;
        }
    }

}
