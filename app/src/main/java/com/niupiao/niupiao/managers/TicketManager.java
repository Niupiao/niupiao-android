package com.niupiao.niupiao.managers;

import com.android.volley.VolleyError;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.TicketStatus;
import com.niupiao.niupiao.requesters.ResourceCallback;
import com.niupiao.niupiao.requesters.TicketsRequester;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kevinchen on 2/23/15.
 */
public class TicketManager implements TicketsRequester.OnTicketsLoadedListener {

    private boolean LAZY_LOAD = false;

    private ResourceCallback callback;

    private Set<Event> events;
    private Set<Event> past;
    private Set<Event> upcoming;

    // listeners, i.e., fragments that will be called when stuff loads
    private OnEventsLoadedListener eventsListener;
    private OnEventsLoadedListener pastEventsListener;
    private OnEventsLoadedListener upcomingEventsListener;

    public interface OnEventsLoadedListener {
        public void onEventsLoaded(Collection<Event> events);
    }

    public TicketManager(ResourceCallback callback) {
        this.callback = callback;
        if (!LAZY_LOAD) {
            TicketsRequester.loadTickets(this);
        }
    }

    public void loadEvents(final OnEventsLoadedListener listener) {
        if (events != null) {
            // we have events, so pass them to the UI
            listener.onEventsLoaded(events);
        } else {
            // otherwise, we will call the UI when we load them
            eventsListener = listener;
            if (LAZY_LOAD) {
                TicketsRequester.loadTickets(this);
            }
        }
    }

    public void loadPastEvents(final OnEventsLoadedListener listener) {
        if (past != null) {
            // we have events, so pass them to the UI
            listener.onEventsLoaded(past);
        } else {
            // otherwise, we will call the UI when we load them
            pastEventsListener = listener;
            if (LAZY_LOAD) {
                TicketsRequester.loadPastTickets(this);
            }
        }
    }

    public void loadUpcomingEvents(final OnEventsLoadedListener listener) {
        if (upcoming != null) {
            // we have events, so pass them to the UI
            listener.onEventsLoaded(upcoming);
        } else {
            // otherwise, we will call the UI when we load them
            upcomingEventsListener = listener;
            if (LAZY_LOAD) {
                TicketsRequester.loadUpcomingTickets(this);
            }
        }
    }

    @Override
    public void onTicketsLoaded(List<Event> tickets) {
        if (tickets != null) {
            if (this.events == null) {
                this.events = new HashSet<>(tickets);
            } else {
                this.events.addAll(tickets);
            }
        }

        if (eventsListener != null) eventsListener.onEventsLoaded(this.events);
    }

    @Override
    public void onPastTicketsLoaded(List<Event> tickets) {
        if (tickets != null) {
            if (this.past == null) {
                this.past = new HashSet<>(tickets);
            } else {
                this.past.addAll(tickets);
            }
        }

        if (pastEventsListener != null) pastEventsListener.onEventsLoaded(this.past);
    }

    @Override
    public void onUpcomingTicketsLoaded(List<Event> tickets) {
        if (tickets != null) {
            if (this.upcoming == null) {
                this.upcoming = new HashSet<>(tickets);
            } else {
                this.upcoming.addAll(tickets);
            }
        }

        if (upcomingEventsListener != null) upcomingEventsListener.onEventsLoaded(this.upcoming);
    }

    @Override
    public String getAccessToken() {
        return callback.getAccessToken();
    }

    @Override
    public void onVolleyError(VolleyError volleyError) {
        callback.onVolleyError(volleyError);
    }
}
