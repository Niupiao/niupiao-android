package com.niupiao.niupiao.fragments.my_tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.activities.TicketActivity;
import com.niupiao.niupiao.adapters.MyTicketsAdapter;
import com.niupiao.niupiao.fragments.ViewPagerFragment;
import com.niupiao.niupiao.managers.TicketManager;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.Ticket;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kmchen1 on 2/21/15.
 */
public abstract class MyTicketsViewPagerFragment extends ViewPagerFragment implements TicketManager.OnEventsLoadedListener {

    protected ListView listView;
    private SwipeRefreshLayout swipeContainer;

    protected abstract void requestEventsFromManager();

    protected TicketManager getTicketManager() {
        return ((MainActivity) getActivity()).getTicketManager();
    }

    @Override
    public void onEventsLoaded(Collection<Event> events) {

        // get the adapter
        MyTicketsAdapter adapter = ((MyTicketsAdapter) listView.getAdapter());

        // pour tickets from each event into one giant collection
        Collection<Ticket> tickets = new ArrayList<>();
        for (Event event : events) {
            Collection<Ticket> eventTickets = event.getTickets();
            if (eventTickets != null) {
                tickets.addAll(eventTickets);
            }
        }

        try {
            Log.d(MyTicketsViewPagerFragment.class.getSimpleName(), "FIRST TICKET: " + events.iterator().next().getTickets().toString());
        } catch (Exception e) {
            Log.d(MyTicketsViewPagerFragment.class.getSimpleName(), "No tickets!");
        }

        // update the adapter
        adapter.setObjects(tickets);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.linear_layout_with_refreshable_list_view, container, false);
        listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(new MyTicketsAdapter(getActivity(), R.layout.list_view_item_my_ticket));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyTicketsAdapter adapter = ((MyTicketsAdapter) listView.getAdapter());
                Ticket ticket = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra(TicketActivity.INTENT_KEY_FOR_USER, ((MainActivity) getActivity()).getUser());
                intent.putExtra(TicketActivity.INTENT_KEY_FOR_TICKET, ticket);
                startActivity(intent);
            }
        });

        // http://nlopez.io/swiperefreshlayout-with-listview-done-right/
        swipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (listView == null || listView.getChildCount() == 0) ?
                                0 : listView.getChildAt(0).getTop();
                swipeContainer.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestEventsFromManager();
            }
        });

        requestEventsFromManager();
        return root;
    }
}
