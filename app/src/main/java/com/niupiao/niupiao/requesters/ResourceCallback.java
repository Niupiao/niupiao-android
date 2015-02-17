package com.niupiao.niupiao.requesters;

import com.android.volley.VolleyError;

/**
 * Created by kevinchen on 2/17/15.
 */
public interface ResourceCallback {

    public void onVolleyError(VolleyError volleyError);
}
