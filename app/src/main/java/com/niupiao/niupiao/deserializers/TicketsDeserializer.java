package com.niupiao.niupiao.deserializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.Ticket;
import com.niupiao.niupiao.models.TicketStatus;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by kevinchen on 2/22/15.
 */
public class TicketsDeserializer implements JsonDeserializer<Ticket> {

    public static List<Event> fromJsonArray(JSONArray jsonArray) {
        Type type = new TypeToken<List<Event>>() {
        }.getType();
        final GsonBuilder gsonBuilder = new GsonBuilder();
        System.out.println("Registering adapter...");
        gsonBuilder.registerTypeAdapter(Ticket.class, new TicketsDeserializer());
        final Gson gson = gsonBuilder.create();

        List<Event> events = gson.fromJson(jsonArray.toString(), type);
        for (Event event : events) {
            Collection<Ticket> ticketCollection = event.getTickets();
            if (ticketCollection != null) {
                for (Ticket ticket : ticketCollection) {
                    ticket.setEvent(event);
                }
            }
        }

        return events;

    }

    @Override
    public Ticket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject ticketJson = json.getAsJsonObject();
        final Ticket ticket = new Ticket();
        System.out.println("WOOHOO");
        ticket.setId(ticketJson.get("id").getAsInt());
        ticket.setStatus(ticketJson.get("status").getAsString());
        ticket.setEventId(ticketJson.get("event_id").getAsInt());
        ticket.setUserId(ticketJson.get("user_id").getAsInt());

        JsonObject ticketStatusJson = ticketJson.getAsJsonObject("ticket_status");
        TicketStatus ticketStatus = new TicketStatus();

        if (ticketStatusJson != null) {
            JsonElement element = ticketStatusJson.get("price");
            if (element != null) ticketStatus.setPrice(element.getAsInt());

            element = ticketStatusJson.get("id");
            if (element != null) ticketStatus.setId(element.getAsInt());

            element = ticketStatusJson.get("max_purchasable");
            if (element != null) ticketStatus.setMaxPurchasable(element.getAsInt());

            element = ticketStatusJson.get("name");
            if (element != null) ticketStatus.setName(element.getAsString());

        }
        ticket.setTicketStatus(ticketStatus);

        return ticket;
    }
}
