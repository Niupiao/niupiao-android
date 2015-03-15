package com.niupiao.niupiao.fragments.payment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.utils.DateUtils;
import com.niupiao.niupiao.utils.ImageLoaderHelper;

/**
 * Created by kevinchen on 2/18/15.
 */
public class EventInfoFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = EventInfoFragment.class.getSimpleName();

    private TextView vipTicketsTextView;
    private TextView generalTicketsTextView;

    private PaymentManager paymentManager;

    public static EventInfoFragment newInstance() {
        return new EventInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_event_info, container, false);

        vipTicketsTextView = (TextView) root.findViewById(R.id.event_info_vip_number_tickets);
        generalTicketsTextView = (TextView) root.findViewById(R.id.event_info_general_number_tickets);

        // Get event for this activity
        PayActivity payActivity = (PayActivity) getActivity();
        paymentManager = payActivity.getPaymentManager();

        Event event = payActivity.getEvent();

        // Set labels and images
        NetworkImageView image = (NetworkImageView) root.findViewById(R.id.event_image);
        image.setImageUrl(Constants.JsonApi.EndPoints.fullUrl(event.getImagePath()), ImageLoaderHelper.getInstance().getImageLoader());
        TextView name = (TextView) root.findViewById(R.id.tv_event_title);
        // TODO do something with subtitle
        TextView subtitle = (TextView) root.findViewById(R.id.tv_event_subtitle);
        TextView date = (TextView) root.findViewById(R.id.tv_event_date);
        TextView location = (TextView) root.findViewById(R.id.tv_event_where);

        name.setText(event.getName());
        date.setText(DateUtils.format(event.getDate(), DateUtils.FORMAT_DATE));
        location.setText(event.getLocation());

        // Set button listeners
        ImageButton imageButton = (ImageButton) root.findViewById(R.id.event_info_general_plus_button);
        imageButton.setOnClickListener(this);

        imageButton = (ImageButton) root.findViewById(R.id.event_info_general_minus_button);
        imageButton.setOnClickListener(this);

        imageButton = (ImageButton) root.findViewById(R.id.event_info_vip_plus_button);
        imageButton.setOnClickListener(this);

        imageButton = (ImageButton) root.findViewById(R.id.event_info_vip_minus_button);
        imageButton.setOnClickListener(this);

        imageButton = (ImageButton) root.findViewById(R.id.ib_checkout);
        imageButton.setOnClickListener(this);

        // Set fonts
        Typeface robotoBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        TextView getTickets = (TextView) root.findViewById(R.id.event_info_get_info_tv);
        getTickets.setTypeface(robotoBold);

        LinearLayout ticketGuidelines = (LinearLayout) root.findViewById(R.id.event_info_ticketing_guidelines);
        final TextView showGuidelines = (TextView) root.findViewById(R.id.event_info_ticketing_guidelines_show);
        ticketGuidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showGuidelines.isShown())
                    showGuidelines.setVisibility(View.GONE);
                else
                    showGuidelines.setVisibility(View.VISIBLE);
            }
        });

        LinearLayout announcements = (LinearLayout) root.findViewById(R.id.event_info_announcements);
        final TextView showAnnouncements = (TextView) root.findViewById(R.id.event_info_announcements_show);
        announcements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showAnnouncements.isShown())
                    showAnnouncements.setVisibility(View.GONE);
                else
                    showAnnouncements.setVisibility(View.VISIBLE);
            }
        });

        LinearLayout eventDescription = (LinearLayout) root.findViewById(R.id.event_info_event_description);
        final TextView showDescription = (TextView) root.findViewById(R.id.event_info_description_show);
        eventDescription.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_info_general_plus_button:
                incrementGeneralTickets();
                break;
            case R.id.event_info_general_minus_button:
                decrementGeneralTickets();
                break;
            case R.id.event_info_vip_plus_button:
                incrementVipTickets();
                break;
            case R.id.event_info_vip_minus_button:
                decrementVipTickets();
                break;
            case R.id.ib_checkout:
                checkout();
                break;
            default:
                Log.wtf(TAG, "What other button was pressed?");
        }
    }


    private void checkout() {
        PayActivity activity = (PayActivity) getActivity();
        activity.nextPaymentPhase();
    }

    private void updateCheckoutCost() {
        TextView checkoutCost = (TextView) getActivity().findViewById(R.id.tv_checkout_cost);
        int cost = paymentManager.getTotalCost();
        checkoutCost.setText("$" + cost);
    }

    private void incrementGeneralTickets() {
        if (paymentManager.getNumberGeneralTickets() < paymentManager.getMaxNumberOfGeneralTickets()) {
            paymentManager.setNumberGeneralTickets(paymentManager.getNumberGeneralTickets() + 1);
            generalTicketsTextView.setText("" + paymentManager.getNumberGeneralTickets());
            updateCheckoutCost();
        } else {
            Toast.makeText(getActivity(), String.format("Can buy up to %d GENERAL tickets", paymentManager.getMaxNumberOfGeneralTickets()), Toast.LENGTH_SHORT).show();
        }
    }

    private void decrementGeneralTickets() {
        if (paymentManager.getNumberGeneralTickets() > 0) {
            paymentManager.setNumberGeneralTickets(paymentManager.getNumberGeneralTickets() - 1);
            generalTicketsTextView.setText("" + paymentManager.getNumberGeneralTickets());
            updateCheckoutCost();
        }
    }

    private void incrementVipTickets() {
        if (paymentManager.getNumberVipTickets() < paymentManager.getMaxNumberOfVipTickets()) {
            paymentManager.setNumberVipTickets(paymentManager.getNumberVipTickets() + 1);
            vipTicketsTextView.setText("" + paymentManager.getNumberVipTickets());
            updateCheckoutCost();
        } else {
            Toast.makeText(getActivity(), String.format("Can buy up to %d VIP tickets", paymentManager.getMaxNumberOfVipTickets()), Toast.LENGTH_SHORT).show();
        }
    }

    private void decrementVipTickets() {
        if (paymentManager.getNumberVipTickets() > 0) {
            paymentManager.setNumberVipTickets(paymentManager.getNumberVipTickets() - 1);
            vipTicketsTextView.setText("" + paymentManager.getNumberVipTickets());
            updateCheckoutCost();
        }
    }
}
