package com.niupiao.niupiao.fragments.payment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.activities.PayActivity;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.TicketStatus;
import com.niupiao.niupiao.models.User;

import java.util.Map;

/**
 * Created by kevinchen on 3/9/15.
 */
public class ConfirmPurchaseFragment extends CheckoutViewPagerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_confirm, container, false);

        User user = getActivity().getIntent().getParcelableExtra(PayActivity.INTENT_KEY_FOR_USER);

        // Tickets and payment info are stored in the PaymentManager
        PaymentManager paymentManager = getPaymentManager();

        // Set the total amount of money spent so far
        final Typeface black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
        final Typeface medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
        final Typeface light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

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
                    " " + user.getFirstName() + " " + user.getLastName());

            purchasers.setTypeface(light);

            receipt.addView(child);
        }

        TextView orderTotal = (TextView) root.findViewById(R.id.tv_order_cost);
        orderTotal.setText(paymentManager.getTotalCost() + "");

        TextView buyerName = (TextView) root.findViewById(R.id.tv_buyer_name);
        buyerName.setText(user.getFirstName() + " " + user.getLastName());

        ImageButton next = (ImageButton) root.findViewById(R.id.ib_next_screen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.payment_dialog_congratulations);

                TextView congrats = (TextView) dialog.findViewById(R.id.tv_congratulations);
                congrats.setTypeface(medium);

                Button dialogButton = (Button) dialog.findViewById(R.id.btn_view_tickets);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });

                dialog.show();
            }
        });
        next.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.confirm));


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
