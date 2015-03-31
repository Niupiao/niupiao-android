package com.niupiao.niupiao.fragments.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.NiuNavigationDrawerFragment;

/**
 * Created by kevinchen on 2/18/15.
 */
public class AccountNavFragment extends NiuNavigationDrawerFragment {

    public enum AccountScreen {
        ACCOUNT_SCREEN(AccountScreenFragment.class.getName()),
        MANAGE_ACCOUNT(ManageAccountFragment.class
        .getName()),
        UPDATE_PROFILE(AccountSettingsFragment.class.getName()),
        MANAGE_ALERTS(AccountSettingsFragment.class.getName()),
        ORDER_HISTORY(AccountSettingsFragment.class.getName());

        private String fragmentClassName;

        private AccountScreen(String fragmentClassName) {
            this.fragmentClassName = fragmentClassName;
        }

        public String getFragmentClassName() {
            return fragmentClassName;
        }
    }

    public void changeScreen(AccountScreen accountScreen) {
        Fragment fragment = Fragment.instantiate(getActivity(), accountScreen.getFragmentClassName());
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_account_container, container, false);
        changeScreen(AccountScreen.ACCOUNT_SCREEN);
        return root;
    }

    public static AccountNavFragment newInstance() {
        return new AccountNavFragment();
    }
}
