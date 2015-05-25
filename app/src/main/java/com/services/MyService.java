package com.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.player.MainActivity;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intentActivity = new Intent(getApplicationContext(), MainActivity.class);
        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intentActivity);
        return START_NOT_STICKY;
    }
}
