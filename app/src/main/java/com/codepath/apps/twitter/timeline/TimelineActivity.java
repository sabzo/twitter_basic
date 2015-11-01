package com.codepath.apps.twitter.timeline;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.login.LoginActivity;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.profile.ActivityProfile;
import com.codepath.apps.twitter.restapi.TwitterApplication;
import com.codepath.apps.twitter.restapi.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient client = null;
    private FragmentTransaction ft = null;
    private User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setActionBar();
        setHomeTimelineFragment();
        getCurrentUserID();
        /* TODO
            0. Customize ActionBar color
            1. Swipe to Refresh Tweets
            4. Create Profile
            5. Reply from Timeline (click) DialogueFragment
            8. Store Tweets in SQL Lite for viewing when no internet
         */
    }

    /* Customize ActionBar */
    private void setActionBar() {
        ActionBar bar = getSupportActionBar();
        // Add Icon on Menu. (menu.xml)
        bar.setDisplayShowHomeEnabled(true);
        bar.setLogo(R.drawable.ic_twitter_logo);
        bar.setDisplayUseLogoEnabled(true);
        bar.setTitle("");
    }

    private void setHomeTimelineFragment() {
        // Set up View Pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpHomeTimeline);
        HometimelinePagerAdapter hometimelinePagerAdapter =
                new HometimelinePagerAdapter( getSupportFragmentManager());
        viewPager.setAdapter(hometimelinePagerAdapter);

        // Set up TabLayout
        TabLayout tabs = (TabLayout) findViewById(R.id.tabsHome);
        tabs.setupWithViewPager(viewPager);

    }

    private void getCurrentUserID() {
        client = TwitterApplication.getRestClient();
        client.getAccountCredentials( new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
               user = new User(jsonObject);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String message = errorResponse != null ? errorResponse.toString(): "Unable to connect";
                Log.d("Debug", message);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch ( id ) {
            case R.id.miProfile:
                Intent intent = new Intent(this, ActivityProfile.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case R.id.miLogout:
                client.clearAccessToken();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
