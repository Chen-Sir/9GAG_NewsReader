package com.chen.jokesreader.utils.image;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.chen.jokesreader.App;


/**
 * Request operations class
 * <p/>
 * Created by ChenSir on 15-6-3.
 */
public class RequestManager {

    private RequestManager() {
        // no instances
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        getRequestQueue().add(request);
    }

    public static void cancelAll(Object tag) {
        getRequestQueue().cancelAll(tag);
    }


    /**
     *
     * @return the singleton class of RequestQueue
     */
    public static RequestQueue getRequestQueue() {
        return RequestQueueHolder.mRequestQueue;
    }


    private static class RequestQueueHolder {

        private final static RequestQueue mRequestQueue = Volley.newRequestQueue(App.getAppContext());

    }


}
