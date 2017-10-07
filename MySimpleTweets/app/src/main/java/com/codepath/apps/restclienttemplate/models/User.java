package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by qunli on 9/26/17.
 */

@Parcel
public class User {

    //List all the attributes;

    public User() {
    }

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public String tagLine;
    public int followersCount;
    public int followingCount;

    public static User fromJSON (JSONObject json) throws JSONException {
        User user = new User();
        user.name=json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        //user.created_at = json.getString("created_at");

        user.tagLine = json.getString("description");
        user.followersCount = json.getInt("followers_count");
        user.followingCount=json.getInt("friends_count");
        return  user;
    }

    public static User fromJSON (JSONArray j) throws JSONException {
        User user = new User();
        JSONObject json = (JSONObject) j.getJSONObject(0);
        user.name=json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        //user.created_at = json.getString("created_at");

        user.tagLine = json.getString("description");
        user.followersCount = json.getInt("followers_count");
        user.followingCount=json.getInt("friends_count");
        return  user;
    }
}
