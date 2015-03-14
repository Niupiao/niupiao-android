package com.niupiao.niupiao.managers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinchen on 3/14/15.
 */
public class PaymentManager {

    private List<TicketRecipient> recipients;

    // the price of the VIP-status ticket
    private int vipTicketPrice;

    // the price of the General-status ticket
    private int generalTicketPrice;

    // the number of VIP-status tickets purchased so far
    private int numberVipTickets;

    // the number of General-status tickets purchased so far
    private int numberGeneralTickets;

    private int maxNumberOfGeneralTickets;
    private int maxNumberOfVipTickets;

    public PaymentManager(int maxNumberOfTicketsPurchaseable) {
        recipients = new ArrayList<>(maxNumberOfTicketsPurchaseable);
    }

    /**
     * Returns the amount of money spent so far
     */
    public int getTotalCostSoFar() {
        return (numberVipTickets * vipTicketPrice) + (numberGeneralTickets * generalTicketPrice);
    }

    public int getMaxNumberOfGeneralTickets() {
        return maxNumberOfGeneralTickets;
    }

    public int getMaxNumberOfVipTickets() {
        return maxNumberOfVipTickets;
    }

    public int getVipTicketPrice() {
        return vipTicketPrice;
    }

    public int getGeneralTicketPrice() {
        return generalTicketPrice;
    }

    public int getNumberVipTickets() {
        return numberVipTickets;
    }

    public int getNumberGeneralTickets() {
        return numberGeneralTickets;
    }

    public PaymentManager setVipTicketPrice(int vipTicketPrice) {
        this.vipTicketPrice = vipTicketPrice;
        return this;
    }

    public PaymentManager setGeneralTicketPrice(int generalTicketPrice) {
        this.generalTicketPrice = generalTicketPrice;
        return this;
    }

    public PaymentManager setNumberVipTickets(int numberVipTickets) {
        this.numberVipTickets = numberVipTickets;
        return this;
    }

    public PaymentManager setNumberGeneralTickets(int numberGeneralTickets) {
        this.numberGeneralTickets = numberGeneralTickets;
        return this;
    }

    public PaymentManager setMaxNumberOfGeneralTickets(int maxNumberOfGeneralTickets) {
        this.maxNumberOfGeneralTickets = maxNumberOfGeneralTickets;
        return this;
    }

    public PaymentManager setMaxNumberOfVipTickets(int maxNumberOfVipTickets) {
        this.maxNumberOfVipTickets = maxNumberOfVipTickets;
        return this;
    }


    /**
     * Used for storing recipients of tickets.
     */
    public static class TicketRecipient {

        private String name;
        private String cell;
        private boolean me;

        public TicketRecipient(String name, String cell, boolean me) {
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
}
