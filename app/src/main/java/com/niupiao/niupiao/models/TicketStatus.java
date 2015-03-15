package com.niupiao.niupiao.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kevinchen on 3/14/15.
 */
public class TicketStatus implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(maxPurchasable);
    }

    public TicketStatus(Parcel in) {
        id = in.readInt();
        name = in.readString();
        maxPurchasable = in.readInt();
    }

    public static final Creator<TicketStatus> CREATOR = new Creator<TicketStatus>() {
        @Override
        public TicketStatus createFromParcel(Parcel source) {
            return new TicketStatus(source);
        }

        @Override
        public TicketStatus[] newArray(int size) {
            return new TicketStatus[size];
        }
    };

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("max_purchasable")
    private int maxPurchasable;

    public String getName() {
        return name;
    }

    public int getMaxPurchasable() {
        return maxPurchasable;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TicketStatus && ((TicketStatus) o).getName().equals(name);
    }
}
