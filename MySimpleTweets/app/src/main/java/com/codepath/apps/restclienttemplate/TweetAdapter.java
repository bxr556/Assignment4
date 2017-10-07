package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

import static com.codepath.apps.restclienttemplate.R.id.ivProfileImage;
import static com.raizlabs.android.dbflow.config.FlowLog.Level.I;

/**
 * Created by qunli on 9/28/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>  {

    private List<Tweet> mTweets;
    Context context;


    //Pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet,parent,false);

        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        holder.tvUsername.setText(tweet.user.name+"@"+tweet.user.screenName);
        holder.tvBody.setText(tweet.body);
        holder.tvAgo.setText(tweet.createdAtAgo);

        //put screenName to ivProfileName;

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);

        holder.ivProfileImage.setTag(tweet.user.screenName);

        holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "I am clicked:"+ v.getTag());
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("screen_name",(String)v.getTag());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //For each row, inflat the layout and cache references into View Holder


    //Bind the values

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvAgo;

        public ViewHolder(View itemView){
            super(itemView);

            ivProfileImage = (ImageView)itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView)itemView.findViewById(R.id.tvUserName);
            tvBody =(TextView)itemView.findViewById(R.id.tvBody);
            tvAgo =(TextView)itemView.findViewById(R.id.tvAgo);




        }
    }
}
