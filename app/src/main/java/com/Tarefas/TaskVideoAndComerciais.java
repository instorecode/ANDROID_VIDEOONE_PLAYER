package com.Tarefas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.banco.BancoDAO;

public class TaskVideoAndComerciais implements Runnable {
    private BancoDAO bancoDAO;
    private Context context;

    public TaskVideoAndComerciais(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        bancoDAO = new BancoDAO(context);
        bancoDAO.programacoes();
        bancoDAO.categorias();
        bancoDAO.criarArquivoPlaylist();
        bancoDAO.close();
        Log.e("Log", "TaskVideoAndComerciais");
    }
}
