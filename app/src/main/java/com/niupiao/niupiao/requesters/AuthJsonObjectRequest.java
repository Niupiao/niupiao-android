package com.niupiao.niupiao.requesters;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevinchen on 2/23/15.
 */
public class AuthJsonObjectRequest extends JsonObjectRequest {
    private VolleyCallback callback;

    public AuthJsonObjectRequest(VolleyCallback callback, int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener) {
        super(method, url, jsonRequest, listener, new NiuErrorListener(callback));
        this.callback = callback;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        return map;
    }

}
