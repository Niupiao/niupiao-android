package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.ApiKeyDeserializer;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.ApiKey;
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
                Constants.JsonApi.EndPoints.BUY_TICKETS_URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // TODO analyze json for success, pass stuff back to listener
                        //Modeled in large part on the RegistrationRequester
                        try{
                            boolean success = jsonObject.getBoolean(Constants.JsonApi.Response.SUCCESS);
                            if(success){
                                System.out.println("Success(?)");
                                ApiKey apiKey = ApiKeyDeserializer.fromJsonObject(jsonObject.getJSONObject(Constants.JsonApi.Response.API_KEY));

                            } else {

                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
        );

        NiupiaoApplication.getRequestQueue().add(request);
    }
}
