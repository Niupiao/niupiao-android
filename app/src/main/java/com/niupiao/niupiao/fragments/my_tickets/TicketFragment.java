package com.niupiao.niupiao.fragments.my_tickets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupiao.niupiao.R;

/**
 * Created by Inanity on 2/27/15.
 */
public class TicketFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ticket, container, false);
        return root;
    }

    public static TicketFragment newInstance() {
        return new TicketFragment();
    }

}
