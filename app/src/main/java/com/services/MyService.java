package com.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.Tarefas.TaskComerciaisDeterminados;
import com.Tarefas.TaskCriarViewExcluirInvalidos;
import com.Tarefas.TaskDiretorios;
import com.Tarefas.TaskLerProperties;
import com.Tarefas.TaskVideoAndComerciais;
import com.player.MainActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by usuario on 06/04/2015.
 */
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
