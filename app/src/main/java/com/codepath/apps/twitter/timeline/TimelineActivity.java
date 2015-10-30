package com.codepath.apps.twitter.timeline;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.restapi.TwitterApplication;
import com.codepath.apps.twitter.restapi.TwitterClient;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utilities.EndlessScroller;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    private TimelineAdapter timelineAdapter;
    private ListView lvTweets;
    private static String TWEET_DETAIL_KEY = "tweet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setActionBar();
        client = TwitterApplication.getRestClient();
        setListViewAdapter();
        populateTimeline();
        setEvents();

        /* TODO
            1. Swipe to Refresh Tweets
            2. Endless Scroll Listener
            4. Create Profile
            5. Reply from Timeline (click) DialogueFragment
            6. Check internet permissions
            7. Progress bar when making requests
         */
    }


    private void setListViewAdapter() {
        timelineAdapter = new TimelineAdapter(this, tweets);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setAdapter(timelineAdapter);
    }

    private void setActionBar() {
        ActionBar bar = getSupportActionBar();
        // Add Icon on Menu
        bar.setDisplayShowHomeEnabled(true);
        bar.setLogo(R.drawable.ic_twitter_logo);
        bar.setDisplayUseLogoEnabled(true);
        bar.setTitle("");
    }

    private void setEvents() {
        // Set up ListView Click event
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(TimelineActivity.this, TweetActivity.class);
                i.putExtra(TWEET_DETAIL_KEY, timelineAdapter.getItem(pos));
                startActivity(i);
            }
        });

        // Fetch new/more data from Twitter Api seamlessly
        lvTweets.setOnScrollListener(new EndlessScroller() {
            @Override
            public void onLoadMore(Long maxID) {
                populateSubsequentTimeline(maxID);
            }

            @Override
            public Long getTweetMaxID() {
                // return activity's maxID
                if( tweets.isEmpty()) {
                    return null;
                } else {
                    int lastItem = tweets.size() - 1;
                    Long id = timelineAdapter.getItem(lastItem).getId();
                    return timelineAdapter.getItem(lastItem).getId();
                }
            }
        });

    }

    private void populateTimeline() {
        client.getInitialHomeTimeline(new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                tweets = Tweet.getTweetsFromJsonArray(jsonArray);
                timelineAdapter.addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        });
    }

    private void populateSubsequentTimeline(Long maxID) {
        client.getSubsequentHomeTimeline(new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                tweets = Tweet.getTweetsFromJsonArray(jsonArray);
                timelineAdapter.addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        }, maxID);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
