package com.codepath.apps.twitter.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sabelo on 10/17/15.
 */
public class User implements Serializable{
    String name;
    String screen_name;
    String profileImageURL;
    String id;
    String location;
    String display_url;
    String expanded_url;
    Boolean follow_request_sent;
    int followers_count;
    int friends_count;
    int statuses_count;

    /* Create user from JSON object */
    public User( JSONObject obj) {
        try{
            this.name = obj.getString("name");
            this.screen_name = obj.getString("screen_name");
            this.profileImageURL = obj.getString("profile_image_url");
            this.id = obj.getString("id_str");
            this.location = obj.getString("location");
            this.follow_request_sent = obj.getBoolean("follow_request_sent");
            this.followers_count = obj.getInt("followers_count");
            this.friends_count = obj.getInt("friends_count");
            this.statuses_count = obj.getInt("statuses_count");
            //May be null so putting last
            this.display_url = obj.getJSONObject("entities").getJSONObject("url")
                    .getJSONArray("urls").getJSONObject(0).getString("display_url");
            this.expanded_url = obj.getJSONObject("entities").getJSONObject("url")
                    .getJSONArray("urls").getJSONObject(0).getString("expanded_url");
        }catch (JSONException e) {
            Log.i("Debug", e.toString());
        }

    }

    public String getName() {
        return name;
    }

    public String getProfileImageURL() {
        return profileImageURL.replace("_normal.", "_bigger.");
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public String getExpanded_url() {
        return expanded_url;
    }

    public Boolean getFollow_request_sent() {
        return follow_request_sent;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public int getStatuses_count() { return statuses_count; }

    /*
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
      ...

     */
}
