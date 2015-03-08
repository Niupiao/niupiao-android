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
 * Created by kevinchen on 3/8/15.
 */
public class RegistrationRequester {

    // Rails params keys
    public static final String PARAM_LEGAL_NAME = "legal_name";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_CELL_PHONE = "cell_phone";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_PASSWORD_CONFIRM = "password_confirm";

    private static final String TAG = RegistrationRequester.class.getSimpleName();

    public interface OnRegistrationListener extends VolleyCallback {
        public void onRegistration(User user, ApiKey apiKey);
        public void onRegistrationFailure(String errorMessage);
    }

    public static void register(final OnRegistrationListener listener, String legalName, String username, String cellPhone, String email, String password, String passwordConfirm) {
        // we provide our login credentials so server knows who's requesting an access token
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(PARAM_LEGAL_NAME, legalName);
            jsonObject.put(PARAM_USERNAME, username);
            jsonObject.put(PARAM_CELL_PHONE, cellPhone);
            jsonObject.put(PARAM_EMAIL, email);
            jsonObject.put(PARAM_PASSWORD, password);
            jsonObject.put(PARAM_PASSWORD_CONFIRM, passwordConfirm);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ResourceRequest request = new ResourceRequest(
                listener,
                Request.Method.POST,
                Constants.Url.SIGNUP_URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, jsonObject.toString());
                        try {
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                ApiKey apiKey = ApiKeyDeserializer.fromJsonObject(jsonObject.getJSONObject("api_key"));
                                User user = UserDeserializer.fromJsonObject(jsonObject.getJSONObject("user"));
                                listener.onRegistration(user, apiKey);
                            } else {
                                listener.onRegistrationFailure(jsonObject.getString("message"));
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
