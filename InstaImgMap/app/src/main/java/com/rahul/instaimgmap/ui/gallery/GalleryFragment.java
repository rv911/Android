package com.rahul.instaimgmap.ui.gallery;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rahul.instaimgmap.R;
import com.rahul.instaimgmap.models.GeoImage;
import com.rahul.instaimgmap.ui.base.BaseFragment;
import com.rahul.instaimgmap.ui.profile.ProfileActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends BaseFragment<GalleryContract.Presenter> implements GalleryContract.GalleryView {

    @BindView(R.id.rvGallery)
    RecyclerView mRecyclerView;

    private Activity mActivity;
    private ArrayList<GeoImage> mGalleryItems;
    private GalleryAdapter mAdapter;

    private double lat, lng;
    private double minDistance, maxDistance;

    public static GalleryFragment getInstance(String data, double lat, double lng, double min, double max) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        bundle.putDouble("minDistance", min);
        bundle.putDouble("maxDistance", max);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GalleryFragment getInstance(String data) {
        return getInstance(data, 0, 0, 0, 0);
    }

    @Override
    protected int getContentResourceId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            setPresenter(new GalleryPresenter());
            mActivity = getActivity();
            initRecyclerView();
        }
    }

    private void getGalleryItemsFromJson() {
        mGalleryItems = new ArrayList<>();
        if (getArguments().containsKey("data")) {
            String data = getArguments().getString("data");
            lat = getArguments().getDouble("lat");
            lng = getArguments().getDouble("lng");
            minDistance = getArguments().getDouble("minDistance");
            maxDistance = getArguments().getDouble("maxDistance");
            if (data != null && !data.isEmpty()) {
                Gson gson = new Gson();
                mGalleryItems = gson.fromJson(data, new TypeToken<ArrayList<GeoImage>>() {
                }.getType());
            }
        }
    }

    @Override
    public void initRecyclerView() {
        if (mActivity instanceof ProfileActivity) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        }
        mRecyclerView.setHasFixedSize(true);
        getGalleryItemsFromJson();
        mAdapter = new GalleryAdapter(mActivity, mGalleryItems);
        mAdapter.setMinMaxDistance((int)minDistance, (int)maxDistance);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupGallery() {

    }
}
