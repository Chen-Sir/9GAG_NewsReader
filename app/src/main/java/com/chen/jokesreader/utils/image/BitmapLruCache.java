package com.chen.jokesreader.utils.image;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by ChenSir on 15-6-3.
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}

