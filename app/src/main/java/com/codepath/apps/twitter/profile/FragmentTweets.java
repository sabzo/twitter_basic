package com.codepath.apps.twitter.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.User;

/**
 * Created by sabelo on 10/21/15.
 */
public class FragmentTweets extends Fragment {
    String id;
    User user = null;

    public static FragmentTweets newInstances( User user ) {
        Bundle args = new Bundle();
        args.putString("id", user.getId());
        args.putSerializable("user", user);
        FragmentTweets fragment = new FragmentTweets();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstance) {
        id = getArguments().getString("id", null);
        user = (User) getArguments().getSerializable("user");
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup parent, Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_tweets, parent, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanace) {
        //View initialization
    }

}
