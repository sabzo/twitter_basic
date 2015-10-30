package com.codepath.apps.twitter.timeline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.squareup.picasso.Picasso;

/**
 * Created by sabelo on 10/28/15.
 */
public class TweetActivity extends AppCompatActivity {
    private Tweet tweet;
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tweet);
        populateLayout();
    }

    private void populateLayout() {
        tweet = (Tweet) getIntent().getSerializableExtra("tweet");
        ((TextView) findViewById(R.id.tvName)).setText(
                Html.fromHtml("<b>" + tweet.getName() + "</b>") );
        ((TextView) findViewById(R.id.tvHandle)).setText(tweet.getScreenName());
        ((TextView) findViewById(R.id.tvTimestamp)).setText(tweet.getRelative_date());
        ((TextView) findViewById(R.id.tvTweet)).setText(tweet.getText());
        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);
        Picasso.with(this).load(tweet.getProfileImageURL()).into(ivProfile);
    }
}
