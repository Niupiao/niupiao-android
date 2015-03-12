package com.niupiao.niupiao.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.ApiKey;
import com.niupiao.niupiao.models.User;
import com.niupiao.niupiao.requesters.LoginRequester;
import com.niupiao.niupiao.requesters.LoginWithFacebookRequester;

import java.util.Arrays;
import java.util.Map;


/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends Activity implements
        LoginRequester.OnLoginListener,
        LoginWithFacebookRequester.OnLoginWithFacebookListener,
        Session.StatusCallback {

    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final String[] FACEBOOK_PERMISSIONS = new String[]{"public_profile"};

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private UiLifecycleHelper uiHelper;

    private boolean isAttemptingLogin = false;

    private void stopProgress() {
        isAttemptingLogin = false;
        showProgress(false);
    }

    private void onClickLogin() {
        Session.StatusCallback statusCallback = this;
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this)
                    .setPermissions(Arrays.asList("public_profile"))
                    .setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    @Override
    public void onLoginWithFacebook() {
        // TODO JSON API will create a Niupiao account associated to a the FB account, then respond with the user and api key
    }

    @Override
    public void call(Session session, SessionState sessionState, Exception e) {
        if (session != null && session.isOpened()) {
            Log.d("LoginActivity", "Facebook called back!!!!!");
            makeMeRequest(session);
        }
    }

    private void makeMeRequest(final Session session) {
        // Make an API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                LoginWithFacebookRequester.login(LoginActivity.this, user);
                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }

    @Override
    public void onLogin(ApiKey apiKey, User user) {
        stopProgress();
        saveApiKey(apiKey);

        final String username = mUsernameView.getText().toString();
        final String password = mPasswordView.getText().toString();

        // Register device with Parse
//        ParseUtils.signup(this, username, password);

        // Save login credentials to SharedPrefs
        saveLoginCredentials(username, password);

        login(user);

    }

    private void login(User user) {
        // Show the main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY_FOR_USER, user);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        stopProgress();
    }

    @Override
    public void onVolleyError(VolleyError volleyError) {
        Toast.makeText(this, "Oops:" + volleyError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        stopProgress();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uiHelper = new UiLifecycleHelper(this, this);
        uiHelper.onCreate(savedInstanceState);

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.et_username);
        mPasswordView = (EditText) findViewById(R.id.et_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        populateFieldsFromSharedPrefs();

        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


        LoginButton authButton = (LoginButton) findViewById(R.id.btn_facebook_login);
        authButton.setReadPermissions(Arrays.asList(FACEBOOK_PERMISSIONS));

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        // FONTS
        Typeface robotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        loginButton.setTypeface(robotoBold);
        mUsernameView.setTypeface(robotoBold);
        mPasswordView.setTypeface(robotoBold);
    }

    private void populateFieldsFromSharedPrefs() {
        SharedPreferences sp = getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, MODE_PRIVATE);
        boolean remembered = sp.getBoolean(Constants.SharedPrefs.REMEMBER_ME, false);
        if (remembered) {
            mUsernameView.setText(sp.getString(Constants.SharedPrefs.USERNAME, ""));
            mPasswordView.setText(sp.getString(Constants.SharedPrefs.PASSWORD, ""));
        }
        CheckBox rememberMe = (CheckBox) findViewById(R.id.cb_remember_me);
        rememberMe.setChecked(remembered);
    }

    private void saveApiKey(ApiKey apiKey) {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, MODE_PRIVATE).edit();
        editor.putString(Constants.SharedPrefs.ACCESS_TOKEN, apiKey.getAccessToken());
        editor.putLong(Constants.SharedPrefs.ACCESS_TOKEN_EXPIRES_AT, apiKey.getExpiresAt());
        editor.apply();
    }

    private void saveLoginCredentials(String username, String password) {
        CheckBox rememberMe = (CheckBox) findViewById(R.id.cb_remember_me);
        if (rememberMe.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, MODE_PRIVATE).edit();
            editor.putString(Constants.SharedPrefs.USERNAME, username);
            editor.putString(Constants.SharedPrefs.PASSWORD, password);
            editor.putBoolean(Constants.SharedPrefs.REMEMBER_ME, true);
            editor.apply();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Ensure that we can only try to login once.
        if (isAttemptingLogin) {
            return;
        }
        isAttemptingLogin = true;

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            LoginRequester.login(username, password, this);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

}



