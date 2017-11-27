package com.rahul.instaimgmap.models;

import android.graphics.Bitmap;

/**
 * Created by rahul on 25/11/17
 */

public class GeoImage {

    public static final String GEO_TABLE = "geoTable";
    public static final String GEO_ID = "geoImgId";
    public static final String GEO_THUMBNAIL = "thumbnail";
    public static final String GEO_IMAGE = "image";
    public static final String GEO_LOCATION = "location";
    public static final String GEO_LATITUDE = "latitude";
    public static final String GEO_LONGITUDE = "longitude";

    private String geoImgId;
    private String thumbnail, image, place;
    private double latitude, longitude;
    private double distance = 0;
    private Bitmap bitmap;

    public GeoImage() {
        this("", "", "", "", 0, 0);
    }

    public GeoImage(String geoImgId, String thumbnail, String image, String place, double latitude, double longitude) {
        this.geoImgId = geoImgId;
        this.thumbnail = thumbnail;
        this.image = image;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getGeoImgId() {
        return geoImgId;
    }

    public void setGeoImgId(String geoImgId) {
        this.geoImgId = geoImgId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
