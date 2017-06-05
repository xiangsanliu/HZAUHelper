package com.xiang.hzauhelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xiang.hzauhelper.entities.Book;

import java.util.List;

/**
 * Created by xiang on 17-6-5.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookHolder> {


    List<Book> bookList;
    Context context;

    public BookListAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookHolder extends RecyclerView.ViewHolder {

        public BookHolder(View itemView) {
            super(itemView);
        }
    }
}
