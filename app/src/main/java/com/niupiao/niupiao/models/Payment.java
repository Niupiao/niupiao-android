package com.niupiao.niupiao.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Inanity on 3/7/2015.
 */
public class Payment implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(cell);
        dest.writeByte((byte) (withMe ? 1 : 0));
    }

    public Payment(Parcel in) {
        number = in.readInt();
        type = in.readString();
        name = in.readString();
        cell = in.readString();
        withMe= in.readByte() != 0;
    }

    @SerializedName("number")
    private int number;

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("cell")
    private String cell;

    @SerializedName("withMe")
    private boolean withMe;

    public String getName(){ return name; }

    public String getCell(){ return cell; }

    public boolean getWithMe(){ return withMe; }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Payment) {
            Payment e = (Payment) o;
            return e.getCell() == this.getCell();
        }
        return false;
    }
}
