package com.Tarefas;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.br.instore.utils.ConfiguaracaoUtils;
import com.utils.AndroidImprimirUtils;
import com.utils.RegistrarLog;

import java.io.File;
import java.util.Observable;

public class TaskLerProperties implements Runnable {

    private final String barraDoSistema = System.getProperty("file.separator");
    private final String caminho = Environment.getExternalStorageDirectory().toString();
    private Context context;



    public TaskLerProperties(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        ConfiguaracaoUtils.lerProperties(caminho.concat(barraDoSistema).concat("videoOne").concat(barraDoSistema).concat("config").concat(barraDoSistema).concat("configuracoes.properties"));
        RegistrarLog.getInstance(context);
        RegistrarLog.imprimirMsg("Log", "TaskLerProperties");
    }
}
