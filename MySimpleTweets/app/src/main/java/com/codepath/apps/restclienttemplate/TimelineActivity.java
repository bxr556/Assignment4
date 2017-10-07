package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;

import static com.codepath.apps.restclienttemplate.R.id.miActionProgress;

public class TimelineActivity extends AppCompatActivity {



    public static final int REQUEST_COMPOSE_CODE=20;
    MenuItem miActionProgressItem;
    private ViewPager mviewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose,menu);
        getMenuInflater().inflate(R.menu.menu_timeline,menu);
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        miActionProgressItem = menu.findItem(miActionProgress);
        ProgressBar v =(ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        return super.onPrepareOptionsMenu(menu);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //fragmentTweetsList =(TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);

        ViewPager vpPager =(ViewPager)findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(),this));
        TabLayout tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

    }


    public void onProfileView(MenuItem item) {

        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void showProgressBar(){
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar(){
        miActionProgressItem.setVisible(false);
    }
}
