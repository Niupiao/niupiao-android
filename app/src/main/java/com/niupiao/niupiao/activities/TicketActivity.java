package com.niupiao.niupiao.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.my_tickets.TicketFragment;

/**
 * Created by Ryan on 2/27/15.
 */
public class TicketActivity extends ActionBarActivity {

    public static final String INTENT_KEY_FOR_TICKET = "ticket";
    public static final String INTENT_KEY_FOR_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.niupiao_blue)));

        View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar_ticket, null);
        actionBar.setCustomView(mActionBarView);
        View postView = actionBar.getCustomView();
        ActionBar.LayoutParams lp = (ActionBar.LayoutParams) postView.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        postView.setLayoutParams(lp);
        Button rtnToTickets= (Button) mActionBarView.findViewById(R.id.btn_return_mytickets);

        rtnToTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        show();
    }

    private void show() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = TicketFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
