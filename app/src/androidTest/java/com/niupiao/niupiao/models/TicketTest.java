package com.niupiao.niupiao.models;


import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * Created by kmchen1 on 3/21/15.
 */
public class TicketTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
        Ticket ticket = new Ticket();
        ticket.setEventId(1)
                .setStatus("General")
                .setUserId(1);

        Assert.assertEquals(1, ticket.getUserId());
        Assert.assertEquals(1, ticket.getEventId());
        Assert.assertEquals("General", ticket.getStatus());
    }
}
