package com.niupiao.niupiao.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class Event implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(organizer);
        dest.writeString(date);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeString(imagePath);
        dest.writeInt(totalTickets);
        dest.writeInt(ticketsSold);
        dest.writeParcelableArray(tickets.toArray(new Ticket[tickets.size()]), 0);
    }

    public Event(Parcel in) {
        name = in.readString();
        organizer = in.readString();
        date = in.readString();
        location = in.readString();
        description = in.readString();
        link = in.readString();
        imagePath = in.readString();
        totalTickets = in.readInt();
        ticketsSold = in.readInt();
        Parcelable[] parcelables = in.readParcelableArray(Ticket.class.getClassLoader());
        Collection<Ticket> ticketCollection = new ArrayList<>(parcelables.length);
        for (Parcelable parcelable : parcelables) {
            ticketCollection.add((Ticket) parcelable);
        }
        tickets = ticketCollection;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @SerializedName("id")
    private int id;

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

    @SerializedName("tickets")
    private Collection<Ticket> tickets;

    public int getId() {
        return id;
    }

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

    public String getLink() {
        return link;
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

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Event) {
            Event e = (Event) o;
            return e.getId() == this.getId();
        }
        return false;
    }
}
