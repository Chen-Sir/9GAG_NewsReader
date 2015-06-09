package com.chen.jokesreader.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.chen.jokesreader.App;
import com.chen.jokesreader.R;
import com.chen.jokesreader.api.GagApi;
import com.chen.jokesreader.provider.FeedsDataHelper;
import com.chen.jokesreader.utils.image.GsonRequest;
import com.chen.jokesreader.utils.image.RequestManager;
import com.chen.jokesreader.model.Category;
import com.chen.jokesreader.model.Feed;
import com.chen.jokesreader.ui.adapter.FeedsAdapter;
import com.chen.jokesreader.ui.base.DrawerItemBaseFragment;
import com.chen.jokesreader.utils.TaskUtils;
import com.chen.jokesreader.utils.ToastUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * The Fragment that shows the news.
 */
public class HomeFragment extends DrawerItemBaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    public final static String TAG = "Home Fragment";

    private OnHomeFragmentInteractionListener mListener;

    //UI
    private RecyclerView mFeedsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private FeedsDataHelper mDataHelper;
    private FeedsAdapter mAdapter;
    private String mPage = "0";
    private Category mCategory;


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_home, container, false);
        Log.e(TAG,"onCreateView");

        //Set "hot" as the default category and would update logic later.
        mCategory = Category.valueOf(Category.hot.name());
        mDataHelper = new FeedsDataHelper(App.getAppContext(), mCategory);
        //Set up the components and set listener.
        mFeedsRecyclerView = (RecyclerView) view.findViewById(R.id.feeds_list_rv);
        mFeedsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FeedsAdapter(getActivity());
        mFeedsRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.purple, R.color.red, R.color.black);
        setListener();

        getLoaderManager().initLoader(0, null, this);
        loadFirst();

        return view;
    }

    private void setListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);

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

        mFeedsRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    onButtonPressed(position, mAdapter);
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


    public void onButtonPressed(int position, FeedsAdapter adapter) {
        if (mListener != null) {
            mListener.onHomeFragmentInteraction(position, adapter);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnHomeFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        loadFirst();
    }

    private void refreshData(String next) {
        if (!mSwipeRefreshLayout.isRefreshing() && ("0".equals(next))) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
        executeRequest(new GsonRequest(String.format(GagApi.LIST, mCategory.name(), next), Feed.FeedRequestData.class, responseListener(), errorListener()));
    }

    private void executeRequest(GsonRequest gsonRequest) {
        RequestManager.addRequest(gsonRequest, this);
    }

    private void loadFirst() {
        mPage = "0";
        refreshData(mPage);
    }

    private void loadNext() {
        refreshData(mPage);
    }


    private Response.Listener<Feed.FeedRequestData> responseListener() {
        //TODO:The logic of refreshing data should be update
        final boolean isRefreshFromTop = ("0".equals(mPage));
        return new Response.Listener<Feed.FeedRequestData>() {
            @Override
            public void onResponse(final Feed.FeedRequestData response) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        if (isRefreshFromTop) {
                            mDataHelper.deleteAll();
                        }
                        mPage = response.getPage();

                        ArrayList<Feed> feeds = response.data;
                        mDataHelper.bulkInsert(feeds);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        if (isRefreshFromTop) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        } else {
                            //update later
                        }
                    }
                });
            }
        };
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showShort(R.string.load_failed);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mDataHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
        if (data != null && data.getCount() == 0) {
            loadFirst();
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }


    public interface OnHomeFragmentInteractionListener {
        /**
         * RecyclerView onInterceptTouchEvent
         *
         * @param position where be pressed
         */
        void onHomeFragmentInteraction(int position, FeedsAdapter adapter);
    }

}
