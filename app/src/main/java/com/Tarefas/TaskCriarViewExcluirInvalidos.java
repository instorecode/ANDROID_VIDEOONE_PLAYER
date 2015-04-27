package com.Tarefas;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.banco.BancoDAO;

import java.io.File;

public class TaskCriarViewExcluirInvalidos implements Runnable {

    private Context context;
    private BancoDAO bancoDAO;

    public TaskCriarViewExcluirInvalidos(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        bancoDAO = new BancoDAO(context);
        bancoDAO.criarViewComercial();
        bancoDAO.criarViewComercialDetermidos();
        bancoDAO.criarViewProgramacao();
        bancoDAO.criarViewVideo();
        bancoDAO.excluirComercialDoBanco();
        bancoDAO.excluirVideosDoBanco();
        bancoDAO.close();
    }
}
