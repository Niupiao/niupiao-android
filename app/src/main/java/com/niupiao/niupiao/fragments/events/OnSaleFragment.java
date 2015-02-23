package com.niupiao.niupiao.fragments.events;

import android.os.Bundle;

import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.managers.EventManager;

/**
 * Created by kevinchen on 2/17/15.
 */
public class OnSaleFragment extends EventsViewPagerFragment {

    @Override
    protected void requestEventsFromManager() {
        EventManager eventManager = ((MainActivity) getActivity()).getEventManager();
        eventManager.loadOnSaleEvents(this);
    }

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
