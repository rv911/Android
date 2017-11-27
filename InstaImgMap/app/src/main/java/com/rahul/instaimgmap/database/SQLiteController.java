package com.rahul.instaimgmap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rahul.instaimgmap.models.GeoImage;
import com.rahul.instaimgmap.models.ModelUserFeed;

import java.util.ArrayList;

import static com.rahul.instaimgmap.models.GeoImage.GEO_ID;
import static com.rahul.instaimgmap.models.GeoImage.GEO_IMAGE;
import static com.rahul.instaimgmap.models.GeoImage.GEO_LATITUDE;
import static com.rahul.instaimgmap.models.GeoImage.GEO_LOCATION;
import static com.rahul.instaimgmap.models.GeoImage.GEO_LONGITUDE;
import static com.rahul.instaimgmap.models.GeoImage.GEO_TABLE;
import static com.rahul.instaimgmap.models.GeoImage.GEO_THUMBNAIL;
import static com.rahul.instaimgmap.models.ModelUserFeed.FEED_CAPTION;
import static com.rahul.instaimgmap.models.ModelUserFeed.FEED_CREATION_TIME;
import static com.rahul.instaimgmap.models.ModelUserFeed.FEED_ID;
import static com.rahul.instaimgmap.models.ModelUserFeed.FEED_IMAGE;
import static com.rahul.instaimgmap.models.ModelUserFeed.FEED_LOCATION;
import static com.rahul.instaimgmap.models.ModelUserFeed.FEED_PROFILE_IMAGE;
import static com.rahul.instaimgmap.models.ModelUserFeed.FEED_TABLE;
import static com.rahul.instaimgmap.models.ModelUserFeed.FEED_USERNAME;

/**
 * Created by rahul on 25/11/17
 */

public class SQLiteController {

    private DBHelper dbHelper;

    public SQLiteController(Context mContext) {
        dbHelper = DBHelper.getInstance(mContext);
    }

    public boolean insertGeoImages(ArrayList<GeoImage> geoImages) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            for (GeoImage geoImage : geoImages) {
                cv.put(GEO_ID, geoImage.getGeoImgId());
                cv.put(GEO_IMAGE, geoImage.getImage());
                cv.put(GEO_LATITUDE, geoImage.getLatitude());
                cv.put(GEO_LONGITUDE, geoImage.getLongitude());
                cv.put(GEO_LOCATION, geoImage.getPlace());
                cv.put(GEO_THUMBNAIL, geoImage.getThumbnail());
                database.insert(GEO_TABLE, null, cv);
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            database.endTransaction();
        }
        return true;
    }

    public ArrayList<GeoImage> getAllGeoImages() {
        ArrayList<GeoImage> geoImages = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + GEO_TABLE;
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    GeoImage geoImage = new GeoImage();
                    geoImage.setGeoImgId(cursor.getString(cursor.getColumnIndex(GEO_ID)));
                    geoImage.setImage(cursor.getString(cursor.getColumnIndex(GEO_IMAGE)));
                    geoImage.setLatitude(cursor.getLong(cursor.getColumnIndex(GEO_LATITUDE)));
                    geoImage.setLongitude(cursor.getLong(cursor.getColumnIndex(GEO_LONGITUDE)));
                    geoImage.setPlace(cursor.getString(cursor.getColumnIndex(GEO_LOCATION)));
                    geoImage.setThumbnail(cursor.getString(cursor.getColumnIndex(GEO_THUMBNAIL)));

                    geoImages.add(geoImage);
                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return geoImages;
    }
}
