package com.codepath.apps.restclienttemplate.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;
import static com.codepath.apps.restclienttemplate.TimelineActivity.REQUEST_COMPOSE_CODE;

/**
 * Created by qunli on 10/3/17.
 */

public abstract  class TweetsListFragment extends Fragment {

    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    protected TwitterClient client;
    protected Long maxID=Long.MAX_VALUE-1;


    protected EndlessRecyclerViewScrollListener scrollListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragments_tweets_list,container,false);



        rvTweets = (RecyclerView)v.findViewById(R.id.rvTweet);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };

        rvTweets.addOnScrollListener(scrollListener);
        tweets = new ArrayList<>();
        tweetAdapter = new TweetAdapter(tweets);
        //rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setAdapter(tweetAdapter);



        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("DEBUG","I am at onActivityResult");
        if (resultCode==RESULT_OK && requestCode==REQUEST_COMPOSE_CODE)
        {
            Log.d("DEBUG","I am at onActivityResult & inside OK");
            //Tweet tweet = data.getParcelableExtra("tweet");
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet")) ;
            tweets.add(0,tweet);


            tweetAdapter.notifyDataSetChanged();
            rvTweets.getLayoutManager().scrollToPosition(0);

        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void loadNextDataFromApi(int offset);

    //protected void populateTimeline(int page);

    public void addItems(JSONArray response){
        for ( int i=0;i<response.length();i++){
            try {
                JSONObject JSonResult = response.getJSONObject(i);
                //Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                Tweet tweet = Tweet.fromJSON(JSonResult);
//                if (TwitterClient.maxID > Tweet.getID(JSonResult)){
//                    TwitterClient.maxID = Tweet.getID(JSonResult);
//                }
                if (maxID > Tweet.getID(JSonResult)){
                    maxID = Tweet.getID(JSonResult);
                }
                tweets.add(tweet);
                tweetAdapter.notifyItemChanged(tweets.size() - 1);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
