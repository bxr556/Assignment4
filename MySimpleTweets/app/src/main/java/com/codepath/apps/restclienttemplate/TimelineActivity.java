package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.github.scribejava.apis.TwitterApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    static final int REQUEST_COMPOSE_CODE=20;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item_compose:
                Intent intent = new Intent(this, ComposeActivity.class);
                startActivityForResult(intent,REQUEST_COMPOSE_CODE);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient();

        rvTweets = (RecyclerView)findViewById(R.id.rvTweet);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

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

        populateTimeline(0);

    }

    public void loadNextDataFromApi(int offset){
        populateTimeline(offset);
    }

    private void populateTimeline(int page) {

        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient",response.toString());

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient",response.toString());
                for ( int i=0;i<response.length();i++){
                    try {
                        JSONObject JSonResult = response.getJSONObject(i);
                        //Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        Tweet tweet = Tweet.fromJSON(JSonResult);
                        if (TwitterClient.maxID > Tweet.getID(JSonResult)){
                            TwitterClient.maxID = Tweet.getID(JSonResult);
                        }
                        tweets.add(tweet);
                        tweetAdapter.notifyItemChanged(tweets.size() - 1);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient",responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient",errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient",errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
