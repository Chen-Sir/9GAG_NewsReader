package com.chen.jokesreader.model;


import com.google.gson.Gson;

/**
 * Created by ChenSir on 2015/5/31 0031.
 */
public class BaseModel {
    public String toJson() {
        return new Gson().toJson(this);
    }
}
