package com.niupiao.niupiao.deserializers;

import com.google.gson.Gson;
import com.niupiao.niupiao.models.User;

import org.json.JSONObject;

/**
 * Created by kevinchen on 2/18/15.
 */
public class UserDeserializer {

    public static User fromJsonObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), User.class);
    }
}
