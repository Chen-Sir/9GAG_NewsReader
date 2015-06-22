package com.chen.jokesreader.ui.base;


import com.chen.jokesreader.ui.fragment.ExploreFragment;
import com.chen.jokesreader.ui.fragment.HomeFragment;
import com.chen.jokesreader.ui.fragment.NavigationDrawerFragment;

/**
 * Created by ChenSir on 2015/5/18 0018.
 */
public interface HostingActivityInterface extends DrawerActivityInterface,NavigationDrawerFragment.NavigationDrawerCallbacks, HomeFragment.OnHomeFragmentInteractionListener {

    public void setSelectedFragment(BaseFragment fragment);

    public void popBackStack();

    public void popBackStackTillTag(String tag);

    public void addFragment(BaseFragment fragment, boolean withAnimation);

}
