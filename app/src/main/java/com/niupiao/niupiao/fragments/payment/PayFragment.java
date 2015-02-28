package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;

/**
 * Created by kevinchen on 2/25/15.
 */
public class PayFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_transfer, container, false);
        int price = TempPayInformation.PayInfo.getPrice();

        TextView price_declaration = (TextView) root.findViewById(R.id.tv_cost_of_tickets);
        String price_text = price_declaration.getText().toString().split("\\$")[0];
        price_declaration.setText(price_text + "$" + price);

        ImageButton paynowButton = (ImageButton) root.findViewById(R.id.ib_pay_now);

        paynowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etn1 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_name_one);
                EditText etn2 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_name_two);
                EditText etn3 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_name_three);
                EditText etn4 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_name_four);
                EditText etn5 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_name_five);

                String n1 = etn1.getText().toString();
                String n2 = etn2.getText().toString();
                String n3 = etn3.getText().toString();
                String n4 = etn4.getText().toString();
                String n5 = etn5.getText().toString();

                EditText etc1 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_cell_one);
                EditText etc2 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_cell_two);
                EditText etc3 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_cell_three);
                EditText etc4 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_cell_four);
                EditText etc5 = (EditText) getActivity().findViewById(R.id.et_transfer_recipient_cell_five);

                String c1 = etc1.getText().toString();
                String c2 = etc2.getText().toString();
                String c3 = etc3.getText().toString();
                String c4 = etc4.getText().toString();
                String c5 = etc5.getText().toString();

                CheckBox cb1 = (CheckBox) getActivity().findViewById(R.id.cb_transfer_me_one);
                CheckBox cb2 = (CheckBox) getActivity().findViewById(R.id.cb_transfer_me_two);
                CheckBox cb3 = (CheckBox) getActivity().findViewById(R.id.cb_transfer_me_three);
                CheckBox cb4 = (CheckBox) getActivity().findViewById(R.id.cb_transfer_me_four);
                CheckBox cb5 = (CheckBox) getActivity().findViewById(R.id.cb_transfer_me_five);

                Boolean me1 = cb1.isChecked();
                Boolean me2 = cb2.isChecked();
                Boolean me3 = cb3.isChecked();
                Boolean me4 = cb4.isChecked();
                Boolean me5 = cb5.isChecked();

                TempPayInformation.PayInfo.setNames(n1, n2, n3, n4, n5);
                TempPayInformation.PayInfo.setCells(c1, c2, c3, c4, c5);
                TempPayInformation.PayInfo.setMe(me1, me2, me3, me4, me5);

                PayActivity activity = (PayActivity) getActivity();
                activity.nextPaymentPhase();
            }
        });
        return root;
    }

    public static PayFragment newInstance() {
        return new PayFragment();
    }

}
