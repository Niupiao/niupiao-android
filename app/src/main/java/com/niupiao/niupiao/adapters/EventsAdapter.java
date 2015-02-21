package com.niupiao.niupiao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.events.ComingSoonFragment;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.utils.ImageLoaderHelper;

/**
* Created by kmchen1 on 2/21/15.
*/
public class EventsAdapter extends ParcelableArrayAdapter<Event> {

    public EventsAdapter(Context context) {
        super(context, R.layout.list_view_item_concert);
    }

    class ViewHolder {
        NetworkImageView image;
        TextView date;
        TextView name;
        TextView ticketInfo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_concert, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (NetworkImageView) convertView.findViewById(R.id.image);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.ticketInfo = (TextView) convertView.findViewById(R.id.ticket_info);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = getItem(position);

        NetworkImageView image = viewHolder.image;
        image.setImageUrl(Constants.Url.fullUrl(event.getImagePath()), ImageLoaderHelper.getInstance().getImageLoader());

        viewHolder.date.setText(event.getDate());
        viewHolder.name.setText(event.getName());
        viewHolder.ticketInfo.setText(String.format("%d out of %d tickets sold", event.getTicketsSold(), event.getTotalTickets()));
        return convertView;
    }
}
