package com.rahul.instaimgmap.ui.gallery;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.rahul.instaimgmap.R;
import com.rahul.instaimgmap.models.GeoImage;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

/**
 * Created by rahul on 25/11/17
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Activity mActivity;
    private ArrayList<GeoImage> mGallery;
    private boolean isGallery;

    private double minDistance, maxDistance;

    GalleryAdapter(Activity mActivity, ArrayList<GeoImage> mGallery) {
        this.mActivity = mActivity;
        this.mGallery = mGallery;
        isGallery = mActivity instanceof GalleryActivity;
    }

    void setMinMaxDistance(double minDistance, double maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        GeoImage gallery = mGallery.get(position);
        Glide.with(mActivity).load(gallery.getThumbnail()).apply(fitCenterTransform()).into(holder.ivGalleryItem);

        if (isGallery) {
            holder.tvLocation.setVisibility(View.VISIBLE);
            holder.tvLocation.setText(gallery.getDistance() + " KM");
        }

        if (gallery.getDistance() == minDistance) {
            holder.tvLocation.setText("Nearest");
            holder.tvLocation.setBackgroundColor(Color.GREEN);
            holder.tvLocation.setTypeface(null, Typeface.BOLD);
            minDistance = 0.0;
        }

        if (gallery.getDistance() == maxDistance) {
            holder.tvLocation.setText("Farthest");
            holder.tvLocation.setBackgroundColor(Color.RED);
            holder.tvLocation.setTypeface(null, Typeface.BOLD);
            maxDistance = 0.0;
        }
    }

    @Override
    public int getItemCount() {
        return mGallery.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivGalleryItem)
        ImageView ivGalleryItem;

        @BindView(R.id.tvLocation)
        TextView tvLocation;

        GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
