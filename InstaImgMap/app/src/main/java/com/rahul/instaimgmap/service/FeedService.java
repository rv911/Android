package com.rahul.instaimgmap.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.rahul.instaimgmap.AppClass;
import com.rahul.instaimgmap.models.GeoImage;
import com.rahul.instaimgmap.models.ModelUserFeed;
import com.rahul.instaimgmap.models.postFeed.Datum;
import com.rahul.instaimgmap.models.postFeed.ModelPost;
import com.rahul.instaimgmap.network.RestCall;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rahul on 25/11/17.
 */

public class FeedService extends IntentService {

    public static final String RC_CODE = "fetch_data";


    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    public FeedService() {
        super("FeedService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        onHandleIntent(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.hasExtra("data")) {
            String requestCode = intent.getStringExtra("data");
            if (requestCode.equals(RC_CODE)) {
                getUserFeeds();
            }
        }
    }

    private void getUserFeeds() {
        updateRequester(STATUS_RUNNING);
        new RestCall().getOwnPosts("", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ModelPost>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ModelPost post) {
                        if (fetchAllImagesWithLocation(post)) {
                            updateRequester(STATUS_FINISHED);
                        } else {
                            updateRequester(STATUS_ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateRequester(STATUS_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public boolean fetchAllImagesWithLocation(ModelPost modelPost) {
        ArrayList<GeoImage> geoImages = new ArrayList<>();
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
            return saveGeoImages(geoImages);
        }
        return false;
    }

    private boolean saveGeoImages(ArrayList<GeoImage> geoImages) {
        return new AppClass().getSqLiteController().insertGeoImages(geoImages);
    }

    private void updateRequester(int status) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(RC_CODE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RC_CODE, status);
        sendBroadcast(broadcastIntent);
    }
}
