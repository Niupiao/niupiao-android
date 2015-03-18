package com.niupiao.niupiao.fragments.payment;

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

import java.util.Map;

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

        Map<TicketStatus, Integer> numTickets = paymentManager.getNumTickets();

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

        // We will be adding a row for each ticket status
        for (final TicketStatus ticketStatus : numTickets.keySet()) {

            //View child = layoutInflater.inflate(R.layout.payment_checkout_tickets_row, insideScrollView);
            View child = factory.inflate(R.layout.payment_checkout_tickets_row, null);

            TextView ticketStatusTextView = (TextView) child.findViewById(R.id.tv_ticket_status);
            ticketStatusTextView.setText(ticketStatus.getName());

            TextView ticketPriceTextView = (TextView) child.findViewById(R.id.tv_ticket_price);
            ticketPriceTextView.setText("$" + ticketStatus.getPrice());

            final TextView numberOfTicketsPurchasedTextView = (TextView) child.findViewById(R.id.tv_number_tickets);

            ImageButton minusButton = (ImageButton) child.findViewById(R.id.ib_minus_button);
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentManager.decrement(ticketStatus, numberOfTicketsPurchasedTextView, checkoutCostTextView);
                }
            });

            ImageButton plusButton = (ImageButton) child.findViewById(R.id.ib_plus_button);
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentManager.increment(ticketStatus, numberOfTicketsPurchasedTextView, checkoutCostTextView);
                }
            });
            insideScrollView.addView(child);

            // TODO If it's not the last status, add the divider
            //View divider = layoutInflater.inflate(R.layout.payment_checkout_tickets_row_divider, insideScrollView);
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

        name.setText(event.getName());
        date.setText(DateUtils.format(event.getDate(), DateUtils.FORMAT_DATE));
        location.setText(event.getLocation());

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
