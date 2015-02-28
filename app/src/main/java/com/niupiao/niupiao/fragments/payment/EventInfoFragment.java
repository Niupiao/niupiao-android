package com.niupiao.niupiao.fragments.payment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        Button blueplus = (Button) root.findViewById(R.id.asdasd);
        blueplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button blueminus = (Button) root.findViewById(R.id.asdasd);
        blueminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button orangeplus = (Button) root.findViewById(R.id.asdasd);
        blueplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button orangeminus = (Button) root.findViewById(R.id.asdasd);
        blueplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageButton checkoutButton = (ImageButton) root.findViewById(R.id.ib_checkout);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempPayInformation.PayInfo.setPrice(2);

                PayActivity activity = (PayActivity) getActivity();
                activity.nextPaymentPhase();
            }
        });
        
        Typeface robotoBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        
        TextView getTickets = (TextView) root.findViewById( R.id.event_info_get_info_tv );
        getTickets.setTypeface( robotoBold );

        LinearLayout ticketGuidelines = (LinearLayout) root.findViewById(R.id.event_info_ticketing_guidelines);
        final TextView showGuidelines = (TextView) root.findViewById(R.id.event_info_ticketing_guidelines_show );
        ticketGuidelines.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showGuidelines.isShown())
                    showGuidelines.setVisibility(View.GONE);
                else
                    showGuidelines.setVisibility(View.VISIBLE);
            }
        });

        LinearLayout announcements = (LinearLayout) root.findViewById(R.id.event_info_announcements);
        final TextView showAnnouncements = (TextView) root.findViewById(R.id.event_info_announcements_show );
        announcements.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showAnnouncements.isShown())
                    showAnnouncements.setVisibility(View.GONE);
                else
                    showAnnouncements.setVisibility(View.VISIBLE);
            }
        });

        LinearLayout eventDescription = (LinearLayout) root.findViewById(R.id.event_info_event_description);
        final TextView showDescription = (TextView) root.findViewById(R.id.event_info_description_show );
        eventDescription.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showDescription.isShown())
                    showDescription.setVisibility(View.GONE);
                else
                    showDescription.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }

    public static EventInfoFragment newInstance() {
        return new EventInfoFragment();
    }
}
