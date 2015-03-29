package com.niupiao.niupiao.requesters;

import com.android.volley.Request;
import com.android.volley.Response;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kmchen1 on 3/29/15.
 */
public class TicketsPurchaseRequester {

    public interface OnTicketsPurchasedListener extends ResourceCallback {
        public void onTicketsPurchased();
    }

    public static void purchaseTickets(OnTicketsPurchasedListener listener, Event event, ArrayList<PaymentManager.Tickets> tickets) {

        // e.g., { "event_id" : 1, "tickets_purchased" : { "VIP" : 2, "General" : 3 } }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("event_id", event.getId());
            JSONObject ticketsPurchasedJsonObject = new JSONObject();
            if (tickets != null) {
                for (PaymentManager.Tickets ticketPacket : tickets) {
                    ticketsPurchasedJsonObject.put(ticketPacket.getTicketStatus().getName(),
                            ticketPacket.getNumberTicketsPurchased());
                }
            }
            jsonObject.put("tickets_purchased", ticketsPurchasedJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ResourceRequest request = new ResourceRequest(
                listener,
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // TODO analyze json for success, pass stuff back to listener
                    }
                }
        );

        NiupiaoApplication.getRequestQueue().add(request);
    }
}
