package com.Tarefas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.banco.BancoDAO;

public class TaskComerciaisDeterminados implements Runnable {

    private BancoDAO bancoDAO;
    private Context context;

    public TaskComerciaisDeterminados(Context context) {
        this.bancoDAO = new BancoDAO(context);
        this.context = context;
    }


    @Override
    public void run() {
        bancoDAO.comerciaisDeterminados();
        bancoDAO.controladorComercialDependencia();
        bancoDAO.criarPlaylistDeterminados();
        bancoDAO.close();
    }
}
