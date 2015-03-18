package com.niupiao.niupiao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.Ticket;
import com.niupiao.niupiao.models.TicketStatus;
import com.niupiao.niupiao.models.User;
import com.niupiao.niupiao.utils.DateUtils;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class MyTicketsAdapter extends ParcelableArrayAdapter<Ticket> {

    public MyTicketsAdapter(Context context, int resource) {
        super(context, resource);
    }

    class ViewHolder {
        TextView price;
        TextView status;
        TextView eventName;
        TextView buyerName;
        TextView date;
        TextView time;

        TextView location;
        TextView street;
        TextView quantity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_ticket_price);
            viewHolder.status = (TextView) convertView.findViewById(R.id.tv_ticket_admit_status);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.tv_event_title);
            viewHolder.buyerName = (TextView) convertView.findViewById(R.id.tv_buyer_name);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tv_event_date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_event_time);
            viewHolder.location = (TextView) convertView.findViewById(R.id.tv_event_location);
            viewHolder.street = (TextView) convertView.findViewById(R.id.tv_event_street);
            viewHolder.quantity = (TextView) convertView.findViewById(R.id.tv_ticket_quantity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Ticket ticket = getItem(position);
        TicketStatus ticketStatus = ticket.getTicketStatus();
        Event event = ticket.getEvent();
        if (event != null) {
            viewHolder.eventName.setText(event.getName());
            viewHolder.location.setText(event.getLocation());

            // TODO viewHolder.street.setText(event.getStreet());

            // TODO change later to actual num purchased
            viewHolder.quantity.setText("" + ticketStatus.getMaxPurchasable());

            String dateTime = event.getDate();
            String date = DateUtils.format(dateTime, DateUtils.FORMAT_DATE);
            String time = DateUtils.format(dateTime, DateUtils.FORMAT_TIME);

            viewHolder.date.setText(date);
            viewHolder.time.setText(time);
        }

        viewHolder.price.setText("$" + ticketStatus.getPrice());
        viewHolder.status.setText(ticket.getStatus());
        User user = getMainActivity().getUser();
        viewHolder.buyerName.setText(user.getName());
        return convertView;
    }
}
