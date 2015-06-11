package com.player;

import java.util.TimerTask;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.Tarefas.TarefaComunicao;
import com.Tarefas.TaskBackup;
import com.Tarefas.TaskBanco;
import com.Tarefas.TaskLerProperties;
import com.Tarefas.TaskPlayer;
import com.Tarefas.TaskPlayerComericiaisDeterminados;
import com.banco.BancoDAO;
import com.timer.SimpleTimer;

import it.sauronsoftware.ftp4j.FTPClient;

public class MainActivity extends Activity {
    private Context context;
    FTPClient ftp = new FTPClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        context = getApplicationContext();

        BancoDAO.getInstance(context);
        SimpleTimer timer = new SimpleTimer();

        final TaskLerProperties taskLerProperties = new TaskLerProperties(context);
        timer.sched(new TimerTask() {
            @Override
            public void run() {
                taskLerProperties.run();
            }
        }, 0, 10000l);

        final TaskBackup taskBackup = new TaskBackup();
        timer.sched(new TimerTask() {
            @Override
            public void run() {
                taskBackup.run();
            }
        }, 3000l, (8 * (60 * (60 * (1 * 1000)))));

        final TarefaComunicao tarefaComunicao = new TarefaComunicao();
        timer.sched(new TimerTask() {
            @Override
            public void run() {
                if(!ftp.isConnected()){
                    tarefaComunicao.run(false, ftp);
                }
            }
        }, 1000l, 180000l );

        timer.sched(new TimerTask() {
            @Override
            public void run() {
                if (!ftp.isConnected()) {
                    tarefaComunicao.run(true, ftp);
                }
            }
        }, 10000l, (30 * (60 * (1 * 1000))));

        final TaskBanco taskBanco = new TaskBanco();
        timer.sched(new TimerTask() {
            @Override
            public void run() {
                taskBanco.run();
            }
        }, 3000l, 120000);

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




