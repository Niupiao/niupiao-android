package com.niupiao.niupiao.fragments;

/**
 * Created by kevinchen on 2/17/15.
 */
public class ComingSoonFragment extends ViewPagerFragment {

    @Override
    public String getTitle() {
        return "Coming Soon";
    }

    public static ComingSoonFragment newInstance() {
        return new ComingSoonFragment();
    }
}
