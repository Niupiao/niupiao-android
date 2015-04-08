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

    public interface OnEventsRequestedListener extends ResourceCallback {
        public void onEventsLoaded(List<Event> events);

        public void onOnSaleEventsLoaded(List<Event> events);

        public void onComingSoonEventsLoaded(List<Event> events);

        public void onRecommendedEventsLoaded(List<Event> events);

    }

    private static Map<String, String> getHeadersWithRequestType(String requestType) {
        Map<String, String> map = new HashMap<>(1);
        map.put(Constants.JsonApi.Event.HEADER_KEY_REQUEST_TYPE, requestType);
        return map;
    }

    public static void loadEvents(final OnEventsRequestedListener listener) {
        AuthJsonArrayRequest request = new AuthJsonArrayRequest(
                listener,
                Constants.JsonApi.EndPoints.TICKETS_URL,
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
        AuthJsonArrayRequest request = new AuthJsonArrayRequest(
                listener,
                Constants.JsonApi.EndPoints.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onOnSaleEventsLoaded(events);
                    }
                },
                getHeadersWithRequestType(Constants.JsonApi.Event.HEADER_VALUE_EVENT_ON_SALE)
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadComingSoonEvents(final OnEventsRequestedListener listener) {
        AuthJsonArrayRequest request = new AuthJsonArrayRequest(
                listener,
                Constants.JsonApi.EndPoints.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onComingSoonEventsLoaded(events);
                    }
                },
                getHeadersWithRequestType(Constants.JsonApi.Event.HEADER_VALUE_EVENT_COMING_SOON)
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadRecommendedEvents(final OnEventsRequestedListener listener) {
        AuthJsonArrayRequest request = new AuthJsonArrayRequest(
                listener,
                Constants.JsonApi.EndPoints.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onRecommendedEventsLoaded(events);
                    }
                },
                getHeadersWithRequestType(Constants.JsonApi.Event.HEADER_VALUE_EVENT_RECOMMENDED)
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

}
