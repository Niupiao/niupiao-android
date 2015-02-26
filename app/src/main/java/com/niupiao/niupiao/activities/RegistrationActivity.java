package com.niupiao.niupiao.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.niupiao.niupiao.R;

/**
 * Created by kevinchen on 2/25/15.
 */
public class RegistrationActivity extends Activity {

    private EditText legalName;
    private EditText cellPhone;
    private EditText email;
    private EditText password;
    private EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        legalName = (EditText) findViewById(R.id.et_legal_name);
        cellPhone = (EditText) findViewById(R.id.et_cell_phone);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        passwordConfirm = (EditText) findViewById(R.id.et_confirm_password);

        Button registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {

    }
}
