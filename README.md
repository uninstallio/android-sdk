[Notiphi](http://www.notiphi.com) Android SDK
===================

[Notiphi SDK (www.notiphi.com)](http://www.notiphi.com) allows you to monetise your Android apps (2.3.3 and above) by receiving contextual notifications. This guide will help you integrate it in a few minutes. Following steps outline the integration process.

### Steps to integrate the sdk to your Android project.

####Setup

Clone this repository

```
git clone https://github.com/alokmishra/notiphi-android-sdk.git
```

or download the zipped package.

```
wget https://github.com/alokmishra/notiphi-android-sdk/archive/master.zip
```

Unzip the files (if downloaded as a zip) and then add the files in jars directory to your project path. If you
are using Eclipse then you could use the following steps if you are unfamiliar with the process of adding jar files.

1. Select your project
2. Copy all the jar file from jars directory and paste it into libs directory of your project
3. Add "notiphi_app_token" and  notiphi_app_secret provided by us, in string.xml file inside res->values directory of your android project
4. Copy notiphi_notification_icon.png from each directory in Drawables and paste it into respective each drawable directory of your android project
5. Copy all xml files from layout folder and paste it into layout folder of your android project

####Manifest file

After adding the JARs into your project, modify your AndroidManifest.xml file using these steps:

1. Android Version: Set the minimum android SDK version to 8  or higher. Notiphi library will not work if minimum android SDK version is less than 8.

```
<uses-sdk android:minSdkVersion="8" />
```

2. Permissions: Following permission are required in manifest file for library to work properly. So please declare the following permission in AndroidManifest.xml and replace the occurrence of **YOUR_PACKAGE_NAME** by your application's package name.

```
<permission android:name="YOUR_PACKAGE_NAME.permission.C2D_MESSAGE"
     android:protectionLevel="signature" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<uses-permission android:name="YOUR_PACKAGE_NAME.permission.C2D_MESSAGE" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.VIBRATE"/>
<uses-permission android:name="com.google.android.gms.permission.ACTIVITY_RECOGNITION"/>
```

3. Notiphi Service and Receivers: Please add the following xml fragment into AndroidManifest.xml under <application> tag and replace **YOUR_PACKAGE_NAME** with your application’s package name

```
<receiver android:name="com.notikum.notifypassive.receivers.LocationAlertReceiver"
     android:enabled="true"
     android:exported="true">
</receiver>
<receiver android:name="com.notikum.notifypassive.receivers.BootCompleteReceiver">
    <intent-filter>
    		<action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>
<receiver android:name="com.notikum.notifypassive.services.NotiphiGCMMessageReceiver"
    android:permission="com.google.android.c2dm.permission.SEND">
    <intent-filter>
    	<action android:name="com.google.android.c2dm.intent.RECEIVE"/>
     	<action android:name="com.google.android.c2dm.intent.REGISTRATION"/>
    	<category android:name="YOUR_PACKAGE_NAME"/>
    </intent-filter>
</receiver>
<receiver android:name="com.notikum.notifypassive.receivers.NetworkStateChangeReceiver">
    <intent-filter >
       <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
    </intent-filter>
</receiver>

<service android:name="com.notikum.notifypassive.services.GCMIntentService"></service>
<service android:name="com.notikum.notifypassive.services.NotiphiService"></service>
<service android:name="com.notikum.notifypassive.services.GCMInformService"></service>
<service android:name="com.notikum.notifypassive.NewApiActivityRecognization"></service>
<service android:name="com.notikum.notifypassive.NotiphiClusterSyncIntentService"></service>
<service android:name="com.notikum.notifypassive.services.DiscardedNotificationService"></service>
<service android:name="com.notikum.notifypassive.services.NotificationInformService" > </service>
<service android:name="com.notikum.notifypassive.services.SendBulkDataIntentService"></service>
```
4. Reference Google Play Services Library:  In eclipse goto File -> New -> Other and from the list select "Android Project from Existing Code" then select androidsdk -> extras -> google ->
	google_play_services -> libproject directory and click Ok .
	
	Now select your project, right click then select properties -> android, click add and select the above library then click ok. 
####Main Activity

After the configuration changes, in your main Activity of your application  add this import statement

```
import com.notikum.notifypassive.NotiphiSession;
```

Inside the onCreate method of your Main Activity, add the following lines of code.

```
Context context = this;
NotiphiSession.init(context,1);
```

####Resource files

#####string.xml

Go to your project's root folder and open res folder. Then open values folder. Here you should find strings.xml file. Add the following line to it.
The app_token and app_secret is provided by us on registration of your app with us. As of now there is no online process and you need to contact us at dev-support@notiphi.com to get these.

```
<string name="notiphi_app_token">TOKEN_GIVEN_BY_NOTIPHI_SEPARATELY</string>
<string name="notiphi_app_secret">APP_SECRET_GIVEN_BY_NOTIPHI_SEPARATELY</string>
```

#####Set the Icons

We provide you with a resource file (of our logo) named notiphi_notification_icon.png. Appropriate sized versions should be copied to drawable, drawable-hdpi,drawable-mdpi and drawable-xhdpi folder under your project’s res directory.

If there is any doubt, feel free to have a look at the sample apps provided.

#### Capture Event

We provide the way to capture user event from your app so that we can identify potential and interested users for your business 

To capture event create a json object and add information in key and value format 

For Example : 
```
JSONObject jsonObject = new JSONObject();
jsonObject.put(key, value);
jsonObject.put(key, value);
```
Once  json object is packed with data , call the below method and pass json obejct and context of the application
```
new NotiphiEventReceiver(jsonObject, context);
```
Chill out , You are done with event capture implementation, now events from your app will be captured

#### Support to send push message from your server as well as from our server

To send push message from your server just copy and paste the below line into string.xml file of your project

```
<string name="vendor_gcm_sender_id" translatable="false">YOUR GCM SENDER ID </string>
```

If you are also registering for GCM then please pass our GCM sender id while registering for GCM using step below
```
import com.notikum.notifypassive.utils.Constants

GCMRegistrar.register(context, YOUR GCM SENDER ID +","+Constants.GCM_SENDER_ID);
```
and add the below code at top inside onMessage(Context context, Intent intentData) method of class which extends 
GCMBaseIntentService 

```
if(intentData.getStringExtra("is_notiphi") != null){
return;
}

```
#### Handling client payload (configured for a promotion in our dashboard) 

The client payload configured for the promotion in our dashboard is available to you through an intent. This intent is handed over to the Activity class that you have configured for the promotion. 
Following code snippet shows you how to retrieve this payload from the intent.

```
String mClientPayload =  getIntent().getStringExtra("client_data");

```
make sure you have this activty in your AndroidManifest.xml file.

### Authors and Contributors

This library owes its existence to the hard work of Arjun (@arjunrn) , Nagendra (@sanu-nagendra).

### Support or Contact

Having trouble with integration? Please contact us at dev-support@notiphi.com and we’ll help you sort it out in a jiffy.
