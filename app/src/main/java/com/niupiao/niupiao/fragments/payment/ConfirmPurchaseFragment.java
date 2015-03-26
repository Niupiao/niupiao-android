package com.niupiao.niupiao.fragments.payment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.TicketStatus;

import java.util.Map;

/**
 * Created by kevinchen on 3/9/15.
 */
public class ConfirmPurchaseFragment extends CheckoutViewPagerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_confirm, container, false);

        // Tickets and payment info are stored in the PaymentManager
        PaymentManager paymentManager = getPaymentManager();

        // Set the total amount of money spent so far
        Typeface black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
        Typeface light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        TextView cost = (TextView) root.findViewById(R.id.tv_cost);
        cost.setText("$" + paymentManager.getTotalCost());
        cost.setTypeface(light);

        TextView title = (TextView) root.findViewById(R.id.tv_title);
        title.setTypeface(black);

        Map<TicketStatus, Integer> numTickets = paymentManager.getNumTickets();
        LinearLayout receipt = (LinearLayout) root.findViewById(R.id.ll_tickets_bought);

        for (TicketStatus ticketStatus : numTickets.keySet()) {
            // TODO dynamically add views

            LayoutInflater factory = LayoutInflater.from(root.getContext());
            View child = factory.inflate(R.layout.payment_confirm_row, null);

            TextView tickets = (TextView) child.findViewById(R.id.tv_number_tickets);
            TextView ticketCost = (TextView) child.findViewById(R.id.tv_cost);
            TextView purchasers = (TextView) child.findViewById(R.id.tv_purchasers);

            int numTicketsPurchased = numTickets.get(ticketStatus);
            tickets.setText(numTicketsPurchased + " " + ticketStatus.getName() + " Tickets");

            int costOfTickets = numTicketsPurchased * ticketStatus.getPrice();
            ticketCost.setText(costOfTickets + "");

            purchasers.setText(numTicketsPurchased + " " +
                    getActivity().getResources().getString(R.string.owned_by) +
                    " Ryan Kwon");

            purchasers.setTypeface(light);

            receipt.addView(child);
        }

        TextView orderTotal = (TextView) root.findViewById(R.id.tv_order_cost);
        orderTotal.setText(paymentManager.getTotalCost() + "");

        TextView buyerName = (TextView) root.findViewById(R.id.tv_buyer_name);
        buyerName.setText("Ryan Kwon");

        ImageButton next = (ImageButton) root.findViewById(R.id.ib_next_screen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCheckoutPhase();
            }
        });
        next.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.view_tickets));


        return root;
    }


    @Override
    public String getTitle() {
        return "Confirm";
    }

    public static ConfirmPurchaseFragment newInstance() {
        return new ConfirmPurchaseFragment();
    }
}
