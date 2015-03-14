package com.niupiao.niupiao.fragments.my_tickets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.ViewPagerAdapter;
import com.niupiao.niupiao.fragments.NiuNavigationDrawerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinchen on 2/18/15.
 */
public class MyTicketsNavFragment extends NiuNavigationDrawerFragment {

    private static final int FRAGMENT_POSITION_UPCOMING_EVENTS = 0;
    private static final int FRAGMENT_POSITION_PAST_EVENTS = 1;

    private ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_mytickets, container, false);

        // Initialize fragments
        List<Fragment> fragments = new ArrayList<>(2);
        fragments.add(FRAGMENT_POSITION_UPCOMING_EVENTS, UpcomingEventsFragment.newInstance());
        fragments.add(FRAGMENT_POSITION_PAST_EVENTS, PastEventsFragment.newInstance());


        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) root.findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        // if we use listener, set it on widget, not the pager
//        tabs.setOnPageChangeListener();

        return root;
    }
}
