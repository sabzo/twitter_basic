package com.codepath.apps.twitter.tweet;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.restapi.TwitterApplication;
import com.codepath.apps.twitter.restapi.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by sabelo on 11/1/15.
 */
public class ActivityPostTweet extends AppCompatActivity {
    // The tweet the user is sending
    private String tweet;
    private EditText etTweet;
    private Button btnTweet;
    private TwitterClient client;
    @Override
    public void onCreate( Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_post_tweet);
        setTwitterClient();
        setUpActionBar();
        setUpViews();
        setEvents();

    }

    private void setUpActionBar() {
        // Remove ActionBar Label
        ActionBar bar = getSupportActionBar();
        bar.setTitle("");
        bar.setDisplayUseLogoEnabled(true);
        // Set App Icon
        bar.setLogo(android.R.drawable.ic_menu_close_clear_cancel);
        bar.setDisplayShowHomeEnabled(true);
        // put "back" arrow
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
    }

    private void setUpViews() {
        etTweet = (EditText) findViewById(R.id.etTweet);
        // Remove Edit Text border
        etTweet.setBackground(null);
        btnTweet = (Button) findViewById(R.id.btnTweet);
    }

    private void setEvents() {
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweet = etTweet.getText().toString();
                if(!tweet.isEmpty()) {
                    client.postTweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Toast.makeText(ActivityPostTweet.this, "Tweet sent", Toast.LENGTH_SHORT).show();
                            // Go back to last Activity
                            finish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("Debug", "Unable to send tweet");
                            Toast.makeText(ActivityPostTweet.this, "Unable to send tweet", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });
                }
            }
        });
    }

    private void setTwitterClient() {
        client = TwitterApplication.getRestClient();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
            // home button clicked, go home!
            case android.R.id.home:
                finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }

    }


}
