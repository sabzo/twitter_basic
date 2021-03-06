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
public class FragmentPhotos extends Fragment {
    String id;
    User user;

    public static FragmentPhotos newInstance(User user) {

        Bundle args = new Bundle();
        args.putSerializable("user", user);
        FragmentPhotos fragment = new FragmentPhotos();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        user = (User) getArguments().getSerializable("user");
        id = user.getId();
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
