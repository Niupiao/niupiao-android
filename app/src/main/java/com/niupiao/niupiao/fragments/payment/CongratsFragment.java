package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.activities.PayActivity;

/**
 * Created by kevinchen on 2/25/15.
 */
public class CongratsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_congrats, container, false);
        ImageButton myTicketsButton = (ImageButton) root.findViewById(R.id.ib_viewtickets);

        String[] names = TempPayInformation.PayInfo.getNames();
        String[] cells = TempPayInformation.PayInfo.getCells();
        Boolean[] mes = TempPayInformation.PayInfo.getMe();
        int price = TempPayInformation.PayInfo.getPrice();
        int tickets = TempPayInformation.PayInfo.getTotalticket();

        EditText etn1 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_one);
        EditText etn2 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_two);
        EditText etn3 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_three);
        EditText etn4 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_four);
        EditText etn5 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_five);

        etn1.setText(names[0]);
        etn2.setText(names[1]);
        etn3.setText(names[2]);
        etn4.setText(names[3]);
        etn5.setText(names[4]);

        EditText etc1 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_one);
        EditText etc2 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_two);
        EditText etc3 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_three);
        EditText etc4 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_four);
        EditText etc5 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_five);

        etc1.setText(cells[0]);
        etc2.setText(cells[1]);
        etc3.setText(cells[2]);
        etc4.setText(cells[3]);
        etc5.setText(cells[4]);

        CheckBox cb1 = (CheckBox) root.findViewById(R.id.cb_congrats_me_one);
        CheckBox cb2 = (CheckBox) root.findViewById(R.id.cb_congrats_me_two);
        CheckBox cb3 = (CheckBox) root.findViewById(R.id.cb_congrats_me_three);
        CheckBox cb4 = (CheckBox) root.findViewById(R.id.cb_congrats_me_four);
        CheckBox cb5 = (CheckBox) root.findViewById(R.id.cb_congrats_me_five);

        cb1.setChecked(mes[0]);
        cb2.setChecked(mes[1]);
        cb3.setChecked(mes[2]);
        cb4.setChecked(mes[3]);
        cb5.setChecked(mes[4]);

        TextView price_declaration = (TextView) root.findViewById(R.id.tv_congrats_price);
        String price_text = price_declaration.getText().toString().split("\\$")[0];
        String[] ticket_text = price_text.split("0");
        price_declaration.setText(ticket_text[0] + tickets + ticket_text[1] + "$" + price);

        myTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayActivity activity = (PayActivity) getActivity();
                activity.nextPaymentPhase();
            }
        });
        return root;
    }

    public static CongratsFragment newInstance() {
        return new CongratsFragment();
    }
}
