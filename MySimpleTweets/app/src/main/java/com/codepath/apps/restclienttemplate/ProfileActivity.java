package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer,userTimelineFragment).commit();

        client = TwitterApp.getRestClient();

        if (screenName !=null) {
            client.getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        //populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    //super.onSuccess(statusCode, headers, response);
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        //populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    super.onSuccess(statusCode, headers, responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }else{
            client.getSelfInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        //populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    //super.onSuccess(statusCode, headers, response);
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        //populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    super.onSuccess(statusCode, headers, responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }
    }

    private void populateUserHeadline(User user) {
        TextView tvName=(TextView)findViewById(R.id.tvName);
        TextView tvTagline =(TextView )findViewById(R.id.tvTagline);
        TextView tvFollowers= (TextView )findViewById(R.id.tvFollowers);
                TextView tvFollowing =(TextView )findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);


        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount+ " Followers");
        tvFollowing.setText(user.followingCount +" Following");
        Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);
    }
}
