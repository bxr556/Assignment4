package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by qunli on 9/26/17.
 */

@Parcel
public class Tweet {

    //list of attributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public String createdAtAgo;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtAgo() {
        return createdAtAgo;
    }

    public Tweet() {

    }
    public Tweet(String body, long uid, User user, String createdAt, String createdAtAgo) {
        this.body = body;
        this.uid = uid;
        this.user = user;
        this.createdAt = createdAt;
        this.createdAtAgo = createdAtAgo;
    }

    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.createdAtAgo = getRelativeTimeAgo(tweet.createdAt);
        return tweet;
    }

    public static Long getID(JSONObject jsonObject) throws JSONException {
        Long maxid = jsonObject.getLong("id");

        return maxid;
    }

    public static String getRelativeTimeAgo(String rawJsonDate){
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate="";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS).toString();
        }catch (ParseException e){
            e.printStackTrace();
        }

        return relativeDate;
    }
}
