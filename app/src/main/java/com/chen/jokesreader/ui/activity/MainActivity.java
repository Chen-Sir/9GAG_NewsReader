package com.chen.jokesreader.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.chen.jokesreader.R;
import com.chen.jokesreader.ui.adapter.FeedsAdapter;
import com.chen.jokesreader.ui.base.BaseActivity;
import com.chen.jokesreader.ui.base.BaseFragment;
import com.chen.jokesreader.ui.base.DrawerItemBaseFragment;
import com.chen.jokesreader.ui.base.HostingActivityInterface;
import com.chen.jokesreader.ui.fragment.ExploreFragment;
import com.chen.jokesreader.ui.fragment.HomeFragment;
import com.chen.jokesreader.ui.fragment.NavigationDrawerFragment;
import com.chen.jokesreader.utils.image.RequestManager;

/**
 * Main class hosting the navigation drawer
 */
public class MainActivity extends BaseActivity implements HostingActivityInterface {

    private BaseFragment mSelectedFragment;
    private DrawerItemBaseFragment mSelectedDrawerItemFragment;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    private Toolbar mToolbar;


    private boolean isWarnedToClose = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = getActionBarToolbar();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_drawer_layout), mToolbar);

        addFragment(HomeFragment.newInstance(), true);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Clear backStack if app is not at a first-tier fragment
        // and drawer is not locked, so that app could be switched between
        // any first-tier fragment from anywhere.
        if (!(mSelectedFragment instanceof DrawerItemBaseFragment)) {
            clearBackStack();
        }
        onSectionAttached(position);
    }


    /**
     * Clears transaction backStack. In this case, after this method
     * all (or any) extended fragment(s) will get removed and
     * view will resort back to current drawer-item fragment.
     * <p/>
     * Will only be useful when we are not locking the drawer while in
     * extended fragments and can switch between drawer-item fragments
     * from anywhere in the app.
     */
    private void clearBackStack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    public void onSectionAttached(int number) {

        switch (number) {

            case 0:
                mTitle = getString(R.string.title_home);
                // update the main content by replacing fragments
                addFragment(HomeFragment.newInstance(), true);
                break;
            case 1:
                mTitle = getString(R.string.title_explore);
                addFragment(ExploreFragment.newInstance(), true);
                break;

            case 3:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PreferenceActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public void restoreActionBar() {
        mToolbar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        restoreActionBar();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_main_share) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // That is it for back press handling of whole app. Fragments will be first asked to consume.
    @Override
    public void onBackPressed() {
        // Check if selectedFragment is not consuming back press
        if (!mSelectedFragment.onBackPressed()) {
            // If not consumed, handle it.
            handleBackPressInThisActivity();
        }
    }

    /**
     * Will close this Activity if double back pressed within 2000 ms
     */
    private void handleBackPressInThisActivity() {
        if (isWarnedToClose) {
            finish();
        } else {
            isWarnedToClose = true;
            Toast.makeText(this, "Tap again to close the application", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    isWarnedToClose = false;
                }
            }, 2000);
        }
    }

    /**
     * Mark the fragment as the selected Fragment from the Navigation Drawer.
     *
     * @param fragment the selected Fragment.
     */
    @Override
    public void setSelectedFragment(BaseFragment fragment) {
        this.mSelectedFragment = fragment;
    }

    @Override
    public void setSelectedDrawerItem(DrawerItemBaseFragment fragment) {
        this.mSelectedDrawerItemFragment = fragment;
    }

    @Override
    public void popBackStack() {
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void popBackStackTillTag(String tag) {
        getSupportFragmentManager().popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void addFragment(BaseFragment fragment, boolean withAnimation) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (withAnimation) {
            // TO ENABLE FRAGMENT ANIMATION
            // Format: setCustomAnimations(old_frag_exit, new_frag_enter, old_frag_enter, new_frag_exit);
            ft.setCustomAnimations(R.anim.fragment_slide_in_left, R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_right);
        }

        ft.replace(R.id.main_activity_content_frame, fragment, fragment.getTagText());
        ft.addToBackStack(fragment.getTagText());
        ft.commit();
    }

    @Override
    public void onHomeFragmentInteraction(int position, FeedsAdapter adapter) {
        String imageUrl = adapter.getItem(position).images.large;
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, FeedItemDetailActivity.class);
        intent.putExtra(FeedItemDetailActivity.FEED_ITEM_IMAGE_URL, imageUrl);
        startActivity(intent);
    }

}