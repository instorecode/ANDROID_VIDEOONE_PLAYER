package com.player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.Tarefas.TaskComerciaisDeterminados;
import com.Tarefas.TaskCriarViewExcluirInvalidos;
import com.Tarefas.TaskDiretorios;
import com.Tarefas.TaskLerProperties;
import com.Tarefas.TaskPlayer;
import com.Tarefas.TaskPlayerComericiaisDeterminados;
import com.Tarefas.TaskVideoAndComerciais;

public class MainActivity extends Activity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        context = getApplicationContext();

        ScheduledExecutorService thread1 = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService thread2 = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService thread3 = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService thread4 = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService thread5 = Executors.newScheduledThreadPool(1);
        thread1.scheduleAtFixedRate(new TaskLerProperties(context), 0, 10, TimeUnit.SECONDS);
        thread2.scheduleAtFixedRate(new TaskDiretorios(), 500, 10000, TimeUnit.MILLISECONDS);
        thread3.scheduleAtFixedRate(new TaskCriarViewExcluirInvalidos(context), 600, 3600000, TimeUnit.MILLISECONDS);
       // thread4.scheduleAtFixedRate(new TaskComerciaisDeterminados(context), 700, 30000, TimeUnit.MILLISECONDS);
        thread5.scheduleAtFixedRate(new TaskVideoAndComerciais(context), 700, 30000, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Handler handlerNormal = new Handler();
        TaskPlayer taskPlayer = new TaskPlayer(this, handlerNormal,getApplicationContext());
        //handlerNormal.post(new TaskPlayer(this, handlerNormal,getApplicationContext()));
        handlerNormal.post(taskPlayer);

        final Handler handlerDeterminados = new Handler();
        handlerDeterminados.post(new TaskPlayerComericiaisDeterminados(this,handlerDeterminados, getApplicationContext(), taskPlayer));

    }


}




