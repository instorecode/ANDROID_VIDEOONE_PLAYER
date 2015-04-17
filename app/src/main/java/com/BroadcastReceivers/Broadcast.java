package com.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.services.MyService;

/**
 * Created by usuario on 06/04/2015.
 */
public class Broadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast OK", Toast.LENGTH_LONG).show();
        Intent intentService = new Intent(context, MyService.class);
        intentService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intentService);
    }
}
