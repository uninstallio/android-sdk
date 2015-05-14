[Uninstall Insights](http://uninstall.io) Android SDK
===================

[Uninstall SDK](http://uninstall.io) helps you understand the reasons for your app uninstalls, reduce the uninstall rate using a powerful predictive engine and also get app Re-installs through a unique actionable channel (Android version 2.3.3 and above). 

This guide will provide you step by step details on how to integrate the SDK in few minutes. Following steps outline the integration process in detail.


### Steps to integrate the sdk to your Android project.

1. [Clone the github repository or download the zipped file\.](#clone-the-github-repository-or-download-the-zipped-file)
2. [Add SDK jar files to libs folder\.](#add-sdk-jar-files-to-libs-folder)
3. [Set the SDK “Token and Secret” in your project's string.xml file.](#set-the-sdk-token-and-secret-in-your-projects-stringxml-file)
4. [Configure SDK settings in the your project's AndroidManifest.xml file.](#configure-sdk-settings-in-the-your-projects-androidmanifestxml-file)
5. [Initialize the SDK in the MainActivity class.](#initialize-the-sdk-in-the-mainactivity-class)
6. [Pass information to SDK from the App.](#passing-information-to-sdk-from-the-app)               
         

[Uninstall permission requirements](#uninstall-permission-requirements)

####Clone the github repository or download the zipped file.

Clone the github repository

```
git clone https://github.com/uninstallio/android-sdk.git
```

or download the zipped file.

```
https://github.com/uninstallio/android-sdk/archive/master.zip
```

Unzip the "android-sdk-master.zip" file. 


#### Add UninstallIO_10.*.jar SDK file to project.

If you are using Eclipse, then follow the below steps to add the jar file.      
        [How to add a jar file](http://www.wikihow.com/Add-JARs-to-Project-Build-Paths-in-Eclipse-(Java))         
         
If you are using Android Studio, then follow the below steps to add the jar file.        
        [How to add a jar file](http://stackoverflow.com/questions/16608135/android-studio-add-jar-as-library)

####Set the SDK “Token and Secret” in your project's string.xml file.

In eclipse, goto project's root folder --> res folder --> values folder --> strings.xml file. Add the following lines in the file.

```
<string name="notiphi_app_token">TOKEN_GIVEN_BY_UNINSTALL_SEPARATELY</string>
<string name="notiphi_app_secret">APP_SECRET_GIVEN_BY_UNINSTALL_SEPARATELY</string>
```
Note: If you do not have the token and secret then please drop a mail with name and email to android-dev-support@uninstall.io to get these credentials for your app. 

####Push Notification Configuration 
If you are using your own or a third party push notification facility for your app, then 

a) Add the following line to string.xml file of your project

```
<string name="vendor_gcm_sender_id" translatable="false">YOUR_GCM_SENDER_ID </string>
```
You can get this from your Google Console or your third party push provider.

b) Change your GCM registration related function call in your java file as mentioned below.      
* GCM using gcm.jar
```
GCMRegistrar.register(context, YOUR_GCM_SENDER_ID + "," + Constants.GCM_SENDER_ID);
```        
* GCM using Google Play Service.
```
gcm.register(YOUR_GCM_SENDER_ID+","+Constants.GCM_SENDER_ID);
```
c) Add the following code snippet to ignore GCM message from Uninstall.

```
 if (mIntent.getStringExtra("is_notiphi") != null) {
	return;
 }
```

####Configure SDK settings in the Your project's AndroidManifest.xml file.

After adding the JAR into your project, modify your AndroidManifest.xml file as mentioned below:

1) Android Version: Set the minimum android SDK version to 10 or higher. 

```
<uses-sdk android:minSdkVersion="10" />
```
2) Permissions: Add the following permissions in the file and replace **YOUR_PACKAGE_NAME** with your application's package name. 

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
<uses-permission android:name="android.permission.WAKE_LOCK" />
```


3) Uninstall Service and Receivers: Add the following xml code inside "application" tag and replace **YOUR_PACKAGE_NAME** with your application’s package name

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

<service android:name="com.notikum.notifypassive.services.GCMInformService" />
<service android:name="com.notikum.notifypassive.services.NotiphiGCMIntentService" />
<service android:name="com.notikum.notifypassive.services.NotificationInformService" />
```

4) Google Play Services Library Configuration .      
     a) [Add Google Play Services to Eclipse.](http://hmkcode.com/adding-google-play-services-library-to-your-android-app)   
     b) [Add Google Play Services to Android Studio.](http://developer.android.com/google/play-services/setup.html)

Note:: Google Play Services must be compiled against version 6.5 or above.
	
5) Add Meta data for Google play service : Add below meta data tag into your AndroidManifest.xml file inside the "application" tag.

```
<meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version" />
```

####Initialize the SDK In the MainActivity class.

a) Add the below import statement in your Launcher Activity of your application

```
import com.notikum.notifypassive.UninstallSession;
```

b) Add the below code inside the onCreate method of your Launcher Activity

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

#### Passing information to SDK from the App.

You could pass various types of information to our backend systems e.g. Email id, your backend system's Userid or In App Events through our SDK's efficient event-capturing ability. 

##### 1) Unique System User ID and Email ID - 
Please pass the User ID assigned by your backend system for this user. Also pass the email (if available). This data will be used to synchronize the various ID’s between our and your backend systems and also to take relevant actions. This information has to be passed only once in the lifetime of the app and not everytime. 

Please pass the UserID and Email using the sample code shown below: 

```
SharedPreferences sharedPreferences = getSharedPreferences("Constants.NOTIPHI_SHARED_PREFERENCES", Context.MODE_PRIVATE);
boolean isFirstTimeInstall = sharedPreferences.getBoolean("isFirstTimeInstall", true);
if (isFirstTimeInstall) {

   //Send email-id
   UninstallAnalytics.with(MainActivity.this).identify(new Traits().putEmail("YOUR_EMAIL_ID"));    
   //send user-id
   UninstallAnalytics.with(MainActivity.this).identify(new Traits().putUsername("YOUR_USER_ID"));
   
Editor editor = sharedPreferences.edit();
editor.putBoolean("isFirstTimeInstall", false);
editor.commit();
}       
```


##### 2) In-App Events - 
 You could pass the In App events using the following code snippet. 

 ```
 UninstallAnalytics.with(MainActivity.this).track("Viewed Product", new Properties().putValue("Shirt", "Shirt_ID"));
```
Note :: Send events only using the "track" method.


####UNINSTALL permission requirements

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
        <td>Network state permission to detect network status.
        </td>
     </tr>
     
     <tr>
        <td>"android.permission.READ_PHONE_STATE"
        </td>
        <td>Required to get DeviceId of phone.
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
        <td>Required to access your location.
        </td>
     </tr>
</Table>


#### Support or Contact

Having trouble with integration? Please contact us with name & email to android-dev-support@uninstall.io and we’ll help you sort it out in a jiffy.

