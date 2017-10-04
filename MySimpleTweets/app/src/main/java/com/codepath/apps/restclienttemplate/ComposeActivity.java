package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.data;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    private EditText tweetText;
    private TextView tvWordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient();
        tweetText = (EditText) findViewById(R.id.et_tweetText);
        tvWordCount = (TextView)findViewById(R.id.tvWordCount);

        tweetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvWordCount.setText(""+s.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvWordCount.setText(""+s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void tweet(View view) {
        String tweetStatus = tweetText.getText().toString();
        tweet(tweetStatus);
    }

    private void tweet(String tweetStatus) {
        client.tweet(tweetStatus,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                Log.d("DEBUG", "success:"+responseBody.toString());
                Tweet tweet = new Tweet();
                try {
                    tweet = Tweet.fromJSON(responseBody);
                    Intent composeIntent = new Intent();
                    Parcelable wrapped = Parcels.wrap(tweet);
                    composeIntent.putExtra("tweet", wrapped);

                    setResult(RESULT_OK,composeIntent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG","1");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG","2");
                Log.d("DEBUG","statusCode is:"+statusCode);
                Log.d("DEBUG","throwable is:"+throwable.getMessage().toString());
                Log.d("DEBUG","errorResponse:"+errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG","3");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG","4");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("DEBUG","5");
            }
        });

    }
}
