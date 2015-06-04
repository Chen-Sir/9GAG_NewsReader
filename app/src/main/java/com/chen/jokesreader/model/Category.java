package com.chen.jokesreader.model;

/**
 * Created by ChenSir on 15-6-3.
 */
public enum Category {
    hot("Hot"), trending("Trending"), fresh("Fresh");
    private String mDisplayName;

    Category(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
