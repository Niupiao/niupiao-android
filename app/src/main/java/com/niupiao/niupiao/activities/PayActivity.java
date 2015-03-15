package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.payment.CheckoutFragment;
import com.niupiao.niupiao.fragments.payment.EventInfoFragment;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.Event;

/**
 * Created by kevinchen on 2/25/15.
 */
public class PayActivity extends ActionBarActivity {

    private static final String TAG = PayActivity.class.getSimpleName();

    public static final String INTENT_KEY_FOR_EVENT = "event";

    private PaymentManager paymentManager;

    /**
     * The phase of ticket purchasing in which the user starts.
     */
    private PaymentPhase paymentPhase;

    /**
     * The event for which the user is purchasing tickets.
     */
    private Event event;

    /**
     * The phases of ticket purchasing.
     */
    public enum PaymentPhase {
        PURCHASE_TICKETS(EventInfoFragment.class.getName()),
        CHECKOUT(CheckoutFragment.class.getName());

        private String fragmentClassName;

        private PaymentPhase(String fragmentClassName) {
            this.fragmentClassName = fragmentClassName;
        }

        public String getFragmentClassName() {
            return fragmentClassName;
        }
    }

    public PaymentManager getPaymentManager() {
        return paymentManager;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        event = getIntent().getParcelableExtra(INTENT_KEY_FOR_EVENT);
        paymentManager = new PaymentManager(event);
        show(PaymentPhase.PURCHASE_TICKETS);
    }

    public void nextPaymentPhase() {
        switch (paymentPhase) {
            case PURCHASE_TICKETS:
                show(PaymentPhase.CHECKOUT);
                break;
            default:
                Log.wtf(TAG, "what other phase?");
        }
    }

    private void show(PaymentPhase paymentPhase) {
        this.paymentPhase = paymentPhase;
        Fragment fragment = Fragment.instantiate(this, paymentPhase.getFragmentClassName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
