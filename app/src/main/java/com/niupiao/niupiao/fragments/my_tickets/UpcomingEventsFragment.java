package com.niupiao.niupiao.fragments.my_tickets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.MyTicketsAdapter;
import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by kmchen1 on 2/21/15.
 */
public class UpcomingEventsFragment extends ViewPagerFragment {

    private ListView events;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.linear_layout_with_list_view, container, false);
        events = (ListView) root.findViewById(R.id.list_view);
        events.setAdapter(new MyTicketsAdapter(getActivity(), R.layout.list_view_item_my_ticket));
        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    public static UpcomingEventsFragment newInstance() {
        return new UpcomingEventsFragment();
    }

    @Override
    public String getTitle() {
        return "Upcoming Events";
    }
}
