package com.niupiao.niupiao.fragments.registration;

import android.support.v4.app.Fragment;

import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by Inanity on 3/26/2015.
 */
public class SetupProfileFragment extends ViewPagerFragment {


    public static SetupProfileFragment newInstance(int position){
        SetupProfileFragment fragment = new SetupProfileFragment();

        return fragment;
    }

    @Override
    public String getTitle() {
        return "Setup Profile";
    }
}
