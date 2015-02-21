package com.niupiao.niupiao.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.ApiKey;
import com.niupiao.niupiao.models.User;
import com.niupiao.niupiao.requesters.LoginRequester;


/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends Activity implements LoginRequester.OnLoginListener {

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private boolean isAttemptingLogin = false;

    private void stopProgress() {
        isAttemptingLogin = false;
        showProgress(false);
    }

    @Override
    public String getAccessToken() {
        return null;
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

        // Show the main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY_FOR_USER, user);
        startActivity(intent);
        finish();

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

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
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

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateFieldsFromSharedPrefs() {
        SharedPreferences sp = getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, MODE_PRIVATE);
        boolean remembered = sp.getBoolean(Constants.SharedPrefs.REMEMBER_ME, false);
        if (remembered) {
            mUsernameView.setText(sp.getString(Constants.SharedPrefs.USERNAME, ""));
            mPasswordView.setText(sp.getString(Constants.SharedPrefs.PASSWORD, ""));
        }
        CheckBox rememberMe = (CheckBox) findViewById(R.id.remember_me);
        rememberMe.setChecked(remembered);
    }

    private void saveApiKey(ApiKey apiKey) {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, MODE_PRIVATE).edit();
        editor.putString(Constants.SharedPrefs.ACCESS_TOKEN, apiKey.getAccessToken());
        editor.putLong(Constants.SharedPrefs.ACCESS_TOKEN_EXPIRES_AT, apiKey.getExpiresAt());
        editor.apply();
    }

    private void saveLoginCredentials(String username, String password) {
        CheckBox rememberMe = (CheckBox) findViewById(R.id.remember_me);
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

}



