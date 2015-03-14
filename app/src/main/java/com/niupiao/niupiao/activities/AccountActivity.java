package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.account.AccountSettingsFragment;
import com.niupiao.niupiao.fragments.account.MyAccountFragment;
import com.niupiao.niupiao.fragments.account.PaymentSettingsFragment;

/**
 * Created by Ryan on 2/27/15.
 */
public class AccountActivity extends MainActivity {

    public enum AccountScreen {
        ACCOUNT_SCREEN(MyAccountFragment.class.getName()),
        ACCOUNT_SETTINGS(AccountSettingsFragment.class.getName()),
        PAYMENT_SETTINGS(PaymentSettingsFragment.class.getName());

        private String fragmentClassName;

        private AccountScreen(String fragmentClassName) {
            this.fragmentClassName = fragmentClassName;
        }

        public String getFragmentClassName() {
            return fragmentClassName;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        changeScreen(AccountScreen.ACCOUNT_SCREEN);
    }

    public void changeScreen(AccountScreen accountScreen) {
        Fragment fragment = Fragment.instantiate(this, accountScreen.getFragmentClassName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
