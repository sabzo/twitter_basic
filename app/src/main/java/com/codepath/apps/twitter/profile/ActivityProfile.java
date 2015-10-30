package com.codepath.apps.twitter.profile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.User;

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
        //Set up the ViewPager
        profilePager = (ViewPager) findViewById(R.id.vpProfile);
        User user = (User) getIntent().getSerializableExtra("user");
        // TODO verify user was passed in otherwise error
        profileAdapter = new ProfileAdapter(getSupportFragmentManager(), user);
        profilePager.setAdapter(profileAdapter);
        // Set up Tabs
        TabLayout tabs = (TabLayout) findViewById(R.id.tabsProfile);
        tabs.setupWithViewPager(profilePager);
    }
}
