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

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("total_tickets")
    private int totalTickets;

    @SerializedName("tickets_sold")
    private int ticketsSold;

    public String getName() {
        return name;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }
}
