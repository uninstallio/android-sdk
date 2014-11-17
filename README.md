[Uninstall Insights](http://uninstall.io) Android SDK
===================

[Uninstall SDK (http://uninstall.io)](http://uninstall.io) helps you to understand the reasons for your app uninstalls, reduce the uninstall rate using a powerful predictive engine and also get app Re-installs through a unique actionable channel (2.3.3 and above). 

This guide will provide you step by step details on how to integrate the SDK in just a few minutes. Following steps outline the integration process in details.

### Steps to integrate the sdk to your Android project.

1. Setup - Clone the github repository (add url) or download and unzip the zip file (all url).
2. Add SDK jar files to libs folder.
3. Configure SDK settings in the Your project's AndroidManifest.xml file.
4. Set the SDK “Token and Secret” in Your project's string.xml file.
5. Initialize the SDK - In the main activity class.
6. Passing Information to SDK.      
    a) Unique System User ID and Email ID.      
    b) Install Source.     
    c) App Events.      
    d) Crash Events.     

####Setup

Clone this repository

```
git clone https://github.com/alokmishra/notiphi-android-sdk.git
```

or download the zipped package.

```
https://github.com/alokmishra/notiphi-android-sdk/archive/master.zip
```

Unzip the files (if downloaded as a zip). Add the files (names) in jars directory to your project path. If you
are using Eclipse then you could use the following steps if you are unfamiliar with the process of adding jar files.

1. Select your project
2. Copy all the jar file from jars directory and paste it into libs directory of your project

3. Add "notiphi_app_token" and  notiphi_app_secret provided by us, in string.xml file inside res->values directory of your android project

####Configure SDK settings in the AndriodManifest.xml file.

After adding the JARs into your project, modify your AndroidManifest.xml file using these steps:

1) Android Version: Set the minimum android SDK version to 8  or higher. Notiphi library will not work if minimum android SDK version is less than 10.

```
<uses-sdk android:minSdkVersion="10" />
```
2) Permissions: Following permission are required in manifest file for library to work properly. So please declare the following permission in AndroidManifest.xml and replace the occurrence of **YOUR_PACKAGE_NAME** by your application's package name.

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
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

3) Notiphi Service and Receivers: Please add the following xml fragment into AndroidManifest.xml under <application> tag and replace **YOUR_PACKAGE_NAME** with your application’s package name

```
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
<service android:name="com.notikum.notifypassive.NotiphiClusterSyncIntentService"></service>
<service android:name="com.notikum.notifypassive.services.SendBulkDataIntentService"></service>
```

4) Reference Google Play Services Library:  In eclipse goto File -> New -> Other and from the list select "Android Project from Existing Code" then select androidsdk -> extras -> google ->
	google_play_services -> libproject directory and click Ok .
	
	Now select your project, right click then select properties -> android, click add and select the above library then click ok. 

	
5) meta data for Google play service : Add below meta data tag into your AndroidManifest.xml file inside <application> tag.

```
     <meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version" />
```


###Set the SDK “Token and Secret” in string.xml file.

Go to your project's root folder and open res folder. Then open values folder. Here you should find strings.xml file. Add the following line to it.
The app_token and app_secret is provided by us on registration of your app with us. As of now there is no online process and you need to contact us at dev-support@notiphi.com to get these.

```
<string name="notiphi_app_token">TOKEN_GIVEN_BY_NOTIPHI_SEPARATELY</string>
<string name="notiphi_app_secret">APP_SECRET_GIVEN_BY_NOTIPHI_SEPARATELY</string>
```


###Main Activity

After the configuration changes, in your main Activity of your application  add this import statement

```
import com.notikum.notifypassive.NotiphiSession;
```

Inside the onCreate method of your Main Activity, add the following lines of code.

```
Context context = this;
NotiphiSession.init(context,1);
```


#### Passing Information from App to SDK

##### 1) Business Unique User ID and Email ID: 
Pass the Unique User ID assigned by your backend system to our SDK. Also pass the email (if available) to our SDK. This data will be used to synchronize the ID’s between our systems and also to take relevant actions. This information has to be passed only once in the lifetime of the app and not everytime. Please refer code snippet below to do the same. 

```
SharedPreferences sharedPreferences = getSharedPreferences("Notiphi", Context.MODE_PRIVATE);
boolean isFirstTimeInstall = sharedPreferences.getBoolean("isFirstTimeInstall", true);
if (isFirstTimeInstall) {
    try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("USERID", username);
        jsonObject.put("EMAILID", email_id);
        new NotiphiEventReceiver(jsonObject, context);
    } catch (JSONException e) {
        e.printStackTrace();
    }            
Editor editor = sharedPreferences.edit();
editor.putBoolean("isFirstTimeInstall", false);
editor.commit();
}       
```

##### 2) Install Source: 
The Install source needs to be passed to our SDK. This is used to measure the Ad channels (especially InOrganic sources) performance. Information can be passed in two ways:

###### a. Via 3rd party platform:
If you use any third party attribution platform and supports data extraction via API, then send us the API keys and we will directly extract the information from there. Pls check with your product/marketing manager for details on 3rd party platform.

###### b. Via the App:
In case you do not use any 3rd party platform or the platform doesn’t support any API then pass the data to our SDK via our event capturing feature This information has to be passed only once in the lifetime of the app during installation and not everytime.
Help code snippet below.

```
SharedPreferences sharedPreferences = getSharedPreferences("Notiphi", Context.MODE_PRIVATE);
boolean isFirstSourceData = sharedPreferences.getBoolean("isFirstSourceData", true);
if (isFirstSourceData) {
    try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("SOURCEDATA", install_source);  //provided by the app-store
        jsonObject.put("SOURCEDATA", null); // via your app.
        new NotiphiEventReceiver(jsonObject, context);
    } catch (JSONException e) {
        e.printStackTrace();
    }            
}       
Editor editor = sharedPreferences.edit();
editor.putBoolean("isFirstSourceData", false);
editor.commit();
```

##### 3) App Events:
All app events have to be passed to our SDK. Information can be passed in two ways:

###### a. Via 3rd party platform:
If you use any third party analytics platform and supports data extraction via API, then send us the API keys and we will directly extract the information from there. Pls check with your product/marketing manager for details on 3rd party platform.

###### b. Via the App:
In case you do not use any 3rd party platform or the platform doesn’t support any API then pass the data to our SDK via our event capturing feature.
Help code snippet below.

```
JSONObject jsonObject = new JSONObject();
jsonObject.put(key, value);
jsonObject.put(key, value);
```
Once  json object is packed with data , call the below method and pass json obejct and context of the application
```
new NotiphiEventReceiver(jsonObject, context);
```

##### 4) Crash Events:
Send the API keys of the crash reporting platform to us. We will extract the information using their API. In case you are reporting the crash manually, then pass the information to our SDK as well. 
Help code snippet below.

```
SharedPreferences sharedPreferences = getSharedPreferences("Notiphi", Context.MODE_PRIVATE);
boolean isFirstReportData = sharedPreferences.getBoolean("isFirstReportData", true);
if (isFirstReportData) {
    try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CRASH_REPORT_ID", crash_report_id); 
        new NotiphiEventReceiver(jsonObject, context);
    } catch (JSONException e) {
        e.printStackTrace();
    }            
}       
Editor editor = sharedPreferences.edit();
editor.putBoolean("isFirstReportData", false);
editor.commit();
```

You are done with event capture implementation, now events from your app will be captured.

#### Working with your own GCM Push notifications. 

If you are already sending your own push notifications then slight more configuration is required. Please add the following line to string.xml file of your project

```
<string name="vendor_gcm_sender_id" translatable="false">YOUR GCM SENDER ID </string>
```

#### Authors and Contributors

This library owes its existence to the hard work of @Notiphi Team.

#### Support or Contact

Having trouble with integration? Please contact us at dev-support@notiphi.com and we’ll help you sort it out in a jiffy.
	
Add the permissions explanation.
