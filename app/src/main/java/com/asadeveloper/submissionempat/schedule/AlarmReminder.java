package com.asadeveloper.submissionempat.schedule;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.asadeveloper.submissionempat.MainActivity;
import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.Schedule_Setting;

import java.util.Calendar;

public class AlarmReminder extends BroadcastReceiver {
    private final String EXTRA_MESSAGE_PREF = "message";
    private final String EXTRA_TYPE_PREF = "type";
    public final static int NOTIFICATION_ID = 301;


    public AlarmReminder(){

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotif(context, "Buka Aplikasi Movie", EXTRA_MESSAGE_PREF, NOTIFICATION_ID);
    }

    public void setAlarmReminder(Context c,  String type,String pesan){
        alarmCancel(c);
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, AlarmReminder.class);
        intent.putExtra(EXTRA_MESSAGE_PREF, pesan);
        intent.putExtra(EXTRA_TYPE_PREF, type);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, NOTIFICATION_ID, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void alarmCancel(Context c){
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, AlarmReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, NOTIFICATION_ID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private void sendNotif(Context context, String title, String desc, int notifId) {

        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "asadeveloper";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(desc)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

}
