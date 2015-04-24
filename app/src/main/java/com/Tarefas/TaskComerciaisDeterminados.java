package com.Tarefas;

import android.content.Context;
import android.util.Log;

import com.banco.BancoDAO;

public class TaskComerciaisDeterminados implements Runnable {

    private BancoDAO bancoDAO;

    public TaskComerciaisDeterminados(Context context) {
        this.bancoDAO = new BancoDAO(context);
    }

    @Override
    public void run() {
        bancoDAO.comerciaisDeterminados();
        bancoDAO.controladorComercialDependencia();
        bancoDAO.criarPlaylistDeterminados();
        bancoDAO.close();
        Log.e("Log", "TaskComerciaisDeterminados");
    }
}
