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
import com.niupiao.niupiao.activities.PayActivity;

import java.util.List;

/**
 * Created by kevinchen on 2/25/15.
 */
public class CongratsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_congrats, container, false);
        ImageButton myTicketsButton = (ImageButton) root.findViewById(R.id.ib_viewtickets);

        PayActivity activity = (PayActivity) getActivity();
        List<PayActivity.Person> recipients = activity.getRecipients();


        EditText etn1 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_one);
        EditText etn2 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_two);
        EditText etn3 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_three);
        EditText etn4 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_four);
        EditText etn5 = (EditText) root.findViewById(R.id.et_congrats_recipient_name_five);

        EditText etc1 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_one);
        EditText etc2 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_two);
        EditText etc3 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_three);
        EditText etc4 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_four);
        EditText etc5 = (EditText) root.findViewById(R.id.et_congrats_recipient_cell_five);

        CheckBox cb1 = (CheckBox) root.findViewById(R.id.cb_congrats_me_one);
        CheckBox cb2 = (CheckBox) root.findViewById(R.id.cb_congrats_me_two);
        CheckBox cb3 = (CheckBox) root.findViewById(R.id.cb_congrats_me_three);
        CheckBox cb4 = (CheckBox) root.findViewById(R.id.cb_congrats_me_four);
        CheckBox cb5 = (CheckBox) root.findViewById(R.id.cb_congrats_me_five);

        etn1.setText(recipients.get(0).getName());
        etn2.setText(recipients.get(1).getName());
        etn3.setText(recipients.get(2).getName());
        etn4.setText(recipients.get(3).getName());
        etn5.setText(recipients.get(4).getName());

        etc1.setText(recipients.get(0).getCell());
        etc2.setText(recipients.get(1).getCell());
        etc3.setText(recipients.get(2).getCell());
        etc4.setText(recipients.get(3).getCell());
        etc5.setText(recipients.get(4).getCell());

        cb1.setChecked(recipients.get(0).isMe());
        cb2.setChecked(recipients.get(1).isMe());
        cb3.setChecked(recipients.get(2).isMe());
        cb4.setChecked(recipients.get(3).isMe());
        cb5.setChecked(recipients.get(4).isMe());

        TextView priceDeclaration = (TextView) root.findViewById(R.id.tv_congrats_price);
        String priceText = priceDeclaration.getText().toString().split("\\$")[0];
        String[] ticketText = priceText.split("0");
        priceDeclaration.setText(ticketText[0] + activity.getTotalTicketCount() + ticketText[1] + "$" + activity.getTotalPrice());

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
