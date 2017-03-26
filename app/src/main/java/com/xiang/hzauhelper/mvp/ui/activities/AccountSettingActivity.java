package com.xiang.hzauhelper.mvp.ui.activities;

import android.os.Bundle;
import android.widget.EditText;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.mvp.presenter.AccountSettingPresenter;
import com.xiang.hzauhelper.mvp.view.AccountSettingView;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.OnClick;

public class AccountSettingActivity extends BaseActivity implements AccountSettingView {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password_jw)
    EditText passwordJw;
    @BindView(R.id.password_lib)
    EditText passwordLib;
    @BindView(R.id.password_pe)
    EditText passwordPe;
    @BindView(R.id.save)
    CircleButton save;

    AccountSettingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AccountSettingPresenter(this);
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_setting;
    }

    @OnClick(R.id.save)
    public void onClick() {
        presenter.setAccount(account.getText().toString(), passwordJw.getText().toString(),
                passwordLib.getText().toString(), passwordPe.getText().toString());
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void loadAccount(String accountText) {
        account.setText(accountText);
    }

    @Override
    public void loadPasswordJw(String passwordJwText) {
        passwordJw.setText(passwordJwText);
    }

    @Override
    public void loadPasswordLib(String passwordLibText) {
        passwordLib.setText(passwordLibText);
    }

    @Override
    public void loadPasswordPe(String passwordPeText) {
        passwordPe.setText(passwordPeText);
    }
}
