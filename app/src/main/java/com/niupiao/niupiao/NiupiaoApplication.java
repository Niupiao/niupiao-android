package com.niupiao.niupiao;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.niupiao.niupiao.utils.ImageLoaderHelper;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class NiupiaoApplication extends Application {

    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        // init queue BEFORE the image helper!!
        requestQueue = Volley.newRequestQueue(this);
        ImageLoaderHelper.init(1024 * 1024 * 10);
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
