package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;

/**
 * Created by kevinchen on 2/18/15.
 */
public class EventInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_event_info, container, false);
        ImageButton checkoutButton = (ImageButton) root.findViewById(R.id.ib_checkout);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayActivity activity = (PayActivity) getActivity();
                activity.nextPaymentPhase();
            }
        });
        return root;
    }

    public static EventInfoFragment newInstance() {
        return new EventInfoFragment();
    }
}
