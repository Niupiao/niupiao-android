package com.niupiao.niupiao.fragments.my_tickets;

import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.managers.TicketManager;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class UpcomingEventsFragment extends MyTicketsViewPagerFragment {

    @Override
    protected void requestEventsFromManager() {
        TicketManager manager = ((MainActivity) getActivity()).getTicketManager();
// TODO swap back in
//        manager.loadUpcomingEvents(this);
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
