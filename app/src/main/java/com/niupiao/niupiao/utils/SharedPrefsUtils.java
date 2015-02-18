package com.niupiao.niupiao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.niupiao.niupiao.Constants;

/**
 * Created by kevinchen on 2/18/15.
 */
public class SharedPrefsUtils {

    public static String getAccessToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, Context.MODE_PRIVATE);
        return sp.getString(Constants.SharedPrefs.ACCESS_TOKEN, null);
    }

}
