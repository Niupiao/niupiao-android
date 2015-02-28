package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.PayActivity;

/**
 * Created by kevinchen on 2/25/15.
 */
public class ConfirmFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_confirm, container, false);
        ImageButton confirmButton = (ImageButton) root.findViewById(R.id.ib_confirm);

        String[] names = TempPayInformation.PayInfo.getNames();
        String[] cells = TempPayInformation.PayInfo.getCells();

        EditText etn1 = (EditText) root.findViewById(R.id.et_confirm_recipient_name_one);
        EditText etn2 = (EditText) root.findViewById(R.id.et_confirm_recipient_name_two);
        EditText etn3 = (EditText) root.findViewById(R.id.et_confirm_recipient_name_three);
        EditText etn4 = (EditText) root.findViewById(R.id.et_confirm_recipient_name_four);
        EditText etn5 = (EditText) root.findViewById(R.id.et_confirm_recipient_name_five);

        etn1.setText(names[0]);
        etn2.setText(names[1]);
        etn3.setText(names[2]);
        etn4.setText(names[3]);
        etn5.setText(names[4]);

        EditText etc1 = (EditText) root.findViewById(R.id.et_confirm_recipient_cell_one);
        EditText etc2 = (EditText) root.findViewById(R.id.et_confirm_recipient_cell_two);
        EditText etc3 = (EditText) root.findViewById(R.id.et_confirm_recipient_cell_three);
        EditText etc4 = (EditText) root.findViewById(R.id.et_confirm_recipient_cell_four);
        EditText etc5 = (EditText) root.findViewById(R.id.et_confirm_recipient_cell_five);

        etc1.setText(cells[0]);
        etc2.setText(cells[1]);
        etc3.setText(cells[2]);
        etc4.setText(cells[3]);
        etc5.setText(cells[4]);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayActivity activity = (PayActivity) getActivity();
                System.out.println("Confirm");
                activity.nextPaymentPhase();
            }
        });
        return root;
    }

    public static ConfirmFragment newInstance() {
        return new ConfirmFragment();
    }

}
