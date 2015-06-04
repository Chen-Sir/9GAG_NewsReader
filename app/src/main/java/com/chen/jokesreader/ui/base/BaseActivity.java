package com.chen.jokesreader.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.chen.jokesreader.R;

/**
 * Created by ChenSir on 2015/5/18 0018.
 */
public abstract class BaseActivity extends ActionBarActivity {

    public Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
