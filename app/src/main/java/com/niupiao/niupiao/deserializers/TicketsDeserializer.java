package com.niupiao.niupiao.deserializers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niupiao.niupiao.models.Ticket;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kevinchen on 2/22/15.
 */
public class TicketsDeserializer {

    public static List<Ticket> fromJsonArray(JSONArray jsonArray) {
        Type type = new TypeToken<List<Ticket>>() {
        }.getType();
        return new Gson().fromJson(jsonArray.toString(), type);
    }
}
