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
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.ApiKey;
import com.niupiao.niupiao.models.User;
import com.niupiao.niupiao.requesters.LoginRequester;
import com.niupiao.niupiao.requesters.LoginWithFacebookRequester;

import java.util.Arrays;


/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends Activity implements
        LoginRequester.OnLoginListener,
        LoginWithFacebookRequester.OnLoginWithFacebookListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private UiLifecycleHelper uiHelper;

    private boolean isAttemptingLogin = false;

    @Override
    public void onLoginWithFacebook(LoginWithFacebookRequester.Status status, ApiKey apiKey, User user) {

        // Set field to FB-authorized user's email
        mEmailView.setText(user.getEmail());

        // TODO don't hardcode password, get from user?
        mPasswordView.setText("foobar");

        switch (status) {
            case USER_CREATED:
                // TODO use dialog for this note
                Toast.makeText(this, "Created user for " + user.getEmail(), Toast.LENGTH_LONG).show();
                break;
            case USER_EXISTS:
                Toast.makeText(this, "User exists for " + user.getEmail(), Toast.LENGTH_LONG).show();
                // TODO check if they still need a password or if they want to change their username
                // TODO check ApiKey valid/non-null before logging in
                break;
        }

        // Log us in!
        onLogin(apiKey, user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.et_username);
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
        Session.StatusCallback statusCallback = new FacebookSessionHelper(this);
        authButton.setSessionStatusCallback(statusCallback);
        authButton.setReadPermissions(Arrays.asList(Constants.FacebookApi.Permissions.getPermissions()));

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        // FONTS
        Typeface robotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        loginButton.setTypeface(robotoBold);
        mEmailView.setTypeface(robotoBold);
        mPasswordView.setTypeface(robotoBold);


        // After initializing widgets, initialize Facebook lifecycle helper
        uiHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState sessionState, Exception e) {
                if (session.isOpened()) {
                    // TODO we're already logged in from FB, so login
                    // User user = serializeUserFromDB(getEmailFromSharedPrefs());
                    // login(user);
                }

            }
        });
        uiHelper.onCreate(savedInstanceState);

    }

    //////////////////////////////////////////////////////////////////
    //////////////////////// SHARED PREFS
    //////////////////////////////////////////////////////////////////

    private void populateFieldsFromSharedPrefs() {
        SharedPreferences sp = getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, MODE_PRIVATE);
        boolean remembered = sp.getBoolean(Constants.SharedPrefs.REMEMBER_ME, false);
        if (remembered) {
            mEmailView.setText(sp.getString(Constants.SharedPrefs.USERNAME, ""));
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


    //////////////////////////////////////////////////////////////////////////
    ////////// LOGIN METHODS /////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

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
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        email = email.replaceAll(" ", "");
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
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

            LoginRequester.login(this, email, password);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void stopProgress() {
        isAttemptingLogin = false;
        showProgress(false);
    }

    @Override
    public void onLogin(ApiKey apiKey, User user) {
        stopProgress();
        saveApiKey(apiKey);

        final String username = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        // TODO Register device with Parse
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


    //////////////////////////////////////////////////////////////////////////
    ////////// LIFE CYCLE METHODS ////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

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



