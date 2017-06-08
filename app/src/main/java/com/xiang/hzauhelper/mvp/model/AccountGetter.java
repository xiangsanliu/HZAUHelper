package com.xiang.hzauhelper.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.preference.PreferenceManager;

/**
 * Created by xiang on 2017/3/23.
 *
 */

public class AccountGetter {

    private static AccountGetter accountGetter;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    private AccountGetter(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public static AccountGetter newInstance(Context context) {
        if (accountGetter == null) {
            return new AccountGetter(context);
        }
        return accountGetter;
    }

    public void setAccount(String account) {
        editor.putString("account", account);
    }

    public void setPasswordJw(String passwordJW) {
        editor.putString("password_jw", passwordJW);
    }

    public void setPasswordLib(String passwordLib) {
        editor.putString("password_lib", passwordLib);
    }

    public void setPasswordPe(String passwordPe) {
        editor.putString("password_pe", passwordPe);
    }

    public void commit() {
        editor.commit();
    }

    public String getAccount() {
        return preferences.getString("account", "");
    }

    public String getPasswordJw() {
        return preferences.getString("password_jw", "");
    }

    public String getPasswrodLib() {
        return preferences.getString("password_lib", "");
    }

    public String getPasswordPe() {
        return preferences.getString("password_pe", "");
    }

}
