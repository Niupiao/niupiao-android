package com.niupiao.niupiao.requesters;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by kevinchen on 2/17/15.
 */
public class VolleyRequester {
    protected static Response.ErrorListener createNewErrorListener(final ResourceCallback callback) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onVolleyError(volleyError);
            }
        };
    }
}
