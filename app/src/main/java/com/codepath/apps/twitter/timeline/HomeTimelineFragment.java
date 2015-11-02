package com.codepath.apps.twitter.timeline;

import android.util.Log;
import android.widget.ProgressBar;

import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sabelo on 10/29/15.
 */
public class HomeTimelineFragment extends TimelineFragment {

    @Override
    public void populateTimeline() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        client.getInitialHomeTimeline( new JsonHttpResponseHandler(){
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                clearTimeline();
                addTweetsToTimelineView(jsonArray);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                stopRefresh();
            }
            // failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String message = errorResponse != null ? errorResponse.toString(): "Unable to connect";
                Log.d("Debug", message);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                stopRefresh();
            }
        });
    }

    @Override
    public void populateSubsequentTimeline(Long maxID) {
        client.getSubsequentHomeTimeline(new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                addTweetsToTimelineView(jsonArray);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                String message = errorResponse != null ? errorResponse.toString(): "Unable to connect";
                Log.d("Debug", message);
            }
        }, maxID);
    }

}
