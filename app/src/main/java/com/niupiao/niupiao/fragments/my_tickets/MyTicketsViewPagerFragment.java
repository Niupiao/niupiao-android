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
import com.niupiao.niupiao.adapters.EventsAdapter;
import com.niupiao.niupiao.adapters.MyTicketsAdapter;
import com.niupiao.niupiao.fragments.ViewPagerFragment;
import com.niupiao.niupiao.managers.EventManager;
import com.niupiao.niupiao.models.Event;

import java.util.Collection;

/**
 * Created by kmchen1 on 2/21/15.
 */
public abstract class MyTicketsViewPagerFragment extends ViewPagerFragment implements EventManager.OnEventsLoadedListener {

    protected ListView listView;

    protected abstract void requestEventsFromManager();

    @Override
    public void onEventsLoaded(Collection<Event> events) {
        EventsAdapter adapter = ((EventsAdapter) listView.getAdapter());
        adapter.setObjects(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.linear_layout_with_list_view, container, false);
        listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(new MyTicketsAdapter(getActivity(), R.layout.list_view_item_my_ticket));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        requestEventsFromManager();
        return root;
    }
}
