# shimla-push-notifications-android

### Include the SDK binary

File -> New -> New Module -> Import .JAR/.AAR Package, click Next and provide the location of the SDK AAR file.
Add in the app gradle file ```compile project(':<aar-filename>')```. In our case it would be ```compile project(':app-debug')```

### Connect your device to Infiswift IOT Platform

**First you need to provision your device on the Infiswift Platform. Provide your Android device serial number to the Infiswift team to get this part done. Get your customerId from them.**

To initialize the SDK, you would need to instantiate the ShimlaClient class by passing the customerId. This would get the  credentials for your device and connect it to the Infiswift Platform.
       
        myShimlaClient = new ShimlaClient(customerId);

Wrap this call in a try catch block to catch the DeviceCredsNotFoundException.

### Subscribe to Push Notifications 

        myShimlaClient.subscribeToPushNotifications(channelList);

### Publish messages

        myShimlaClient.publish(channel, message);

Here channel is one of the channels you subscribed for push notifications above.

## Misc
SDK uses the MQTT Paho library, so include this library in the app gradle file
	```compile 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.0.2'```

Import these packages

import com.infiswift.pushnotificationsdk.ShimlaClient;
import com.infiswift.pushnotificationsdk.DeviceCredsNotFoundException;

### Receive Push notifications from FCM

Infiswift Platform uses Firebase to send the push notifications to the mobile clients. To receive the push notifications in  your app, you would need to do the following

1. Include ```compile 'com.google.firebase:firebase-messaging:11.8.0'``` in the app gradle file.
2. Apply the google-services plugin in app gradle file.
     ```apply plugin: 'com.google.gms.google-services'```
3. Add this plugin in the dependencies section in the project gradle file.
     ```classpath 'com.google.gms:google-services:3.2.0'```
4. Add your app to the Firebase console and download the google-services.json. This file contains the info which is used by Firebase to authenticate your app. Add this google-services.json to your app folder (i.e. your application\app folder).
   Make sure the package_name in the google_services.json matches the application id in the app gradle file.
5. Create a class that extends FirebaseMessagingService and override the onMessageReceived method. This method would be invoked when your app receives the notification from Firebase.
    The parameter for this message of type RemoteMessage contains the actual message. getNotification().getBody() of this parameter will give the message.
     You can then use this message content to create the notification popup to show in the app.
6. Add this to your manifest file, where .MyNotificationService is the name of the class you created in the above step.
```html
	<service
            android:name=".MyNotificationService">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT"/>
            </intent-filter>
        </service>
```