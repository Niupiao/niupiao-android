package com.niupiao.niupiao.managers;

import com.android.volley.VolleyError;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.requesters.EventsRequester;
import com.niupiao.niupiao.requesters.ResourceCallback;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class EventManager implements EventsRequester.OnEventsRequestedListener {

    private boolean LAZY_LOAD = false;

    private ResourceCallback callback;

    private Set<Event> events;
    private Set<Event> onSale;
    private Set<Event> comingSoon;
    private Set<Event> recommended;
    private Set<Event> past;
    private Set<Event> upcoming;

    // listeners, i.e., fragments that will be called when stuff loads
    private OnEventsLoadedListener eventsListener;
    private OnEventsLoadedListener onSaleEventsListener;
    private OnEventsLoadedListener comingSoonEventsListener;
    private OnEventsLoadedListener recommendedEventsListener;
    private OnEventsLoadedListener pastEventsListener;
    private OnEventsLoadedListener upcomingEventsListener;

    public interface OnEventsLoadedListener {
        public void onEventsLoaded(Collection<Event> events);
    }

    public void loadEvents(final OnEventsLoadedListener listener) {
        if (events != null) {
            // we have events, so pass them to the UI
            listener.onEventsLoaded(events);
        } else {
            // otherwise, we will call the UI when we load them
            eventsListener = listener;
            if (LAZY_LOAD) {
                EventsRequester.loadEvents(this);
            }
        }
    }

    public void loadOnSaleEvents(final OnEventsLoadedListener listener) {
        if (onSale != null) {
            // we have events, so pass them to the UI
            listener.onEventsLoaded(onSale);
        } else {
            // otherwise, we will call the UI when we load them
            onSaleEventsListener = listener;
            if (LAZY_LOAD) {
                EventsRequester.loadOnSaleEvents(this);
            }
        }
    }

    public void loadComingSoonEvents(final OnEventsLoadedListener listener) {
        if (comingSoon != null) {
            // we have events, so pass them to the UI
            listener.onEventsLoaded(comingSoon);
        } else {
            // otherwise, we will call the UI when we load them
            comingSoonEventsListener = listener;
            if (LAZY_LOAD) {
                EventsRequester.loadComingSoonEvents(this);
            }
        }
    }

    public void loadRecommendedEvents(final OnEventsLoadedListener listener) {
        if (recommended != null) {
            // we have events, so pass them to the UI
            listener.onEventsLoaded(recommended);
        } else {
            // otherwise, we will call the UI when we load them
            recommendedEventsListener = listener;
            if (LAZY_LOAD) {
                EventsRequester.loadRecommendedEvents(this);
            }
        }
    }

    public EventManager(ResourceCallback callback) {
        this.callback = callback;
        if (!LAZY_LOAD) {
            EventsRequester.loadComingSoonEvents(this);
            EventsRequester.loadOnSaleEvents(this);
            EventsRequester.loadRecommendedEvents(this);
        }
    }

    @Override
    public void onEventsLoaded(List<Event> events) {
        if (events != null) {
            if (this.events == null) {
                this.events = new HashSet<>(events);
            } else {
                this.events.addAll(events);
            }
        }

        if (eventsListener != null) eventsListener.onEventsLoaded(this.events);
    }

    @Override
    public void onOnSaleEventsLoaded(List<Event> events) {
        if (events != null) {
            if (this.onSale == null) {
                this.onSale = new HashSet<>(events);
            } else {
                this.onSale.addAll(events);
            }
        }

        if (onSaleEventsListener != null) onSaleEventsListener.onEventsLoaded(this.onSale);
    }

    @Override
    public void onComingSoonEventsLoaded(List<Event> events) {
        if (events != null) {
            if (this.comingSoon == null) {
                this.comingSoon = new HashSet<>(events);
            } else {
                this.comingSoon.addAll(events);
            }
        }

        if (comingSoonEventsListener != null)
            comingSoonEventsListener.onEventsLoaded(this.comingSoon);
    }

    @Override
    public void onRecommendedEventsLoaded(List<Event> events) {
        if (events != null) {
            if (this.recommended == null) {
                this.recommended = new HashSet<>(events);
            } else {
                this.recommended.addAll(events);
            }
        }

        if (recommendedEventsListener != null)
            recommendedEventsListener.onEventsLoaded(this.recommended);
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
