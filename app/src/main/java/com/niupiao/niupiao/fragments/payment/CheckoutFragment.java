package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;
import com.niupiao.niupiao.adapters.ViewPagerAdapter;

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
                PayActivity payActivity = (PayActivity) getActivity();
                // TODO determine whether checkout was cancelled -- for now assume it was not cancelled
                payActivity.confirmTicketPurchase(false);
        }
    }

    private void show(CheckoutPhase checkoutPhase) {
        this.checkoutPhase = checkoutPhase;
        pager.setCurrentItem(checkoutPhase.getPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sliding_view_pager, container, false);

        // Initialize fragments (order in which they're added to list matters)
        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(TransferTicketsFragment.newInstance());
        fragments.add(PaymentMethodFragment.newInstance());
        fragments.add(ConfirmPurchaseFragment.newInstance());

        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) root.findViewById(R.id.pager);
        pager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        tabs.setIndicatorColor(getResources().getColor(R.color.niupiao_orange));
        tabs.setViewPager(pager);

        return root;
    }

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

}
