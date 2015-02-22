package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.EventsDeserializer;
import com.niupiao.niupiao.models.Event;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class EventsRequester {

    public interface OnEventsRequestedListener extends ResourceCallback {
        public void onEventsLoaded(List<Event> events);

        public void onOnSaleEventsLoaded(List<Event> events);

        public void onComingSoonEventsLoaded(List<Event> events);

        public void onRecommendedEventsLoaded(List<Event> events);

        public void onPastEventsLoaded(List<Event> events);

        public void onUpcomingEventsLoaded(List<Event> events);
    }

    public static void loadEvents(final OnEventsRequestedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onEventsLoaded(events);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadOnSaleEvents(final OnEventsRequestedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onOnSaleEventsLoaded(events);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadComingSoonEvents(final OnEventsRequestedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onComingSoonEventsLoaded(events);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadRecommendedEvents(final OnEventsRequestedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onRecommendedEventsLoaded(events);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadPastEvents(final OnEventsRequestedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onPastEventsLoaded(events);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadUpcomingEvents(final OnEventsRequestedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onUpcomingEventsLoaded(events);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }


}
