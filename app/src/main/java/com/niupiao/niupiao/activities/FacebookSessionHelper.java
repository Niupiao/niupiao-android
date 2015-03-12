package com.niupiao.niupiao.activities;

import android.app.Activity;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.requesters.LoginWithFacebookRequester;

import java.util.Arrays;

/**
 * Created by kevinchen on 3/12/15.
 */
public class FacebookSessionHelper implements Session.StatusCallback {

    private static final String TAG = FacebookSessionHelper.class.getSimpleName();

    private LoginWithFacebookRequester.OnLoginWithFacebookListener listener;

    public FacebookSessionHelper(LoginWithFacebookRequester.OnLoginWithFacebookListener listener) {
        this.listener = listener;
    }

    @Override
    public void call(final Session session, SessionState sessionState, Exception e) {
        if (session != null && session.isOpened()) {
            Log.d(TAG, "Request email permission.......");
            // Make sure we get email permission
            requestPermissions(session, listener,
                    Constants.FacebookApi.Permissions.EMAIL
            );
            Log.d(TAG, "Sending FB info to our server.......");
            loginOrRegisterWithFacebook(session, listener);
        }
    }

    private static void loginOrRegisterWithFacebook(final Session session,
                                                    final LoginWithFacebookRequester.OnLoginWithFacebookListener listener) {
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                LoginWithFacebookRequester.login(listener, user);
                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }

    private static void requestPermissions(Session session,
                                           LoginWithFacebookRequester.OnLoginWithFacebookListener listener,
                                           String... permissions) {
        Session.NewPermissionsRequest newPermissionsRequest =
                new Session.NewPermissionsRequest((Activity) listener, Arrays.asList(permissions));
        session.requestNewReadPermissions(newPermissionsRequest);
    }

}
