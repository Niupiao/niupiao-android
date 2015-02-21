package com.niupiao.niupiao.managers;

import com.android.volley.VolleyError;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.requesters.EventsRequester;
import com.niupiao.niupiao.requesters.ResourceCallback;

import java.util.List;
import java.util.Set;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class EventManager implements EventsRequester.OnEventsLoadedListener {

    private ResourceCallback callback;

    private Set<Event> onSale;
    private Set<Event> comingSoong;
    private Set<Event> recommended;
    private Set<Event> past;
    private Set<Event> upcoming;

    public EventManager(ResourceCallback callback) {
        this.callback = callback;
        // TODO load events, in fragment#onCreateView, have them access events from MainActivity's manager
    }

    @Override
    public void onEventsLoaded(List<Event> events) {

    }

    public Set<Event> getOnSale() {
        return onSale;
    }

    public Set<Event> getComingSoong() {
        return comingSoong;
    }

    public Set<Event> getRecommended() {
        return recommended;
    }

    public Set<Event> getPast() {
        return past;
    }

    public Set<Event> getUpcoming() {
        return upcoming;
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
