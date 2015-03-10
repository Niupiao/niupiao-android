package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.facebook.model.GraphUser;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.ApiKeyDeserializer;
import com.niupiao.niupiao.deserializers.UserDeserializer;
import com.niupiao.niupiao.models.ApiKey;
import com.niupiao.niupiao.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevinchen on 3/10/15.
 */
public class LoginWithFacebookRequester {

    private static final String TAG = LoginWithFacebookRequester.class.getSimpleName();

    public interface OnLoginWithFacebookListener extends VolleyCallback {
        public void onLoginWithFacebook();
    }

    public static void login(final OnLoginWithFacebookListener listener, GraphUser user) {
        // we provide our login credentials so server knows who's requesting an access token
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(Constants.JsonApi.Facebook.User.BIRTHDAY, user.getBirthday());
            jsonObject.put(Constants.JsonApi.Facebook.User.FIRST_NAME, user.getFirstName());
            jsonObject.put(Constants.JsonApi.Facebook.User.MIDDLE_NAME, user.getMiddleName());
            jsonObject.put(Constants.JsonApi.Facebook.User.LAST_NAME, user.getLastName());
            jsonObject.put(Constants.JsonApi.Facebook.User.NAME, user.getName());
            jsonObject.put(Constants.JsonApi.Facebook.User.USERNAME, user.getUsername());
            jsonObject.put(Constants.JsonApi.Facebook.User.LOCATION, user.getLocation());
            jsonObject.put(Constants.JsonApi.Facebook.User.LINK, user.getLink());
            jsonObject.put(Constants.JsonApi.Facebook.User.ID, user.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "SENDING JSON TO OUR SERVER: " + jsonObject.toString());

        ResourceRequest request = new ResourceRequest(
                listener,
                Request.Method.POST,
                Constants.Url.FACEBOOK_LOGIN_URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, jsonObject.toString());
                        try {
                            boolean success = jsonObject.getBoolean(Constants.JsonApi.Response.SUCCESS);
                            if (success) {
                                /**
                                 * TODO server will pass back {@link com.niupiao.niupiao.models.User} and {@link com.niupiao.niupiao.models.ApiKey}
                                 * so we can serialize them and pass back to listener
                                 */
                                listener.onLoginWithFacebook();
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
