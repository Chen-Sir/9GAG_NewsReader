package com.chen.jokesreader.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.jokesreader.R;

/**
 * Created by ChenSir on 2015/5/31 0031.
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mFeed_img;
        private TextView mFeedCaption_txt;

        public ViewHolder(View itemView) {
            super(itemView);

            mFeed_img = (ImageView) itemView.findViewById(R.id.feed_item_img);
            mFeedCaption_txt = (TextView) itemView.findViewById(R.id.feed_item_caption_txt);
        }
    }
}
