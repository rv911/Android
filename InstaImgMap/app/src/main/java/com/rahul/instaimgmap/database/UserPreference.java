package com.rahul.instaimgmap.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rahul on 25/11/17
 */

public class UserPreference {

    private static final String APP_PREFS_NAME = "InstaMapFeed";

    private static final String IS_LOGIN = "is_login";
    private static final String ACCESS_TOKEN = "access_token";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public UserPreference(Context context) {
        mPreferences = context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean getIsLogin() {
        return mPreferences.getBoolean(IS_LOGIN, false);
    }

    public void setIsLogin(boolean isLogin) {
        mEditor = mPreferences.edit();
        mEditor.putBoolean(IS_LOGIN, isLogin);
        mEditor.apply();
    }

    public String getAccessToken() {
        return mPreferences.getString(ACCESS_TOKEN, "");
    }

    public void setAccessToken(String accessToken) {
        mEditor = mPreferences.edit();
        mEditor.putString(ACCESS_TOKEN, accessToken);
        mEditor.apply();
    }

    public void logout() {
        setIsLogin(false);
        setAccessToken("");
    }


}
