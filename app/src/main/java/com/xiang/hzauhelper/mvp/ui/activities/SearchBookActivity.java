package com.xiang.hzauhelper.mvp.ui.activities;

import android.app.ProgressDialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.entities.Book;
import com.xiang.hzauhelper.mvp.presenter.SearchBookPresenter;
import com.xiang.hzauhelper.mvp.view.SearchBookView;
import com.xiang.hzauhelper.network.RequestCodes;

import java.util.List;

import butterknife.BindView;

public class SearchBookActivity extends BaseActivity implements SearchBookView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.book_list)
    RecyclerView bookList;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    SearchBookPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchBookPresenter(this);
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.setBookList(query, RequestCodes.LIB_CHINESE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_book;
    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {
        progressDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_book, menu);
        MenuItem item  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void dismissProgress(ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }

    @Override
    public void initBookList(RecyclerView.Adapter adapter) {
        bookList.setLayoutManager(new LinearLayoutManager(this));
        bookList.setAdapter(adapter);
    }
}
