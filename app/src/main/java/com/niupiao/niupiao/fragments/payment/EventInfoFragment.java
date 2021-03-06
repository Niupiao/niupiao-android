package com.niupiao.niupiao.fragments.payment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.TicketStatus;
import com.niupiao.niupiao.utils.DateUtils;
import com.niupiao.niupiao.utils.ImageLoaderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kevinchen on 2/18/15.
 */
public class EventInfoFragment extends Fragment {

    public static final String TAG = EventInfoFragment.class.getSimpleName();

    private PaymentManager paymentManager;

    public static EventInfoFragment newInstance() {
        return new EventInfoFragment();
    }

    private void initializeTicketRows(ViewGroup root) {

        Map<TicketStatus, Integer> numTickets = paymentManager.getTickets();

        Typeface black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");

        //TODO Clean up ex-code that's now stuffed in comments.
        // We will be adding stuff to the sole child of a ScrollView. (ScrollView can only have one child).
        //RelativeLayout insideScrollView = (RelativeLayout) root.findViewById(R.id.sv_child);
        LinearLayout insideScrollView = (LinearLayout) root.findViewById(R.id.sv_child);

        // The layout inflater will be dynamically inflating views,
        // From: http://developer.android.com/reference/android/view/LayoutInflater.html
        // "It is never used directly"
        // LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater factory = LayoutInflater.from(root.getContext());

        // The TextView showing total checkout
        final TextView checkoutCostTextView = (TextView) root.findViewById(R.id.tv_cost);

        Set<TicketStatus> ticketStatusesSet = numTickets.keySet();
        List<TicketStatus> ticketStatuses = new ArrayList<>(ticketStatusesSet.size());
        ticketStatuses.addAll(ticketStatusesSet);

        // We will be adding a row for each ticket status
        for (int i = 0; i < ticketStatuses.size(); i++) {

            final TicketStatus ticketStatus = ticketStatuses.get(i);

            //View child = layoutInflater.inflate(R.layout.payment_checkout_tickets_row, insideScrollView);
            View child = factory.inflate(R.layout.payment_checkout_tickets_row, null);

            int color = getActivity().getResources().getColor(i % 2 == 0 ? R.color.niupiao_blue : R.color.niupiao_orange);
            LinearLayout ticketInfoBox = (LinearLayout) child.findViewById(R.id.ll_ticket_info);
            ticketInfoBox.setBackgroundColor(color);

            TextView ticketStatusTextView = (TextView) child.findViewById(R.id.tv_ticket_status);
            ticketStatusTextView.setText(ticketStatus.getName());

            TextView ticketPriceTextView = (TextView) child.findViewById(R.id.tv_ticket_price);
            ticketPriceTextView.setText("$" + ticketStatus.getPrice());

            final TextView numberOfTicketsPurchasedTextView = (TextView) child.findViewById(R.id.tv_number_tickets);
            numberOfTicketsPurchasedTextView.setTextColor(color);
            numberOfTicketsPurchasedTextView.setTypeface(black);

            ImageButton minusButton = (ImageButton) child.findViewById(R.id.ib_minus_button);
            minusButton.setImageDrawable(getActivity().getResources().getDrawable(i % 2 == 0 ? R.drawable.blue_minus : R.drawable.orange_minus));
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentManager.decrement(ticketStatus, numberOfTicketsPurchasedTextView, checkoutCostTextView);
                }
            });

            ImageButton plusButton = (ImageButton) child.findViewById(R.id.ib_plus_button);
            plusButton.setImageDrawable(getActivity().getResources().getDrawable(i % 2 == 0 ? R.drawable.blue_plus : R.drawable.orange_plus));
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentManager.increment(ticketStatus, numberOfTicketsPurchasedTextView, checkoutCostTextView);
                }
            });
            insideScrollView.addView(child);

            // TODO If it's not the last status, add the divider
            if (i < ticketStatuses.size() - 1) {
                View divider = factory.inflate(R.layout.payment_checkout_tickets_row_divider, null);
                insideScrollView.addView(divider);
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_event_info, container, false);

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
        TextView getTickets = (TextView) root.findViewById(R.id.tv_get_tickets);
        TextView price = (TextView) root.findViewById(R.id.tv_cost);

        name.setText(event.getName());
        subtitle.setText("");
        date.setText(DateUtils.format(event.getDate(), DateUtils.FORMAT_DATE));
        location.setText(event.getLocation());

        Typeface black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
        Typeface light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        getTickets.setTypeface(black);
        price.setTypeface(light);

        initializeTicketRows(root);

        ImageButton imageButton = (ImageButton) root.findViewById(R.id.ib_next_screen);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });

        // TODO Set fonts
//        Typeface robotoBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");

        return root;
    }

    private void checkout() {
        PayActivity activity = (PayActivity) getActivity();
        activity.nextPaymentPhase();
    }


}
