package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.TicketsDeserializer;
import com.niupiao.niupiao.models.Ticket;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by kevinchen on 2/22/15.
 */
public class TicketsRequester {

    public interface OnTicketsLoadedListener extends ResourceCallback {
        public void onTicketsLoaded(List<Ticket> tickets);
    }

    public static void loadTickets(final OnTicketsLoadedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.TICKETS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Ticket> tickets = TicketsDeserializer.fromJsonArray(jsonArray);
                        listener.onTicketsLoaded(tickets);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }
}
