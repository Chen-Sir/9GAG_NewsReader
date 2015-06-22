package com.chen.jokesreader;

import android.app.Application;
import android.content.Context;

import com.chen.jokesreader.utils.image.ImageCacheManager;

/**
 * Created by ChenSir on 2015/5/18 0018.
 */
public class App extends Application{

    private static Context mAppContext;

    public static Context getAppContext() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = getApplicationContext();
        ImageCacheManager.initImageCacheManager();
    }
}
