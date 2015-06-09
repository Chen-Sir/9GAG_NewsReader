package com.chen.jokesreader.utils.image;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.chen.jokesreader.App;


/**
 * Created by ChenSir on 15-6-3.
 */
public class ImageCacheManager {

    private static ImageLoader mImageLoader;

    private ImageCacheManager() {

    }

    public static void initImageCacheManager(){
        //Use 1/8th of the available memory for this memory cache.
        final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager) App.getAppContext()
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;

        mImageLoader = new ImageLoader(RequestManager.getRequestQueue(), new BitmapLruCache(
                MEM_CACHE_SIZE));
    }

    public static ImageLoader.ImageContainer loadImage(String requestUrl,
                                                       ImageLoader.ImageListener imageListener) {
        return loadImage(requestUrl, imageListener, 0, 0);
    }

    public static ImageLoader.ImageContainer loadImage(String requestUrl,
                                                       ImageLoader.ImageListener imageListener, int maxWidth, int maxHeight) {
        return mImageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
    }

}
