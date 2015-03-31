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

        Typeface black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");

        TextView title = (TextView) root.findViewById(R.id.tv_title);

        title.setTypeface(black);

        return root;
    }

}
