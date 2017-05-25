package com.xiang.hzauhelper.mvp.view;

import android.graphics.Bitmap;

/**
 * Created by xiang on 2017/5/24.
 *
 */

public interface EmptyRoomView extends BaseView {
    public void loadCheckCode(Bitmap bitmap);
    public void initSpinner(String[] startDate, String[] lessonNum);
}
