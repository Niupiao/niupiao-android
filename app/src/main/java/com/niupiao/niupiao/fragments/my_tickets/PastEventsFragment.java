package com.niupiao.niupiao.fragments.my_tickets;

import com.niupiao.niupiao.managers.TicketManager;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class PastEventsFragment extends MyTicketsViewPagerFragment {

    @Override
    protected void requestEventsFromManager() {
        TicketManager manager = getTicketManager();
        manager.loadPastEvents(this);
    }

    public static PastEventsFragment newInstance() {
        return new PastEventsFragment();
    }

    @Override
    public String getTitle() {
        return "Past Events";
    }


}
