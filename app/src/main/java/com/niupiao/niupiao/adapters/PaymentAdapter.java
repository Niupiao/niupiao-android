package com.niupiao.niupiao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.Payment;
import com.niupiao.niupiao.utils.DateUtils;
import com.niupiao.niupiao.utils.ImageLoaderHelper;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class PaymentAdapter extends ParcelableArrayAdapter<Payment> {

    public PaymentAdapter(Context context, int resource) {
        super(context, resource);
    }

    class ViewHolder {
        TextView number;
        TextView type;
        TextView name;
        TextView cell;
        CheckBox withMe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            // TODO Restructure layouts with correct ids.
            viewHolder.number = (TextView) convertView.findViewById(R.id.tv_number);
            viewHolder.type = (TextView) convertView.findViewById(R.id.tv_type);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.cell = (TextView) convertView.findViewById(R.id.tv_cell);
            viewHolder.withMe = (CheckBox) convertView.findViewById(R.id.cb_withme);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Payment payment = getItem(position);

        viewHolder.number.setText(payment.getNumber());
        viewHolder.type.setText(payment.getType());
        viewHolder.name.setText(payment.getName());
        viewHolder.cell.setText(payment.getCell());
        viewHolder.withMe.setChecked(payment.getWithMe());
        return convertView;
    }

}
