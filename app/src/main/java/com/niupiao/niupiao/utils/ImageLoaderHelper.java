package com.niupiao.niupiao.utils;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;
import com.niupiao.niupiao.NiupiaoApplication;

/**
 * Created by kevinchen on 1/24/15.
 */
public class ImageLoaderHelper {
    private static ImageLoaderHelper mInstance = null;

    private final ImageLoader mImageLoader;
    private final ImageLoader.ImageCache mImageCache;

    private final int cacheSize;

    public static void init(int cacheSize) {
        mInstance = new ImageLoaderHelper(cacheSize);
    }

    public static ImageLoaderHelper getInstance() {
        if (mInstance == null)
            throw new RuntimeException("Never initialized: " + ImageLoaderHelper.class.getSimpleName());
        return mInstance;
    }

    private ImageLoaderHelper(int cacheSize) {
        this.cacheSize = cacheSize;
        mImageCache = new BitmapLruImageCache(cacheSize);
        mImageLoader = new ImageLoader(NiupiaoApplication.getRequestQueue(), mImageCache);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    // USE HASH B/C some chars in URL are not supported in the cache key
    private String createKey(String url) {
        return String.valueOf(url.hashCode());
    }

    public Bitmap getBitmap(String url) {
        try {
            return mImageCache.getBitmap(createKey(url));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Image cache not initialized");
        }
    }

    public void putBitmap(String url, Bitmap bitmap) {
        try {
            mImageCache.putBitmap(createKey(url), bitmap);
        } catch (NullPointerException e) {
            throw new IllegalStateException("Image cache not initialized");
        }
    }
}

