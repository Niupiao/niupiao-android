package com.niupiao.niupiao.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.ApiKey;
import com.niupiao.niupiao.models.User;
import com.niupiao.niupiao.requesters.RegistrationRequester;

/**
 * Created by kevinchen on 2/25/15.
 */
public class RegistrationActivity extends Activity implements RegistrationRequester.OnRegistrationListener {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;

    @Override
    public void onVolleyError(VolleyError volleyError) {
        Toast.makeText(this, "VE: " + volleyError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegistration(User user, ApiKey apiKey) {
        Log.d("callback, user -- ", user.toString());
        Log.d("callback, apiK -- ", apiKey.toString());
    }

    @Override
    public void onRegistrationFailure(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");

        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setTypeface(font);

        firstNameEditText = (EditText) findViewById(R.id.et_first_name);
        lastNameEditText = (EditText) findViewById(R.id.et_last_name);
        emailEditText = (EditText) findViewById(R.id.et_email);
        passwordEditText = (EditText) findViewById(R.id.et_password);
        passwordConfirmEditText = (EditText) findViewById(R.id.et_confirm_password);

        Button registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidFields()) {
                    register();
                }
            }
        });

    }

    private boolean isValidFields() {
        return isValidField(firstNameEditText) && isValidField(lastNameEditText) && isValidField(emailEditText)
                && isValidField(passwordEditText) && isValidField(passwordConfirmEditText)
                && TextUtils.equals(passwordEditText.getText(), passwordConfirmEditText.getText());
    }

    private boolean isValidField(EditText editText) {
        if (editText == null) {
            throw new IllegalArgumentException("null EditText");
        }
        return !TextUtils.isEmpty(editText.getText());
    }

    private void register() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        RegistrationRequester.register(this, firstName, lastName, email, password);
    }
}
