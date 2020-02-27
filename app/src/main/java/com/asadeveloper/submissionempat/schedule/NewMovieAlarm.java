package com.asadeveloper.submissionempat.schedule;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.asadeveloper.submissionempat.BuildConfig;
import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.activity.DetailActivity;
import com.asadeveloper.submissionempat.model.Favorite;
import com.asadeveloper.submissionempat.model.MovieItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewMovieAlarm extends BroadcastReceiver {
    private final String EXTRA_MESSAGE_PREF = "message";
    private final String EXTRA_TYPE_PREF = "type";
    int NOTIFICATION_ID , movid;
    public List<MovieItems> itemsList = new ArrayList<>();
    String judul, poster, overview,date;

    public NewMovieAlarm(){

    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        AsyncHttpClient client = new AsyncHttpClient();
        Date local = Calendar.getInstance().getTime();
        String API_KEY = BuildConfig.MovieAppId;
         date= new SimpleDateFormat("yyyy-MM-dd").format(local);
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&primary_release_date.gte="+date+"&primary_release_date.lte="+date;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        judul = movieItems.getOriginal_title();
                        poster = movieItems.getPoster_path();
                        overview = movieItems.getOverview();
                        movid = movieItems.getId();

                        sendNotif(context,judul, overview, movid);
                    }

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }

        });

    }

    public void setAlarmReminder(Context c,  String type,String pesan){
        alarmCancel(c);
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, AlarmReminder.class);
        intent.putExtra(EXTRA_MESSAGE_PREF, pesan);
        intent.putExtra(EXTRA_TYPE_PREF, type);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, NOTIFICATION_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
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
        String Keterangan = "New Release Movie";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        MovieItems mData = new MovieItems(poster,judul,overview,date,"movie", movid);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("EXTRA_FILM", mData);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(Keterangan)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setContentIntent(pendingIntent)
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
