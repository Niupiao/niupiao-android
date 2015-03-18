package com.niupiao.niupiao.managers;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.TicketStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kevinchen on 3/14/15.
 */
public class PaymentManager {

    private Map<TicketStatus, Integer> numTickets;

    private Context context;

    public Map<TicketStatus, Integer> getNumTickets() {
        return numTickets;
    }

    public PaymentManager(Event event, Context context) {
        this.context = context;
        numTickets = new HashMap<>(event.getNumberOfTicketStatuses());
        Collection<TicketStatus> statuses = event.getTicketStatuses();
        Iterator statusIterator = statuses.iterator();
        while (statusIterator.hasNext()) {
            numTickets.put((TicketStatus) statusIterator.next(), 0);
        }
    }

    public void increment(TicketStatus ticketStatus, TextView numberOfTicketsTextView, TextView checkoutCostTextView) {

        // Update the number of tickets purchased
        Integer numberOfTicketsInCart = numTickets.get(ticketStatus);
        if (numberOfTicketsInCart < ticketStatus.getMaxPurchasable()) {
            numTickets.put(ticketStatus, numberOfTicketsInCart + 1);
            numberOfTicketsTextView.setText("" + numTickets.get(ticketStatus));

            // Update the checkout cost
            checkoutCostTextView.setText("$" + getTotalCost());
        } else {
            String message = String.format("Can buy up to %d \"%s\" tickets", ticketStatus.getMaxPurchasable(), ticketStatus.getName());
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public void decrement(TicketStatus ticketStatus, TextView numberOfTicketsTextView, TextView checkoutCostTextView) {

        // Update the number of tickets purchased
        Integer numberOfTicketsInCart = numTickets.get(ticketStatus);
        if (numberOfTicketsInCart > 0) {
            numTickets.put(ticketStatus, numberOfTicketsInCart - 1);
            numberOfTicketsTextView.setText("" + numTickets.get(ticketStatus));

            // Update the checkout cost
            checkoutCostTextView.setText("$" + getTotalCost());
        }
    }

    public int getTotalCost() {
        int totalCost = 0;
        for (TicketStatus ticketStatus : numTickets.keySet()) {
            int incrementBy = numTickets.get(ticketStatus) * ticketStatus.getPrice();
            totalCost += incrementBy;
        }
        return totalCost;
    }
}
