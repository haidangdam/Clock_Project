package com.example.haidangdam.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by haidangdam on 9/9/16.
 */
public class CreateAlarm extends Activity {
    private static int numID = 1;
    AlarmManager alarmManager;
    Button addButton;
    TimePicker timePicker;
    PendingIntent pendingIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock);
        Button addButton = (Button) findViewById(R.id.clock_add_button);
        TimePicker timePicker = (TimePicker) findViewById(R.id.clock_time_picker);
        final int hour = timePicker.getCurrentHour();
        final int minute = timePicker.getCurrentMinute();
        alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long number = timeToAlarm(hour, minute);
                Intent intentAlarm = new Intent(getApplicationContext(), Alarm.class);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), numID,
                        intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, number, pendingIntent); //add all of this to manifest
            }
        });
    }

    private long timeToAlarm(int hour, int minute) {
        int currentHour = Calendar.HOUR_OF_DAY;
        int currentMinute = Calendar.MINUTE;
        long timeToWakeUp = ((hour - currentHour) * 3600 +
                (minute - currentMinute) * 60) * 1000;
        if (timeToWakeUp < 0) {
            timeToWakeUp += 24 * 3600 * 1000;
        }
        return timeToWakeUp;
    }
}
