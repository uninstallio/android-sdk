package com.locuslabs.notiphisample;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.notikum.notifypassive.NotiphiSession;
import com.notikum.notifypassive.utils.NotiphiEventReceiver;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button)findViewById(R.id.button_click);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					JSONObject json = new JSONObject();
					json.put("name", "Nagendra");
					json.put("location", " BTM layout ");
					new NotiphiEventReceiver(json, getApplicationContext());
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				
			}
		});
		
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
