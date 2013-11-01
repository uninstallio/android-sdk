package com.locuslabs.notiphisample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.notikum.notifypassive.NotiphiSession;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
			NotiphiSession.init(MainActivity.this,1);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
