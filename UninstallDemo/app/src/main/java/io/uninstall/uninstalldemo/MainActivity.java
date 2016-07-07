package io.uninstall.uninstalldemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.songline.uninstall.UninstallSession;
import com.songline.uninstall.segmentIO.Properties;
import com.songline.uninstall.segmentIO.Traits;
import com.songline.uninstall.segmentIO.UninstallAnalytics;
import com.songline.uninstall.utils.Constants;

public class MainActivity extends AppCompatActivity {

    Button btn_event_demo_one, btn_event_demo_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UninstallSession.init(MainActivity.this,1);
        btn_event_demo_one = (Button) findViewById(R.id.btn_event_one);
        btn_event_demo_two = (Button) findViewById(R.id.btn_event_two);

        // Send an event using "track" method

        // track a viewed product :
        btn_event_demo_one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UninstallAnalytics.with(MainActivity.this).track("View Product", new Properties().putValue("Shirt", "Shirt_ID"));
            }
        });
        // track an item purchase.
        btn_event_demo_two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UninstallAnalytics.with(MainActivity.this).track("Purchase", new Properties().putValue("Shirt", "Shirt_ID").putRevenue(499.99));
            }
        });


        //Send UserName to Uninstall server

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.UNINSTALL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        boolean isFirstTimeInstall = sharedPreferences.getBoolean("isFirstTimeInstall", true);
        if (isFirstTimeInstall) {

            // send user-id
            UninstallAnalytics.with(MainActivity.this).identify(new Traits().putUsername("USERNAME"));

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstTimeInstall", false);
            editor.commit();
        }


    }
}
