package com.codepath.apps.twitter.utilities;

import android.widget.AbsListView;

/**
 * Created by sabelo on 10/29/15.
 */
public abstract class EndlessScroller implements AbsListView.OnScrollListener {

    // minimum num of items below scroll position before new items are fetched
    private int visibleThreshhold = 5;
    // current index of items loaded
    private int currIndex = 0;
    private int maxID = 0;
    // total Items since last load
    private int previousTotalItemCount = 0;
    // starting index
    private int startingIndex;
    // True if waiting for data to load
    private Boolean isLoading = false;

    public EndlessScroller(){}
    public EndlessScroller(int visibleThreshhold, int max_id) {
        this.visibleThreshhold = visibleThreshhold;
        this.maxID = max_id;
    }
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemsCount, int totalItemsCount) {

        // If loading Check to see if Items have loaded
        if (isLoading && (totalItemsCount > previousTotalItemCount) ) {
            isLoading = false;
            previousTotalItemCount = totalItemsCount;
        }

        // If not loading check to see if loading needs to be made by seeing
        // if we have passed the visible threshhold
        if (!isLoading && (totalItemsCount - visibleItemsCount) <= (firstVisibleItem + visibleThreshhold)) {
            Long maxID = getTweetMaxID();
            // If the first initial Timeline load hasn't finished/succeeded, onLoadMore is useless
            if( maxID == null) {
                return;
            }
            // Notify scroller that we're now loading new items so don't make another request!
            isLoading = true;
            onLoadMore( maxID );

        }
    }


    public abstract void onLoadMore(Long max_id);
    public abstract Long getTweetMaxID();
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }
}
