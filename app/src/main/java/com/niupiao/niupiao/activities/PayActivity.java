package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.payment.EventInfoFragment;
import com.niupiao.niupiao.fragments.payment.ConfirmFragment;
import com.niupiao.niupiao.fragments.payment.CongratsFragment;
import com.niupiao.niupiao.fragments.payment.PayFragment;
import com.niupiao.niupiao.fragments.payment.TempPayInformation;
import com.niupiao.niupiao.models.Event;

/**
 * Created by kevinchen on 2/25/15.
 */
public class PayActivity extends ActionBarActivity {

    public static final String INTENT_KEY_FOR_EVENT = "event";

    private PaymentPhase paymentPhase = PaymentPhase.INFO;

    public enum PaymentPhase {
        INFO, PAY, CONFIRM, CONGRATS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Event event = getIntent().getParcelableExtra(INTENT_KEY_FOR_EVENT);

        TempPayInformation.EventInfo.setName(event.getName());
        TempPayInformation.EventInfo.setDate(event.getDate());
        TempPayInformation.EventInfo.setLoc(event.getLocation());
        TempPayInformation.EventInfo.setImagepath(event.getImagePath());

        show(PaymentPhase.INFO);
    }

    public void nextPaymentPhase() {
        switch (paymentPhase) {
            case INFO:
                show(PaymentPhase.PAY);
                changeInternalPaymentPhase();
                break;
            case PAY:
                show(PaymentPhase.CONFIRM);
                changeInternalPaymentPhase();
                break;
            case CONFIRM:
                show(PaymentPhase.CONGRATS);
                changeInternalPaymentPhase();
                break;
            case CONGRATS:
                finish();
                break;
        }
    }

    public void changeInternalPaymentPhase(){
        switch (paymentPhase) {
            case INFO:
                paymentPhase = PaymentPhase.PAY;
                break;
            case PAY:
                paymentPhase = PaymentPhase.CONFIRM;
                break;
            case CONFIRM:
                paymentPhase = PaymentPhase.CONGRATS;
                break;
        }
    }

    private void show(PaymentPhase paymentPhase) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (paymentPhase) {
            case INFO:
                fragment = EventInfoFragment.newInstance();
                break;
            case PAY:
                fragment = PayFragment.newInstance();
                break;
            case CONFIRM:
                fragment = ConfirmFragment.newInstance();
                break;
            case CONGRATS:
                fragment = CongratsFragment.newInstance();
                break;
            default:
                throw new IllegalArgumentException("Bad enum for " +
                        PaymentPhase.class.getSimpleName());
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }
}
