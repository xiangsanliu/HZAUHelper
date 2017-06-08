package com.xiang.hzauhelper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.entities.BookHistory;
import com.xiang.hzauhelper.mvp.presenter.LibHistoryPresenter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xiang on 2017/6/7.
 *
 */

public class BookHistoryAdapter extends BaseQuickAdapter<BookHistory, BaseViewHolder> {

    private LibHistoryPresenter presenter;

    public BookHistoryAdapter(@Nullable List<BookHistory> data, LibHistoryPresenter presenter) {
        super(R.layout.item_book_history, data);
        this.presenter = presenter;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, final BookHistory item) {

        String fine = item.getFine();
        String leftDays = "";
        TextView textView = viewHolder.getView(R.id.book_fine);
        if (fine.length()>0) {
            fine = "未缴罚款：" + fine;
            textView.setText(fine);
        } else {
            textView.setVisibility(View.GONE);
        }

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date d2 = format.parse(item.getReturnTime().substring(5));
            Date d1 = format.parse(item.getBorrowTime().substring(5));
            leftDays = (int) ((d2.getTime() - d1.getTime()) / (1000*3600*24)) +"天";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String returnTime = item.getReturnTime();
        if (item.getContinueUrl().equals("已还")) {
            leftDays = "已归还";
        }

        viewHolder.setText(R.id.book_name, item.getName())
                .setText(R.id.book_author, "作者："+item.getAuthor())
                .setText(R.id.borrow_time, item.getBorrowTime())
                .setText(R.id.book_return_time, returnTime)
                .setText(R.id.book_left_days, leftDays);
        CardView cardView = viewHolder.getView(R.id.continue_book);
        if (item.getContinueUrl().length() < 10 ) {
            cardView.setVisibility(View.GONE);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.continuedBook(item.getContinueUrl());
            }
        });

    }
}
