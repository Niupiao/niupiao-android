package com.niupiao.niupiao.fragments.events;

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
public class EventsNavFragment extends NiuNavigationDrawerFragment {

    public static final String TAG = EventsNavFragment.class.getSimpleName();

    private static final int FRAGMENT_POSITION_ON_SALE = 0;
    private static final int FRAGMENT_POSITION_COMING_SOON = 1;
    private static final int FRAGMENT_POSITION_RECOMMENDED = 2;

    private ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sliding_view_pager, container, false);

        // Initialize fragments
        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(FRAGMENT_POSITION_ON_SALE, OnSaleFragment.newInstance(FRAGMENT_POSITION_ON_SALE));
        fragments.add(FRAGMENT_POSITION_COMING_SOON, ComingSoonFragment.newInstance(FRAGMENT_POSITION_COMING_SOON));
        fragments.add(FRAGMENT_POSITION_RECOMMENDED, RecommendedFragment.newInstance(FRAGMENT_POSITION_RECOMMENDED));

        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) root.findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        tabs.setIndicatorColor(getResources().getColor(R.color.niupiao_orange));
        tabs.setViewPager(pager);

        // if we use listener, set it on widget, not the pager
//        tabs.setOnPageChangeListener();

        return root;
    }

}
