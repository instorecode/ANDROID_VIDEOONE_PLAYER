package com.Tarefas;

import android.util.Log;

import com.br.instore.utils.ImprimirUtils;
import com.player.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by usuario on 02/04/2015.
 */
public class VerificarDeterminados implements Runnable {

    private TaskDiretorios diretorio;
    private MainActivity mainActivity;

    public VerificarDeterminados (MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        while(true) {
            lerLinhas();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                ImprimirUtils.imprimirErro(VerificarDeterminados.this, e);
            }
        }
    }

    public void lerLinhas() {
        Log.e("Log", "Rodando Thread");
        diretorio = new TaskDiretorios();
        File playlistDet = new File(diretorio.diretorioPlaylist.concat("playlistDet.exp"));
        FileReader fileReader = null;

        if (playlistDet.exists()) {
            try {
                fileReader = new FileReader(playlistDet);
            } catch (FileNotFoundException e) {
                ImprimirUtils.imprimirErro(VerificarDeterminados.this, e);
                return;
            }

            BufferedReader bf = new BufferedReader(fileReader);
            String line;

            try {
                while (null != (line = bf.readLine())) {
                    //Log.e("Log" , line);
                    String horarioSistema = new SimpleDateFormat("HH:mm").format(new Date());
                    String horario = line.split("\\|")[0];
                    String interrompe = line.split("\\|")[1];

                    if (null != horario && horario.matches(horarioSistema) && horario.equals(horarioSistema)) {
                        if (null != interrompe && interrompe.equals("1") && interrompe.matches("1")) {

                        } else {

                        }
                    } else {
                        continue;
                    }
                }
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(VerificarDeterminados.this, e);
                return;
            } finally {
                try {
                    bf.close();
                } catch (IOException e) {
                    ImprimirUtils.imprimirErro(VerificarDeterminados.this, e);
                    return;
                }

                try {
                    fileReader.close();
                } catch (IOException e) {
                    ImprimirUtils.imprimirErro(VerificarDeterminados.this, e);
                    return;
                }
            }
        }
    }
}
