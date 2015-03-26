package com.niupiao.niupiao.adapters;

import android.content.Context;
import android.graphics.Typeface;
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

import org.w3c.dom.Text;

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

            Typeface black = Typeface.createFromAsset(getMainActivity().getAssets(), "fonts/Roboto-Black.ttf");
            Typeface medium = Typeface.createFromAsset(getMainActivity().getAssets(), "fonts/Roboto-Medium.ttf");
            Typeface regular = Typeface.createFromAsset(getMainActivity().getAssets(), "fonts/Roboto-Regular.ttf");

            TextView day = (TextView) convertView.findViewById(R.id.tv_day);
            TextView month = (TextView) convertView.findViewById(R.id.tv_month);
            TextView name = (TextView) convertView.findViewById(R.id.tv_event_name);
            TextView time = (TextView) convertView.findViewById(R.id.tv_event_time);
            TextView location = (TextView) convertView.findViewById(R.id.tv_event_location);

            day.setTypeface(black);
            month.setTypeface(medium);
            name.setTypeface(medium);
            time.setTypeface(medium);
            location.setTypeface(regular);

            viewHolder = new ViewHolder();
            viewHolder.image = (NetworkImageView) convertView.findViewById(R.id.image);
            viewHolder.day = day;
            viewHolder.month = month;
            viewHolder.name = name;
            viewHolder.time = time;
            viewHolder.location = location;
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
