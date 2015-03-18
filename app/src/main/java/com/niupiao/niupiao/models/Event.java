package com.niupiao.niupiao.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.niupiao.niupiao.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class Event extends ParcelableModel {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
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

    private void writeTicketStatusesToParcel(Parcel dest) {
        dest.writeParcelableArray(ticketStatuses.toArray(new TicketStatus[ticketStatuses.size()]), 0);
    }

    private void readTicketStatusesFromParcel(Parcel in) {
        Parcelable[] parcelables = in.readParcelableArray(TicketStatus.class.getClassLoader());
        for (Parcelable parcelable : parcelables) {
            ticketStatuses.add((TicketStatus) parcelable);
        }
    }

    //Hacked Method:
    //TODO Remove
    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public Event(Parcel in) {
        super(in);
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

    @SerializedName(Constants.JsonApi.Event.NAME)
    private String name;

    @SerializedName(Constants.JsonApi.Event.ORGANIZER)
    private String organizer;

    @SerializedName(Constants.JsonApi.Event.DATE)
    private String date;

    @SerializedName(Constants.JsonApi.Event.LOCATION)
    private String location;

    @SerializedName(Constants.JsonApi.Event.DESCRIPTION)
    private String description;

    @SerializedName(Constants.JsonApi.Event.LINK)
    private String link;

    @SerializedName(Constants.JsonApi.Event.IMAGE_PATH)
    private String imagePath;

    @SerializedName(Constants.JsonApi.Event.TOTAL_TICKETS)
    private int totalTickets;

    @SerializedName(Constants.JsonApi.Event.TICKETS_SOLD)
    private int ticketsSold;

    @SerializedName(Constants.JsonApi.Event.TICKETS)
    private Collection<Ticket> tickets;

    @SerializedName(Constants.JsonApi.Event.NUMBER_OF_TICKET_STATUSES)
    private int numberOfTicketStatuses;

    @SerializedName(Constants.JsonApi.Event.TICKET_STATUSES)
    private Collection<TicketStatus> ticketStatuses;

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
