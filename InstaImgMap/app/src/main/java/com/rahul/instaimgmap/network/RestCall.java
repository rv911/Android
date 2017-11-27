package com.rahul.instaimgmap.network;


import com.rahul.instaimgmap.AppClass;
import com.rahul.instaimgmap.database.UserPreference;
import com.rahul.instaimgmap.models.ModelAccessToken;
import com.rahul.instaimgmap.models.postFeed.ModelPost;
import com.rahul.instaimgmap.models.userProfile.ModelUserProfile;

import io.reactivex.Observable;

/**
 * Created by rahul on 25/11/17
 */

public class RestCall {

    private static NetworkAPI mNetworkAPI;
    private static UserPreference mPreference;

    public RestCall() {
        if (mNetworkAPI == null) {
            mNetworkAPI = new AppClass().getNetworkService().getAPI();
        }
        if (mPreference == null) {
            mPreference = new AppClass().getPreference();
        }
    }

    public Observable<ModelAccessToken> getAccessToken(String code) {
        return mNetworkAPI.getAccessToken(
                Constants.CLIENT_ID,
                Constants.CLIENT_SECRET,
                Constants.REDIRECT_URL,
                Constants.AUTHORIZATION_CODE,
                code
        );
    }

    public Observable<ModelUserProfile> getUserProfile() {
        return mNetworkAPI.getUserProfile(mPreference.getAccessToken());
    }

    public Observable<ModelPost> getOwnPosts(String maxId, String count){
        return mNetworkAPI.getAllPost(mPreference.getAccessToken(), maxId, count);
    }

}
