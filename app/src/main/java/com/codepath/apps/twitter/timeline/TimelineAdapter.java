package com.codepath.apps.twitter.timeline;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sabelo on 10/23/15.
 * The TimeLineAdapter is instantiated, populated and listened to by TimelineFragment
 */
public class TimelineAdapter extends ArrayAdapter<Tweet> {
    public TimelineAdapter(Context context, ArrayList<Tweet> tweets) {
       super(context, 0, tweets);
    }

    private static class ViewHolder {
        public TextView tvName;
        public TextView tvHandle;
        public TextView tvTimestamp;
        public TextView tvTweet;
        public ImageView ivProfile;
    }



    @Override
    public View getView(final int pos, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvHandle = (TextView) convertView.findViewById(R.id.tvHandle);
            viewHolder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
            viewHolder.tvTweet = (TextView) convertView.findViewById(R.id.tvTweet);
            viewHolder.ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Tweet tweet = getItem(pos);
        viewHolder.tvName.setText(
                Html.fromHtml("<b>" + tweet.getName() + "</b>"));
        viewHolder.tvHandle.setText(tweet.getScreenName());
        viewHolder.tvTimestamp.setText(tweet.getRelative_date());
        viewHolder.tvTweet.setText(tweet.getText());
        viewHolder.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, pos, 0);
            }
        });
        Picasso.with(getContext()).load(tweet.getProfileImageURL()).into(viewHolder.ivProfile);
        return convertView;
    }
}
