Notiphi Android SDK
===================

Notiphi Android SDK allows you to monetise your Android app (2.3.3 and above) by receiving contextual notifications. 
This guide will help you to integrate in a few minutes.

Please follow the following steps in order to integrate the Notiphi Android SDK into your android app:

### Steps to integrate the sdk to your Android project.

Clone this repository or download the zipped package.

Add the downloaded jar file to your project path. If you are using Eclipse then you could use these following steps if you are unfamiliar with the process of adding jar files.

The following instructions are for adding JAR files to your project.

Create new folder in your project called libs and move the Notify jar file, android-async-http-1.4.2.jar, gcm.jar , android-support-v4.jar into this folder.
Select your project
Right Click -> Select Properties
Choose Java Build Path
Select libraries tab
Choose Add JARS and browse to the notify jar file which is in libs folder into your project. In a similar way add jars async-http-1.4.2.jar, gcm.jar to the project path.

After adding the JAR’s into your project, modify your AndroidManifest.xml file using these steps:

1. Android Version: Set the minimum android SDK version to 10 (Android 2.3.3) or higher. Notiphi library will not work if minimum android SDK version is less than 10.

```
<uses-sdk android:minSdkVersion="10" />
```

2. Permissions: Following permission are required in manifest file for library to work properly. So please declare the following permission in AndroidManifest.xml and replace the occurrence of YOUR_PACKAGE_NAME by your application's package name.

```
<permission android:name="YOUR_PACKAGE_NAME.permission.C2D_MESSAGE" android:protectionLevel="signature" />
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
```

3. Notiphi Service and Receivers: Please add the following xml fragment into AndroidManifest.xml under <application> tag and replace YOUR_PACKAGE_NAME with your application’s package name

```
<receiver android:name="com.notikum.notifypassive.receivers.LocationAlertReceiver"></receiver>
<receiver android:name="com.notikum.notifypassive.receivers.BootCompleteReceiver">
    <intent-filter>
    		<action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>
<receiver android:name="com.notikum.notifypassive.services.NotiphiGCMMessageReceiver" android:permission="com.google.android.c2dm.permission.SEND">
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
```
	 	 	 	
Edit the MainActivity, In your main Activity of your application  add this import statement

```
import com.notikum.notifypassive.NotiphiSession;
```	 	 	 	

Inside the onCreate method of your Main Activity, add the following lines of code.

```
Context context = this;			
NotiphiSession.init(context);
```

Edit the string.xml: Go to your project's root folder and open res folder. Then open values folder. Here you should find strings.xml file. Add the following line to it.

```
<string name="notiphi_app_token">TOKEN_GIVEN_BY_NOTIPHI_SEPARATELY</string>
<string name="notiphi_app_secret">TOKEN_GIVEN_BY_NOTIPHI_SEPARATELY</string>
```

Set the Icons:  We provide you with a resource file (of our logo) named notiphi_notification_icon.png. Appropriate sized versions should be copied to drawable, drawable-hdpi,drawable-mdpi and drawable-xhdpi folder under your project’s res directory.

### Authors and Contributors
This library owes its existence to the hard work of Arjun (@defunkt), Nagendra (@pjhyett), and Abhijith (@mojombo).

### Support or Contact
Having trouble with integration? Please contact us at adv-support@notiphi.com and we’ll help you sort it out in a jiffy.
