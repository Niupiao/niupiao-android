package com.niupiao.niupiao.requesters;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by kevinchen on 2/18/15.
 */
public class NiuErrorListener implements Response.ErrorListener {

    private VolleyCallback callback;

    public NiuErrorListener(VolleyCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        callback.onVolleyError(volleyError);
    }
}
