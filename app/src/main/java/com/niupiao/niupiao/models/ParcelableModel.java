package com.niupiao.niupiao.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kevinchen on 3/18/15.
 */
public abstract class ParcelableModel extends Model implements Parcelable {

    public ParcelableModel() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

    public ParcelableModel(Parcel in) {
        id = in.readInt();
    }

}
