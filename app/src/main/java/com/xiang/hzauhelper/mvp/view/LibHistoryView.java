package com.xiang.hzauhelper.mvp.view;

import android.graphics.Bitmap;
import android.view.View;

import com.xiang.hzauhelper.adapter.BookHistoryAdapter;

import java.io.InputStream;

/**
 * Created by xiang on 2017/6/7.
 */

public interface LibHistoryView extends BaseView {
    public void loadCheckCode(Bitmap bitmap);
    public void hideSoftInput(View view);
    public void loadBookHistoryList(BookHistoryAdapter adapter);
}
