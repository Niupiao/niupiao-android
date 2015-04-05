package com.niupiao.niupiao.requesters;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevinchen on 2/18/15.
 */
public class ResourcesRequest extends JsonArrayRequest {

    private ResourceCallback callback;
    private Map<String, String> headers;

    public ResourcesRequest(int method, ResourceCallback callback, String url, Response.Listener<JSONArray> listener, JSONObject jsonObject) {
        super(method, url, jsonObject, listener, new NiuErrorListener(callback));
        this.callback = callback;
    }

    public ResourcesRequest(int method, ResourceCallback callback, String url, Response.Listener<JSONArray> listener, Map<String, String> headers) {
        this(method, callback, url, listener);
        this.headers = headers;
    }

    public ResourcesRequest(ResourceCallback callback, String url, Response.Listener<JSONArray> listener, Map<String, String> headers) {
        this(Method.GET, callback, url, listener, headers);
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
