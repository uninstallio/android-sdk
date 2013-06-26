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
2. Right Click -> Select Properties
3. Choose Java Build Path
4. Select libraries tab
5. Choose Add JARS and browse to the jars directory in cloned/unzipped files. Select all the jars one by one.
6. Add "notiphi_app_token" and  notiphi_app_secret provided by us, in string.xml file inside res->values directory of your android project
7. Put notiphi_notification_icon.png in drawable directory of your android project

####Manifest file

After adding the JARs into your project, modify your AndroidManifest.xml file using these steps:

1. Android Version: Set the minimum android SDK version to 10 (Android 2.3.3) or higher. Notiphi library will not work if minimum android SDK version is less than 10.

```
<uses-sdk android:minSdkVersion="10" />
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
```

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

### Authors and Contributors

This library owes its existence to the hard work of Arjun (@arjunrn) , Nagendra (@sanu-nagendra), and Abhijith (@redshift13).

### Support or Contact

Having trouble with integration? Please contact us at dev-support@notiphi.com and we’ll help you sort it out in a jiffy.
