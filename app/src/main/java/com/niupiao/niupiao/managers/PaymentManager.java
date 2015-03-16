package com.niupiao.niupiao.managers;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.Ticket;
import com.niupiao.niupiao.models.TicketStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevinchen on 3/14/15.
 */
public class PaymentManager {

    // used if PRICE is PER-TicketStatus
    private Map<TicketStatus, Integer> numTickets;

    // used if PRICE is PER-Ticket
    private Map<TicketStatus, Collection<Ticket>> tickets;

    public Map<TicketStatus, Collection<Ticket>> getTickets() {
        return tickets;
    }

    private Context context;

    public PaymentManager(Event event, Context context) {
        this.context = context;
        Collection<TicketStatus> ticketStatuses = event.getTicketStatuses();
        numTickets = new HashMap<>(event.getNumberOfTicketStatuses());
        tickets = new HashMap<>(event.getNumberOfTicketStatuses());
        if (ticketStatuses != null) {
            for (TicketStatus ticketStatus : ticketStatuses) {
                tickets.put(ticketStatus, new ArrayList<Ticket>(ticketStatus.getMaxPurchasable()));
            }
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

    // Per Ticket-Status method
    public int getTotalCost() {
        int totalCost = 0;
        for (TicketStatus ticketStatus : numTickets.keySet()) {
            int incrementBy = numTickets.get(ticketStatus) * ticketStatus.getPrice();
            totalCost += incrementBy;
        }
        return totalCost;
    }
}
