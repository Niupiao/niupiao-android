package com.niupiao.niupiao.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.ViewPagerAdapter;
import com.niupiao.niupiao.fragments.registration.DoneFragment;
import com.niupiao.niupiao.fragments.registration.MoreInfoFragment;
import com.niupiao.niupiao.fragments.registration.SetupProfileFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinchen on 2/25/15.
 */
public class RegistrationActivity extends ActionBarActivity {

    private static final int FRAGMENT_MORE_INFO = 0;
    private static final int FRAGMENT_SETUP_PROFILE = 1;
    private static final int FRAGMENT_DONE = 2;

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.niupiao_blue)));

        View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar_registration, null);
        actionBar.setCustomView(mActionBarView);
        View postView = actionBar.getCustomView();
        ActionBar.LayoutParams lp = (ActionBar.LayoutParams) postView.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        postView.setLayoutParams(lp);
        Button rtnToLogin = (Button) mActionBarView.findViewById(R.id.btn_return_login);

        rtnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(FRAGMENT_MORE_INFO, MoreInfoFragment.newInstance(FRAGMENT_MORE_INFO));
        fragments.add(FRAGMENT_SETUP_PROFILE, SetupProfileFragment.newInstance(FRAGMENT_SETUP_PROFILE));
        fragments.add(FRAGMENT_DONE, DoneFragment.newInstance(FRAGMENT_DONE));

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        //TODO: Perhaps move these tab strip customization code to the XML.
        //TODO: For above, look at https://github.com/astuetz/PagerSlidingTabStrip#customization
        tabs.setIndicatorColor(getResources().getColor(R.color.niupiao_orange));
        tabs.setViewPager(pager);

    }
}
