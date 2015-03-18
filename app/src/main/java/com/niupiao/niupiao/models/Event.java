package com.niupiao.niupiao.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        dest.writeInt(numberOfTicketStatuses);
        dest.writeList((List) ticketStatuses);
//        writeTicketsToParcel(dest);
    }

    private void writeTicketsToParcel(Parcel dest) {
        dest.writeParcelableArray(tickets.toArray(new Ticket[tickets.size()]), 0);
    }

    private void readTicketsFromParcel(Parcel in) {
        Parcelable[] parcelables = in.readParcelableArray(Ticket.class.getClassLoader());
        Collection<Ticket> ticketCollection = new ArrayList<>(parcelables.length);
        for (Parcelable parcelable : parcelables) {
            ticketCollection.add((Ticket) parcelable);
        }
        tickets = ticketCollection;
    }

    private void writeTicketStatusesToParcel(Parcel dest){
        dest.writeParcelableArray(ticketStatuses.toArray(new TicketStatus[ticketStatuses.size()]), 0);
    }

    private void readTicketStatusesFromParcel(Parcel in){
        Parcelable[] parcelables = in.readParcelableArray(TicketStatus.class.getClassLoader());
        for (Parcelable parcelable : parcelables){
            ticketStatuses.add((TicketStatus) parcelable);
        }
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
        numberOfTicketStatuses = in.readInt();
        ticketStatuses = in.readArrayList(TicketStatus.class.getClassLoader());

//        readTicketsFromParcel(in);
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

    @SerializedName("number_of_ticket_statuses")
    private int numberOfTicketStatuses;

    @SerializedName("ticket_statuses")
    private Collection<TicketStatus> ticketStatuses;

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

    public int getNumberOfTicketStatuses() {
        return numberOfTicketStatuses;
    }

    public Collection<TicketStatus> getTicketStatuses() {
        return ticketStatuses;
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
