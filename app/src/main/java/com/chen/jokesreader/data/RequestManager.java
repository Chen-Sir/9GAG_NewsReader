package com.chen.jokesreader.data;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.chen.jokesreader.App;


/**
 * Created by ChenSir on 15-6-3.
 */
public class RequestManager {

    public static RequestQueue mRequestQueue = Volley.newRequestQueue(App.getAppContext());

    private RequestManager() {
        // no instances
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
