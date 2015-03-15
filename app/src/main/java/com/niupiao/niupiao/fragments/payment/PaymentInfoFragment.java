package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.niupiao.niupiao.R;

/**
 * Created by kevinchen on 3/9/15.
 */
public class PaymentInfoFragment extends CheckoutViewPagerFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_info, container, false);

        ImageButton next = (ImageButton) root.findViewById(R.id.ib_next_screen);
        next.setOnClickListener(this);
        next.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.payment_confirm_arrow));

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_next_screen:
                nextCheckoutPhase();
                break;
        }
    }

    @Override
    public String getTitle() {
        return "Payment Info";
    }

    public static PaymentInfoFragment newInstance() {
        return new PaymentInfoFragment();
    }
}
