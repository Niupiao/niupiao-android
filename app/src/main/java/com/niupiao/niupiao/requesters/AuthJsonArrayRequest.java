package com.niupiao.niupiao.requesters;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevinchen on 2/18/15.
 */
public class AuthJsonArrayRequest extends JsonArrayRequest {

    protected ResourceCallback callback;
    protected Map<String, String> headers;

    public AuthJsonArrayRequest(int method,
                                ResourceCallback callback,
                                String url,
                                Response.Listener<JSONArray> listener,
                                JSONObject jsonObject,
                                Map<String, String> headers) {
        super(method, url, jsonObject, listener, new NiuErrorListener(callback));
        this.headers = headers;
        this.callback = callback;
    }

    public AuthJsonArrayRequest(ResourceCallback callback, String url, Response.Listener<JSONArray> listener, Map<String, String> headers) {
        this(Method.GET, callback, url, listener, null, headers);
    }

    public AuthJsonArrayRequest(ResourceCallback callback, String url, Response.Listener<JSONArray> listener, JSONObject jsonObject) {
        this(Method.GET, callback, url, listener, jsonObject, null);
    }

    public AuthJsonArrayRequest(ResourceCallback callback, String url, Response.Listener<JSONArray> listener) {
        this(Method.GET, callback, url, listener, null, null);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        if (headers != null) {
            for (String header : headers.keySet()) {
                map.put(header, headers.get(header));
            }
        }
        map.put("Content-Type", "application/json");
        map.put("Authorization", "Token token=\"" + callback.getAccessToken() + "\"");
        return map;
    }

}
