package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.Event;

import org.json.JSONArray;
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

    public static void purchaseTickets(final OnTicketsPurchasedListener listener, Event event, ArrayList<PaymentManager.Tickets> tickets) {

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

        ResourceResponseRequest request = new ResourceResponseRequest(
                listener,
                Request.Method.POST,
                Constants.JsonApi.EndPoints.BUY_TICKETS_URL,
                jsonObject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try{
                            Log.d("PURCHASE TICKETS - From localhost: ", jsonArray.toString());

                            //PurchasedTicketDeseralizer was a mini-attempt of mine to deserialize the jsonArray. Later deleted, didn't get anywhere.
                            //List<Ticket> tickets = PurchasedTicketsDeserializer.fromJsonArray(jsonArray);
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );

        /* //TODO: I'm guessing you don't want this Kevin?
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.JsonApi.EndPoints.BUY_TICKETS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try{
                            Log.d("PURCHASE TICKETS - From localhost: ", jsonArray.toString());
                            List<Ticket> tickets = PurchasedTicketsDeserializer.fromJsonArray(jsonArray);
                            //listener.onTicketsPurchased(tickets);
                        } catch(Exception e){ //TODO Specify exception. Currently, no JSONException thrown in try.
                            e.printStackTrace();
                        }

                    }
                }
        );*/

        NiupiaoApplication.getRequestQueue().add(request);
    }
}
