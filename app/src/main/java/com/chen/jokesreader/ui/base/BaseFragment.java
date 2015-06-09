package com.chen.jokesreader.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.chen.jokesreader.utils.image.RequestManager;

/**
 * Created by ChenSir on 2015/5/18 0018.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    protected HostingActivityInterface mHostingActivityInterface;

    /**
     * Will be used as handle to save transactions in backStack
     *
     * @return tag text
     */
    public abstract String getTagText();

    /**
     * To enable fragments capture back-press event and utilize it before
     * it's used in the hosting Activity.
     *
     * @return true if consumed, else false
     */
    public abstract boolean onBackPressed();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof HostingActivityInterface) {
            mHostingActivityInterface = (HostingActivityInterface) getActivity();
        } else {
            throw new ClassCastException("Hosting activity must implement HostActivityInterface");
        }

    }


    @Override
    public void onStart() {
        super.onStart();

        mHostingActivityInterface.setSelectedFragment(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }
}
