package com.niupiao.niupiao.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.account.AccountSettingsFragment;
import com.niupiao.niupiao.fragments.account.MyAccountFragment;
import com.niupiao.niupiao.fragments.account.PaymentSettingsFragment;

/**
 * Created by Ryan on 2/27/15.
 */
public class AccountActivity extends ActionBarActivity {

    private AccountScreen currentscreen = AccountScreen.ACCOUNT_SCREEN;

    public enum AccountScreen {
        ACCOUNT_SCREEN,
        ACCOUNT_SETTINGS,
        PAYMENT_SETTINGS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        show(AccountScreen.ACCOUNT_SCREEN);
    }

    private void show(AccountScreen Screen) {
        this.currentscreen = Screen;
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (Screen) {
            case ACCOUNT_SCREEN:
                fragment = MyAccountFragment.newInstance();
                break;
            case ACCOUNT_SETTINGS:
                fragment = AccountSettingsFragment.newInstance();
                break;
            case PAYMENT_SETTINGS:
                fragment = PaymentSettingsFragment.newInstance();
                break;
            default:
                throw new IllegalArgumentException("Bad enum for " +
                        AccountScreen.class.getSimpleName());
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }

    public void changeScreen(AccountScreen Screen) {
        switch (Screen) {
            case ACCOUNT_SCREEN:
                this.currentscreen = AccountScreen.ACCOUNT_SCREEN;
                show(currentscreen);
                break;
            case ACCOUNT_SETTINGS:
                this.currentscreen = AccountScreen.ACCOUNT_SETTINGS;
                show(currentscreen);
                break;
            case PAYMENT_SETTINGS:
                this.currentscreen = AccountScreen.PAYMENT_SETTINGS;
                show(currentscreen);
                break;
            default:
                throw new IllegalArgumentException("Bad enum for " +
                        AccountScreen.class.getSimpleName());
        }
    }
}
