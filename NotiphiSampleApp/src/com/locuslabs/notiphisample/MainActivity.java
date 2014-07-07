package com.locuslabs.notiphisample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.notikum.notifypassive.NotiphiSession;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button helloButton = (Button)findViewById(R.id.button_click);
		Button showMeWebView = (Button)findViewById(R.id.show_me_web_view);
		
		try{ 
			NotiphiSession.init(MainActivity.this,1);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		

		helloButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					Intent actIntent = new Intent(MainActivity.this, NotificationCenter.class);
					startActivity(actIntent);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		showMeWebView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent showMeWebView = new Intent(MainActivity.this, NotificationCenterWebView.class);
				startActivity(showMeWebView);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
