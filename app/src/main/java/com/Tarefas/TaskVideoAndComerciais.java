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
        bancoDAO.close();
        Toast.makeText(context, "TaskVideoAndComerciais", Toast.LENGTH_LONG).show();
        Log.e("Log", "TaskVideoAndComerciais");
    }
}
