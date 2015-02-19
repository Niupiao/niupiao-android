package com.niupiao.niupiao.fragments.concerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.adapters.ViewPagerAdapter;
import com.niupiao.niupiao.fragments.NiuNavigationDrawerFragment;
import com.niupiao.niupiao.fragments.ViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinchen on 2/18/15.
 */
public class ConcertsFragment extends NiuNavigationDrawerFragment {

    public static final String TAG = ConcertsFragment.class.getSimpleName();
    public static final String FRAGMENT_POSITION_KEY = "position";

    private static final int FRAGMENT_POSITION_COMING_SOON = 0;
    private static final int FRAGMENT_POSITION_ON_SALE = 1;
    private static final int FRAGMENT_POSITION_MY_TICKETS = 2;

    private ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_concerts, container, false);

        // Initialize fragments
        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(FRAGMENT_POSITION_COMING_SOON, ComingSoonFragment.newInstance(FRAGMENT_POSITION_COMING_SOON));
        fragments.add(FRAGMENT_POSITION_ON_SALE, OnSaleFragment.newInstance(FRAGMENT_POSITION_ON_SALE));
        fragments.add(FRAGMENT_POSITION_MY_TICKETS, MyTicketsFragment.newInstance(FRAGMENT_POSITION_MY_TICKETS));

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


///////////// LIFE CYCLE CALLBACKS FOR TESTING

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            ViewPagerFragment fragment = (ViewPagerFragment) adapter.getItem(i);
            if (fragment.isResumed()) {
                Intent intent = getActivity().getIntent();
                ArrayList<Parcelable> parcelables = intent.getParcelableArrayListExtra(fragment.getTitle());
                fragment.setParcelables(parcelables);
            }
        }
    }

    @Override
    public void iwantstuff(ViewPagerFragment fragment) {
        // TODO unused?
        fragment.setParcelables(getActivity().getIntent().getParcelableArrayListExtra(fragment.getTitle()));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            ViewPagerFragment fragment = (ViewPagerFragment) adapter.getItem(i);
            ArrayList<Parcelable> parcelables = fragment.getParcelableArrayList();
            if (parcelables != null) {
                MainActivity activity = (MainActivity) getActivity();
                activity.saveParcelables(fragment.getTitle(), parcelables);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }
}
