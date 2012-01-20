package com.example.c2dm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.c2dm.C2DMBaseReceiver;
import com.google.android.c2dm.C2DMessaging;

import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:03 PM - 1/19/12
 */
public class C2DMReceiver extends C2DMBaseReceiver {
    private static final String SENDER_ID = ""; // Enter your "c2dm enabled e-mail"
    private static final int NOTIFICATION_ID = 0;

    public C2DMReceiver() {
        super(SENDER_ID);
    }

    public void register(Context context) {
        C2DMessaging.register(context, SENDER_ID);
    }

    public void unRegister(Context context) {
        C2DMessaging.unregister(context);
    }

    @Override
    public void onRegistered(Context context, String registrationId) throws IOException {
        Log.i(C2DMActivity.TAG, "Registration id: " + registrationId);
        buildNotification(context, "Registration id", registrationId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        if (intent.hasExtra("msg")) {
            String msg = intent.getStringExtra("msg");
            Log.i(C2DMActivity.TAG, "Message: " + msg);

            buildNotification(context, "Message received", msg);
        }
    }

    @Override
    public void onError(Context context, String errorMsg) {
        Log.i(C2DMActivity.TAG, "Error: " + errorMsg);
    }

    private void buildNotification(Context context, String title, String content) {
        Intent notificationIntent = new Intent(this, C2DMActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification(R.drawable.icon, title, System.currentTimeMillis());
        notification.setLatestEventInfo(context, title, content, contentIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
