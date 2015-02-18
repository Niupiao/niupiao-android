package com.niupiao.niupiao.requesters;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by kevinchen on 2/18/15.
 */
public class NiuErrorListener implements Response.ErrorListener {

    private ResourceCallback callback;

    public NiuErrorListener(ResourceCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        callback.onVolleyError(volleyError);
    }
}
