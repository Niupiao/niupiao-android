package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.TicketsDeserializer;
import com.niupiao.niupiao.models.Event;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevinchen on 2/22/15.
 */
public class TicketsRequester {

    public static final String TAG = TicketsRequester.class.getSimpleName();

    /**
     * Rails controller will check for this key in params, to see what is being requested.
     */
    public static final String REQUEST_TYPE_HEADER = "request_type";

    /**
     * A value for the request type.
     */
    public static final String PAST = "past";

    /**
     * A value for the request type.
     */
    public static final String UPCOMING = "upcoming";


    public interface OnTicketsLoadedListener extends ResourceCallback {
        public void onTicketsLoaded(List<Event> tickets);

        public void onPastTicketsLoaded(List<Event> tickets);

        public void onUpcomingTicketsLoaded(List<Event> tickets);
    }

    public static void loadTickets(final OnTicketsLoadedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.TICKETS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d(TAG, jsonArray.toString());
                        List<Event> tickets = TicketsDeserializer.fromJsonArray(jsonArray);
                        listener.onTicketsLoaded(tickets);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadUpcomingTickets(final OnTicketsLoadedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.TICKETS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d(TAG, jsonArray.toString());
                        List<Event> tickets = TicketsDeserializer.fromJsonArray(jsonArray);
                        listener.onUpcomingTicketsLoaded(tickets);
                    }
                },
                specialRequest(UPCOMING)
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    public static void loadPastTickets(final OnTicketsLoadedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.TICKETS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d(TAG, jsonArray.toString());
                        List<Event> tickets = TicketsDeserializer.fromJsonArray(jsonArray);
                        listener.onPastTicketsLoaded(tickets);
                    }
                },
                specialRequest(PAST)
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    private static Map<String, String> specialRequest(String value) {
        Map<String, String> map = new HashMap<>(1);
        map.put(REQUEST_TYPE_HEADER, value);
        return map;
    }


}
