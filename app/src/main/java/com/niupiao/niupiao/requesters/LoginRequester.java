package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.ApiKeyDeserializer;
import com.niupiao.niupiao.deserializers.UserDeserializer;
import com.niupiao.niupiao.models.ApiKey;
import com.niupiao.niupiao.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevinchen on 2/18/15.
 */
public class LoginRequester {

    public static final String TAG = LoginRequester.class.getSimpleName();

    public interface OnLoginListener extends VolleyCallback {
        public void onLogin(ApiKey apiKey, User user);

        public void onLoginFailure(String message);
    }

    public static void login(final String username, final String password, final OnLoginListener callback) {

        // we provide our login credentials so server knows who's requesting an access token
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Constants.JsonApi.Login.USERNAME, username);
            jsonObject.put(Constants.JsonApi.Login.PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ResourceRequest request = new ResourceRequest(
                callback,
                Request.Method.POST,
                Constants.JsonApi.EndPoints.LOGIN_URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, jsonObject.toString());
                        try {
                            boolean success = jsonObject.getBoolean(Constants.JsonApi.Response.SUCCESS);
                            if (success) {
                                ApiKey apiKey = ApiKeyDeserializer.fromJsonObject(jsonObject.getJSONObject(Constants.JsonApi.Response.API_KEY));
                                User user = UserDeserializer.fromJsonObject(jsonObject.getJSONObject(Constants.JsonApi.Response.USER));
                                callback.onLogin(apiKey, user);
                            } else {
                                callback.onLoginFailure(jsonObject.getString(Constants.JsonApi.Response.MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        NiupiaoApplication.getRequestQueue().add(request);


    }
}
