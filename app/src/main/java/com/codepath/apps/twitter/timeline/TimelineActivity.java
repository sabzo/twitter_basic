package com.codepath.apps.twitter.timeline;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.profile.ActivityProfile;
import com.codepath.apps.twitter.restapi.TwitterApplication;
import com.codepath.apps.twitter.restapi.TwitterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
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
            6. Check internet permissions
            7. Progress bar when making requests
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
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flHomeTimeline, new HomeTimelineFragment());
        ft.commit();
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
                Log.d("Debug", errorResponse.toString());
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
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}