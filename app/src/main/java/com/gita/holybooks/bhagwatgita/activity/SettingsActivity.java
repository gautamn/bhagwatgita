package com.gita.holybooks.bhagwatgita.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.service.NotificationService;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchCompat=(SwitchCompat)findViewById(R.id.notifications);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Snackbar.make(buttonView, "Switch state checked "+isChecked, Snackbar.LENGTH_LONG)
                        .setAction("ACTION",null).show();

                if(isChecked){
                    startService(new Intent(SettingsActivity.this, NotificationService.class));
                }else{
                    stopService(new Intent(SettingsActivity.this, NotificationService.class));
                }


            }
        });
    }



}
