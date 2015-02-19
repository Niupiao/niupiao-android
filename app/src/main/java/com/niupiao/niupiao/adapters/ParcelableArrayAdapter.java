package com.niupiao.niupiao.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinchen on 2/18/15.
 */
public class ParcelableArrayAdapter<T extends Parcelable> extends ArrayAdapter<T> {

    public ParcelableArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ArrayList<Parcelable> getParcelableArrayList() {
        int count = getCount();
        ArrayList<Parcelable> objects = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            objects.add(getItem(i));
        }
        return objects;
    }

    public ArrayList<T> getArrayList() {
        int count = getCount();
        ArrayList<T> objects = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            objects.add(getItem(i));
        }
        return objects;
    }

    public void setObjects(List<T> objects) {
        clear();
        addAll(objects);
    }

    public void setParcelables(List<Parcelable> parcelables) {
        if (parcelables != null) {
            clear();
            for (Parcelable parcelable : parcelables) {
                add((T) parcelable);
            }
        }
    }

}
