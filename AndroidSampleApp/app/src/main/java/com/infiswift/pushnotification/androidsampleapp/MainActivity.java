package com.infiswift.pushnotification.androidsampleapp;

import com.infiswift.pushnotificationsdk.DeviceCredsNotFoundException;
import com.infiswift.pushnotificationsdk.ShimlaClient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ShimlaClient myShimlaClient;
    String channel = "testchannel";
    String messagePrefix = "Testing Push Notifications @ ";
    String customerId = "swiftlab-2cee2c15-b282-4eec-af9b-f4dd80a5cf86";

    List<String> channelList = new ArrayList<String>();

    private static final String TAG = "SampleApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        channelList.add(channel);

        try {

            // Initialize the ShimlaSDK
            myShimlaClient = new ShimlaClient(customerId);

        }
        catch (DeviceCredsNotFoundException e){
            Log.d(TAG, "Device creds could not be retrieved for serial number  " + e);
        }

        Button subscribeButton = findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myShimlaClient.subscribeToPushNotifications(channelList);

                // Log and toast
                String msg = getString(R.string.msg_subscribed);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button publishButton = findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH.mm.ss").format(new Date());
                String message = messagePrefix + timeStamp;

                // Publish message to specific channel
                myShimlaClient.publish(channel, message);

                // Log and toast
                String msg = getString(R.string.msg_published);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button unsubscribeButton = findViewById(R.id.unsubscribeButton);
        unsubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myShimlaClient.unsubscribeFromPushNotifications(channelList);

                // Log and toast
                String msg = getString(R.string.msg_unsubscribed);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
