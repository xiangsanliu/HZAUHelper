package com.xiang.hzauhelper.mvp.view;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by xiang on 2017/3/23.
 *
 */

public interface MainView extends BaseView {
    public void loadBitmap(Bitmap bitmap);
    public void loadAccountTv(String account);
    public void hideSoftInput(View view);
}
