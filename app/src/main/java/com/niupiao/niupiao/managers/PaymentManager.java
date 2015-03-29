package com.niupiao.niupiao.managers;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.TicketStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kevinchen on 3/14/15.
 */
public class PaymentManager {

    private Map<TicketStatus, Integer> tickets;

    private Context context;

    public Map<TicketStatus, Integer> getTickets() {
        return tickets;
    }

    public PaymentManager(Event event, Context context) {
        this.context = context;
        tickets = new HashMap<>(event.getNumberOfTicketStatuses());
        Collection<TicketStatus> statuses = event.getTicketStatuses();
        Iterator statusIterator = statuses.iterator();
        while (statusIterator.hasNext()) {
            tickets.put((TicketStatus) statusIterator.next(), 0);
        }
    }

    private int getNumTickets() {
        int numTickets = 0;
        if (tickets != null) {
            for (TicketStatus ticketStatus : tickets.keySet()) {
                numTickets += tickets.get(ticketStatus);
            }
        }
        return numTickets;
    }

    /**
     * Encapsulates information about how many tickets were purchased for a given Ticket status.
     */
    public static class Tickets implements Parcelable {

        private TicketStatus ticketStatus;
        private int numberTicketsPurchased;

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(ticketStatus, 0);
            dest.writeInt(numberTicketsPurchased);
        }

        Tickets(Parcel in) {
            ticketStatus = in.readParcelable(TicketStatus.class.getClassLoader());
            numberTicketsPurchased = in.readInt();
        }

        Tickets(TicketStatus ticketStatus, int numberTicketsPurchased) {
            this.ticketStatus = ticketStatus;
            this.numberTicketsPurchased = numberTicketsPurchased;
        }

        public TicketStatus getTicketStatus() {
            return ticketStatus;
        }

        public int getNumberTicketsPurchased() {
            return numberTicketsPurchased;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Tickets> CREATOR = new Creator<Tickets>() {
            @Override
            public Tickets createFromParcel(Parcel source) {
                return new Tickets(source);
            }

            @Override
            public Tickets[] newArray(int size) {
                return new Tickets[size];
            }
        };

    }

    public ArrayList<Tickets> getTicketsArrayList() {
        ArrayList<Tickets> ticketsArrayList = null;
        if (tickets != null) {
            ticketsArrayList = new ArrayList<>(tickets.size());
            for (TicketStatus ticketStatus : tickets.keySet()) {
                ticketsArrayList.add(new Tickets(ticketStatus, tickets.get(ticketStatus)));
            }
        }
        return ticketsArrayList;
    }

    public void increment(TicketStatus ticketStatus, TextView numberOfTicketsTextView, TextView checkoutCostTextView) {

        // Update the number of tickets purchased
        Integer numberOfTicketsInCart = tickets.get(ticketStatus);
        if (numberOfTicketsInCart < ticketStatus.getMaxPurchasable()) {
            tickets.put(ticketStatus, numberOfTicketsInCart + 1);
            numberOfTicketsTextView.setText("" + tickets.get(ticketStatus));

            // Update the checkout cost
            checkoutCostTextView.setText("$" + getTotalCost());
        } else {
            String message = String.format("Can buy up to %d \"%s\" tickets", ticketStatus.getMaxPurchasable(), ticketStatus.getName());
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public void decrement(TicketStatus ticketStatus, TextView numberOfTicketsTextView, TextView checkoutCostTextView) {

        // Update the number of tickets purchased
        Integer numberOfTicketsInCart = tickets.get(ticketStatus);
        if (numberOfTicketsInCart > 0) {
            tickets.put(ticketStatus, numberOfTicketsInCart - 1);
            numberOfTicketsTextView.setText("" + tickets.get(ticketStatus));

            // Update the checkout cost
            checkoutCostTextView.setText("$" + getTotalCost());
        }
    }

    public int getTotalCost() {
        int totalCost = 0;
        for (TicketStatus ticketStatus : tickets.keySet()) {
            int incrementBy = tickets.get(ticketStatus) * ticketStatus.getPrice();
            totalCost += incrementBy;
        }
        return totalCost;
    }
}
