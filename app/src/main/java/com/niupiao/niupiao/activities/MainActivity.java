package com.niupiao.niupiao.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.niupiao.niupiao.Constants;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.adapters.LeftNavAdapter;
import com.niupiao.niupiao.fragments.NiuNavigationDrawerFragment;
import com.niupiao.niupiao.fragments.SettingsNavFragment;
import com.niupiao.niupiao.fragments.StarredNavFragment;
import com.niupiao.niupiao.fragments.account.AccountNavFragment;
import com.niupiao.niupiao.fragments.events.EventsNavFragment;
import com.niupiao.niupiao.fragments.my_tickets.MyTicketsNavFragment;
import com.niupiao.niupiao.managers.EventManager;
import com.niupiao.niupiao.managers.TicketManager;
import com.niupiao.niupiao.models.Data;
import com.niupiao.niupiao.models.User;
import com.niupiao.niupiao.requesters.ResourceCallback;
import com.niupiao.niupiao.utils.SharedPrefsUtils;

import java.util.ArrayList;

/**
 * Created by kmchen1 on 2/17/15.
 */
public class MainActivity extends ActionBarActivity implements ResourceCallback {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String INTENT_KEY_FOR_USER = "user";

    //Positions of main fragments in the NavDrawer
    private static final int POSITION_OF_EVENTS = 0;
    private static final int POSITION_OF_MY_TICKETS=1;
    private static final int POSITION_OF_LOGOUT = 5;

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
        User user = getIntent().getParcelableExtra(INTENT_KEY_FOR_USER);
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO broadcast receiver should listen for connection and have EventManger go then
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
            selectItem(0);
        }

        SharedPreferences sp = getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences(Constants.SharedPrefs.LOGIN_CREDENTIALS, MODE_PRIVATE).edit();
        boolean firstTime = sp.getBoolean(Constants.SharedPrefs.FIRST_RUN, true);

        if(firstTime){ //If this is the first time the App is being used.
            eventRun(); //Begin the tutorial process!
            editor.putBoolean(Constants.SharedPrefs.FIRST_RUN, false);
            editor.apply();
        }

    }

    private ArrayList<Data> instantiateData() {
        mFragmentTitles = getResources().getStringArray(R.array.fragments_array);
        int[] mFragmentDrawables = {R.drawable.documents, R.drawable.ticket, R.drawable.star,
                R.drawable.person, R.drawable.gear, R.drawable.navbar_empty};

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

    private NiuNavigationDrawerFragment getSelectedFragment(int position) {
        switch (position) {
            case 0:
                return new EventsNavFragment();
            case 1:
                return new MyTicketsNavFragment();
            case 2:
                return new StarredNavFragment();
            case 3:
                return new AccountNavFragment();
            case 4:
                return new SettingsNavFragment();
            default:
                Log.wtf(TAG, "unhandled fragment");
                return null;
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        if(position == MainActivity.POSITION_OF_LOGOUT){
            finish(); // TODO: Better way to do this? This seems clunky.
        } else {
            Fragment fragment = getSelectedFragment(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            //mDrawerList.setItemChecked(position, true);
            ((LeftNavAdapter)mDrawerList.getAdapter()).setSelection(position);
            setTitle(mFragmentTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
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

    /*
     * First Run Showcased views below. Each one is a step describing a particular
     */

    private void eventRun() {
        new ShowcaseView.Builder(this)
                .setContentTitle(this.getResources().getString(R.string.welcome))
                .setContentText(this.getResources().getString(R.string.find_events))
                .setStyle(R.style.FirstRunTheme)
                .setTarget(new ViewTarget(R.id.content_frame, this))
                        .setShowcaseEventListener(new OnShowcaseEventListener() {

                            @Override
                            public void onShowcaseViewShow(final ShowcaseView scv) { }

                            @Override
                            public void onShowcaseViewHide(final ShowcaseView scv) {
                                scv.setVisibility(View.GONE);
                                ticketRun();
                            }

                            @Override
                            public void onShowcaseViewDidHide(final ShowcaseView scv) { }

                        })
                        .build();
    }

    private void ticketRun() {
        //TODO: Might be better to just have a method that takes care of opening the drawer.
        mDrawerLayout.openDrawer(Gravity.LEFT);
        invalidateOptionsMenu();

        new ShowcaseView.Builder(this)
                .setContentTitle(this.getResources().getString(R.string.nav_drawer_title))
                .setContentText(this.getResources().getString(R.string.find_tickets))
                .setStyle(R.style.FirstRunTheme)
                .setTarget(new ViewTarget(R.id.ib_home, this))
                .setShowcaseEventListener(new OnShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewShow(final ShowcaseView scv) { }

                    @Override
                    public void onShowcaseViewHide(final ShowcaseView scv) {
                        scv.setVisibility(View.GONE);
                        openingTicketRun();
                    }

                    @Override
                    public void onShowcaseViewDidHide(final ShowcaseView scv) { }

                })
                .build();
    }

    private void openingTicketRun() {
        selectItem(POSITION_OF_MY_TICKETS);

        new ShowcaseView.Builder(this)
                .setContentTitle(this.getResources().getString(R.string.tickets_at_door))
                .setContentText(this.getResources().getString(R.string.bring_up_tickets))
                .setStyle(R.style.FirstRunTheme)
                .setTarget(new ViewTarget(R.id.content_frame, this))
                .setShowcaseEventListener(new OnShowcaseEventListener() {

                    @Override
                    public void onShowcaseViewShow(final ShowcaseView scv) { }

                    @Override
                    public void onShowcaseViewHide(final ShowcaseView scv) {
                        scv.setVisibility(View.GONE);
                        selectItem(POSITION_OF_EVENTS);
                    }

                    @Override
                    public void onShowcaseViewDidHide(final ShowcaseView scv) { }

                })
                .build();
    }

}
