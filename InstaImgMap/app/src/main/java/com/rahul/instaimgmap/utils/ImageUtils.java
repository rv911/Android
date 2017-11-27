package com.rahul.instaimgmap.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.rahul.instaimgmap.listeners.OnCompleteListener;
import com.rahul.instaimgmap.models.GeoImage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rahulprasadverma on 26/11/17.
 */

public class ImageUtils {

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return Bitmap.createScaledBitmap(output, 60, 60, false);
    }

    public static void getBitmapDescriptor(final ArrayList<GeoImage> mGeoImages, final OnCompleteListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (GeoImage geoImage : mGeoImages) {
                    try {
                        URL url = new URL(geoImage.getThumbnail());
                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        image = getCroppedBitmap(image);
                        geoImage.setBitmap(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                listener.onComplete(mGeoImages);
            }
        }).start();
    }

}
