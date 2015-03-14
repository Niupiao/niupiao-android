package com.niupiao.niupiao.fragments.payment;

import com.niupiao.niupiao.activities.PayActivity;
import com.niupiao.niupiao.fragments.ViewPagerFragment;
import com.niupiao.niupiao.managers.PaymentManager;

/**
 * Created by kevinchen on 3/11/15.
 */
public abstract class CheckoutViewPagerFragment extends ViewPagerFragment {

    protected void nextCheckoutPhase() {
        CheckoutFragment checkoutFragment = (CheckoutFragment) getParentFragment();
        checkoutFragment.nextCheckoutPhase();
    }

    protected PaymentManager getPaymentManager() {
        PayActivity payActivity = (PayActivity) getActivity();
        return payActivity.getPaymentManager();
    }
}
