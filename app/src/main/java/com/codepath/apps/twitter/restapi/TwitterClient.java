package com.codepath.apps.twitter.restapi;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.codepath.apps.twitter.timeline.TimelineActivity;
import com.codepath.oauth.OAuthAsyncHttpClient;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "hv7LM4l2uSkO0nWCxrn1pglsN";
	public static final String REST_CONSUMER_SECRET = "HF8uheFLKNZBmiY34LC5eK9G4Vp3QXFWeXqSbu6O3uq6QugOEb"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://sabtweet"; // Change this (here and in manifest)

    private static ConnectivityManager connectivityManager = null;
    private Context context;
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        this.context = context;
	}


    public void getInitialHomeTimeline(AsyncHttpResponseHandler handler) {
        if (!isNetworkAvailable()) return;
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        getClient().get(apiUrl, params, handler);
    }

	public void getSubsequentHomeTimeline(AsyncHttpResponseHandler handler, Long maxID) {
        if (!isNetworkAvailable()) return;
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("max_id", maxID);
		getClient().get(apiUrl, params, handler);
	}

    public void getAccountCredentials(AsyncHttpResponseHandler handler) {
        if (!isNetworkAvailable()) return;
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        params.put("skip_status", true);
        getClient().get(apiUrl, params, handler);
    }

	public void getInitialTweetsByUserID(String userID, AsyncHttpResponseHandler handler) {
        if (!isNetworkAvailable()) return;
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("since_id", 1);
        params.put("count", 25);
        getClient().get(apiUrl, params, handler);
	}

    public void getSubsequentTweetsByUserID(String userID, Long maxID, AsyncHttpResponseHandler handler) {
        if (!isNetworkAvailable()) return;
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("max_id", maxID);
        params.put("count", 25);
        getClient().get(apiUrl, params, handler);
    }

    public void getInitialMentions(AsyncHttpResponseHandler handler) {
        if (!isNetworkAvailable()) return;
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        getClient().get(apiUrl, params, handler);
    }

    public void getSubsequentMentions(Long maxID, AsyncHttpResponseHandler handler) {
        if (!isNetworkAvailable()) return;
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("max_id", maxID);
        getClient().get(apiUrl, params, handler);
    }

    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
        if (!isNetworkAvailable()) return;
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        getClient().post(apiUrl, params, handler);
    }

    public Boolean isNetworkAvailable() {
        if(connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        Boolean connectedOrConnecting =  activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        if (!connectedOrConnecting) {
            Toast.makeText(context, "NO INTERNET CONNECTION!", Toast.LENGTH_LONG).show();
        }
        return connectedOrConnecting;
    }


	public void getPhotos(String userID, AsyncHttpResponseHandler handler) {

	}

	public void getFavorites(String userID, AsyncHttpResponseHandler handler) {

	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}