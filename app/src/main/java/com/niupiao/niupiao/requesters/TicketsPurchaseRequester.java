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
            //TODO: Authorization token? Where is it put in?
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

                            //If the response was not an error.
                            if(!jsonArray.getJSONObject(0).keys().next().contains("error")){
                                Log.d("PURCHASE TICKETS - From localhost: ", "success");
                                //TODO Something in response to success.
                                int length = jsonArray.length();
                                for(int i = 0; i < length; i ++){
                                    JSONObject ticketInfo = jsonArray.getJSONObject(i);
                                    boolean success = (Boolean) ticketInfo.get("success");
                                }
                            } else {
                                Log.d("PURCHASE TICKETS - From localhost: ", "failure");
                                //TODO Inform users of failure.
                            }
                            //PurchasedTicketDeseralizer was a mini-attempt of mine to deserialize the jsonArray.
                            // Later deleted, was essentially a copy-paste of TicketsDeserializer.
                            //List<Ticket> tickets = PurchasedTicketsDeserializer.fromJsonArray(jsonArray);
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );

        NiupiaoApplication.getRequestQueue().add(request);
    }
}
