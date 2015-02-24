[Uninstall Insights](http://uninstall.io) Android SDK
===================

[Uninstall SDK (http://uninstall.io)](http://uninstall.io) helps you to understand the reasons for your app uninstalls, reduce the uninstall rate using a powerful predictive engine and also get app Re-installs through a unique actionable channel (2.3.3 and above). 

This guide will provide you step by step details on how to integrate the SDK in just a few minutes. Following steps outline the integration process in details.


### Steps to integrate the sdk to your Android project.

1. [Clone the github repository or download the zipped file\.](#setup)
2. [Add SDK jar files to libs folder\.](#add-sdk-jar-files-to-libs-folder)
3. [Set the SDK “Token and Secret” in Your project's string.xml file.](#set-the-sdk-token-and-secret-in-your-projects-stringxml-file)
4. [Configure SDK settings in the Your project's AndroidManifest.xml file.](#configure-sdk-settings-in-the-your-projects-androidmanifestxml-file)
5. [Initialize the SDK in the MainActivity class.](#initialize-the-sdk-in-the-mainactivity-class)
6. [Passing Information to SDK.](#passing-information-to-sdk) 
    a) [App Events.](#app-events)      
    b) [Crash Events.](#crash-events)     

[Uninstall permission requirements](#uninstall-permission-requirements)

####Setup

Clone the github repository

```
git clone https://github.com/uninstallio/android-sdk.git


```

or download the zipped file.

```
https://github.com/uninstallio/android-sdk/archive/master.zip
```

Unzip the files (if downloaded as a zip). Add the files UninstallIO.jar in jars directory to your project path. If you
are using Eclipse then you could use the following steps if you are unfamiliar with the process of adding jar files.


#### Add SDK jar files to libs folder.

Copy **UninstallIO.jar** jar file from jars directory and paste it into libs directory of your project.


####Set the SDK “Token and Secret” in Your project's string.xml file.

Go to your project's root folder and open res folder. Then open values folder. Here you should find strings.xml file. Add the following line to it.
The app_token and app_secret is provided by us on registration of your app with us. As of now there is no online process and you need to contact us at dev-support@notiphi.com to get these.

```
<string name="notiphi_app_token">TOKEN_GIVEN_BY_UNINSTALL_SEPARATELY</string>
<string name="notiphi_app_secret">APP_SECRET_GIVEN_BY_UNINSTALL_SEPARATELY</string>
```

```
IMPORTANT
```
If you are already sending your own push notifications then slight more configuration is required. Please add the following line to string.xml file of your project

```
<string name="vendor_gcm_sender_id" translatable="false">YOUR_GCM_SENDER_ID </string>
```
Apart from this please change the way you are making the call to register for GCM device tokens in your java file.

```
gcm.register(YOUR_GCM_SENDER_ID+","+Constants.GCM_SENDER_ID);
```

####Configure SDK settings in the Your project's AndroidManifest.xml file.

After adding the JARs into your project, modify your AndroidManifest.xml file using these steps:

1) Android Version: Set the minimum android SDK version to 10  or higher. Uninstall library will not work if minimum android SDK version is less than 10.

```
<uses-sdk android:minSdkVersion="10" />
```
2) Permissions: Following permission are required in manifest file for library to work properly. So please declare the following permission in AndroidManifest.xml and replace the occurrence of **YOUR_PACKAGE_NAME** by your application's package name.

```
<permission android:name="YOUR_PACKAGE_NAME.permission.C2D_MESSAGE"
     android:protectionLevel="signature" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<uses-permission android:name="YOUR_PACKAGE_NAME.permission.C2D_MESSAGE" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

3) Uninstall Service and Receivers: Please add the following xml fragment into AndroidManifest.xml under <application> tag and replace **YOUR_PACKAGE_NAME** with your application’s package name

```
<receiver
    android:name="com.notikum.notifypassive.receivers.NotiphiGCMBroadCastReceiver"
    android:permission="com.google.android.c2dm.permission.SEND" >
    <intent-filter>				
	<action android:name="com.google.android.c2dm.intent.RECEIVE" />
	<category android:name="YOUR_PACKAGE_NAME" />
    </intent-filter>
</receiver>
<receiver android:name="com.notikum.notifypassive.receivers.NetworkStateChangeReceiver">
    <intent-filter >
       <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
    </intent-filter>
</receiver>
<receiver
    android:name="com.notikum.notifypassive.receivers.InstallReferrerReceiver"
    android:exported="true" >
    <intent-filter>
	<action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>

<service android:name="com.notikum.notifypassive.NotiphiGCMIntentService" />
<service android:name="com.notikum.notifypassive.services.GCMIntentService"/>
<service android:name="com.notikum.notifypassive.services.GCMInformService"/>
<service android:name="com.notikum.notifypassive.services.NotificationInformService"/>
```

4) Reference Google Play Services Library:  In eclipse goto File -> New -> Other and from the list select "Android Project from Existing Code" then select androidsdk -> extras -> google ->
	google_play_services -> libproject directory and click Ok .
	
	Now select your project, right click then select properties -> android, click add and select the above library then click ok. 

	
5) meta data for Google play service : Add below meta data tag into your AndroidManifest.xml file inside <application> tag.

```
     <meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version" />
```

####Initialize the SDK In the MainActivity class.

After the configuration changes, in your main Activity of your application  add this import statement

```
import com.notikum.notifypassive.NotiphiSession;
```

Inside the onCreate method of your Main Activity, add the following lines of code.

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Context context = this;
    try {
	 UninstallSession.init(context, 1);
    } catch (Exception e) {
    }
}

@Override
protected void onPause() {
    super.onPause();
    UninstallSession.appFocusChange();
}
    
```


#### Passing Information to SDK.

##### a) App Events:
All app events should be passed to our SDK for analysis and insights.

######  Via the App:
You do not use any 3rd party platform or the platform doesn’t support any API then pass the data to our SDK via our event capturing feature.
Help code snippet below.

Properties are simple key-value pairs that can be anything you want to record, for example:

        Track your event : 
The track method is how you record any actions your users perform.
      
```
 UninstallAnalytics.with(context).track("eventName", new Properties().putValue("IdSync", "ABC1234"));
```
         or
 ```
 UninstallAnalytics.with(context).track("eventName", new Properties().putValue("ActivityScreen", "Login Screen"));
```

    


##### b) Crash Events:
Send the API keys of the crash reporting platform to us. We will extract the information using their API. In case you are reporting the crash manually, then pass the information to our SDK as well. 
Help code snippet below.

```
SharedPreferences sharedPreferences = getSharedPreferences("UNINSTALL", Context.MODE_PRIVATE);
boolean isFirstReportData = sharedPreferences.getBoolean("isFirstReportData", true);
if (isFirstReportData) {
     UninstallAnalytics.with(context).track("crashReport", new Properties().putValue("CRASH_REPORT", "crash_report_id"));

     Editor editor = sharedPreferences.edit();
     editor.putBoolean("isFirstReportData", false);
     editor.commit();   
}       

```

You are done with event capture implementation, now events from your app will be captured.

####UNINSTALL permission requirements

Our SDK requires the following permissions in order to function correctly. We have outlined the reasons 
why we need each of these permissions. 

#####Must have permissions

<table>
    <tr>
        <td>"YOUR_PACKAGE_NAME.permission.C2D_MESSAGE”
            “YOUR_PACKAGE_NAME.permission.C2D_MESSAGE" 
            android:protectionLevel="signature" 
            
        </td>
        <td>These 2 permission ensures that Our SDK 
            can use GCM facilities. Also enforces that only 
            your app can read the messages.
            
        </td>
     </tr>
     
     <tr>
        <td>"com.google.android.c2dm.permission.RECEIVE"
        </td>
        <td>App has permission to register for and receive GCM 
            tokens from GCM server.
        </td>
     </tr>
     
     <tr>
        <td>"android.permission.GET_ACCOUNTS"
        </td>
        <td>GCM requires a Google account if the device is 
            running a version lower than Android 4.0.4.
        </td>
     </tr>
     
     <tr>
        <td>“android.permission.INTERNET"
        </td>
        <td>Internet permission is required to communicate
           with the server.
        </td>
     </tr>
     
     <tr>
        <td>“android.permission.ACCESS_NETWORK_STATE”
        </td>
        <td>Network state permission to detect network status, 
            so we get full access of network.
        </td>
     </tr>
     
     <tr>
        <td>"android.permission.ACCESS_COARSE_LOCATION"
        </td>
        <td>Required to access your location.
        </td>
     </tr>
     
     <tr>
        <td>"android.permission.WRITE_EXTERNAL_STORAGE"
        </td>
        <td>Required to accumulate events.
        </td>
     </tr>
     
     <tr>
        <td>"android.permission.READ_PHONE_STATE"
        </td>
        <td>Required to get DeviceId of phone.
        </td>
     </tr>
</table>



#### Authors and Contributors

This library owes its existence to the hard work of @UNINSTALL Team.

#### Support or Contact

Having trouble with integration? Please contact us at dev-support@notiphi.com and we’ll help you sort it out in a jiffy.
	
Add the permissions explanation.
