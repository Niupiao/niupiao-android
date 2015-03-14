package com.niupiao.niupiao.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupiao.niupiao.R;

/**
 * Created by kevinchen on 2/18/15.
 */
public class StarredNavFragment extends NiuNavigationDrawerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_starred, container, false);
        return root;
    }
}
