package com.niupiao.niupiao.fragments;

import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.niupiao.niupiao.adapters.ParcelableArrayAdapter;

import java.util.ArrayList;

/**
 * Created by kevinchen on 2/17/15.
 */
public abstract class ViewPagerFragment extends Fragment {

    /**
     * A Bundle key for storing a fragment's position in its ViewPager.
     */
    protected final static String POSITION_KEY = "position";

    public abstract String getTitle();

    public abstract ParcelableArrayAdapter getParcelableArrayAdapter();

    public ArrayList<Parcelable> getParcelableArrayList() {
        ParcelableArrayAdapter adapter = getParcelableArrayAdapter();
        return (adapter == null) ? null : adapter.getParcelableArrayList();
    }

    public void setParcelables(ArrayList<Parcelable> parcelables) {
        ParcelableArrayAdapter adapter = getParcelableArrayAdapter();
        adapter.setParcelables(parcelables);
    }




}
