package com.niupiao.niupiao.fragments.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.models.User;

/**
 * Created by kevinchen on 3/14/15.
 */
public class AccountScreenFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_myaccount, container, false);

        User user = ((MainActivity) getActivity()).getUser();
        TextView name = (TextView) root.findViewById(R.id.tv_name);
        name.setText(user.getFirstName() + " " + user.getLastName());

        Button accountSettings = (Button) root.findViewById(R.id.btn_account_settings);
        accountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Account settings clicked", Toast.LENGTH_SHORT).show();
                ((AccountNavFragment) getParentFragment()).changeScreen(AccountNavFragment.AccountScreen.ACCOUNT_SETTINGS);
            }
        });

        Button paymentSettings = (Button) root.findViewById(R.id.btn_payment_settings);
        paymentSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Payment settings clicked", Toast.LENGTH_SHORT).show();
                ((AccountNavFragment) getParentFragment()).changeScreen(AccountNavFragment.AccountScreen.PAYMENT_SETTINGS);
            }
        });

        return root;
    }

}