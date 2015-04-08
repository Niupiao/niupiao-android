package com.niupiao.niupiao.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.niupiao.niupiao.Constants;

/**
 * Created by kevinchen on 2/17/15.
 */
public class Ticket extends ParcelableModel {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(eventId);
        dest.writeParcelable(event, 0);
        dest.writeInt(userId);
        dest.writeString(status);
        dest.writeParcelable(ticketStatus, 0);
    }

    public Ticket() {

    }

    public Ticket(Parcel in) {
        super(in);
        eventId = in.readInt();
        event = in.readParcelable(Event.class.getClassLoader());
        userId = in.readInt();
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

    @SerializedName(Constants.JsonApi.Ticket.EVENT_ID)
    private int eventId;

    @SerializedName(Constants.JsonApi.Ticket.EVENT)
    private Event event;

    @SerializedName(Constants.JsonApi.Ticket.USER_ID)
    private int userId;

    @SerializedName(Constants.JsonApi.Ticket.STATUS)
    private String status;

    @SerializedName(Constants.JsonApi.Ticket.TICKET_STATUS)
    private TicketStatus ticketStatus;

    public int getEventId() {
        return eventId;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public Event getEvent() {
        return event;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public Ticket setEvent(Event event) {
        this.event = event;
        return this;
    }

    public Ticket setEventId(int eventId) {
        this.eventId = eventId;
        return this;
    }

    public Ticket setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Ticket setStatus(String status) {
        this.status = status;
        return this;
    }

    public Ticket setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
        return this;
    }
}
