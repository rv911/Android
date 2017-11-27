package com.rahul.instaimgmap.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rahul.instaimgmap.ui.profile.ProfileActivity;
import com.rahul.instaimgmap.utils.AppUtils;

/**
 * Created by rahul on 25/11/17
 */

public class FeedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int reqCode = intent.getIntExtra(FeedService.RC_CODE, 0);
        String msg = "Download finished!";
        switch (reqCode) {
            case FeedService.STATUS_RUNNING:
                msg = "Downloading...";
                break;

            case FeedService.STATUS_FINISHED:
                msg = "Download finished!";
                if(context != null && context instanceof ProfileActivity) {
                    ((ProfileActivity) context).getPresenter().updateProfile();
                }
                break;

            case FeedService.STATUS_ERROR:
                msg = "Failed, try again!";
                break;
        }
        AppUtils.showToastMsg(context, msg);
    }
}
