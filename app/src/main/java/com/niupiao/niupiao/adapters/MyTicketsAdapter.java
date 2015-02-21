package com.niupiao.niupiao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupiao.niupiao.models.Event;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class MyTicketsAdapter extends EventsAdapter {

    public MyTicketsAdapter(Context context, int resource) {
        super(context, resource);
    }


    class ViewHolder {
        // TODO sync with list_item_view_my_ticket
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
//            viewHolder.image = (NetworkImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = getItem(position);
        return convertView;
    }
}
