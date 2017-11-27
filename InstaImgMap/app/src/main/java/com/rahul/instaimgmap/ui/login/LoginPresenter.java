package com.rahul.instaimgmap.ui.login;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rahul.instaimgmap.models.ModelAccessToken;
import com.rahul.instaimgmap.network.Constants;
import com.rahul.instaimgmap.network.RestCall;
import com.rahul.instaimgmap.ui.base.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rahul on 25/11/17
 */

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.Presenter {

    @Override
    public void getAccessToken(String code) {
        getView().showLoadingDialog("Please wait, Authenticating...");
        new RestCall().getAccessToken(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ModelAccessToken>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ModelAccessToken modelAccessToken) {
                        getView().updateAccessToken(modelAccessToken.getAccessToken());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showAlertDialog("Alert!", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public OAuthWebViewClient getOAuthWebViewClient() {
        return new OAuthWebViewClient();
    }


    class OAuthWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            getView().showLog("Redirecting URL " + url);
            if (url.startsWith(Constants.REDIRECT_URL)) {
                String urls[] = url.split("=");
                getView().onCodeReceived(urls[1]);
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            getView().showLog("Page error: " + description);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            getView().showLog("Loading URL: " + url);
            super.onPageStarted(view, url, favicon);
            getView().showLoadingDialog("Please wait...");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            getView().showLog("onPageFinished URL: " + url);
            getView().hideLoadingDialog();
        }
    }

}
