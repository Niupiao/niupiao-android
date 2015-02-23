package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.EventsDeserializer;
import com.niupiao.niupiao.models.Event;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class EventsRequester {

    /**
     * Rails controller will check for this key in params, to see what is being requested.
     */
    public static final String REQUEST_TYPE_HEADER = "request_type";

    /**
     * A value for the request type.
     */
    public static final String ON_SALE = "on_sale";

    /**
     * A value for the request type.
     */
    public static final String COMING_SOON = "coming_soon";

    /**
     * A value for the request type.
     */
    public static final String RECOMMENDED = "recommended";


    public interface OnEventsRequestedListener extends ResourceCallback {
        public void onEventsLoaded(List<Event> events);

        public void onOnSaleEventsLoaded(List<Event> events);

        public void onComingSoonEventsLoaded(List<Event> events);

        public void onRecommendedEventsLoaded(List<Event> events);

    }

    private static Map<String, String> specialRequest(String value) {
        Map<String, String> map = new HashMap<>(1);
        map.put(REQUEST_TYPE_HEADER, value);
        return map;
    }

    public static void loadEvents(final OnEventsRequestedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.TICKETS_URL,
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
                },
                specialRequest(ON_SALE)
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
                },
                specialRequest(COMING_SOON)
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
                },
                specialRequest(RECOMMENDED)
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

}
