package com.niupiao.niupiao.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    public static final String INTENT_KEY_FOR_USER = "user";

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.niupiao_blue)));

        View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar_payment, null);
        actionBar.setCustomView(mActionBarView);
        View postView = actionBar.getCustomView();
        ActionBar.LayoutParams lp = (ActionBar.LayoutParams) postView.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        postView.setLayoutParams(lp);
        Button rtnToEvents = (Button) mActionBarView.findViewById(R.id.btn_return_events);

        rtnToEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        event = getIntent().getParcelableExtra(INTENT_KEY_FOR_EVENT);
        paymentManager = new PaymentManager(event, this);
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
