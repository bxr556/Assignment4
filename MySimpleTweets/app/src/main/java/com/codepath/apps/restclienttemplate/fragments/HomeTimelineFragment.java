package com.codepath.apps.restclienttemplate.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TimelineActivity;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;

import cz.msebera.android.httpclient.Header;

/**
 * Created by qunli on 10/3/17.
 */

public class HomeTimelineFragment extends TweetsListFragment {

    TimelineActivity mCallback;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (TimelineActivity)getActivity();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient();

    }

    @Override
    public void onStart() {
        super.onStart();
        populateTimeline(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        //TwitterClient.maxID= Long.MAX_VALUE-1;

    }

    protected void loadNextDataFromApi(int offset){
        Log.d("DEUBG", "HomeTimeline is loading page:"+ offset);
        populateTimeline(offset);
                mCallback.showProgressBar();
    }
    protected void populateTimeline(int page) {

        client.getHomeTimeline(maxID,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient1",response.toString());
                mCallback.hideProgressBar();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient2",response.toString());
                addItems(response);
                mCallback.hideProgressBar();

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient3",responseString);
                throwable.printStackTrace();
                mCallback.hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient4",errorResponse.toString());
                throwable.printStackTrace();
                mCallback.hideProgressBar();
                scrollListener.resetState();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient5",errorResponse.toString());
                throwable.printStackTrace();
                mCallback.hideProgressBar();
            }
        });
    }

}
