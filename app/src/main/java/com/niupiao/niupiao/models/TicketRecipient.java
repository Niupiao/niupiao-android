package com.niupiao.niupiao.models;

/**
 * Used for storing recipients of tickets.
 */
public class TicketRecipient {

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
