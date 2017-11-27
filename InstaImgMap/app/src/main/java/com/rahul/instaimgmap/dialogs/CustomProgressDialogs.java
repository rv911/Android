package com.rahul.instaimgmap.dialogs;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by rahul on 25/11/17
 */
public class CustomProgressDialogs {

    private static ProgressDialog sProgressDialog;

    public static void showProgressDialog(Activity activity, String title, String content) {
        try {
            if (activity == null) return;
            if (sProgressDialog != null) sProgressDialog.dismiss();
            sProgressDialog = new ProgressDialog(activity);
            if (title != null && !title.isEmpty()) {
                sProgressDialog.setTitle(title);
            }
            sProgressDialog.setMessage(content);
            sProgressDialog.setCancelable(false);
            sProgressDialog.setIndeterminate(true);
            showDialog(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showProgressDialog(Activity activity, String title, String content, boolean flag) {
        if (!flag) {
            hideProgressDialog(activity);
        }
        sProgressDialog = new ProgressDialog(activity);
        if (title != null && !title.isEmpty()) {
            sProgressDialog.setTitle(title);
        }
        sProgressDialog.setMessage(content);
        sProgressDialog.setCancelable(false);
        sProgressDialog.setIndeterminate(true);
        showDialog(activity);
    }

    private static void showDialog(Activity activity) {
        try {
            if (activity == null) return;
            if (sProgressDialog == null) return;
            if (!sProgressDialog.isShowing()) sProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog(Activity activity) {
        if (activity == null) return;
        if (sProgressDialog != null) sProgressDialog.dismiss();
        sProgressDialog = null;
    }

}
