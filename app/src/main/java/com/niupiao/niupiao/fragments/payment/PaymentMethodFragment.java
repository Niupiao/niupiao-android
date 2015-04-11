package com.niupiao.niupiao.fragments.payment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.managers.PaymentManager;

/**
 * Created by kevinchen on 3/9/15.
 */
public class PaymentMethodFragment extends CheckoutViewPagerFragment {
    
    public static boolean isPaypal;
    public static boolean isVisa;
    public static boolean isCard;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_method, container, false);

        Typeface black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
        Typeface light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        TextView title = (TextView) root.findViewById(R.id.tv_title);

        title.setTypeface(black);

        // Determines the payment method chosen
        final CheckBox paypal = (CheckBox) root.findViewById(R.id.cb_paypal);
        final CheckBox visa = (CheckBox) root.findViewById(R.id.cb_visa);
        final CheckBox card = (CheckBox) root.findViewById(R.id.cb_card);

        paypal.setChecked(true);
        isPaypal = true;
        paypal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPaypal = true;
                isVisa = false;
                visa.setChecked(false);
                card.setChecked(false);
            }
        });
        
        visa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isVisa = true;
                isPaypal = true;
                paypal.setChecked(false);
                card.setChecked(false);
            }
        });
        
        card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCard = true;
                isVisa = false;
                visa.setChecked(false);
                paypal.setChecked(false);
            }
        });

        // Tickets and payment info are stored in the PaymentManager
        PaymentManager paymentManager = getPaymentManager();

        // Set the total amount of money spent so far
        TextView cost = (TextView) root.findViewById(R.id.tv_cost);
        cost.setText("$" + paymentManager.getTotalCost());
        cost.setTypeface(light);

        ImageButton next = (ImageButton) root.findViewById(R.id.ib_next_screen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCheckoutPhase();
            }
        });
        next.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.paynow));

        return root;
    }
    
    private void uncheck(int which) {
        switch (which) {
            case 0:
                
        }
        
    }

    @Override
    public String getTitle() {
        return "Payment Info";
    }

    public static PaymentMethodFragment newInstance() {
        return new PaymentMethodFragment();
    }
}
