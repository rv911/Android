package com.rahul.instaimgmap.ui.map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rahul.instaimgmap.R;
import com.rahul.instaimgmap.listeners.OnCompleteListener;
import com.rahul.instaimgmap.models.GeoImage;

import java.util.ArrayList;

import static com.rahul.instaimgmap.utils.ImageUtils.getBitmapDescriptor;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<GeoImage> mGeoImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGeoImages = getGeoLocations();
        getBitmapDescriptor(mGeoImages, new OnCompleteListener() {
            @Override
            public void onComplete(ArrayList<GeoImage> geoImages) {
                mGeoImages = geoImages;
                addCustomGeoMarkers();
            }
        });
    }

    ArrayList<GeoImage> getGeoLocations() {
        String data = getIntent().getStringExtra("data");
        if (!data.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(data, new TypeToken<ArrayList<GeoImage>>() {
            }.getType());
        }
        return new ArrayList<>();
    }

    private void addGeoMarkers() {
        if (mGeoImages != null && !mGeoImages.isEmpty()) {
            for (GeoImage geoImage : mGeoImages) {
                LatLng imgLocation = new LatLng(geoImage.getLatitude(), geoImage.getLongitude());
                mMap.addMarker(
                        new MarkerOptions()
                                .position(imgLocation)
                                .title(geoImage.getPlace())
                ).setTag(geoImage.getImage());
            }
        }
    }

    private void addCustomGeoMarkers() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mGeoImages != null && !mGeoImages.isEmpty()) {
                    mMap.clear();
                    for (GeoImage geoImage : mGeoImages) {
                        LatLng imgLocation = new LatLng(geoImage.getLatitude(), geoImage.getLongitude());
                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(imgLocation)
                                        .title(geoImage.getPlace())
                                        .icon(BitmapDescriptorFactory.fromBitmap(geoImage.getBitmap()))
                        ).setTag(geoImage.getImage());
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(23.033863, 72.585022);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Ahmedabad"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        addGeoMarkers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
