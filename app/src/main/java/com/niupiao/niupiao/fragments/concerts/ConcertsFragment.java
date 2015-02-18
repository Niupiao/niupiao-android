package com.niupiao.niupiao.fragments.concerts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinchen on 2/18/15.
 */
public class ConcertsFragment extends Fragment {

    public static final String FRAGMENT_POSITION_KEY = "position";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_concerts, container, false);

        // Initialize fragments
        List<android.support.v4.app.Fragment> fragments = new ArrayList<>(3);
        fragments.add(ComingSoonFragment.newInstance());
        fragments.add(OnSaleFragment.newInstance());
        fragments.add(MyTicketsFragment.newInstance());

        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) root.findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(((ActionBarActivity) getActivity()).getSupportFragmentManager(), fragments));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        // if we use listener, set it on widget, not the pager
        //tabs.setOnPageChangeListener(mPageChangeListener);

        return root;
    }
}
