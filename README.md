[Uninstall Insights](http://uninstall.io) Android SDK
===================

[Uninstall SDK](http://uninstall.io) helps you understand the reasons for your app uninstalls, reduce the uninstall rate using a powerful predictive engine and also get app Re-installs through a unique actionable channel (Android version 4.0 and above). 

This guide will provide you step by step details on how to integrate the SDK in few minutes. Following steps outline the integration process in detail.


### Steps to integrate the sdk to your Android project.

1. [Include sdk in your projects .](#1include-sdk-in-your-projects)
2. [Add Uninstall.io credentials.](#2add-uninstallio-credentials)
3. [Update AndroidManifest.xml file.](#3update-androidmanifestxml-file)
4. [Initialize the SDK.](#4initialize-the-sdk)
5. [User Identify and In-App events](#5user-identify-and-in-app-events)  
6. [Ignore GCM message from Uninstall.io](#6ignore-gcm-message-from-uninstallio)
7. [Uninstall permission requirements](#7uninstall-permission-requirements)

#### 1.Include sdk in your projects.

##### Using Gradle Depencency(recommended)

Add dependencies for Uninstall.io in your app/build.gradle file.

```
dependencies {
  ...
compile 'com.songline.uninstall:app:12.3.+'
  ...
}
```

#### OR

##### Using Jar File (Manually).


[Download the Uninstall.io sdk ](https://github.com/uninstallio/android-sdk/blob/master/jars/Uninstallio_12.3.jar?raw=true)

If you are using Eclipse, then follow the below steps to add the jar file.      
        [How to add a jar file](http://www.wikihow.com/Add-JARs-to-Project-Build-Paths-in-Eclipse-(Java))         
         
If you are using Android Studio, then follow the below steps to add the jar file.        
        [How to add a jar file](http://stackoverflow.com/questions/16608135/android-studio-add-jar-as-library)

#### 2.Add Uninstall.io credentials.

In eclipse, goto project's root folder --> res folder --> values folder --> strings.xml file. Add the following lines in the file.
-  Add Uninstall.io Token and secret to your strings.xml. you can find out your **strings.xml** file in your **values** folder in your project.  Your  Uninstall.io Token and secret are available under Dashboard → Settings

```
<string name="uninstall_token">TOKEN_GIVEN_BY_UNINSTALL_SEPARATELY</string>
<string name="uninstall_secret">APP_SECRET_GIVEN_BY_UNINSTALL_SEPARATELY</string>
```

#### 3.Update AndroidManifest.xml file.

-  Add the following permissions and replace **YOUR_PACKAGE_NAME** with your application's package name. 

```
<permission android:name="YOUR_PACKAGE_NAME.permission.C2D_MESSAGE"
     android:protectionLevel="signature" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<uses-permission android:name="YOUR_PACKAGE_NAME.permission.C2D_MESSAGE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
```
Optional Permissions 
```
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

- Add the following **InstallReferrerReceiver** receiver at top of the AndroidManifest file  between the ```<application></application> ``` tags and other **INSTALL_REFERRER** receivers put below it. we provides a solution that broadcasts **INSTALL_REFERRER**  action to all other receivers automatically.
```
<!- Uninstall.io  provides a solution that broadcasts INSTALL_REFERRER to all other receivers automatically. add the following receiver as the FIRST receiver for INSTALL_REFERRER -->
<receiver
    android:name="com.songline.uninstall.receivers.InstallReferrerReceiver"
    android:exported="true" >
    <intent-filter>
	<action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>
```

Example : If you want to use multiple receivers, the AndroidManifest.xml must appear, as follows:
```
<!—Uninstall.io Install Receiver is first and will broadcast to all receivers placed below it -->

<receiver android:name="com.songline.uninstall.receivers.InstallReferrerReceiver" android:exported="true">
  <intent-filter>
     <action android:name="com.android.vending.INSTALL_REFERRER" />
  </intent-filter>
</receiver>

<!—All other receivers should follow right after --> 

<receiver android:name="com.google.android.apps.analytics.AnalyticsReceiver" android:exported="true">
 <intent-filter>
      <action android:name="com.android.vending.INSTALL_REFERRER" />
 </intent-filter>
</receiver>
<receiver android:name="com.admob.android.ads.analytics.InstallReceiver" android:exported="true">
      <intent-filter>
          <action android:name="com.android.vending.INSTALL_REFERRER" />
      </intent-filter>
</receiver>
```

- Add the following services, receivers and replace **YOUR_PACKAGE_NAME** with your application’s package name  between the ```<application></application>``` tags
```
<receiver
    android:name="com.songline.uninstall.receivers.UninstallGCMReceiver"
    android:permission="com.google.android.c2dm.permission.SEND" >
    <intent-filter>				
	<action android:name="com.google.android.c2dm.intent.RECEIVE" />
	<category android:name="YOUR_PACKAGE_NAME" />
    </intent-filter>
</receiver>

<service android:name="com.songline.uninstall.services.GCMInformService" />
<service android:name="com.songline.uninstall.services.UninstallGCMIntentService" />
<service android:name="com.songline.uninstall.services.UninstallInstanceIdService" android:exported="false">
            <intent-filter>
                <action android:name="com.google.android.gms.iid.InstanceID"/>
            </intent-filter>
        </service>
```
Note : Both, your push notifications and Uninstall.io push configuration shall work swiftly in parallel with no conflicts.

- Google Play Services Library Configuration .      
     -  [Add Google Play Services to Eclipse.](http://hmkcode.com/adding-google-play-services-library-to-your-android-app)  
     -  [Add Google Play Services to Android Studio.](http://developer.android.com/google/play-services/setup.html)

Note:: Google Play Services must be compiled against version 7.0 or above.
	

#### 4.Initialize the sdk.

-  Add the below import statement in your Launcher Activity of your application

```
import com.songline.uninstall.UninstallSession;
```

-  Add the below code inside the onCreate method of your Launcher Activity

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    UninstallSession.init(context, 1);   // context is you activity context.
}

``` 
       
-  Capture email id : If you do not wish to fetch email id using the SDK, please add the below code snippet before the SDK is initialized  -> i.e. before this line ```{ UninstallSession.init(context, 1); }```
```
UninstallSession.fetchEmailId(false);
```
Note : By default, this feature is enabled.

#### 5.User Identify and In-App events.

You could pass various types of information to our backend systems e.g. Email id, your backend system's Userid or In App Events through our SDK's efficient event-capturing ability. 

##### -  Unique User ID and Email ID for your app users - 
Please pass the User ID assigned by your backend system for this user. Also pass the email (if available). This data will be used to synchronize the various ID’s between our and your backend systems and also to take relevant actions. This information has to be passed only once in the lifetime of the app and not everytime. 

Please pass the UserID and Email ID using the sample code shown below: 

```
SharedPreferences sharedPreferences = getSharedPreferences(Constants.UNINSTALL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
boolean isFirstTimeInstall = sharedPreferences.getBoolean("isFirstTimeInstall", true);
if (isFirstTimeInstall) {

   //Send email-id
   UninstallAnalytics.with(context).identify(new Traits().putEmail("YOUR_EMAIL_ID"));    
   //send user-id
   UninstallAnalytics.with(context).identify(new Traits().putUsername("YOUR_USER_ID"));
   
   // NOTE: context is your activity context.
   
Editor editor = sharedPreferences.edit();
editor.putBoolean("isFirstTimeInstall", false);
editor.commit();
}       
```
##### -  In-App Events
An In-App Event is an action a user takes in your application. we records the event using an Event Name and additional paramaeter you can passed with associated key:value-based Event Properties
 You could pass the In App events using the following code snippet. 
 
 	-  An example of passing In-App Events called **Add To Cart** without Properties. 
 		```
 		UninstallAnalytics.with(context).track("Added To Cart");
 		// NOTE: context is your activity context.
 		```
	-  An example of passing In-App Events called **Add To Cart** with Properties
 ```
 UninstallAnalytics.with(context).track("Added To Cart", new Properties().putValue("Shirt", "Shirt_ID"));
 // NOTE: context is your activity context.
```
####6.Ignore GCM message from Uninstall.io    
  
Add below code snippet at beginning of the following functions, Class which handle the GCM messages (Either by Play service or GCM jar respective order) respective order.     
a) onHandleIntent(Intent intent)  
```
@Override
protected void onHandleIntent(Intent intent) {

	 if (intent.getStringExtra("is_notiphi") != null) {
		return;
 	}
 	// Your code 
 }
```
b) onMessage(Context context, Intent intent)
```
@Override
protected void onMessage(Intent intent) {

	 if (intent.getStringExtra("is_notiphi") != null) {
		return;
 	}
 	// Your code 
 }
```
If you’re using GCM for registration then you have to put below snippet code to the receiver which will receive the token.

```
@Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getStringExtra("registration_id").contains("|ID|")) {
      return;
    }
    // Your Code 
  }
```

#### 7.UNINSTALL permission requirements

Our SDK requires the following permissions in order to function correctly. We have outlined the reasons 
why we need each of these permissions. 

#####Must have permissions

<table>
    <tr>
        <td>"YOUR_PACKAGE_NAME.permission.C2D_MESSAGE”
        </td>
        <td>This permission ensures that Our SDK 
            can use GCM facility.
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
        <td>“android.permission.INTERNET"
        </td>
        <td>Internet permission is required to communicate
           with the server.
        </td>
     </tr>
 
      <tr>
        <td>“android.permission.WAKE_LOCK”
        </td>
        <td>Required so the application can keep the processor
            from sleeping when a message is received.
        </td>
     </tr>
</table>

#####Good to have permission (Optional)

<Table>
    <tr>
        <td>"android.permission.ACCESS_COARSE_LOCATION"
        </td>
        <td>Required to access user's location.
        </td>
     </tr>
     <tr>
        <td>"android.permission.READ_PHONE_STATE"
        </td>
        <td>Required to get details about phone and sim network. eg.IMEI, Phone type, sim operator,  etc..
        </td>
     </tr>
      <tr>
        <td>“android.permission.ACCESS_NETWORK_STATE”
        </td>
        <td>Network state permission to detect network status.
        </td>
     </tr>
    <tr>
        <td>"android.permission.ACCESS_WIFI_STATE"
        </td>
        <td>Required to access user's basic wifi data.
        </td>
     </tr>
     <tr>
        <td>"android.permission.GET_ACCOUNTS"
        </td>
        <td>GCM requires a Google account if the device is 
            running a version lower than Android 4.0.4.
        </td>
     </tr>
</Table>


#### Support or Contact

Having trouble with integration? Please contact us with name & email to sdk_integration@uninstall.io and we’ll help you sort it out in a jiffy.

