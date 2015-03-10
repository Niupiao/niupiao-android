package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.payment.CheckoutFragment;
import com.niupiao.niupiao.fragments.payment.EventInfoFragment;
import com.niupiao.niupiao.models.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevinchen on 2/25/15.
 */
public class PayActivity extends ActionBarActivity {

    private static final String TAG = PayActivity.class.getSimpleName();

    public static final String INTENT_KEY_FOR_EVENT = "event";

    private List<Person> recipients;

    private final static int MAX_NUMBER_OF_RECIPIENTS = 5;

    private int totalTicketCount;
    private int totalPrice;

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
        PURCHASE_TICKETS,
        CHECKOUT
    }

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

    public PaymentPhase getPaymentPhase() {
        return paymentPhase;
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
        setContentView(R.layout.activity_pay);
        recipients = new ArrayList<>(MAX_NUMBER_OF_RECIPIENTS);
        event = getIntent().getParcelableExtra(INTENT_KEY_FOR_EVENT);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (paymentPhase) {
            case PURCHASE_TICKETS:
                fragment = EventInfoFragment.newInstance();
                break;
            case CHECKOUT:
                Log.d(TAG, "showing checkout fragment");
                fragment = CheckoutFragment.newInstance();
                break;
            default:
                throw new IllegalArgumentException("Bad enum for " +
                        PaymentPhase.class.getSimpleName());
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }
}
