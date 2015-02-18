package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.ViewPagerAdapter;
import com.niupiao.niupiao.fragments.ComingSoonFragment;
import com.niupiao.niupiao.fragments.MyTicketsFragment;
import com.niupiao.niupiao.fragments.OnSaleFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class MainActivity extends ActionBarActivity {

    public static final String INTENT_KEY_FOR_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concerts);

        // Initialize fragments
        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(ComingSoonFragment.newInstance());
        fragments.add(OnSaleFragment.newInstance());
        fragments.add(MyTicketsFragment.newInstance());

        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        // if we use listener, set it on widget, not the pager
        //tabs.setOnPageChangeListener(mPageChangeListener);

    }

}
