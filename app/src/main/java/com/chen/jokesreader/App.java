package com.chen.jokesreader;

import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

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
//        initUniversalImageLoader(mAppContext);
    }

    /**
     * Initialize Universal-ImageLoader
     * @param context Application context
     */
    public static void initUniversalImageLoader(Context context){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)).discCacheSize(10 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

}
