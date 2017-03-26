package com.xiang.hzauhelper.mvp.view;

/**
 * Created by xiang on 2017/3/23.
 *
 */

public interface AccountSettingView extends BaseView {

    public void loadAccount(String account);
    public void loadPasswordJw(String passwordJw);
    public void loadPasswordLib(String passwordLib);
    public void loadPasswordPe(String passwordPe);

}
