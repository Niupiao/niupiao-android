package com.niupiao.niupiao.fragments.concerts;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.ParcelableArrayAdapter;
import com.niupiao.niupiao.fragments.ViewPagerFragment;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.requesters.EventsRequester;
import com.niupiao.niupiao.utils.ImageLoaderHelper;
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
        adapter.setParcelables(events);
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
        concertsListView.setAdapter(new EventsAdapter(getActivity()));
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

    public static ComingSoonFragment newInstance() {
        return new ComingSoonFragment();
    }

    class EventsAdapter extends ParcelableArrayAdapter<Event> {

        public EventsAdapter(Context context) {
            super(context, R.layout.list_view_item_concert);
        }

        class ViewHolder {
            NetworkImageView image;
            TextView date;
            TextView name;
            TextView ticketInfo;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_concert, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.image = (NetworkImageView) convertView.findViewById(R.id.image);
                viewHolder.date = (TextView) convertView.findViewById(R.id.date);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.ticketInfo = (TextView) convertView.findViewById(R.id.ticket_info);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Event event = getItem(position);

            NetworkImageView image = viewHolder.image;
            image.setImageUrl(Constants.Url.fullUrl(event.getImagePath()), ImageLoaderHelper.getInstance().getImageLoader());

            viewHolder.date.setText(event.getDate());
            viewHolder.name.setText(event.getName());
            viewHolder.ticketInfo.setText(String.format("%d out of %d tickets sold", event.getTicketsSold(), event.getTotalTickets()));
            return convertView;
        }
    }


    ///////////// LIFE CYCLE CALLBACKS FOR TESTING

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
//        outState.putParcelableArrayList("events", ((EventsAdapter) concertsListView.getAdapter()).getParcelableArrayList());
    }

}
