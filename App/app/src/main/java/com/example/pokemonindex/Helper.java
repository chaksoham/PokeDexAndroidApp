package com.example.pokemonindex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Name: soham chakraborti.
 *
 * @author sohamc.
 */
public class Helper {

    /**
     * Returns a bitmap from an url.
     * @param url to retrive image
     * @return a bitmap version of the image
     * refer: https://stackoverflow.com/questions/11831188/how-to-get-bitmap-from-a-url-in-android
     */
    public static Bitmap getBitmapFromURL(final URL url) {
        try {
            final URLConnection conn = url.openConnection();
            conn.connect();
            Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(conn.getInputStream()));
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
