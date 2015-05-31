package com.chen.jokesreader.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chen.jokesreader.R;
import com.chen.jokesreader.ui.adapter.FeedsAdapter;
import com.chen.jokesreader.ui.adapter.NavDrawerListAdapter;
import com.chen.jokesreader.ui.base.DrawerItemBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * The Fragment that shows the news.
 */
public class HomeFragment extends DrawerItemBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public final static String TAG = "Home Fragment";

    private OnHomeFragmentInteractionListener mListener;

    //UI
    private RecyclerView mFeedsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_home, container, false);

        //Set up the components and set listener.
        mFeedsRecyclerView = (RecyclerView) view.findViewById(R.id.feeds_list_rv);
        mFeedsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFeedsRecyclerView.setAdapter(new FeedsAdapter());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.purple, R.color.red, R.color.black);

        setmListener();

        return view;
    }

    private void setmListener(){

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
                    int position = rv.getChildPosition(child);
                    onButtonPressed();
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onHomeFragmentInteraction();
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
        //TODO
        refreshData();
    }

    private void refreshData() {
        //TODO:network operations
        mSwipeRefreshLayout.setRefreshing(true);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity
     */
    public interface OnHomeFragmentInteractionListener {
        //TODO:add the arguments
        void onHomeFragmentInteraction();
    }

}
