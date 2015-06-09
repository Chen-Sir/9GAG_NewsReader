package com.chen.jokesreader.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.chen.jokesreader.R;
import com.chen.jokesreader.utils.image.ImageCacheManager;
import com.chen.jokesreader.ui.base.BaseActivity;

/**
 * Created by ChenSir on 2015/6/6 0006.
 */
public class FeedItemDetailActivity extends BaseActivity {

    public static final String FEED_ITEM_IMAGE_URL = "feed_item_image_url";

    private Toolbar mToolbar;
    private ImageView mImageView;
    private ImageLoader.ImageContainer mImageRequest;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_item_detail);

        mToolbar = getActionBarToolbar();
        mImageView = (ImageView) findViewById(R.id.activity_item_detail_img);

        getArguments();
        mImageRequest = ImageCacheManager.loadImage(imgUrl, ImageLoader.getImageListener(mImageView, R.drawable.empty_image, R.drawable.empty_image), 0, 0);

    }

    public void getArguments() {
        imgUrl = getIntent().getStringExtra(FEED_ITEM_IMAGE_URL);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageRequest.cancelRequest();
    }
}
