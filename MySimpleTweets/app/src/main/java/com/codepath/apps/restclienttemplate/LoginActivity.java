package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActionBarActivity;

import java.io.IOException;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		//Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
		 Intent i = new Intent(this, TimelineActivity.class);
		 startActivity(i);

	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();

		if (!isNetworkAvailable()){
			Toast.makeText(this, "Network is not available, please retry later", Toast.LENGTH_SHORT).show();
		}

		if (!isOnline()){
			Toast.makeText(this,"Your device is not online, please retry later",Toast.LENGTH_LONG).show();
		}

	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}


	private Boolean isNetworkAvailable(){
		ConnectivityManager connectivityManager
				= (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo!=null && activeNetworkInfo.isConnectedOrConnecting();
	}

	public boolean isOnline(){
		Runtime runtime =Runtime.getRuntime();
		try{
			Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
			int exitValue = ipProcess.waitFor();
			return (exitValue==0);
		}catch(IOException e){e.printStackTrace();}
		catch(InterruptedException e){e.printStackTrace();};
			return false;
	}

}
