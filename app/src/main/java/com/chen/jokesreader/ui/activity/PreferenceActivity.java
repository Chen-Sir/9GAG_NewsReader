package com.chen.jokesreader.ui.activity;

import android.net.Uri;
import android.os.Bundle;

import com.chen.jokesreader.R;
import com.chen.jokesreader.ui.base.BaseActivity;
import com.chen.jokesreader.ui.fragment.AppPreferenceFragment;

/**
 * Created by ChenSir on 2015/6/8 0008.
 */
public class PreferenceActivity extends BaseActivity implements AppPreferenceFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        getFragmentManager().beginTransaction().replace(R.id.activity_preference_container,AppPreferenceFragment.newInstance()).commit();

    }


    @Override
    public void onPreferenceFgInteraction(Uri uri) {

    }
}
