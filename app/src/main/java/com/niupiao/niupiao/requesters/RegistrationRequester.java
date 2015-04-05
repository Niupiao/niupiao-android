package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.ApiKeyDeserializer;
import com.niupiao.niupiao.deserializers.UserDeserializer;
import com.niupiao.niupiao.models.ApiKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevinchen on 3/8/15.
 */
public class RegistrationRequester {

    private static final String TAG = RegistrationRequester.class.getSimpleName();

    public interface OnRegistrationListener extends VolleyCallback {
        public void onRegistration(com.niupiao.niupiao.models.User user, ApiKey apiKey);

        public void onRegistrationFailure(String errorMessage);
    }

    public static void register(final OnRegistrationListener listener,
                                String firstName,
                                String lastName,
                                String email,
                                String password) {
        // we provide our login credentials so server knows who's requesting an access token
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Constants.JsonApi.User.FIRST_NAME, firstName);
            jsonObject.put(Constants.JsonApi.User.LAST_NAME, lastName);
            jsonObject.put(Constants.JsonApi.User.EMAIL, email);
            jsonObject.put(Constants.JsonApi.User.PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AuthJsonObjectRequest request = new AuthJsonObjectRequest(
                listener,
                Request.Method.POST,
                Constants.JsonApi.EndPoints.SIGNUP_URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, jsonObject.toString());
                        try {
                            boolean success = jsonObject.getBoolean(Constants.JsonApi.Response.SUCCESS);
                            if (success) {
                                ApiKey apiKey = ApiKeyDeserializer.fromJsonObject(jsonObject.getJSONObject(Constants.JsonApi.Response.API_KEY));
                                com.niupiao.niupiao.models.User user = UserDeserializer.fromJsonObject(jsonObject.getJSONObject(Constants.JsonApi.Response.USER));
                                listener.onRegistration(user, apiKey);
                            } else {
                                listener.onRegistrationFailure(jsonObject.getString(Constants.JsonApi.Response.MESSAGE));
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
