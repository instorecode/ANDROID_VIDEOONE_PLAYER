package com.Tarefas;

import android.content.Context;

import com.banco.BancoDAO;

public class TaskComerciaisDeterminados implements Runnable {

    private BancoDAO bancoDAO;

    public TaskComerciaisDeterminados(Context context) {
        this.bancoDAO = new BancoDAO(context);
    }

    @Override
    public void run() {
        bancoDAO.comerciaisDeterminados();
        bancoDAO.horariosComercialDeterminado();
        bancoDAO.criarPlaylistDeterminados();
        bancoDAO.close();
    }
}
