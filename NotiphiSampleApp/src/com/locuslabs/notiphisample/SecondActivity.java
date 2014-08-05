package com.locuslabs.notiphisample;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class SecondActivity extends Activity{

	private static String TAG = SecondActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		String clientData = getIntent().getStringExtra("client_data");
		Log.d(TAG, " Data received through notification = " + getIntent().getStringExtra("client_data"));
		Toast.makeText(getApplicationContext(), clientData, Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
