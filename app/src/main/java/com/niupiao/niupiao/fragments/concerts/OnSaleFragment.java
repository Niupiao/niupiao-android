package com.niupiao.niupiao.fragments.concerts;

import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by kevinchen on 2/17/15.
 */
public class OnSaleFragment extends ViewPagerFragment {
    @Override
    public String getTitle() {
        return "On Sale";
    }

    public static OnSaleFragment newInstance() {
        return new OnSaleFragment();
    }
}
