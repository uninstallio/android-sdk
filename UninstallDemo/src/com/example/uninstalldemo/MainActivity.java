package com.example.uninstalldemo;

import com.notikum.notifypassive.UninstallSession;
import com.notikum.notifypassive.segmentIO.Properties;
import com.notikum.notifypassive.segmentIO.UninstallAnalytics;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btn_event_demo_one, btn_event_demo_two;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UninstallSession.init(MainActivity.this, 1);

		btn_event_demo_one = (Button) findViewById(R.id.btn_event_one);
		btn_event_demo_two = (Button) findViewById(R.id.btn_event_two);

		// Send an event using "track" method

		// track a Product viewed :
		btn_event_demo_one.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UninstallAnalytics.with(MainActivity.this).track("View Product", new Properties().putValue("Shirt", "Shirt_ID"));
			}
		});
		// track a Purchase item.
		btn_event_demo_two.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UninstallAnalytics.with(MainActivity.this).track("Purchase", new Properties().putValue("Shirt", "Shirt_ID").putRevenue(499.99));
			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		UninstallSession.appFocusChange();
	}
}
