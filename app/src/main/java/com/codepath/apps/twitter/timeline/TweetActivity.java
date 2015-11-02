package com.codepath.apps.twitter.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.profile.ActivityProfile;
import com.squareup.picasso.Picasso;

/**
 * Created by sabelo on 10/28/15.
 */
public class TweetActivity extends AppCompatActivity {
    private Tweet tweet;
    ImageView ivProfile;
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tweet);
        setUpActionBar();
        populateLayout();
        setEvents();
    }

    private void setUpActionBar() {
        ActionBar bar = getSupportActionBar();
        bar.setTitle("");
        bar.setDisplayUseLogoEnabled(true);
        // Set App Icon
        bar.setLogo(R.drawable.ic_twitter_logo);
        bar.setDisplayShowHomeEnabled(true);
        // put "back" arrow
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
    }

    private void populateLayout() {
        tweet = (Tweet) getIntent().getSerializableExtra("tweet");
        ((TextView) findViewById(R.id.tvName)).setText(
                Html.fromHtml("<b>" + tweet.getName() + "</b>"));
        ((TextView) findViewById(R.id.tvHandle)).setText(tweet.getScreenName());
        ((TextView) findViewById(R.id.tvTimestamp)).setText(tweet.getRelative_date());
        ((TextView) findViewById(R.id.tvTweet)).setText(tweet.getText());
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        Picasso.with(this).load(tweet.getProfileImageURL()).into(ivProfile);
    }

    private void setEvents() {
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = tweet.getUser();
                Intent intent = new Intent(TweetActivity.this, ActivityProfile.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
            // home button clicked, go to previous activity
            case android.R.id.home:
                finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }

    }
}
