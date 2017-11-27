package com.rahul.instaimgmap.ui.base;

/**
 * Created by rahul on 25/11/17
 */

public interface BaseView {

    void showAlertDialog(String title, String message);

    void showLoadingDialog(String message);

    void hideLoadingDialog();

    void showToastMsg(String message);

    void showLog(String message);

}
