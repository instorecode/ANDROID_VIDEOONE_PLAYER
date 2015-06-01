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
import com.Tarefas.TaskLerProperties;
import com.Tarefas.TaskPlayer;
import com.Tarefas.TaskPlayerComericiaisDeterminados;
import com.Tarefas.TaskVideoAndComerciais;
import com.utils.RegistrarLog;

public class MainActivity extends Activity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        context = getApplicationContext();

        ScheduledExecutorService lerProperties = Executors.newScheduledThreadPool(1);
        //ScheduledExecutorService criarViewExcluirVencidos = Executors.newScheduledThreadPool(1);
        //ScheduledExecutorService threadComunicacaoNormal = Executors.newScheduledThreadPool(1);
        //ScheduledExecutorService threadComunicacaoEmergencia = Executors.newScheduledThreadPool(1);
        //ScheduledExecutorService criarPlayListDeterminados = Executors.newScheduledThreadPool(1);
        //ScheduledExecutorService criarPlayListNormal = Executors.newScheduledThreadPool(1);

        lerProperties.scheduleAtFixedRate(new TaskLerProperties(context), 0, 10, TimeUnit.SECONDS);
        //criarViewExcluirVencidos.scheduleAtFixedRate(new TaskCriarViewExcluirInvalidos(context), 0, 24, TimeUnit.HOURS);
        //threadComunicacaoNormal.scheduleAtFixedRate(new TarefaComunicao(context,false), 2, 60, TimeUnit.SECONDS);
        //threadComunicacaoEmergencia.scheduleAtFixedRate(new TarefaComunicao(context,true), 3, 1800, TimeUnit.SECONDS);
        //criarPlayListDeterminados.scheduleAtFixedRate(new TaskComerciaisDeterminados(context), 4, 30, TimeUnit.SECONDS);
        //criarPlayListNormal.scheduleAtFixedRate(new TaskVideoAndComerciais(context), 4, 30, TimeUnit.SECONDS);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final Handler handlerNormal = new Handler();
        TaskPlayer taskPlayer = new TaskPlayer(this, handlerNormal, getApplicationContext(),getSystemService(ACTIVITY_SERVICE));
        handlerNormal.post(taskPlayer);
        final Handler handlerDeterminados = new Handler();
        handlerDeterminados.post(new TaskPlayerComericiaisDeterminados(handlerDeterminados, taskPlayer, context));

    }
}




