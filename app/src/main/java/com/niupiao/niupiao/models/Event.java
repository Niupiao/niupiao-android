package com.niupiao.niupiao.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class Event {

    @SerializedName("name")
    private String name;

    @SerializedName("organizer")
    private String organizer;

    @SerializedName("date")
    private String date;

    @SerializedName("location")
    private String location;

    @SerializedName("description")
    private String description;

    @SerializedName("link")
    private String link;

    @SerializedName("total_tickets")
    private int totalTickets;

    @SerializedName("tickets_sold")
    private int ticketsSold;
}
