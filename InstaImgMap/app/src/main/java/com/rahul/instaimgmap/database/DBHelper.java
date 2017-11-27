package com.rahul.instaimgmap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rahul.instaimgmap.models.ModelUserFeed;

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
 * Created by rahul on 27/11/17
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper sInstance;

    private static final String DATABASE_NAME = "InstaImgMap.sqlite";
    private static final int DATABASE_VERSION = 1;

    static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context);
        }
        return sInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_GEO_IMAGE_TABLE = "create table " + GEO_TABLE + "("
            + GEO_ID + " TEXT PRIMARY KEY, "
            + GEO_THUMBNAIL + " TEXT NOT NULL, "
            + GEO_IMAGE + " TEXT NOT NULL, "
            + GEO_LOCATION + " TEXT, "
            + GEO_LATITUDE + " LONG, "
            + GEO_LONGITUDE + " LONG);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GEO_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GEO_TABLE);
        onCreate(db);
    }
}

