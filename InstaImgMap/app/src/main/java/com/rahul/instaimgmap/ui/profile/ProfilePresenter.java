package com.rahul.instaimgmap.ui.profile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.rahul.instaimgmap.AppClass;
import com.rahul.instaimgmap.models.GeoImage;
import com.rahul.instaimgmap.models.ModelUserFeed;
import com.rahul.instaimgmap.models.postFeed.Datum;
import com.rahul.instaimgmap.models.postFeed.ModelPost;
import com.rahul.instaimgmap.models.userProfile.ModelUserProfile;
import com.rahul.instaimgmap.network.RestCall;
import com.rahul.instaimgmap.ui.base.BasePresenter;

/**
 * Created by rahul on 25/11/17
 */

public class ProfilePresenter extends BasePresenter<ProfileContract.ProfileView> implements ProfileContract.Presenter {

    private ArrayList<GeoImage> geoImages;
    private boolean needLiveData = true;

    @Override
    public ArrayList<GeoImage> getGeoImages() {
        return geoImages;
    }

    @Override
    public void getUserProfile() {
        if (getView() == null) return;
        getView().showLoadingDialog("Please wait, Loading...");
        getSavedGeoImages();
        new RestCall().getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ModelUserProfile>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ModelUserProfile userProfile) {
                        if (userProfile != null) {
                            getView().setupProfile(userProfile);
                        }
                        getUsersPosts();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoadingDialog();
                        getView().showAlertDialog("Error!", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getUsersPosts() {
        if (getView() == null) return;
        if(!needLiveData){
            updateProfile();
            return;
        }
        getView().showLoadingDialog("Please wait, Loading...");
        new RestCall().getOwnPosts("", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ModelPost>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ModelPost post) {
                        getView().hideLoadingDialog();
                        fetchAllImagesWithLocation(post);
                        getView().setupImageGrid(getGeoImages());
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoadingDialog();
                        getView().showAlertDialog("Error!", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void fetchAllImagesWithLocation(ModelPost modelPost) {
        geoImages = new ArrayList<>();
        List<Datum> posts = modelPost.getData();
        if (posts != null && !posts.isEmpty()) {
            for (Datum post : posts) {
                String imgUrl = post.getImages().getThumbnail().getUrl();
                String place;
                double latitude, longitude;
                place = "";
                latitude = 0.0;
                longitude = 0.0;
                if (post.getLocation() != null) {
                    place = post.getLocation().getName();
                    latitude = post.getLocation().getLatitude();
                    longitude = post.getLocation().getLongitude();
                }
                geoImages.add(new GeoImage(post.getId(), imgUrl, "", place, latitude, longitude));
            }
            saveGeoImages();
        }
    }

    private void getSavedGeoImages() {
        geoImages = new AppClass().getSqLiteController().getAllGeoImages();
        needLiveData = geoImages == null || geoImages.isEmpty();
    }

    private void saveGeoImages() {
        boolean isSuccessful = new AppClass().getSqLiteController().insertGeoImages(geoImages);
        if (isSuccessful) {
            getView().showToastMsg("Geo images saved.");
        } else {
            getView().showToastMsg("Geo images not saved, try again!");
        }
    }

    @Override
    public String getImageJson() {
        ArrayList<GeoImage> geoImages = getGeoImages();
        if (geoImages != null && !geoImages.isEmpty()) {
            Gson gson = new Gson();
            return gson.toJson(geoImages, new TypeToken<ArrayList<GeoImage>>() {
            }.getType());
        }
        return "";
    }

    @Override
    public void updateProfile() {
        getSavedGeoImages();
        getView().hideLoadingDialog();
        getView().setupImageGrid(getGeoImages());
    }
}
