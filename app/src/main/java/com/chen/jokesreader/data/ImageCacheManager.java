package com.chen.jokesreader.data;

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

    //Use 1/8th of the available memory for this memory cache.
    private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager) App.getAppContext()
            .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;

    private static ImageLoader mImageLoader = new ImageLoader(RequestManager.mRequestQueue, new BitmapLruCache(
            MEM_CACHE_SIZE));

    private ImageCacheManager() {

    }

    public static ImageLoader.ImageContainer loadImage(String requestUrl,
                                                       ImageLoader.ImageListener imageListener) {
        return loadImage(requestUrl, imageListener, 0, 0);
    }

    public static ImageLoader.ImageContainer loadImage(String requestUrl,
                                                       ImageLoader.ImageListener imageListener, int maxWidth, int maxHeight) {
        return mImageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
    }

    public static ImageLoader.ImageListener getImageListener(final ImageView view,
                                                             final Drawable defaultImageDrawable, final Drawable errorImageDrawable) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageDrawable != null) {
                    view.setImageDrawable(errorImageDrawable);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                } else if (defaultImageDrawable != null) {
                    view.setImageDrawable(defaultImageDrawable);
                }
            }
        };
    }
}
