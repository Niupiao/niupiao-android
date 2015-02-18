package com.niupiao.niupiao.deserializers;

import com.google.gson.Gson;
import com.niupiao.niupiao.models.ApiKey;

import org.json.JSONObject;

/**
 * Created by kevinchen on 2/18/15.
 */
public class ApiKeyDeserializer {

    public static ApiKey fromJsonObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), ApiKey.class);
    }

}
