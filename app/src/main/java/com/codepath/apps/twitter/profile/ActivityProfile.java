package com.codepath.apps.twitter.profile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.User;
import com.squareup.picasso.Picasso;

/**
 * Created by sabelo on 10/21/15.
 */
public class ActivityProfile extends AppCompatActivity {
    private ViewPager profilePager = null;
    private ProfileAdapter profileAdapter = null;
    @Override
    public void onCreate( Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_profile);

        setUpActionBar();

        //Set up the ViewPager
        profilePager = (ViewPager) findViewById(R.id.vpProfile);
        User user = (User) getIntent().getSerializableExtra("user");

        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);
        Picasso.with(this).load(user.getProfileImageURL()).into(ivProfile);
        ((TextView) findViewById(R.id.tvName)).setText(user.getName());
        ((TextView) findViewById(R.id.tvHandle)).setText("@" + user.getScreen_name());
        ((TextView) findViewById(R.id.tvNumTweets))
                .setText(Integer.toString( user.getStatuses_count() ) + " Tweets");
        ((TextView) findViewById(R.id.tvNumFollowers))
                .setText(Integer.toString( user.getFollowers_count()) + " Followers");
        ((TextView) findViewById(R.id.tvNumFollowing))
                .setText(Integer.toString( user.getFriends_count()) + " Following");

        // TODO verify user was passed in otherwise error
        profileAdapter = new ProfileAdapter(getSupportFragmentManager(), user);
        profilePager.setAdapter(profileAdapter);
        // Set up Tabs
        TabLayout tabs = (TabLayout) findViewById(R.id.tabsProfile);
        tabs.setupWithViewPager(profilePager);
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
