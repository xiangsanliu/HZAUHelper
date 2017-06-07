package com.xiang.hzauhelper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.entities.BookHistory;

import java.util.List;

/**
 * Created by xiang on 2017/6/7.
 */

public class BookHistoryAdapter extends BaseQuickAdapter<BookHistory, BaseViewHolder> {

    public BookHistoryAdapter(@Nullable List<BookHistory> data) {
        super(R.layout.item_book, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookHistory item) {

    }
}
