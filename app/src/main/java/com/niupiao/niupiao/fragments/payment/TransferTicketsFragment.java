package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.niupiao.niupiao.R;

/**
 * Created by kevinchen on 3/9/15.
 */
public class TransferTicketsFragment extends CheckoutViewPagerFragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_payment_method:
                nextCheckoutPhase();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_transfer, container, false);

        TextView cost = (TextView) root.findViewById(R.id.tv_cost);
        cost.setText("$" + getPaymentManager().getTotalCost());

        ImageButton contact1 = (ImageButton) root.findViewById(R.id.ib_contacts_1);
        contact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "TODO - show contact chooser", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton next = (ImageButton) root.findViewById(R.id.ib_payment_method);
        next.setOnClickListener(this);

        return root;
    }

    @Override
    public String getTitle() {
        return "Transfer Tickets";
    }

    public static TransferTicketsFragment newInstance() {
        return new TransferTicketsFragment();
    }
}
