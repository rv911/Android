package com.rahul.instaimgmap.ui.gallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rahul.instaimgmap.R;
import com.rahul.instaimgmap.models.GeoImage;
import com.rahul.instaimgmap.ui.base.BaseActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class GalleryActivity extends BaseActivity<GalleryContract.Presenter>
        implements GalleryContract.GalleryView,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    private static final double EARTH_RADIUS_KM = 6378137 / 1000;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private double latitude, longitude;
    private double min, max;
    private ArrayList<GeoImage> mGeoImages;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;

    @Override
    protected int getContentResourceId() {
        return R.layout.activity_gallary;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setPresenter(new GalleryPresenter());
            createLocationRequest();
            initGoogleApiClient();
            initRecyclerView();
        }
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void initRecyclerView() {
        setupGallery();
        if (mGeoImages != null && !mGeoImages.isEmpty()) {
            Gson gson = new Gson();
            String data = gson.toJson(mGeoImages, new TypeToken<ArrayList<GeoImage>>() {
            }.getType());

            Fragment fragment = GalleryFragment.getInstance(data, latitude, longitude, min, max);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        } else {
            String data = getIntent().getStringExtra("data");
            Fragment fragment = GalleryFragment.getInstance(data, latitude, longitude, min, max);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void setupGallery() {
        mGeoImages = new ArrayList<>();
        if (getIntent().hasExtra("data")) {
            String data = getIntent().getStringExtra("data");
            if (data != null && !data.isEmpty()) {
                Gson gson = new Gson();
                mGeoImages = gson.fromJson(data, new TypeToken<ArrayList<GeoImage>>() {
                }.getType());
            }
        }

        if (mGeoImages != null && !mGeoImages.isEmpty()) {
            ArrayList<Double> mLocations = new ArrayList<>();
            for (GeoImage geoImage : mGeoImages) {
                double distance = (int) getDistance(geoImage.getLatitude(), geoImage.getLongitude());
                geoImage.setDistance(distance);
                mLocations.add(distance);
            }
            min = Collections.min(mLocations);
            max = Collections.max(mLocations);
        }
    }

    double getDistance(double lat, double lng) {
        LatLng start = new LatLng(latitude, longitude);
        LatLng end = new LatLng(lat, lng);
        return calculationByDistance(start, end);
    }

    public double calculationByDistance(LatLng StartP, LatLng EndP) {
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = EARTH_RADIUS_KM * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        showLog(valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);

        return EARTH_RADIUS_KM * c;
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null && latitude == 0.0) {
            mCurrentLocation = location;
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            initRecyclerView();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
