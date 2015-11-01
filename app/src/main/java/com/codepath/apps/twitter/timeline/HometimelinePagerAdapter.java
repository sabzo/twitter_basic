package com.codepath.apps.twitter.timeline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sabelo on 11/1/15.
 */
public class HometimelinePagerAdapter extends FragmentPagerAdapter {
    private static final int count = 2;
    private String [] tabTitles= {"Timeline", "Mentions"};

    public HometimelinePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return new HomeTimelineFragment();
            case 1:
                return new MentionsTimelineFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
