package com.niupiao.niupiao.fragments.events;

import android.os.Bundle;

import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by kevinchen on 2/17/15.
 */
public class OnSaleFragment extends ViewPagerFragment {
    @Override
    public String getTitle() {
        return "On Sale";
    }

    /**
     * @param position This fragment's position in its ViewPager
     */
    public static OnSaleFragment newInstance(int position) {
        OnSaleFragment fragment = new OnSaleFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

}
