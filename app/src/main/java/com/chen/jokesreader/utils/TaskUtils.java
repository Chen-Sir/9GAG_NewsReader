package com.chen.jokesreader.utils;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by ChenSir on 2015/5/31 0031.
 */
public class TaskUtils {
    public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> task, Params... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}
