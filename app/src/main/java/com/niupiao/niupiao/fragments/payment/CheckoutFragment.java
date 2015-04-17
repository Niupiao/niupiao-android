package com.niupiao.niupiao.fragments.payment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;
import com.niupiao.niupiao.adapters.ViewPagerAdapter;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pick Contact request
        if (requestCode == TransferTicketsFragment.RQS_PICK_CONTACT) {
            // If the request was for the contact picker
            if (resultCode == Activity.RESULT_OK) {
                Uri contactURI = data.getData();
                
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getActivity().getContentResolver().query(contactURI, projection,
                        null, null, null);
                cursor.moveToFirst();

                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                TransferTicketsFragment fragment = TransferTicketsFragment.getTransferFragment();
                
                Button contactButton = fragment.getLastButtonClicked();
                contactButton.setText(number);
                contactButton.setBackground(null);
            }

        } else if (requestCode == ConfirmPurchaseFragment.REQUEST_PAYPAL_PAYMENT) {
            // Paypal request
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirm != null) {
                    try {
                        Log.i("paymentExample", confirm.toJSONObject().toString(4));
                        // TODO send confirmation to NiuPiao servers for verification

                        // Shows the reader congratulations dialogue
                        // showConfirmationDialogue();

                    } catch (JSONException e) {
                        Log.e("PaymentExample", "an unlikely failure occured:", e);
                    }

                    Log.e("Check", "Just Checking to see if it reaches here");
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }

    }

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

}
