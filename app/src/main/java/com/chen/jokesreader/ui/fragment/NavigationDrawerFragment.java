package com.chen.jokesreader.ui.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chen.jokesreader.R;
import com.chen.jokesreader.utils.ManagerTypeface;
import com.chen.jokesreader.ui.adapter.NavDrawerListAdapter;
import com.chen.jokesreader.ui.base.BaseFragment;
import com.chen.jokesreader.utils.UtilsDevice;
import com.chen.jokesreader.utils.UtilsMiscellaneous;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 */
public class NavigationDrawerFragment extends BaseFragment {

    public final static String TAG = "NavDrawerFragment";

    private final static double sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO = 9d / 16d;

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavDrawerListAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private RecyclerView mRecyclerView;

    private FrameLayout mFrameLayout_NavItemRow;
    private TextView mTextView_AccountDisplayName;
    private TextView mTextView_AccountEmail;
    private FrameLayout mFrameLayout_AccountView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    /**
     * Navigation drawer items resources.
     */

    String[] mNavDrawerItemTitleArray;


    public NavigationDrawerFragment() {
    }

    @Override
    public String getTagText() {
        return TAG;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_drawer, null);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {

        int[] mNavDrawerItemIconArray = {R.drawable.ic_home_white_24dp, R.drawable.ic_explore_white_24dp, R.drawable.ic_settings_white_24dp, R.drawable.ic_info_white_24dp};
        mNavDrawerItemTitleArray = getResources().getStringArray(R.array.nav_drawer_item_title_array);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.navigation_drawer_recyclerview);
        mToolbar = toolbar;
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mTextView_AccountDisplayName = (TextView) mFragmentContainerView.findViewById(R.id.navigation_drawer_account_information_display_name);
        mTextView_AccountEmail = (TextView) mFragmentContainerView.findViewById(R.id.navigation_drawer_account_information_email);
        mFrameLayout_AccountView = (FrameLayout) mFragmentContainerView.findViewById(R.id.navigation_drawer_account_view);
        mFrameLayout_NavItemRow = (FrameLayout) mFragmentContainerView.findViewById(R.id.nav_drawer_item_row);

        //set typeface
        mTextView_AccountDisplayName.setTypeface(ManagerTypeface.getTypeface(getActivity(), R.string.typeface_roboto_medium));
        mTextView_AccountEmail.setTypeface(ManagerTypeface.getTypeface(getActivity(), R.string.typeface_roboto_regular));

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mRecyclerViewAdapter = new NavDrawerListAdapter(mNavDrawerItemTitleArray, mNavDrawerItemIconArray);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setRecyclerViewListener();

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the toolbar.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                mToolbar,          /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //Navigation Drawer width.
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(getActivity()) - UtilsMiscellaneous.getThemeAttributeDimensionSize(getActivity(), android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mFragmentContainerView.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
        mFrameLayout_AccountView.getLayoutParams().height = (int) (mFragmentContainerView.getLayoutParams().width
                * sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO);

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    private void setRecyclerViewListener() {

        /**
         * To be later called to verify if the touch event is a SingleTapUp type of touch
         * or some other type of touch (swipe touch, long touch).
         */
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if (child != null && mGestureDetector.onTouchEvent(e) && child.getTag() != NavDrawerListAdapter.TYPE_DIVIDER) {
                    int position = rv.getChildAdapterPosition(child);
                    onRowPressed((FrameLayout) child, rv);
                    selectItem(position);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

    }

    /**
     * Set up the rows when list is pressed
     */
    private void onRowPressed(FrameLayout pressedRow, RecyclerView rv) {
        //pressedRow is the drawer main entry (Home, Explore).
        if (!pressedRow.isSelected()) {
            for (int i = 0; i < rv.getChildCount(); i++) {
                View currentView = rv.getChildAt(i);
                boolean isMainEntry = currentView.getTag() == getResources().getString(R.string.tag_nav_drawer_main_entry);
                if (isMainEntry) {
                    if (pressedRow == currentView) {
                        currentView.setSelected(true);
                    } else {
                        currentView.setSelected(false);
                    }
                }
            }
        }
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mHostingActivityInterface != null) {
            mHostingActivityInterface.onNavigationDrawerItemSelected(position);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mHostingActivityInterface = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
