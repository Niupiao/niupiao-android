package com.niupiao.niupiao.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.Session;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.LeftNavAdapter;
import com.niupiao.niupiao.fragments.NiuNavigationDrawerFragment;
import com.niupiao.niupiao.fragments.SettingsNavFragment;
import com.niupiao.niupiao.fragments.StarredNavFragment;
import com.niupiao.niupiao.fragments.account.AccountNavFragment;
import com.niupiao.niupiao.fragments.events.EventsNavFragment;
import com.niupiao.niupiao.fragments.my_tickets.MyTicketsNavFragment;
import com.niupiao.niupiao.managers.EventManager;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.managers.TicketManager;
import com.niupiao.niupiao.models.Data;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.User;
import com.niupiao.niupiao.requesters.ResourceCallback;
import com.niupiao.niupiao.utils.SharedPrefsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class MainActivity extends ActionBarActivity implements ResourceCallback {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String INTENT_KEY_FOR_USER = "user";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mFragmentTitles;

    private EventManager eventManager;
    private TicketManager ticketManager;

    public TicketManager getTicketManager() {
        return ticketManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public String getAccessToken() {
        return SharedPrefsUtils.getAccessToken(this);
    }

    @Override
    public void onVolleyError(VolleyError volleyError) {
        Toast.makeText(this, "Oops: " + volleyError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    public User getUser() {
        return getIntent().getParcelableExtra(INTENT_KEY_FOR_USER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO broadcast receiver should listen for connection and have EventManager go then
        eventManager = new EventManager(this);
        ticketManager = new TicketManager(this);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new LeftNavAdapter(this, instantiateData()));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.niupiao_blue)));

        View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar_main, null);
        actionBar.setCustomView(mActionBarView);
        View postView = actionBar.getCustomView();
        ActionBar.LayoutParams lp = (ActionBar.LayoutParams) postView.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        postView.setLayoutParams(lp);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon

        ImageButton home = (ImageButton) findViewById(R.id.ib_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                if(! mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    //TODO: Figure out: Why are we creating a call to onPrepareOptionsMenu?
                    invalidateOptionsMenu();
                } else{
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    invalidateOptionsMenu();
                }
            }
        });
        /*
        Old Drawer Code:
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.slideicon,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
         */

        if (savedInstanceState == null) {
            selectItem(NAV_DRAWER_INDEX_EVENTS);
        }
    }

    private ArrayList<Data> instantiateData() {
        mFragmentTitles = getResources().getStringArray(R.array.fragments_array);
        int[] mFragmentDrawables = {R.drawable.documents, R.drawable.ticket, R.drawable.star,
                R.drawable.person, R.drawable.gear};

        ArrayList<Data> navBarItems = new ArrayList<Data>();

        int length = mFragmentTitles.length;
        for (int i = 0; i < length; i++) {
            navBarItems.add(new Data(mFragmentTitles[i], mFragmentDrawables[i]));
        }

        return navBarItems;
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private static final int NAV_DRAWER_INDEX_EVENTS = 0;
    private static final int NAV_DRAWER_INDEX_MY_TICKETS = 1;
    private static final int NAV_DRAWER_INDEX_STARRED = 2;
    private static final int NAV_DRAWER_INDEX_ACCOUNT = 3;
    private static final int NAV_DRAWER_INDEX_SETTINGS = 4;

    private NiuNavigationDrawerFragment getSelectedFragment(int position) {
        switch (position) {
            case NAV_DRAWER_INDEX_EVENTS:
                return new EventsNavFragment();
            case NAV_DRAWER_INDEX_MY_TICKETS:
                return new MyTicketsNavFragment();
            case NAV_DRAWER_INDEX_STARRED:
                return new StarredNavFragment();
            case NAV_DRAWER_INDEX_ACCOUNT:
                return new AccountNavFragment();
            case NAV_DRAWER_INDEX_SETTINGS:
                return new SettingsNavFragment();
            default:
                Log.wtf(TAG, "unhandled fragment");
                return null;

        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = getSelectedFragment(position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mFragmentTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        try{
            mDrawerToggle.syncState();
        } catch(Exception e){
            //TODO Figure out why this throws an Exception, and fix.
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.logout).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            //case R.id.logout:
            //    logout();
            //    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        Session.getActiveSession().close();
        finish();
    }

    public void checkout(Event event) {
        Intent intent = new Intent(this, PayActivity.class);
        intent.putExtra(PayActivity.INTENT_KEY_FOR_EVENT, event);
        intent.putExtra(PayActivity.INTENT_KEY_FOR_USER, getUser());
        startActivityForResult(intent, PayActivity.REQUEST_CODE_CHECKOUT_TICKETS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: Remove System.out.println, which are currently here for testing.
        // Check which request we're responding to
        System.out.println("RequestCode: " + requestCode);
        if (requestCode == PayActivity.REQUEST_CODE_CHECKOUT_TICKETS) {
            // Make sure the request was successful
            System.out.println("RESULT_OK: " + RESULT_OK);
            System.out.println("RESULT_CANCELED: " + RESULT_CANCELED);
            System.out.println("ResultCode: " + resultCode);
            if (resultCode == RESULT_OK) {
                Event event = data.getParcelableExtra(PayActivity.RESULT_KEY_FOR_EVENT);
                ArrayList<PaymentManager.Tickets> tickets = data.getParcelableArrayListExtra(PayActivity.RESULT_KEY_FOR_TICKETS_PURCHASED);

                // TODO move to Requester -- POST to server so it knows we just bought these tickets
                // e.g., { "event_id" : 1, "tickets_purchased" : { "VIP" : 2, "General" : 3 } }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("event_id", event.getId());
                    JSONObject ticketsPurchasedJsonObject = new JSONObject();
                    if (tickets != null) {
                        for (PaymentManager.Tickets ticketPacket : tickets) {
                            ticketsPurchasedJsonObject.put(ticketPacket.getTicketStatus().getName(),
                                    ticketPacket.getNumberTicketsPurchased());
                        }
                    }
                    jsonObject.put("tickets_purchased", ticketsPurchasedJsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) {
                // we didn't buy any tickets so show the events page
                try {
                    selectItem(NAV_DRAWER_INDEX_EVENTS);
                } catch (IllegalStateException e){
                    //TODO: IllegalStateException is called. Perhaps see http://stackoverflow.com/questions/3353023/android-illegalstateexception-when-is-it-thrown
                }
            }
        }
    }
}
