package com.niupiao.niupiao.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kevinchen on 2/17/15.
 */
public class Ticket implements Parcelable {

    @Override
    public String toString() {
        return String.format("%s for $%d", status, price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(eventId);
        dest.writeParcelable(event, 0);
        dest.writeInt(userId);
        dest.writeInt(price);
        dest.writeString(status);
        dest.writeParcelable(ticketStatus, 0);
    }

    public Ticket() {
    }

    public Ticket(Parcel in) {
        id = in.readInt();
        eventId = in.readInt();
        event = in.readParcelable(Event.class.getClassLoader());
        userId = in.readInt();
        price = in.readInt();
        status = in.readString();
        ticketStatus = in.readParcelable(TicketStatus.class.getClassLoader());
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel source) {
            return new Ticket(source);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    @SerializedName("id")
    private int id;

    @SerializedName("event_id")
    private int eventId;

    @SerializedName("event")
    private Event event;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("price")
    private int price;

    @SerializedName("status")
    private String status;

    @SerializedName("ticket_status")
    private TicketStatus ticketStatus;

    public int getId() {
        return id;
    }

    public int getEventId() {
        return eventId;
    }

    public int getUserId() {
        return userId;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
