package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.my_tickets.TicketFragment;

/**
 * Created by Ryan on 2/27/15.
 */
public class TicketActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        show();
    }

    private void show() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = TicketFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
