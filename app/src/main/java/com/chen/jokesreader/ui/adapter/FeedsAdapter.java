package com.chen.jokesreader.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.chen.jokesreader.R;
import com.chen.jokesreader.utils.image.ImageCacheManager;
import com.chen.jokesreader.model.Feed;

/**
 * Created by ChenSir on 2015/5/31 0031.
 */
public class FeedsAdapter extends BaseAbstractRecycleCursorAdapter<FeedsAdapter.ViewHolder> {

    private final LayoutInflater mLayoutInflater;

    public FeedsAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.feed_item_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        Feed mItem = Feed.fromCursor(cursor);
        if (holder.imageRequest != null) {
            holder.imageRequest.cancelRequest();
        }

        holder.mFeedCaption_txt.setText(mItem.caption);
        holder.imageRequest = ImageCacheManager.loadImage(mItem.images.normal, ImageLoader.getImageListener(holder.mFeed_img, R.drawable.empty_image, R.drawable.empty_image), 0, 0);

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Feed getItem(int position) {
        Feed mItem = Feed.fromCursor((Cursor) super.getItem(position));
        return mItem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mFeed_img;
        public TextView mFeedCaption_txt;

        public ImageLoader.ImageContainer imageRequest;

        public ViewHolder(View itemView) {
            super(itemView);

            mFeed_img = (ImageView) itemView.findViewById(R.id.feed_item_img);
            mFeedCaption_txt = (TextView) itemView.findViewById(R.id.feed_item_caption_txt);
        }
    }
}
