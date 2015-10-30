package com.codepath.apps.twitter.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.twitter.models.User;

import java.util.ArrayList;

/**
 * Created by sabelo on 10/21/15.
 */
public class ProfileAdapter extends FragmentPagerAdapter {
    private User user;
    private static int NUM_ITEMS = 3;
    private String [] TabTitles = new String[] {"Tweets", "Photos", "Favorites"};

    public ProfileAdapter(FragmentManager fm, User user) {
        super(fm);
        this.user = user;
    }

    @Override
    public int getCount(){
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem( int pos) {
        switch (pos) {
            case 0:
                return FragmentTweets.newInstance(user);
            case 1:
                return FragmentPhotos.newInstance(user);
            case 2:
                return FragmentFavorites.newInstance(user);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        return TabTitles[pos];
    }
}
