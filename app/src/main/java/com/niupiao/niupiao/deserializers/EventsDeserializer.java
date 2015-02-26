package com.niupiao.niupiao.deserializers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niupiao.niupiao.models.Event;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kevinchen on 2/17/15.
 */
public class EventsDeserializer {

    public static List<Event> fromJsonArray(JSONArray jsonArray) {
        Type type = new TypeToken<List<Event>>() {
        }.getType();
        return new Gson().fromJson(jsonArray.toString(), type);
    }

}
