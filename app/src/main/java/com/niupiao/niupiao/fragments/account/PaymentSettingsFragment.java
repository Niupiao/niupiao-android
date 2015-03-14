package com.niupiao.niupiao.fragments.account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupiao.niupiao.R;

/**
 * Created by kevinchen on 2/18/15.
 */
public class PaymentSettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_settings, container, false);

        return root;
    }
}
