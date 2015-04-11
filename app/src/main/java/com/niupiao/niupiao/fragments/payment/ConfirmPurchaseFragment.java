package com.niupiao.niupiao.fragments.payment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.TicketStatus;
import com.niupiao.niupiao.models.User;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by kevinchen on 3/9/15.
 */
public class ConfirmPurchaseFragment extends CheckoutViewPagerFragment {

    private int totalCost;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    private static final String CONFIG_CLIENT_ID = "AaQYgusU5beVhH6HgLfG7LVtgKROHTyuyV5sJ2E-kMF1JcoU6PmO0gP0cgylnFD4PLoHPtI7JVJpOh4H";
    private static final int REQUEST_PAYPAL_PAYMENT = 10;
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(CONFIG_CLIENT_ID);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_confirm, container, false);

        PayActivity payActivity = (PayActivity) getActivity();

        User user = payActivity.getUser();

        // Tickets and payment info are stored in the PaymentManager
        PaymentManager paymentManager = getPaymentManager();

        Intent paypalIntent = new Intent(getActivity(), PayPalService.class);
        paypalIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(paypalIntent);

        // Set the total amount of money spent so far
        final Typeface black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
        final Typeface light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        TextView cost = (TextView) root.findViewById(R.id.tv_cost);
        cost.setText("$" + paymentManager.getTotalCost());
        cost.setTypeface(light);

        TextView title = (TextView) root.findViewById(R.id.tv_title);
        title.setTypeface(black);

        Map<TicketStatus, Integer> numTickets = paymentManager.getTickets();
        LinearLayout receipt = (LinearLayout) root.findViewById(R.id.ll_tickets_bought);

        for (TicketStatus ticketStatus : numTickets.keySet()) {
            // TODO dynamically add views

            LayoutInflater factory = LayoutInflater.from(root.getContext());
            View child = factory.inflate(R.layout.payment_confirm_row, null);

            TextView tickets = (TextView) child.findViewById(R.id.tv_number_tickets);
            TextView ticketCost = (TextView) child.findViewById(R.id.tv_cost);
            TextView purchasers = (TextView) child.findViewById(R.id.tv_purchasers);

            int numTicketsPurchased = numTickets.get(ticketStatus);
            tickets.setText(numTicketsPurchased + " " + ticketStatus.getName() + " Tickets");

            int costOfTickets = numTicketsPurchased * ticketStatus.getPrice();
            ticketCost.setText(costOfTickets + "");

            purchasers.setText(numTicketsPurchased + " " +
                    getActivity().getResources().getString(R.string.owned_by) +
                    " " + user.getFirstName() + " " + user.getLastName());

            purchasers.setTypeface(light);

            receipt.addView(child);
        }

        TextView orderTotal = (TextView) root.findViewById(R.id.tv_order_cost);
        totalCost = paymentManager.getTotalCost();
        orderTotal.setText(totalCost + "");

        TextView buyerName = (TextView) root.findViewById(R.id.tv_buyer_name);
        buyerName.setText(user.getFirstName() + " " + user.getLastName());

        ImageButton next = (ImageButton) root.findViewById(R.id.ib_next_screen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PaymentMethodFragment.isPaypal) {
                    openPayPalGateway();
                } else {
                    //  TODO add payment gateways for other payment methods
                    showConfirmationDialogue();
                }
            }
        });
        next.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.confirm));

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

        Log.e("Check", "Just Checking to see if it reaches here");
    }

    @Override
    public void onPause() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onPause();
    }

    @Override
    public String getTitle() {
        return "Confirm";
    }

    public static ConfirmPurchaseFragment newInstance() {
        return new ConfirmPurchaseFragment();
    }

    private void openPayPalGateway() {
        // PayPal payment gateway
        PayPalPayment payment = new PayPalPayment(new BigDecimal(totalCost), "USD",
                "Tickets", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, REQUEST_PAYPAL_PAYMENT);
    }

    private void showConfirmationDialogue() {

        final Typeface medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_dialog_congratulations);

        TextView congrats = (TextView) dialog.findViewById(R.id.tv_congratulations);
        congrats.setTypeface(medium);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_view_tickets);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                nextCheckoutPhase();
            }
        });

        dialog.show();

    }
}
