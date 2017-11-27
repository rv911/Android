package com.rahul.instaimgmap.ui.login;


import com.rahul.instaimgmap.ui.base.BaseContract;
import com.rahul.instaimgmap.ui.base.BaseView;

/**
 * Created by rahul on 25/11/17
 */

public interface LoginContract {

    interface Presenter extends BaseContract<LoginView> {

        void getAccessToken(String code);

        LoginPresenter.OAuthWebViewClient getOAuthWebViewClient();

    }

    interface LoginView extends BaseView {

        void initWebView();

        void onCodeReceived(String code);

        void updateAccessToken(String accessToken);

    }

}
