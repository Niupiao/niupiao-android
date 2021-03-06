package com.niupiao.niupiao.fragments.registration;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.RegistrationActivity;
import com.niupiao.niupiao.fragments.ViewPagerFragment;
import com.niupiao.niupiao.models.ApiKey;
import com.niupiao.niupiao.models.User;
import com.niupiao.niupiao.requesters.RegistrationRequester;

/**
 * Created by Inanity on 3/26/2015.
 */
public class MoreInfoFragment extends ViewPagerFragment
        implements RegistrationRequester.OnRegistrationListener {

    RegistrationActivity activity;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;

    @Override
    public void onVolleyError(VolleyError volleyError) {
        Toast.makeText(getActivity(), "VE: " + volleyError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegistration(User user, ApiKey apiKey) {
        Log.d("callback, user -- ", user.toString());
        Log.d("callback, apiK -- ", apiKey.toString());
        activity.finish();
    }

    @Override
    public void onRegistrationFailure(String errorMessage) {
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_moreinfo, container, false);
        activity = (RegistrationActivity) getActivity();

        firstNameEditText = (EditText) root.findViewById(R.id.et_first_name);
        lastNameEditText = (EditText) root.findViewById(R.id.et_last_name);
        emailEditText = (EditText) root.findViewById(R.id.et_email);
        passwordEditText = (EditText) root.findViewById(R.id.et_password);
        passwordConfirmEditText = (EditText) root.findViewById(R.id.et_confirm_password);

        Typeface black = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Black.ttf");
        Typeface medium = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Medium.ttf");
        Typeface regular = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Regular.ttf");

        TextView title = (TextView) root.findViewById(R.id.tv_title);
        Button register = (Button) root.findViewById(R.id.btn_register);
        title.setTypeface(black);
        register.setTypeface(medium);
        firstNameEditText.setTypeface(regular);
        lastNameEditText.setTypeface(regular);
        emailEditText.setTypeface(regular);
        passwordEditText.setTypeface(regular);
        passwordConfirmEditText.setTypeface(regular);

        Button registerButton = (Button) root.findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidFields()) {
                    register();
                }
            }
        });

        return root;
    }

    public static MoreInfoFragment newInstance(int position) {
        MoreInfoFragment fragment = new MoreInfoFragment();

        return fragment;
    }

    @Override
    public String getTitle() {
        return "More Info";
    }

    private boolean isValidFields() {
        boolean test1 = isValidField(firstNameEditText);
        boolean test2 = isValidField(lastNameEditText);
        boolean test3 = validateEmail(emailEditText);
        boolean test4 = validatePassword(passwordEditText, passwordConfirmEditText);

        return test1 && test2 && test3 && test4;
    }

    private boolean isValidField(EditText editText) {
        if (editText == null) {
            throw new IllegalArgumentException("null EditText");
        }
        boolean test1 = !TextUtils.isEmpty(editText.getText());

        if (!test1) {
            editText.setError(getResources().getString(R.string.no_text_in_field));
        }

        return test1;
    }

    private boolean validateEmail(EditText email) {
        boolean test1 = Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();

        if (!test1) {
            email.setError(getResources().getString(R.string.invalid_email));
        }

        return test1;
    }

    private boolean validatePassword(EditText pw, EditText confirm) {
        String pwtext = pw.getText().toString();
        String confirmtext = confirm.getText().toString();

        boolean test1 = pwtext.equals(confirmtext);
        boolean test2 = pwtext.length() >= 6;
        boolean test3 = confirmtext.length() >= 6;

        if (!test1) {
            confirm.setError(getResources().getString(R.string.password_does_not_match));
        }
        if (!test2) {
            pw.setError(getResources().getString(R.string.password_too_short));
        }
        if (!test3) {
            confirm.setError(getResources().getString(R.string.password_too_short));
        }

        return test1 && test2 && test3;
    }

    private void register() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        firstName.replaceAll(" ", "");
        lastName.replaceAll(" ", "");
        email.replaceAll(" ", "");
        RegistrationRequester.register(this, firstName, lastName, email, password);
    }
}
