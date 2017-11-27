package com.rahul.instaimgmap.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.rahul.instaimgmap.R;
import com.rahul.instaimgmap.database.UserPreference;
import com.rahul.instaimgmap.network.Constants;
import com.rahul.instaimgmap.ui.base.BaseActivity;
import com.rahul.instaimgmap.ui.profile.ProfileActivity;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.LoginView {

    @BindView(R.id.wvLoginPage)
    WebView mWebView;

    @Override
    protected int getContentResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setPresenter(new LoginPresenter());
        String accessToken = new UserPreference(this).getAccessToken();
        if (accessToken.isEmpty()) {
            initWebView();
        } else {
            updateAccessToken(accessToken);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initWebView() {
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(getPresenter().getOAuthWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(Constants.AUTHORIZATION_API);
    }

    @Override
    public void onCodeReceived(String code) {
        showLog("Code: " + code);
        //showToastMsg("Code: " + code);
        getPresenter().getAccessToken(code);
    }

    @Override
    public void updateAccessToken(String accessToken) {
        showLog("Access Token: " + accessToken);
        //showToastMsg("Access Token: "+accessToken);
        new UserPreference(this).setAccessToken(accessToken);
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
