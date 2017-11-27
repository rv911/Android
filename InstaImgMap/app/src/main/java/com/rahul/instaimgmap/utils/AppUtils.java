package com.rahul.instaimgmap.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rahul on 25/11/17
 */

public class AppUtils {

    private static Toast sToast;

    public static void showToastMsg(Context context, String msg) {
        if (sToast != null) sToast.cancel();
        sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        sToast.show();
    }

    public static void showLog(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static int getWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return (int) (width / 3.5);
    }

}
