package com.codepath.apps.twitter.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.profile.ActivityProfile;
import com.codepath.apps.twitter.restapi.TwitterApplication;
import com.codepath.apps.twitter.restapi.TwitterClient;
import com.codepath.apps.twitter.utilities.EndlessScroller;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by sabelo on 10/29/15.
 */
public abstract class TimelineFragment extends Fragment {

    protected TwitterClient client;
    protected ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    protected TimelineAdapter timelineAdapter;
    protected ListView lvTweets;
    protected static String TWEET_DETAIL_KEY = "tweet";
    protected ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        client = TwitterApplication.getRestClient();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup parent, Bundle savedinstance) {
        //Don't forget to set attach_to_parent to false!
        return inflater.inflate(R.layout.fragment_tweets, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        setListViewAdapter(view);
        progressBar = (ProgressBar) view.findViewById(R.id.pbStartStop);
        populateTimeline();
        setEvents();
    }

    private void setListViewAdapter(View view) {
        timelineAdapter = new TimelineAdapter(getContext(), tweets);
        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(timelineAdapter);

    }

    private void setEvents() {
        // Set up ListView Click event
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                switch (view.getId()) {
                    case R.id.ivProfile:
                        User user = timelineAdapter.getItem(pos).getUser();
                        Intent intent = new Intent(getContext(), ActivityProfile.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        break;
                    default:
                        Intent i = new Intent(getContext(), TweetActivity.class);
                        i.putExtra(TWEET_DETAIL_KEY, timelineAdapter.getItem(pos));
                        startActivity(i);
                }
            }
        });

        // Fetch new/more data from Twitter Api seamlessly
        lvTweets.setOnScrollListener(new EndlessScroller() {
            @Override
            public void onLoadMore(Long maxID) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                populateSubsequentTimeline(maxID);
            }

            @Override
            public Long getTweetMaxID() {
                // return activity's maxID
                if (tweets.isEmpty()) {
                    return null;
                } else {
                    int lastItem = tweets.size() - 1;
                    return timelineAdapter.getItem(lastItem).getId();
                }
            }
        });
    }

    protected void addTweetsToTimelineView(JSONArray response) {
        tweets = Tweet.getTweetsFromJsonArray(response);
        timelineAdapter.addAll(tweets);
    }

    /* Customize the following calls for Homeline and UserProfile */
    public abstract void populateTimeline();
    public abstract void populateSubsequentTimeline(Long maxID);


}
