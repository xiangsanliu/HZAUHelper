package com.xiang.hzauhelper.mvp.ui.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.mvp.view.SearchBookView;

import butterknife.BindView;

public class SearchBookActivity extends BaseActivity implements SearchBookView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.book_list)
    RecyclerView bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_book;
    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_book, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void dismissProgress(ProgressDialog progressDialog) {

    }
}
