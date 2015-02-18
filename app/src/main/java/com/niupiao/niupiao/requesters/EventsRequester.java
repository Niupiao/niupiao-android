package com.niupiao.niupiao.requesters;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.NiupiaoApplication;
import com.niupiao.niupiao.deserializers.EventsDeserializer;
import com.niupiao.niupiao.models.Event;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class EventsRequester {

    public interface OnEventsLoadedListener extends ResourceCallback {
        public void onEventsLoaded(List<Event> events);
    }

    public static void loadEvents(final OnEventsLoadedListener listener) {
        ResourcesRequest request = new ResourcesRequest(
                listener,
                Constants.Url.EVENTS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // TODO handle errors from server
                        Log.d("From localhost: ", jsonArray.toString());
                        List<Event> events = EventsDeserializer.fromJsonArray(jsonArray);
                        listener.onEventsLoaded(events);
                    }
                }
        );
        NiupiaoApplication.getRequestQueue().add(request);
    }


}
