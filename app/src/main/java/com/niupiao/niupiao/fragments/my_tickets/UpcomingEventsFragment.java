package com.niupiao.niupiao.fragments.my_tickets;

import com.niupiao.niupiao.managers.TicketManager;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class UpcomingEventsFragment extends MyTicketsViewPagerFragment {

    @Override
    protected void requestEventsFromManager() {
        TicketManager manager = getTicketManager();
        // TODO server only passing back all events for now. swap back in later
        // manager.loadUpcomingEvents(this);
        manager.loadEvents(this);
    }

    public static UpcomingEventsFragment newInstance() {
        return new UpcomingEventsFragment();
    }

    @Override
    public String getTitle() {
        return "Upcoming Events";
    }
}
