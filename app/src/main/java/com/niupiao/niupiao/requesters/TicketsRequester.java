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
                getHeadersWithRequestType(Constants.JsonApi.Ticket.HEADER_VALUE_TICKET_UPCOMING)
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
                getHeadersWithRequestType(Constants.JsonApi.Ticket.HEADER_VALUE_TICKET_PAST)
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }

    private static Map<String, String> getHeadersWithRequestType(String requestType) {
        Map<String, String> map = new HashMap<>(1);
        map.put(Constants.JsonApi.Ticket.HEADER_KEY_REQUEST_TYPE, requestType);
        return map;
    }


}
