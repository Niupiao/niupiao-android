package com.niupiao.niupiao.fragments.account;

import android.graphics.Typeface;
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

/**
 * Created by Inanity on 3/31/2015.
 */
public class ManageAccountFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_manage_account, container, false);

        Typeface black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");

        TextView title = (TextView) root.findViewById(R.id.tv_title);
        title.setTypeface(black);

        Button save = (Button) root.findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Set up logic (if desirable) to change user information. Unsure yet if this is desirable - Ryan
                Toast.makeText(getActivity().getApplicationContext(), "Save Clicked", Toast.LENGTH_SHORT).show();
                ((AccountNavFragment) getParentFragment()).changeScreen(AccountNavFragment.AccountScreen.ACCOUNT_SCREEN);
            }
        });

        return root;
    }
}
