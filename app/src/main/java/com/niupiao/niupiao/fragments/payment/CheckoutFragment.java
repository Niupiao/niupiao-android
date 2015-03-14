package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.ViewPagerAdapter;
import com.niupiao.niupiao.widgets.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinchen on 2/25/15.
 */
public class CheckoutFragment extends Fragment {

    private ViewPager pager;
    private CheckoutPhase checkoutPhase = CheckoutPhase.TRANSFER_TICKETS;

    public enum CheckoutPhase {

        TRANSFER_TICKETS(0),
        PAYMENT_INFO(1),
        CONFIRM_PURCHASE(2);

        private final int position;

        private CheckoutPhase(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    public void nextCheckoutPhase() {
        switch (checkoutPhase) {
            case TRANSFER_TICKETS:
                show(CheckoutPhase.PAYMENT_INFO);
                break;
            case PAYMENT_INFO:
                show(CheckoutPhase.CONFIRM_PURCHASE);
                break;
            case CONFIRM_PURCHASE:
                getActivity().finish();
        }
    }

    private void show(CheckoutPhase checkoutPhase) {
        this.checkoutPhase = checkoutPhase;
        pager.setCurrentItem(checkoutPhase.getPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_non_sliding_view_pager, container, false);
//        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sliding_view_pager, container, false);

        // Initialize fragments
        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(0, TransferTicketsFragment.newInstance());
        fragments.add(1, PaymentInfoFragment.newInstance());
        fragments.add(2, ConfirmPurchaseFragment.newInstance());

        // Initialize the ViewPager and set an adapter
        pager = (NonSwipeableViewPager) root.findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments));

        return root;
    }

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

}
