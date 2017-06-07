package com.xiang.hzauhelper.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.entities.Book;

import java.util.List;

/**
 * Created by xiang on 17-6-5.
 *
 */

public class BookListAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {

    private Context context;

    public BookListAdapter(@Nullable List<Book> data, Context context) {
        super(R.layout.item_book, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, Book item) {
        viewHolder.setText(R.id.book_name, item.getName())
                .setText(R.id.book_author, item.getAuthor())
                .setText(R.id.book_publisher, item.getPublisher())
                .setText(R.id.book_ask_num, item.getAskNumber())
                .setText(R.id.book_status, item.getStatus());
        ImageView coverImageView = viewHolder.getView(R.id.book_cover);
        Glide.with(context).load(item.getCoverUrl()).into(coverImageView);
    }
}
