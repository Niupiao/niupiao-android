package com.niupiao.niupiao.managers;

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


    // maps status names to tickets
    private Map<TicketStatus, Collection<Ticket>> tickets;

    public Map<TicketStatus, Collection<Ticket>> getTickets() {
        return tickets;
    }

    public int getNumberOfTicketsForStatus(TicketStatus ticketStatus) {
        return tickets.get(ticketStatus).size();
    }


    public PaymentManager(Event event) {
        Collection<TicketStatus> ticketStatuses = event.getTicketStatuses();
        tickets = new HashMap<>(event.getNumberOfTicketStatuses());
        if (ticketStatuses != null) {
            for (TicketStatus ticketStatus : ticketStatuses) {
                tickets.put(ticketStatus, new ArrayList<Ticket>(ticketStatus.getMaxPurchasable()));
            }
        }
    }

    /**
     * Returns the amount of money spent so far
     */
    public int getTotalCost() {
        int totalCost = 0;
        for (TicketStatus ticketStatus : tickets.keySet()) {
            Collection<Ticket> ticketsBought = tickets.get(ticketStatus);
            if (ticketsBought != null) {
                for (Ticket ticket : ticketsBought) {
                    totalCost += ticket.getPrice();
                }
            }
        }
        return totalCost;
    }

}
