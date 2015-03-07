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
        dest.writeString(name);
        dest.writeString(cell);
        dest.writeByte((byte) (with_me ? 1 : 0));
    }

    public Payment(Parcel in) {
        name = in.readString();
        cell = in.readString();
        with_me = in.readByte() != 0;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("cell")
    private String cell;

    @SerializedName("with_me")
    private Boolean with_me;

    public String getName(){ return name; }

    public String getCell(){ return cell; }

    public Boolean getWithMe(){ return with_me; }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Payment) {
            Payment e = (Payment) o;
            return e.getCell() == this.getCell();
        }
        return false;
    }
}
