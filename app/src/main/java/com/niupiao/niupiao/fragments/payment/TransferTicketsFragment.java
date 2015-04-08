package com.niupiao.niupiao.fragments.payment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.TicketStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kevinchen on 3/9/15.
 */
public class TransferTicketsFragment extends CheckoutViewPagerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_transfer, container, false);

        // Tickets and payment info are stored in the PaymentManager
        PaymentManager paymentManager = getPaymentManager();

        // Set the total amount of money spent so far
        Typeface light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        TextView cost = (TextView) root.findViewById(R.id.tv_cost);
        cost.setText("$" + paymentManager.getTotalCost());
        cost.setTypeface(light);

        // Maps ticketStatus to a collection of tickets the capacity of ticketStatus.getMaxPurchasable()
        Map<TicketStatus, Integer> numTickets = paymentManager.getTickets();

        // We will be adding stuff to the sole child of a ScrollView. (ScrollView can only have one child).
        LinearLayout insideScrollView = (LinearLayout) root.findViewById(R.id.sv_child);

        // The recipient number starts at 1
        int recipientNumber = 1;

        Set<TicketStatus> ticketStatusesSet = numTickets.keySet();
        List<TicketStatus> ticketStatuses = new ArrayList<>(ticketStatusesSet.size());
        ticketStatuses.addAll(ticketStatusesSet);

        // We will be adding a group of rows for each ticket status
        for (int i = 0; i < ticketStatuses.size(); i++) {

            final TicketStatus ticketStatus = ticketStatuses.get(i);

            // The max number of tickets you can buy for any given status is the number of rows we show
            int numRowsToAdd = numTickets.get(ticketStatus);

            // The layout inflater will be dynamically inflating views,
            LayoutInflater factory = LayoutInflater.from(root.getContext());

            int color = getResources().getColor(i % 2 == 0 ? R.color.niupiao_blue : R.color.niupiao_orange);

            // Add a row for each ticket the user can potentially buy
            for (int r = 0; r < numRowsToAdd; r++) {

                // Inflate the row that shows the recipient number, the ticket status, and the choose recipient button
                // See payment_transfer_recipient_row.xml
                View child = factory.inflate(R.layout.payment_transfer_recipient_row, null);

                // Show the recipient number
                TextView recipientNumberTextView = (TextView) child.findViewById(R.id.tv_recipient_number);
                recipientNumberTextView.setText("" + recipientNumber);
                recipientNumberTextView.setTextColor(color);
                recipientNumber++;

                // Show the ticket status
                TextView statusTextView = (TextView) child.findViewById(R.id.tv_status);
                statusTextView.setText(ticketStatus.getName());
                statusTextView.setTextColor(color);
                insideScrollView.addView(child);
                //insideScrollView.addView(child, recipientNumber);

                // Set the click listener for the Recipient Chooser button
                ImageButton chooseContactImageButton = (ImageButton) child.findViewById(R.id.ib_choose_contact);
                chooseContactImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO show Contact Chooser dialog/fragment
                        Toast.makeText(getActivity(), "TODO - show contact chooser", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        //Formatting the Note:
        TextView note = (TextView) root.findViewById(R.id.tv_note);
        String noteTextString = root.getResources().getString(R.string.transfer_note);
        Spannable noteText = new SpannableString(noteTextString);
        noteText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.niupiao_blue)),
                noteTextString.indexOf("NOT"), noteTextString.indexOf(" "),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        noteText.setSpan(new StyleSpan(Typeface.BOLD), noteTextString.indexOf("Tickets may not be"),
                noteTextString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        note.setText(noteText);

        // Show the button that will lead to the next screen
        ImageButton next = (ImageButton) root.findViewById(R.id.ib_next_screen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCheckoutPhase();
            }
        });
        next.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.paymentmethod));

        return root;
    }

    @Override
    public String getTitle() {
        return "Transfer";
    }

    public static TransferTicketsFragment newInstance() {
        return new TransferTicketsFragment();
    }
}
