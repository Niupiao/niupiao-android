package com.niupiao.niupiao.fragments.my_tickets;

import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.managers.EventManager;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class PastEventsFragment extends MyTicketsViewPagerFragment {

    @Override
    protected void requestEventsFromManager() {
        EventManager eventManager = ((MainActivity) getActivity()).getEventManager();
        eventManager.loadPastEvents(this);
    }

    public static PastEventsFragment newInstance() {
        return new PastEventsFragment();
    }

    @Override
    public String getTitle() {
        return "Past Events";
    }


}
