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
import com.niupiao.niupiao.activities.AccountActivity;

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
                AccountActivity activity = (AccountActivity) getActivity();
                Toast.makeText(getActivity().getApplicationContext(), "Payment settings clicked", Toast.LENGTH_SHORT).show();
                activity.changeScreen(AccountActivity.AccountScreen.ACCOUNT_SCREEN);
            }
        });

        return root;
    }

    public static AccountSettingsFragment newInstance() {
        return new AccountSettingsFragment();
    }
}
