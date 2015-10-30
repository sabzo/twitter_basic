package com.codepath.apps.twitter.profile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.timeline.TimelineFragment;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sabelo on 10/21/15.
 */
public class FragmentTweets extends TimelineFragment{
    String id;
    User user = null;

    public static FragmentTweets newInstance( User user ) {
        Bundle args = new Bundle();
        args.putString("id", user.getId());
        args.putSerializable("user", user);
        FragmentTweets fragment = new FragmentTweets();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        id = getArguments().getString("id");
        user = (User) getArguments().getSerializable("user");
    }

    @Override
    public void populateTimeline() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        client.getInitialTweetsByUserID(id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addTweetsToTimelineView(response);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    @Override
    public void populateSubsequentTimeline(Long maxID) {
        client.getSubsequentTweetsByUserID(id, maxID, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addTweetsToTimelineView(response);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("Debug", errorResponse.toString());
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }


}
