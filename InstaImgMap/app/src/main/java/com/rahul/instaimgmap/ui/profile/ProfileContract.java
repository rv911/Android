package com.rahul.instaimgmap.ui.profile;

import java.util.ArrayList;

import com.rahul.instaimgmap.models.GeoImage;
import com.rahul.instaimgmap.models.ModelUserFeed;
import com.rahul.instaimgmap.models.postFeed.ModelPost;
import com.rahul.instaimgmap.models.userProfile.Counts;
import com.rahul.instaimgmap.models.userProfile.ModelUserProfile;
import com.rahul.instaimgmap.models.userProfile.User;
import com.rahul.instaimgmap.ui.base.BaseContract;
import com.rahul.instaimgmap.ui.base.BaseView;

/**
 * Created by rahul on 25/11/17
 */

public interface ProfileContract {

    interface ProfileView extends BaseView {

        void setupProfile(ModelUserProfile userProfile);

        void setupUserProfile(User user);

        void setupSocialCount(Counts counts);

        void setupImageGrid(ArrayList<GeoImage> geoImages);

    }

    interface Presenter extends BaseContract<ProfileView> {

        ArrayList<GeoImage> getGeoImages();

        void getUserProfile();

        void getUsersPosts();

        void fetchAllImagesWithLocation(ModelPost modelPost);

        String getImageJson();

        void updateProfile();

    }

}
