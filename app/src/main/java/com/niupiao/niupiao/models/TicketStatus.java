package com.niupiao.niupiao.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.niupiao.niupiao.Constants;

/**
 * Created by kevinchen on 3/14/15.
 */
public class TicketStatus extends ParcelableModel {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(maxPurchasable);
        dest.writeInt(price);
    }

    public TicketStatus() {
    }

    //For the purposes of the hack.
    public TicketStatus(String name, int maxPurchasable, int price){
        this.name = name;
        this.maxPurchasable = maxPurchasable;
        this.price = price;
    }

    public TicketStatus(Parcel in) {
        super(in);
        name = in.readString();
        maxPurchasable = in.readInt();
        price = in.readInt();
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

    @SerializedName(Constants.JsonApi.TicketStatus.NAME)
    private String name;

    @SerializedName(Constants.JsonApi.TicketStatus.PRICE)
    private int price;

    @SerializedName(Constants.JsonApi.TicketStatus.MAX_PURCHASABLE)
    private int maxPurchasable;

    public String getName() {
        return name;
    }

    public int getMaxPurchasable() {
        return maxPurchasable;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TicketStatus && ((TicketStatus) o).getName().equals(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMaxPurchasable(int maxPurchasable) {
        this.maxPurchasable = maxPurchasable;
    }
}
