package com.chen.jokesreader.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.jokesreader.App;
import com.chen.jokesreader.R;
import com.chen.jokesreader.utils.typeface.ManagerTypeface;
import com.chen.jokesreader.ui.customviews.TintOnStateImageView;

/**
 * Created by ChenSir on 2015/5/14 0014.
 */
public class NavDrawerListAdapter extends RecyclerView.Adapter<NavDrawerListAdapter.ViewHolder> {

    // Declaring Variable to Understand which View is divider
    public static final int TYPE_DIVIDER = 0;
    public static final int TYPE_ITEM = 1;

    private String[] mItemTitles;
    private int[] mItemIcons;


    public NavDrawerListAdapter(String[] mItemTitles, int[] mItemIcons) {
        this.mItemTitles = mItemTitles;
        this.mItemIcons = mItemIcons;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        int mHolderId;

        public TintOnStateImageView mItemIcon_img;
        public TextView mItemTitle_txt;

        public View mDivider_v;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if (viewType == TYPE_ITEM) {
                // Creating TextView object with the id of textView from nav_item_row.xml.
                mItemTitle_txt = (TextView) itemView.findViewById(R.id.navigation_drawer_items_title);
                // Creating ImageView object with the id of ImageView from nav_item_row.xml.
                mItemIcon_img = (TintOnStateImageView) itemView.findViewById(R.id.navigation_drawer_items_list_icon);
                // setting holder id as 1 as the object being populated are of type item row.
                mHolderId = 1;
            } else {
                mDivider_v = itemView.findViewById(R.id.nav_drawer_divider);
                // Setting holder id = 0 as the object being populated are of type divider view
                mHolderId = 0;
            }
        }
    }


    /**
     * Check what type of view is being passed with the following method.
     */
    @Override
    public int getItemViewType(int position) {
        if (isDividerView(position)) {
            return TYPE_DIVIDER;
        }
        return TYPE_ITEM;
    }

    private boolean isDividerView(int position) {
        //The third line is divider. Here is just a simple check.
        return position == 2;
    }

    /**
     * Override a method which is called when the item in a row is needed to be displayed, here the int position
     * means item at which position is being constructed to be displayed and the holder id of the holder object mean
     * which view type is being created 1 for item row.
     */
    @Override
    public void onBindViewHolder(NavDrawerListAdapter.ViewHolder holder, int position) {

        //set the default selected item
        if (position == 0)
            holder.itemView.setSelected(true);

        if (holder.mHolderId == 1) {
            if (position < 2) {
                holder.mItemTitle_txt.setText(mItemTitles[position]);
                holder.mItemIcon_img.setImageResource(mItemIcons[position]);
                holder.itemView.setTag(App.getAppContext().getResources().getString(R.string.tag_nav_drawer_main_entry));
            } else if (position > 2) {
                holder.mItemTitle_txt.setText(mItemTitles[position - 1]);
                holder.mItemIcon_img.setImageResource(mItemIcons[position - 1]);
                holder.itemView.setTag(App.getAppContext().getResources().getString(R.string.tag_nav_drawer_special_entry));
            }

            holder.mItemTitle_txt.setTypeface(ManagerTypeface.getTypeface(App.getAppContext(), R.string.typeface_roboto_medium));

        } else if (holder.mHolderId == 0) {
            //do none but only display divider.
        }

    }

    @Override
    public int getItemCount() {
        //the number of items in the list will be +1 the titles including 1 divider view.
        return mItemTitles.length + 1;
    }

    @Override
    public NavDrawerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            //Inflating the layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item_row, parent, false);
            ViewHolder mViewHolderItem = new ViewHolder(v, viewType);

            return mViewHolderItem;

        } else if (viewType == TYPE_DIVIDER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_divider, parent, false);
            v.setTag(TYPE_DIVIDER);
            ViewHolder mViewHolderDivider = new ViewHolder(v, viewType);
            return mViewHolderDivider;

        }
        return null;
    }
}
