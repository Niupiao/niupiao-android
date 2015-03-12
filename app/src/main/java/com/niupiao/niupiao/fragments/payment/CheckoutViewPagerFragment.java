package com.niupiao.niupiao.fragments.payment;

import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by kevinchen on 3/11/15.
 */
public abstract class CheckoutViewPagerFragment extends ViewPagerFragment {
    protected void nextCheckoutPhase() {
        CheckoutFragment checkoutFragment = (CheckoutFragment) getParentFragment();
        checkoutFragment.nextCheckoutPhase();
    }
}
