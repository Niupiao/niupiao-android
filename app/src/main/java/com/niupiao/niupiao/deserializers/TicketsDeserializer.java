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
        final JsonObject jsonObject = json.getAsJsonObject();
        final Ticket ticket = new Ticket();
        ticket.setId(jsonObject.get("id").getAsInt());
        ticket.setStatus(jsonObject.get("status").getAsString());
        ticket.setEventId(jsonObject.get("event_id").getAsInt());
        ticket.setUserId(jsonObject.get("user_id").getAsInt());
        return ticket;
    }
}
