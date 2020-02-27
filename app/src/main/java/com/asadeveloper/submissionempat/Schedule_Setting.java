package com.asadeveloper.submissionempat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.asadeveloper.submissionempat.schedule.AlarmReminder;
import com.asadeveloper.submissionempat.schedule.NewMovieAlarm;

public class Schedule_Setting extends AppCompatActivity implements View.OnClickListener{
    Button startAlarm, stopAlrm, startAlrmRilis,stopAlrmRilis;

    private AlarmReminder alarmReminder;
    private NewMovieAlarm movieRelease;

    String EXTRA_TYPE_PREF = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__setting);

        alarmReminder = new AlarmReminder();
        movieRelease = new NewMovieAlarm();

        startAlarm = findViewById(R.id.btnStartAlarm);
        stopAlrm = findViewById(R.id.btnStopAlarm);
        startAlrmRilis = findViewById(R.id.btnStartRelease);
        stopAlrmRilis = findViewById(R.id.btnStopRelease);

        //alarm jam 7
        startAlarm.setOnClickListener(this);
        stopAlrm.setOnClickListener(this);

        //alarm release film
        startAlrmRilis.setOnClickListener(this);
        stopAlrmRilis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnStartAlarm:

                    String pesan = "Daily Reminder";
                    alarmReminder.setAlarmReminder(Schedule_Setting.this, EXTRA_TYPE_PREF,   pesan);
                    Toast.makeText(this,"Alarm Telah Distel",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnStopAlarm:
                    alarmReminder.alarmCancel(this);
                Toast.makeText(this,"Alarm Telah Distop",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnStartRelease:
                String message = "New Movie Release";
                movieRelease.setAlarmReminder(Schedule_Setting.this, EXTRA_TYPE_PREF,   message);
                Toast.makeText(this,"Notif Movie Telah Distel",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnStopRelease:
                movieRelease.alarmCancel(this);
                Toast.makeText(this,"Notif Release Telah Distop",Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
