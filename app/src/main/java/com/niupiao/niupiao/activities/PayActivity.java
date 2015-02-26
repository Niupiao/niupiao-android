package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.Event;

/**
 * Created by kevinchen on 2/25/15.
 */
public class PayActivity extends ActionBarActivity {

    public static final String INTENT_KEY_FOR_EVENT = "event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_confirm);

        Event event = getIntent().getParcelableExtra(INTENT_KEY_FOR_EVENT);


    }
}
