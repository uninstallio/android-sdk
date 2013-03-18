package com.locuslabs.notiphisample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.notikum.notifypassive.NotiphiSession;

public class MainActivity extends Activity {
	
	private SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPrefs = getSharedPreferences("test", Context.MODE_PRIVATE);

		Boolean firstRun = mPrefs.getBoolean("first_run", true);
		if (firstRun) {
			SharedPreferences.Editor edit = mPrefs.edit();
			edit.putBoolean("first_run", false);
			edit.commit();
			Log.e("Test", "FirstRun");
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setTitle("Display Notifications?");
			dialogBuilder
					.setMessage("This application can send you notifications on the best deals in Singapore.Do you wish to enable this feature?");
			dialogBuilder.setPositiveButton("Yes", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences prefs = getSharedPreferences("test",
							Context.MODE_PRIVATE);
					SharedPreferences.Editor prefsEditor = prefs.edit();
					prefsEditor.putBoolean("notiphi_enabled", true);
					prefsEditor.commit();
					startNotify();
				}
			});

			dialogBuilder.setNegativeButton("No", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences prefs = getSharedPreferences("test",
							Context.MODE_PRIVATE);
					SharedPreferences.Editor prefsEditor = prefs.edit();
					prefsEditor.putBoolean("notiphi_enabled", false);
					prefsEditor.commit();

					NotiphiSession.doNotEnroll(MainActivity.this);
				}
			});
			AlertDialog alertDialog = dialogBuilder.create();
			alertDialog.show();
		}

		startNotify();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void startNotify() {
		Boolean notiphiEnabled = mPrefs.getBoolean("notiphi_enabled", true);
		if (notiphiEnabled) {
			NotiphiSession.init(MainActivity.this);
			Toast.makeText(this, "Notify Started", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Disabled Notify", Toast.LENGTH_SHORT).show();

		}
	}

}
