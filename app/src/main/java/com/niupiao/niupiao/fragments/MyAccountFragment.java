package com.niupiao.niupiao.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by kevinchen on 2/18/15.
 */
public class MyAccountFragment extends NiuNavigationDrawerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_myaccount, container, false);

        TextView name = (TextView) root.findViewById(R.id.tv_name);
        String full_name = ((MainActivity)getActivity()).getUser().getFirstName() + " " +
                ((MainActivity)getActivity()).getUser().getLastName();

        name.setText(full_name);

        Button acnt_settings = (Button) root.findViewById(R.id.btn_account_settings);
        acnt_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "Account settings clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Button pymt_settings = (Button) root.findViewById(R.id.btn_payment_settings);
        pymt_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "Payment settings clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return root;
    }
}
