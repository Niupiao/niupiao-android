package com.niupiao.niupiao.fragments.concerts;

import android.os.Bundle;

import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by kevinchen on 2/17/15.
 */
public class MyTicketsFragment extends ViewPagerFragment {
    @Override
    public String getTitle() {
        return "My Tickets";
    }

    /**
     * @param position This fragment's position in its ViewPager
     */
    public static MyTicketsFragment newInstance(int position) {
        MyTicketsFragment fragment = new MyTicketsFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

}
