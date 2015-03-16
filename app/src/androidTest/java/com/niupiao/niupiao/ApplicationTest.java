package com.niupiao.niupiao;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.niupiao.niupiao.deserializers.TicketsDeserializer;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.Ticket;
import com.niupiao.niupiao.models.TicketStatus;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collection;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public static final String MY_TICKETS_JSON = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"TestEvent\",\n" +
            "    \"organizer\": \"TestOrganizer\",\n" +
            "    \"date\": null,\n" +
            "    \"location\": \"Williamstown, MA\",\n" +
            "    \"description\": \"TestDescription\",\n" +
            "    \"image_path\": \"TestImagePath\",\n" +
            "    \"link\": \"TestLink\",\n" +
            "    \"total_tickets\": 0,\n" +
            "    \"tickets_sold\": 0,\n" +
            "    \"created_at\": \"2015-03-16T16:31:33.037Z\",\n" +
            "    \"updated_at\": \"2015-03-16T16:31:33.037Z\",\n" +
            "    \"ticket_statuses\": [\n" +
            "      {\n" +
            "        \"event_id\": 1,\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"General\",\n" +
            "        \"max_purchasable\": 3,\n" +
            "        \"price\": 50,\n" +
            "        \"created_at\": \"2015-03-16T16:31:33.055Z\",\n" +
            "        \"updated_at\": \"2015-03-16T16:31:33.076Z\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"event_id\": 1,\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"VIP\",\n" +
            "        \"max_purchasable\": 2,\n" +
            "        \"price\": 150,\n" +
            "        \"created_at\": \"2015-03-16T16:31:33.059Z\",\n" +
            "        \"updated_at\": \"2015-03-16T16:31:33.082Z\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"number_of_ticket_statuses\": 2,\n" +
            "    \"tickets\": [\n" +
            "      {\n" +
            "        \"event_id\": 1,\n" +
            "        \"id\": 1,\n" +
            "        \"user_id\": 1,\n" +
            "        \"status\": \"General\",\n" +
            "        \"created_at\": \"2015-03-16T16:31:33.273Z\",\n" +
            "        \"updated_at\": \"2015-03-16T16:31:33.273Z\",\n" +
            "        \"ticket_status_id\": 1,\n" +
            "        \"ticket_status\": {\n" +
            "          \"id\": 1,\n" +
            "          \"name\": \"General\",\n" +
            "          \"max_purchasable\": 3,\n" +
            "          \"price\": 50,\n" +
            "          \"event_id\": 1,\n" +
            "          \"created_at\": \"2015-03-16T16:31:33.055Z\",\n" +
            "          \"updated_at\": \"2015-03-16T16:31:33.076Z\"\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "]\n";

    public ApplicationTest() {
        super(Application.class);

        try {
            JSONArray jsonArray = new JSONArray(MY_TICKETS_JSON);
            List<Event> events = TicketsDeserializer.fromJsonArray(jsonArray);
            Assert.assertFalse(events.isEmpty());
            Event first = events.get(0);
            Assert.assertEquals(first.getId(), 1);

            Collection<Ticket> tickets = first.getTickets();
            Assert.assertFalse(tickets.isEmpty());
            Ticket ticket = tickets.iterator().next();
            Assert.assertNotNull(ticket);
            TicketStatus ticketStatus = ticket.getTicketStatus();
            Assert.assertNotNull(ticketStatus);
            Assert.assertEquals(ticketStatus.getPrice(), 50);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}