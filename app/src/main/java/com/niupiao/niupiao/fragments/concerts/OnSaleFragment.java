package com.niupiao.niupiao.fragments.concerts;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.niupiao.niupiao.adapters.ParcelableArrayAdapter;
import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by kevinchen on 2/17/15.
 */
public class OnSaleFragment extends ViewPagerFragment {
    @Override
    public String getTitle() {
        return "On Sale";
    }

    /**
     * @param position This fragment's position in its ViewPager
     */
    public static OnSaleFragment newInstance(int position) {
        OnSaleFragment fragment = new OnSaleFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ParcelableArrayAdapter getParcelableArrayAdapter() {
        // TODO
        return null;
    }

///////////// LIFE CYCLE CALLBACKS FOR TESTING

    public static final String TAG = OnSaleFragment.class.getSimpleName();
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
//        outState.putParcelableArrayList("events", ((EventsAdapter) concertsListView.getAdapter()).getArrayList());
    }
}
