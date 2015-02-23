package com.niupiao.niupiao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.Ticket;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class MyTicketsAdapter extends ParcelableArrayAdapter<Ticket> {

    public MyTicketsAdapter(Context context, int resource) {
        super(context, resource);
    }

    class ViewHolder {
        // TODO sync with list_item_view_my_ticket
        TextView price;
        TextView status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Ticket ticket = getItem(position);
        viewHolder.price.setText("price: " + ticket.getPrice());
        viewHolder.status.setText("status: " + ticket.getStatus());
        return convertView;
    }
}
