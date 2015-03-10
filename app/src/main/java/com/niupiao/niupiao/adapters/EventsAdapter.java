package com.niupiao.niupiao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.utils.DateUtils;
import com.niupiao.niupiao.utils.ImageLoaderHelper;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class EventsAdapter extends ParcelableArrayAdapter<Event> {

    public EventsAdapter(Context context, int resource) {
        super(context, resource);
    }

    class ViewHolder {
        NetworkImageView image;
        TextView day;
        TextView month;

        TextView name;
        TextView time;
        TextView location;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (NetworkImageView) convertView.findViewById(R.id.image);
            viewHolder.day = (TextView) convertView.findViewById(R.id.tv_day);
            viewHolder.month = (TextView) convertView.findViewById(R.id.tv_month);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_event_name);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_event_time);
            viewHolder.location = (TextView) convertView.findViewById(R.id.tv_event_location);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = getItem(position);
        String dateTime = event.getDate();

        NetworkImageView image = viewHolder.image;
        image.setImageUrl(Constants.JsonApi.EndPoints.fullUrl(event.getImagePath()), ImageLoaderHelper.getInstance().getImageLoader());

        viewHolder.month.setText(DateUtils.format(dateTime, DateUtils.FORMAT_MONTH).toUpperCase());
        viewHolder.day.setText(DateUtils.format(dateTime, DateUtils.FORMAT_DAY_OF_MONTH));
        viewHolder.name.setText(event.getName());
        viewHolder.time.setText(DateUtils.format(dateTime, DateUtils.FORMAT_TIME));
        viewHolder.location.setText(event.getLocation());
        return convertView;
    }

}
