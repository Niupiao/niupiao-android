package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.TicketsDeserializer;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmchen1 on 3/29/15.
 */
public class TicketsPurchaseRequester {

    private static final String TAG = TicketsPurchaseRequester.class.getSimpleName();

    public interface OnTicketsPurchasedListener extends ResourceCallback {
        public void onTicketsPurchased(List<TicketPurchase> ticketPurchases);
    }

    /**
     * Encodes two things:
     * the ticket being purchased,
     * whether it was successfully purchased
     */
    static class TicketPurchase {
        Ticket ticket;
        boolean isSuccessfullyPurchased;

        TicketPurchase(Ticket ticket, boolean isSuccessfullyPurchased) {
            this.ticket = ticket;
            this.isSuccessfullyPurchased = isSuccessfullyPurchased;
        }
    }

    public static void purchaseTickets(final OnTicketsPurchasedListener listener, Event event, ArrayList<PaymentManager.Tickets> tickets) {

        // e.g., { "event_id" : 1, "tickets_purchased" : { "VIP" : 2, "General" : 3 } }
        JSONObject postParamsJsonObject = null;
        try {
            postParamsJsonObject = new JSONObject();
            postParamsJsonObject.put("event_id", event.getId());
            JSONObject ticketsPurchasedJsonObject = new JSONObject();
            if (tickets != null) {
                for (PaymentManager.Tickets ticketPacket : tickets) {
                    ticketsPurchasedJsonObject.put(ticketPacket.getTicketStatus().getName(),
                            ticketPacket.getNumberTicketsPurchased());
                }
            }
            postParamsJsonObject.put("tickets_purchased", ticketsPurchasedJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try{
                    Log.d(TAG, jsonArray.toString());

                    final int length = jsonArray.length();

                    List<TicketPurchase> ticketPurchases = new ArrayList<>(length);

                    for (int i = 0; i < length; i++) {
                        JSONObject ticketJsonObject = jsonArray.getJSONObject(i);
                        Ticket ticket = TicketsDeserializer.fromJsonObject(ticketJsonObject);
                        boolean success = ticketJsonObject.getBoolean("success");
                        ticketPurchases.add(new TicketPurchase(ticket, success));
                    }

                    listener.onTicketsPurchased(ticketPurchases);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        // ResourceResponseRequest request = new ResourceResponseRequest(listener, Request.Method.POST, Constants.JsonApi.EndPoints.BUY_TICKETS_URL, jsonObject, responseListener);
        ResourcesRequest request = new ResourcesRequest(listener, Constants.JsonApi.EndPoints.BUY_TICKETS_URL, responseListener);

        NiupiaoApplication.getRequestQueue().add(request);
    }
}
