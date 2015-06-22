package com.chen.jokesreader.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.chen.jokesreader.R;
import com.chen.jokesreader.utils.ToastUtils;
import com.chen.jokesreader.utils.image.ImageCacheManager;
import com.chen.jokesreader.model.Feed;

/**
 * Created by ChenSir on 2015/5/31 0031.
 */
public class FeedsAdapter extends BaseAbstractRecycleCursorAdapter {

    private LayoutInflater mLayoutInflater;

    public FeedsAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
        mHeaderCount = 0;
        mBottomCount = 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {

        if (holder instanceof ContentViewHolder) {
            Feed mItem = Feed.fromCursor(cursor);
            if (((ContentViewHolder) holder).imageRequest != null) {
                ((ContentViewHolder) holder).imageRequest.cancelRequest();
            }
            ((ContentViewHolder) holder).mFeedCaption_txt.setText(mItem.caption);
            ((ContentViewHolder) holder).imageRequest = ImageCacheManager.loadImage(mItem.images.normal, ImageLoader.getImageListener(((ContentViewHolder) holder).mFeed_img, R.drawable.empty_image, R.drawable.empty_image), 0, 0);

        } else if (holder instanceof BottomViewHolder) {

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.feed_item_card, parent, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {
        return new BottomViewHolder(mLayoutInflater.inflate(R.layout.btn_content_load_more, parent, false));
    }

    @Override
    public int getContentItemCount() {

        return getCursor().getCount();

    }

    @Override
    public Feed getItem(int position) {
        Feed mItem = Feed.fromCursor((Cursor) super.getItem(position));
        return mItem;
    }


    public class ContentViewHolder extends RecyclerView.ViewHolder {

        public ImageView mFeed_img;
        public TextView mFeedCaption_txt;

        public ImageLoader.ImageContainer imageRequest;

        public ContentViewHolder(View itemView) {
            super(itemView);

            mFeed_img = (ImageView) itemView.findViewById(R.id.feed_item_img);
            mFeedCaption_txt = (TextView) itemView.findViewById(R.id.feed_item_caption_txt);
        }
    }


    public class BottomViewHolder extends RecyclerView.ViewHolder {

        public Button mLoadMore_btn;

        public BottomViewHolder(View itemView) {
            super(itemView);

            mLoadMore_btn = (Button) itemView.findViewById(R.id.btn_load_more);
        }
    }

}
