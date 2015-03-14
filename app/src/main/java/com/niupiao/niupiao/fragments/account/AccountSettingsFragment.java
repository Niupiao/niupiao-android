package com.niupiao.niupiao.fragments.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.niupiao.niupiao.R;

/**
 * Created by kevinchen on 2/18/15.
 */
public class AccountSettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_account_settings, container, false);

        Button returnButton = (Button) root.findViewById(R.id.btn_return);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Payment settings clicked", Toast.LENGTH_SHORT).show();
                ((AccountNavFragment) getParentFragment()).changeScreen(AccountNavFragment.AccountScreen.ACCOUNT_SCREEN);
            }
        });

        return root;
    }

}
