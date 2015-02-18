package com.niupiao.niupiao.fragments.concerts;

import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by kevinchen on 2/17/15.
 */
public class MyTicketsFragment extends ViewPagerFragment {
    @Override
    public String getTitle() {
        return "My Tickets";
    }

    public static MyTicketsFragment newInstance() {
        return new MyTicketsFragment();
    }
}
