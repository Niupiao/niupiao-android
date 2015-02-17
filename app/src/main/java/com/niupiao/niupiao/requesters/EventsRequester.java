package com.niupiao.niupiao.requesters;

import com.niupiao.niupiao.models.Event;

import java.util.List;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class EventsRequester {

    public interface OnEventsLoadedListener {
        public void onEventsLoaded(List<Event> events);
    }

    public static void loadEvents(final OnEventsLoadedListener listener) {

    }
}
