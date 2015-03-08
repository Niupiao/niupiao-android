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
        NetworkImageView image;
        TextView day;
        TextView month;

        TextView name;
        TextView time;
        TextView location;
    }

}
