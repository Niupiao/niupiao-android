package com.niupiao.niupiao.fragments.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.EventsAdapter;
import com.niupiao.niupiao.fragments.ViewPagerFragment;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.requesters.EventsRequester;
import com.niupiao.niupiao.utils.SharedPrefsUtils;

import java.util.List;

/**
 * Created by kevinchen on 2/17/15.
 */
public class ComingSoonFragment extends ViewPagerFragment implements EventsRequester.OnEventsLoadedListener {

    public static final String TAG = ComingSoonFragment.class.getSimpleName();
    private ListView concertsListView;

    @Override
    public void onEventsLoaded(List<Event> events) {
        EventsAdapter adapter = ((EventsAdapter) concertsListView.getAdapter());
        adapter.setObjects(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String getAccessToken() {
        return SharedPrefsUtils.getAccessToken(getActivity());
    }

    @Override
    public void onVolleyError(VolleyError volleyError) {
        Toast.makeText(getActivity(), "Oops: " + volleyError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_coming_soon, container, false);
        concertsListView = (ListView) root.findViewById(R.id.list_view);
        concertsListView.setAdapter(new EventsAdapter(getActivity(), R.layout.list_view_item_concert));
        concertsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        EventsRequester.loadEvents(this);
        return root;
    }

    @Override
    public String getTitle() {
        return "Coming Soon";
    }

    /**
     * @param position This fragment's position in its ViewPager
     */
    public static ComingSoonFragment newInstance(int position) {
        ComingSoonFragment fragment = new ComingSoonFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

}
