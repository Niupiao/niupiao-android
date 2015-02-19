package com.niupiao.niupiao.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by kevinchen on 2/17/15.
 */
public abstract class ViewPagerFragment extends Fragment {

    /**
     * A Bundle key for storing a fragment's position in its ViewPager.
     */
    protected final static String POSITION_KEY = "position";

    public abstract String getTitle();

}
