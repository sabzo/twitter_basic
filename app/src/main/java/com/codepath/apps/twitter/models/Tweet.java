package com.codepath.apps.twitter.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sabelo on 10/11/15.
 */
public class Tweet {
    String text;
    ArrayList<String> hashtags = null;
    // Colleciton of tweets
    private static ArrayList<Tweet> tweets = null;
    String id;
    String created_at;
    String relative_date;
    User user = null;

    // Create a Tweet Object
    public Tweet(JSONObject jsonObject) {
        try {
            text = jsonObject.getString("text");
            id = jsonObject.getString("id_str");
            created_at = jsonObject.getString("created_at");
            relative_date = getRelativeTimeAgo(created_at);
            user = new User(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

    public String getScreenName() {
        return this.user.getScreen_name();
    }

    public String getName() {
        return this.user.getName();
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getRelative_date(){
        return relative_date;
    }

    public String getProfileImageURL() {
        return user.getProfileImageURL();
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public String getId() {
        return id;
    }

    // Get Tweets
    public static ArrayList<Tweet> getTweetsFromJsonArray( JSONArray jsonArray ) {
        tweets = new ArrayList<Tweet>();
        for( int i = 0; i < jsonArray.length(); i++) {
            try{
                // create Tweet from JSON Object
                JSONObject object = jsonArray.getJSONObject(i);
                Tweet tweet = new Tweet(object);
                Log.d("Debug", jsonArray.toString());
                tweets.add(tweet);
            } catch( JSONException e) {
                Log.d("Debug", e.toString());
            }
        }
        return tweets;
    }
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    private String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }
}

    /*
    [
  {
    "coordinates": null,
    "truncated": false,
    "created_at": "Tue Aug 28 21:16:23 +0000 2012",
    "favorited": false,
    "id_str": "240558470661799936",
    "in_reply_to_user_id_str": null,
    "entities": {
      "urls": [
 
      ],
      "hashtags": [
 
      ],
      "user_mentions": [
 
      ]
    },
    "text": "just another test",
    "contributors": null,
    "id": 240558470661799936,
    "retweet_count": 0,
    "in_reply_to_status_id_str": null,
    "geo": null,
    "retweeted": false,
    "in_reply_to_user_id": null,
    "place": null,
    "source": "<a href="//realitytechnicians.com\"" rel="\"nofollow\"">OAuth Dancer Reborn</a>",
    "user": {
      "name": "OAuth Dancer",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_background_tile": true,
      "profile_sidebar_border_color": "C0DEED",
      "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
      "created_at": "Wed Mar 03 19:37:35 +0000 2010",
      "location": "San Francisco, CA",
      "follow_request_sent": false,
      "id_str": "119476949",
      "is_translator": false,
      "profile_link_color": "0084B4",
     */

    
