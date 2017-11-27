package com.rahul.instaimgmap.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.rahul.instaimgmap.listeners.OnDialogDismissListener;


/**
 * Created by rahul on 25/11/17
 */
public class CustomAlertDialogs {

    private static final String TITLE = "Caution!";
    private static AlertDialog sAlertDialog;

    public static void showSimpleAlertDialog(Activity activity, String title, String content) {
        if (activity == null) return;
        hideAlertDialog(activity);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        sAlertDialog = builder.create();
        showDialog(activity);
    }

    public static void showSimpleCallbackAlertDialogWithOk(Activity activity, String title, String content, final OnDialogDismissListener dismissListener) {
        if (activity == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dismissListener.onDialogDismiss(true);
            }
        });
        sAlertDialog = builder.create();
        showDialog(activity);
    }

    public static void showSimpleCallbackAlertDialog(Activity activity, String title, String content, final OnDialogDismissListener dismissListener) {
        if (activity == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null && !title.isEmpty()) builder.setTitle(title);
        else builder.setTitle(TITLE);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dismissListener.onDialogDismiss(true);
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dismissListener.onDialogDismiss(false);
            }
        });
        sAlertDialog = builder.create();
        showDialog(activity);
    }

    public static void showCallbackAlertDialogWishYes(Activity activity, String title, String content, final OnDialogDismissListener dismissListener) {
        if (activity == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null && !title.isEmpty()) builder.setTitle(title);
        else builder.setTitle(TITLE);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dismissListener.onDialogDismiss(true);
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dismissListener.onDialogDismiss(false);
            }
        });
        sAlertDialog = builder.create();
        showDialog(activity);
    }

    public static void showSimpleCallbackSettingAlertDialog(Activity activity, String title, String content, final OnDialogDismissListener dismissListener) {
        if (activity == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null && !title.isEmpty()) builder.setTitle(title);
        else builder.setTitle(TITLE);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dismissListener.onDialogDismiss(true);
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dismissListener.onDialogDismiss(false);
            }
        });
        sAlertDialog = builder.create();
        showDialog(activity);
    }

    public static void showExitAlertDialog(final Activity activity, String title, String content) {
        if (activity == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(content);
        builder.setCancelable(true);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                activity.finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        sAlertDialog = builder.create();
        showDialog(activity);
    }

    private static void showDialog(Activity activity) {
        try {
            if (activity == null) return;
            if (sAlertDialog == null) return;
            if (sAlertDialog.isShowing()) sAlertDialog.dismiss();
            sAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideAlertDialog(Activity activity){
        try {
            if (activity == null) return;
            if (sAlertDialog == null) return;
            if (sAlertDialog.isShowing()) sAlertDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
