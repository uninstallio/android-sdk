package com.example.uninstalldemo;



import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.uninstall.UninstallSession;
import com.uninstall.segmentIO.Properties;
import com.uninstall.segmentIO.Traits;
import com.uninstall.segmentIO.UninstallAnalytics;

public class MainActivity extends Activity {

	Button btn_event_demo_one, btn_event_demo_two;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		UninstallSession.fetchRunningApp(true);
		UninstallSession.fetchEmailId(false);
		UninstallSession.init(MainActivity.this, 1);
		

		btn_event_demo_one = (Button) findViewById(R.id.btn_event_one);
		btn_event_demo_two = (Button) findViewById(R.id.btn_event_two);

		// Send an event using "track" method

		// track a viewed product :
		btn_event_demo_one.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UninstallAnalytics.with(MainActivity.this).track("View Product", new Properties().putValue("Shirt", "Shirt_ID"));
			}
		});
		// track an item purchase.
		btn_event_demo_two.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UninstallAnalytics.with(MainActivity.this).track("Purchase", new Properties().putValue("Shirt", "Shirt_ID").putRevenue(499.99));
			}
		});

		
		//Send UserName to Uninstall server
		
		SharedPreferences sharedPreferences = getSharedPreferences("Constants.NOTIPHI_SHARED_PREFERENCES", Context.MODE_PRIVATE);
		boolean isFirstTimeInstall = sharedPreferences.getBoolean("isFirstTimeInstall", true);
		if (isFirstTimeInstall) {
			
			// send user-id
			UninstallAnalytics.with(MainActivity.this).identify(new Traits().putUsername("USER_NAME"));

			Editor editor = sharedPreferences.edit();
			editor.putBoolean("isFirstTimeInstall", false);
			editor.commit();
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		UninstallSession.appFocusChange();
	}
}
