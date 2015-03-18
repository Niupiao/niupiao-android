package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.niupiao.niupiao.R;
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

        TextView info = (TextView) root.findViewById(R.id.info);

        PaymentManager paymentManager = getPaymentManager();

        Map<TicketStatus, Integer> numTickets = paymentManager.getNumTickets();

        for (TicketStatus ticketStatus : numTickets.keySet()) {
            // TODO dynamically add views
        }


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
