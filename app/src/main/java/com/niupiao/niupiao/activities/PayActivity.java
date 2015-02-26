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
import com.niupiao.niupiao.models.Event;

/**
 * Created by kevinchen on 2/25/15.
 */
public class PayActivity extends ActionBarActivity {

    public static final String INTENT_KEY_FOR_EVENT = "event";

    private PaymentPhase paymentPhase = PaymentPhase.PAY;

    public enum PaymentPhase {
        INFO, PAY, CONFIRM, CONGRATS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Event event = getIntent().getParcelableExtra(INTENT_KEY_FOR_EVENT);
        show(PaymentPhase.INFO);
    }

    public void nextPaymentPhase() {
        switch (paymentPhase) {
            case INFO:
                show(PaymentPhase.PAY);
            case PAY:
                show(PaymentPhase.CONFIRM);
                break;
            case CONFIRM:
                show(PaymentPhase.CONGRATS);
                break;
            case CONGRATS:
                finish();
                break;
        }
    }

    private void show(PaymentPhase paymentPhase) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (paymentPhase) {
            case INFO:
                //TODO Fix issues with EventInfo before Final Demo.
                //fragment = EventInfoFragment.newInstance();

                //Temp code to have transfer + confirm to ensure that the correct
                // order of the screens are maintained.
                //fragment = PayFragment.newInstance();
                //nextPaymentPhase();
                //break;
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
