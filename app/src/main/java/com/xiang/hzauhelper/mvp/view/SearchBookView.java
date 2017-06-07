package com.xiang.hzauhelper.mvp.view;

import android.support.v7.widget.RecyclerView;

import com.xiang.hzauhelper.entities.Book;

import java.util.List;

/**
 * Created by xiang on 2017/5/29.
 *
 */

public interface SearchBookView extends BaseView {
    void initBookList(RecyclerView.Adapter adapter);
}
