package com.rahul.instaimgmap.ui.map;

import android.app.Activity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.rahul.instaimgmap.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

/**
 * Created by rahul on 25/11/17
 */

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity mContext;
    MarkerInfoWindowAdapter(Activity context) {
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        String url = (String) marker.getTag();
        View v  = mContext.getLayoutInflater().inflate(R.layout.layout_marker_image_info_window, null);
        CircleImageView ivMarkerInfoWindow = v.findViewById(R.id.ivMarkerInfoWindow);
        Glide.with(mContext).load(url).apply(fitCenterTransform()).into(ivMarkerInfoWindow);
        return v;
    }
}
