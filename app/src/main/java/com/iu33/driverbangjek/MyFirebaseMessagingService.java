package com.iu33.driverbangjek;

import android.app.NotificationManager;
import android.app.Service;
import android.icu.lang.UScript;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by hp on 12/28/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //TODO mengambil Informasi Yang mengirim notif
        String notif = remoteMessage.getFrom();
        Log.d("Notifnya ", remoteMessage.toString());
        buildNotif(notif);
    }

    private void buildNotif(String notif) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.iconapp)
                        .setContentTitle("Test Notification")
                        .setContentText("Hello Ka Aasad!");
        Uri suara = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(suara);
        mBuilder.setVibrate(new long[]{500, 500, 500, 500});
        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
}
