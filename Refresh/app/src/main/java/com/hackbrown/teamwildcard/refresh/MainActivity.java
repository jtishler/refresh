package com.hackbrown.teamwildcard.refresh;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {
    private AddGroceryItemDialog dialog;
    private static ArrayList<GroceryListItem> groceryListItems;
    private static ArrayList<FoodItem> kitchenListItems;
    private static ListView lv;
    private static ListView lvKitchen;
    private static EditText et;
    private static GroceryListAdapter glAdapter;
    private static KitchenListAdapter klAdapter;
    private CompoundButton cb;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        setUpNotifications();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setUpNotifications() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 40);
        calendar.set(Calendar.SECOND, 0);
        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //PendingIntent pi = PendingIntent.getService(this, 0, new Intent(this, Notification_Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager nm;
        nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notifcationIntent = new Intent(this, Notification_Activity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notifcationIntent, 0);
        Notification.Builder notif = new Notification.Builder(this);
        notif.setContentTitle("Food Waste Tip");
        notif.setContentText("Click to see the tip of the week.");
        notif.setSmallIcon(R.drawable.ic_tip_notif);
        notif.setContentIntent(contentIntent);
        notif.build();
        nm.notify(1, notif.getNotification());

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, contentIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createDialog(View view) {
        dialog = new AddGroceryItemDialog(this);
        dialog.show();
    }

    public void addGroceryListItem(View v) {
        groceryListItems.add(new GroceryListItem(dialog.getText(), dialog.getQuantity()));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int pos = lv.getPositionForView(compoundButton);
        if (pos != ListView.INVALID_POSITION) {
            GroceryListItem item = groceryListItems.get(pos);
            item.setSelected(b);

            if (b) {
                boolean added = false;
                for (FoodItem food: kitchenListItems) {
                    if (food.getName().equals(item.getName())) {
                        food.setQuantity(food.getQuantity() + item.getQuantity());
                        added = true;
                    }
                }

                if (!added) {
                    kitchenListItems.add(item);
                }
            } else {
                if (kitchenListItems.indexOf(item) != -1) {
                    FoodItem myFood = item;
                    for (FoodItem food: kitchenListItems) {
                        if (food.getName().equals(item.getName())) {
                            myFood = food;
                        }
                    }
                    myFood.setQuantity(myFood.getQuantity() - item.getQuantity());
                    if (myFood.getQuantity() == 0)  {
                        kitchenListItems.remove(kitchenListItems.indexOf(item));
                    }
                }
            }
            klAdapter.notifyDataSetChanged();

        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(R.layout.fragment_main, container, false);

                lv = (ListView) rootView.findViewById(R.id.groceryList);
                groceryListItems = new ArrayList<GroceryListItem>();
                glAdapter = new GroceryListAdapter(groceryListItems, this.getContext());
                lv.setAdapter(glAdapter);

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.fragment_kitchen, container, false);
                lvKitchen = (ListView) rootView.findViewById(R.id.kitchenList);
                kitchenListItems = new ArrayList<FoodItem>();
                klAdapter = new KitchenListAdapter(kitchenListItems, this.getContext());
                lvKitchen.setAdapter(klAdapter);
            } else {
                rootView = inflater.inflate(R.layout.fragment_why, container, false);
            }
            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "GROCERY LIST";
                case 1:
                    return "MY KITCHEN";
                case 2:
                    return "WHY?";
            }
            return null;
        }
    }


    public void onButtonClicked(View view){
        Intent explicitIntent = new Intent(this, SearcherActivity.class);
        startActivity(explicitIntent);

    }
}
