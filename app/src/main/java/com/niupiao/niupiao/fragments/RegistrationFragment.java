package com.niupiao.niupiao.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.MainActivity;

/**
 * Created by kevinchen on 2/18/15.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_registration, container, false);
        TextView txt = (TextView) root.findViewById(R.id.fragment_registration_title);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
        txt.setTypeface(font);

        Button register = (Button) root.findViewById(R.id.fragment_registration_button);
        register.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        Context context = getActivity();
        switch(v.getId()){
            case R.id.fragment_registration_button:
                System.out.println("Registration button pressed");
                Intent goToMain = new Intent(context, MainActivity.class);
                startActivity(goToMain);
                break;
        }
    }
}