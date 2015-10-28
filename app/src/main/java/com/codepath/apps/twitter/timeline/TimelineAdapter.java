package com.codepath.apps.twitter.timeline;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sabelo on 10/23/15.
 */
public class TimelineAdapter extends ArrayAdapter<Tweet> {
    public TimelineAdapter(Context context, ArrayList<Tweet> tweets) {
       super(context, 0, tweets);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet, parent, false);
        }
        Tweet tweet = getItem(pos);
        ((TextView) convertView.findViewById(R.id.tvName)).setText(
                Html.fromHtml("<b>" + tweet.getName() + "</b>") );
        ((TextView) convertView.findViewById(R.id.tvHandle)).setText(tweet.getScreenName());
        ((TextView) convertView.findViewById(R.id.tvTimestamp)).setText(tweet.getRelative_date());
        ((TextView) convertView.findViewById(R.id.tvTweet)).setText(tweet.getText());
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        Picasso.with(getContext()).load(tweet.getProfileImageURL()).into(ivProfile);
        return convertView;
    }
}
