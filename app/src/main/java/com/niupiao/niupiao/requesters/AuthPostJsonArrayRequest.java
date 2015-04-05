package com.niupiao.niupiao.requesters;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by kevin on 4/4/15.
 */
public class AuthPostJsonArrayRequest extends AuthJsonArrayRequest {

    public AuthPostJsonArrayRequest(ResourceCallback callback,
                                    String url,
                                    Response.Listener<JSONArray> listener,
                                    JSONObject jsonObject,
                                    Map<String, String> headers) {
        super(Method.POST, callback, url, listener, jsonObject, headers);
    }

    public AuthPostJsonArrayRequest(ResourceCallback callback,
                                    String url,
                                    Response.Listener<JSONArray> listener,
                                    JSONObject jsonObject) {
        this(callback, url, listener, jsonObject, null);
    }

}
