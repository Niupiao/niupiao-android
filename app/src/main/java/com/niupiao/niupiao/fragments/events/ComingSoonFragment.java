package com.niupiao.niupiao.fragments.events;

import android.os.Bundle;

import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.managers.EventManager;

/**
 * Created by kevinchen on 2/17/15.
 */
public class ComingSoonFragment extends EventsViewPagerFragment {

    @Override
    protected void requestEventsFromManager() {
        EventManager eventManager = ((MainActivity) getActivity()).getEventManager();
        eventManager.loadComingSoonEvents(this);
    }

    @Override
    public String getTitle() {
        return "Coming Soon";
    }

    /**
     * @param position This fragment's position in its ViewPager
     */
    public static ComingSoonFragment newInstance(int position) {
        ComingSoonFragment fragment = new ComingSoonFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

}
