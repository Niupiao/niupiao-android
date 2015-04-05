package com.niupiao.niupiao.fragments.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.MainActivity;
import com.niupiao.niupiao.adapters.EventsAdapter;
import com.niupiao.niupiao.fragments.ViewPagerFragment;
import com.niupiao.niupiao.managers.EventManager;
import com.niupiao.niupiao.models.Event;

import java.util.Collection;

/**
 * Created by kmchen1 on 2/21/15.
 */
public abstract class EventsViewPagerFragment extends ViewPagerFragment implements EventManager.OnEventsLoadedListener {

    protected ListView listView;
    private SwipeRefreshLayout swipeContainer;

    protected abstract void requestEventsFromManager();

    @Override
    public void onEventsLoaded(Collection<Event> events) {
        EventsAdapter adapter = ((EventsAdapter) listView.getAdapter());
        adapter.setObjects(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.linear_layout_with_refreshable_list_view, container, false);
        listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(new EventsAdapter(getActivity(), R.layout.list_view_item_event));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventsAdapter adapter = ((EventsAdapter) listView.getAdapter());
                Event event = adapter.getItem(position);

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.checkout(event);
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
