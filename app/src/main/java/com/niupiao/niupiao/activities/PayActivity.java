package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.payment.ConfirmFragment;
import com.niupiao.niupiao.fragments.payment.CongratsFragment;
import com.niupiao.niupiao.fragments.payment.EventInfoFragment;
import com.niupiao.niupiao.fragments.payment.PayFragment;
import com.niupiao.niupiao.models.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevinchen on 2/25/15.
 */
public class PayActivity extends ActionBarActivity {

    public static final String INTENT_KEY_FOR_EVENT = "event";

    /**
     * The phase of ticket purchasing in which the user starts.
     */
    private PaymentPhase paymentPhase = PaymentPhase.INFO;

    /**
     * The event for which the user is purchasing tickets.
     */
    private Event event;

    /**
     * The phases of ticket purchasing.
     */
    public enum PaymentPhase {
        INFO, PAY, CONFIRM, CONGRATS;
    }

    private final static int MAX_NUMBER_OF_RECIPIENTS = 5;

    private List<Person> recipients;
    private int totalTicketCount;
    private int totalPrice;

    /**
     * Used for storing recipients of tickets.
     */
    public static class Person {

        private String name;
        private String cell;
        private boolean me;

        public Person(String name, String cell, boolean me) {
            this.name = name;
            this.cell = cell;
            this.me = me;
        }

        public String getName() {
            return name;
        }

        public String getCell() {
            return cell;
        }

        public boolean isMe() {
            return me;
        }
    }

    public Event getEvent() {
        return event;
    }

    public void addRecipients(Person... recipients) {
        Collections.addAll(this.recipients, recipients);
    }

    public List<Person> getRecipients() {
        return recipients;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalTicketCount(int totalTicketCount) {
        this.totalTicketCount = totalTicketCount;
    }

    public int getTotalTicketCount() {
        return totalTicketCount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipients = new ArrayList<>(MAX_NUMBER_OF_RECIPIENTS);
        setContentView(R.layout.activity_pay);
        event = getIntent().getParcelableExtra(INTENT_KEY_FOR_EVENT);
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

    public void changeInternalPaymentPhase() {
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
