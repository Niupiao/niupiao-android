package com.niupiao.niupiao.requesters;

import android.text.TextUtils;
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

    public enum Status {
        USER_EXISTS(Constants.JsonApi.Facebook.ResponseStatus.USER_EXISTS),
        USER_CREATED(Constants.JsonApi.Facebook.ResponseStatus.USER_CREATED);

        private String status;

        private Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public static Status fromString(String s) {
            if (s != null) {
                for (Status status1 : Status.values()) {
                    if (TextUtils.equals(s, status1.getStatus())) {
                        return status1;
                    }
                }
            }
            return null;
        }
    }

    public interface OnLoginWithFacebookListener extends VolleyCallback {
        public void onLoginWithFacebook(Status status, ApiKey apiKey, User user);
    }

    public static void login(final OnLoginWithFacebookListener listener, GraphUser user) {
        // we provide our login credentials so server knows who's requesting an access token
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            Object email = user.getProperty("email");
            if (email != null) {
                jsonObject.put(Constants.JsonApi.Facebook.User.EMAIL, email.toString());
            }
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
                Constants.JsonApi.EndPoints.FACEBOOK_LOGIN_URL,
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
                                Status status = Status.fromString(jsonObject.getString(Constants.JsonApi.Response.STATUS));
                                listener.onLoginWithFacebook(status, apiKey, user);
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
