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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;
import com.niupiao.niupiao.utils.ImageLoaderHelper;

/**
 * Created by kevinchen on 2/18/15.
 */
public class EventInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_event_info, container, false);

        NetworkImageView image = (NetworkImageView) root.findViewById(R.id.event_image);
        image.setImageUrl(Constants.Url.fullUrl(TempPayInformation.EventInfo.getImagepath()),
                ImageLoaderHelper.getInstance().getImageLoader());
        TextView name = (TextView) root.findViewById(R.id.tv_event_title);
        TextView subtitle = (TextView) root.findViewById(R.id.tv_event_subtitle);
        TextView date = (TextView) root.findViewById(R.id.tv_event_date);
        TextView location = (TextView) root.findViewById(R.id.tv_event_where);

        name.setText(TempPayInformation.EventInfo.getName());
        subtitle.setText(TempPayInformation.EventInfo.getSubtitle());
        date.setText(TempPayInformation.EventInfo.getDate());
        location.setText(TempPayInformation.EventInfo.getLoc());

        ImageButton blueplus = (ImageButton) root.findViewById(R.id.event_info_general_plus_button);
        blueplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView general_number = (TextView) getActivity().findViewById(R.id.event_info_general_number_tickets);
                int generalTickets = Integer.parseInt(general_number.getText().toString());

                if(generalTickets < 3){
                    general_number.setText("" + (generalTickets + 1));
                    TextView raw_general_price = (TextView) getActivity().findViewById(R.id.tv_general_price);
                    int ticket_price = Integer.parseInt(raw_general_price.getText().toString().split("\\$")[1]);

                    TextView checkout_cost = (TextView) getActivity().findViewById(R.id.tv_checkout_cost);
                    int checkout_price = Integer.parseInt(checkout_cost.getText().toString().split("\\$")[1]);

                    checkout_price += ticket_price;
                    checkout_cost.setText("$" + checkout_price);
                }
            }
        });

        ImageButton blueminus = (ImageButton) root.findViewById(R.id.event_info_general_minus_button);
        blueminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView general_number = (TextView) getActivity().findViewById(R.id.event_info_general_number_tickets);
                int generalTickets = Integer.parseInt(general_number.getText().toString());

                if(generalTickets > 0) {
                    TextView raw_general_price = (TextView) getActivity().findViewById(R.id.tv_general_price);
                    int ticket_price = Integer.parseInt(raw_general_price.getText().toString().split("\\$")[1]);

                    TextView checkout_cost = (TextView) getActivity().findViewById(R.id.tv_checkout_cost);
                    int checkout_price = Integer.parseInt(checkout_cost.getText().toString().split("\\$")[1]);
                    checkout_price -= ticket_price;
                    checkout_cost.setText("$" + checkout_price);
                    general_number.setText("" + (generalTickets - 1));
                }
            }
        });

        ImageButton orangeplus = (ImageButton) root.findViewById(R.id.event_info_vip_plus_button);
        orangeplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView vip_number = (TextView) getActivity().findViewById(R.id.event_info_vip_number_tickets);
                int vipTickets = Integer.parseInt(vip_number.getText().toString());

                if(vipTickets < 2) {
                    TextView raw_vip_price = (TextView) getActivity().findViewById(R.id.tv_vip_price);
                    int ticket_price = Integer.parseInt(raw_vip_price.getText().toString().split("\\$")[1]);

                    TextView checkout_cost = (TextView) getActivity().findViewById(R.id.tv_checkout_cost);
                    int checkout_price = Integer.parseInt(checkout_cost.getText().toString().split("\\$")[1]);

                    checkout_price += ticket_price;
                    checkout_cost.setText("$" + checkout_price);
                    vip_number.setText("" + (vipTickets + 1));
                }
            }
        });

        ImageButton orangeminus = (ImageButton) root.findViewById(R.id.event_info_vip_minus_button);
        orangeminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView vip_number = (TextView) getActivity().findViewById(R.id.event_info_vip_number_tickets);
                int vipTickets = Integer.parseInt(vip_number.getText().toString());

                if(vipTickets > 0) {
                    TextView raw_vip_price = (TextView) getActivity().findViewById(R.id.tv_vip_price);
                    int ticket_price = Integer.parseInt(raw_vip_price.getText().toString().split("\\$")[1]);

                    TextView checkout_cost = (TextView) getActivity().findViewById(R.id.tv_checkout_cost);
                    int checkout_price = Integer.parseInt(checkout_cost.getText().toString().split("\\$")[1]);

                    checkout_price -= ticket_price;
                    checkout_cost.setText("$" + checkout_price);
                    vip_number.setText("" + (vipTickets - 1));
                }
            }
        });

        ImageButton checkoutButton = (ImageButton) root.findViewById(R.id.ib_checkout);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView general_number = (TextView) getActivity().findViewById(R.id.event_info_general_number_tickets);
                int generalTickets = Integer.parseInt(general_number.getText().toString());
                TextView vip_number = (TextView) getActivity().findViewById(R.id.event_info_vip_number_tickets);
                int vipTickets = Integer.parseInt(vip_number.getText().toString());
                TempPayInformation.PayInfo.setTotalticket(generalTickets + vipTickets);

                TextView checkout_cost = (TextView) getActivity().findViewById(R.id.tv_checkout_cost);
                int ticket_price = Integer.parseInt(checkout_cost.getText().toString().split("\\$")[1]);
                TempPayInformation.PayInfo.setPrice(ticket_price);


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
