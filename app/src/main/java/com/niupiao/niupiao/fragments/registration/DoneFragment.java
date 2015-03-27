package com.niupiao.niupiao.fragments.registration;

import android.support.v4.app.Fragment;

import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by Inanity on 3/26/2015.
 */
public class DoneFragment extends ViewPagerFragment {

    public static DoneFragment newInstance(int position){
        DoneFragment fragment = new DoneFragment();

        return fragment;
    }

    @Override
    public String getTitle() {
        return "Done";
    }
}
