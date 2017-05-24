package com.xiang.hzauhelper.mvp.view;

import android.graphics.Bitmap;
import android.view.View;

import com.xiang.hzauhelper.adapter.ExamPlanListAdapter;

/**
 * Created by xiang on 2017/3/25.
 *
 */

public interface ExamPlanView extends BaseView {
    public void loadTerms();
    public void loadYear();
    public void loadCheckCode(Bitmap bitmap);
    public void hideSoftInput(View view);
    public void loadExamPlanList(ExamPlanListAdapter adapter);
}
