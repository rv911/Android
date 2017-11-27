package com.rahul.instaimgmap;

import android.app.Application;
import android.content.Context;

import com.rahul.instaimgmap.database.SQLiteController;
import com.rahul.instaimgmap.database.UserPreference;
import com.rahul.instaimgmap.network.NetworkService;


/**
 * Created by rahul on 25/11/17
 */

public class AppClass extends Application {

    private NetworkService mNetworkService;
    private static UserPreference mPreference;
    private static SQLiteController mSqLiteController;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkService = new NetworkService();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mPreference == null) {
            mPreference = new UserPreference(base);
        }
        if (mSqLiteController == null) {
            mSqLiteController = new SQLiteController(base);
        }
    }

    public NetworkService getNetworkService() {
        if (mNetworkService == null) {
            mNetworkService = new NetworkService();
        }
        return mNetworkService;
    }

    public UserPreference getPreference() {
        return mPreference;
    }

    public SQLiteController getSqLiteController() {
        return mSqLiteController;
    }
}
