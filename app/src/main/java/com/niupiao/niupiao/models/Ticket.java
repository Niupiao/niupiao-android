package com.niupiao.niupiao.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kevinchen on 2/17/15.
 */
public class Ticket {

    @SerializedName("event")
    private Event event;

    @SerializedName("user")
    private User user;

}
