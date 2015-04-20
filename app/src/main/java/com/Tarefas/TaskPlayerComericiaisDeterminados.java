package com.Tarefas;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.VideoView;
import com.banco.BancoDAO;
import com.br.instore.utils.ConfiguaracaoUtils;
import com.br.instore.utils.ImprimirUtils;
import com.player.MainActivity;
import com.utils.RegistrarLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskPlayerComericiaisDeterminados implements Runnable {


    private Handler handler;

    private List<String> playlistDeterminado = new ArrayList<String>();
    private List<String> comercialATocarAgora = new ArrayList<String>();
    private List<String> listaAux = new ArrayList<String>();
    private final String barraDoSistema = System.getProperty("file.separator");
    private final String caminho = Environment.getExternalStorageDirectory().toString();
    private TaskPlayer taskPlayer;


    public TaskPlayerComericiaisDeterminados(Handler handler, TaskPlayer taskPlayer) {
        this.handler = handler;
        this.taskPlayer = taskPlayer;
    }

    @Override
    public void run() {
        Log.e("Log", "Rodando THREAD DETERMINADO");
        controlador();
    }

    private void controlador() {
        List<String> listaDeterminados = new ArrayList<String>();
        lerLinha();
        capturarVideoATOcar(playlistDeterminado);
        validarSeOComercialInterrompe(comercialATocarAgora);

        if (listaAux != null && !listaAux.isEmpty()) {
            for(String line : listaAux){
                listaDeterminados.add(line);
            }
            listaAux.clear();
        }

        if(taskPlayer.getPlaylist() != null && !taskPlayer.getPlaylist().isEmpty()){
            for(String line: taskPlayer.getPlaylist()){
                listaDeterminados.add(line);
            }
        }

        if (listaDeterminados != null && !listaDeterminados.isEmpty()) {
            Log.e("Log", "run TaskPlayerComericiaisDeterminados listaDeterminados SIZE = " + listaDeterminados.size());
            taskPlayer.getPlaylist().clear();
            for (String line : listaDeterminados) {
                if (!taskPlayer.getPlaylist().contains(line)) {
                    taskPlayer.setPlaylist(line);
                }
            }
            listaDeterminados.clear();
            handler.postDelayed(this, 60000);
        } else {
            handler.postDelayed(this, 10000);
        }

        if (null != taskPlayer.getPlaylist() && !taskPlayer.getPlaylist().isEmpty()) {
            Log.e("Log", "run TaskPlayerComericiaisDeterminados playlist SIZE = " + taskPlayer.getPlaylist().size());
        }
        listaDeterminados.clear();
    }

    private void validarSeOComercialInterrompe(List<String> listaDeComerciaisNoHorario) {
        if (listaDeComerciaisNoHorario != null && !listaDeComerciaisNoHorario.isEmpty()) {
            for (String comercial : listaDeComerciaisNoHorario) {
                if (!listaAux.contains(comercial)) {
                    String comercialInterrompe = comercial.split("\\|")[2];
                    if (comercialInterrompe.contains("0") || comercialInterrompe.equals("0")) {
                        listaAux.add(comercial);
                    } else {
                    /* TODO
                        Aqui é quando o determinado interrompe
                     */
                    }
                }
            }
        }
        comercialATocarAgora.clear();
    }

    private void capturarVideoATOcar(List<String> listaDeLinhas) {
        if (listaDeLinhas != null && !listaDeLinhas.isEmpty()) {
            for (String comercialDeterminado : listaDeLinhas) {
                String horarioQueDeteTocar = comercialDeterminado.split("\\|")[1];
                String horaAtual = new SimpleDateFormat("HH:mm").format(new Date());
                if (horarioQueDeteTocar.equals(horaAtual) || horarioQueDeteTocar.contains(horaAtual)) {
                    if (!comercialATocarAgora.contains(comercialDeterminado)) {
                        comercialATocarAgora.add(comercialDeterminado);
                    }
                }
            }
        }
        listaDeLinhas.clear();
    }

    private void lerLinha() {
        String caminnhoPlaylistDet = caminho.concat(barraDoSistema).concat(ConfiguaracaoUtils.diretorio.getDiretorioPlaylist()).concat(barraDoSistema).concat("playlistDet.exp");
        File arquivoPlaylistDet = new File(caminnhoPlaylistDet);
        if (arquivoPlaylistDet.exists()) {
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(arquivoPlaylistDet);
            } catch (FileNotFoundException e) {
                ImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e);
            }
            BufferedReader bf = new BufferedReader(fileReader);
            String line = "";
            try {
                while (null != (line = bf.readLine())) {
                    if (line.contains("semVideo") || line.equals("semVideo")) {
                        playlistDeterminado.clear();
                        break;
                    } else {
                        playlistDeterminado.add(line);
                    }
                }
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e);
            } finally {
                try {
                    bf.close();
                } catch (IOException e) {
                    ImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e);
                }
                try {
                    fileReader.close();
                } catch (IOException e) {
                    ImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e);
                }
            }
        }
    }
}
