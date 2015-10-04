package com.dr.navigationapplication.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dr.navigationapplication.R;
import com.indoor.parse.Data;
import com.dr.navigationapplication.fragment.FindFragment;
import com.dr.navigationapplication.fragment.MapFragment;
import com.dr.navigationapplication.fragment.NavigationDrawerFragment;
import com.dr.navigationapplication.util.BaiduLocate;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, MapFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity:";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Fragment managing the behaviors, interactions and presentation of the map.
     */
    private MapFragment mMapFragment;
    /**
     * fragment managing the
     */
    private FindFragment findFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        //init map
        mMapFragment = MapFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.activity_main_map, mMapFragment).commit();

        findFragment = (FindFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_find);
        findFragment.setHandler(ReadyActivity.handler);

        if (BaiduLocate.getCurrentCity() != null) {
            mTitle = BaiduLocate.getCurrentCity();
            for (int i = 0; i < Data.cityTableList.size(); i++) {
                Log.i(TAG, mTitle + "==" + Data.cityTableList.get(i));
                if (mTitle.equals(Data.cityTableList.get(i).getName())) {
                    findFragment.setCityID(Data.cityTableList.get(i).getId());
                    Log.i(TAG, "开始更新");
                    findFragment.upDate();
                    break;
                }
            }
        } else {
            mTitle = getTitle();
        }
    }

    /**
     * 监听城市导航事件
     *
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        String city = "";
        if (Data.getOnlyCity() != null && mMapFragment != null) {
            city = Data.getOnlyCity().get(position);
            Log.i(this.TAG, "select of city : " + city);
            mMapFragment.updateMap(city, city);
            mTitle = city;
            for (int i = 0; i < Data.cityTableList.size(); i++) {
                Log.i(TAG, city + "==" + Data.cityTableList.get(i).getName());
                if (city.equals(Data.cityTableList.get(i).getName())) {
                    findFragment.setCityID(Data.cityTableList.get(i).getId());
                    Log.i(TAG, "开始更新");
                    findFragment.upDate();
                    break;
                }
            }
        }
    }

    /**
     * 地图响应事件
     *
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
