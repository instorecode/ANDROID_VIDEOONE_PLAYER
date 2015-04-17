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
import com.player.R;
import com.utils.RegistrarLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by usuario on 16/04/2015.
 */
public class TaskPlayerComericiaisDeterminados implements Runnable {

    private MainActivity main;
    private VideoView videoView;
    private Handler handler;
    private BancoDAO bancoDAO;
    private RegistrarLog registrarLog;
    private Context context;

    private List<String> playlistDeterminado = new ArrayList<String>();
    private List<String> comercialATocarAgora = new ArrayList<String>();

    private List<String> listaAux = new ArrayList<String>();
    private final String barraDoSistema = System.getProperty("file.separator");
    private final String caminho = Environment.getExternalStorageDirectory().toString();

    private boolean semComercial = false;

    public TaskPlayerComericiaisDeterminados (MainActivity mainActivity, Handler handler, Context context) {
        this.main = mainActivity;
        this.handler = handler;
        this.context = context;
        this.registrarLog = new RegistrarLog(context);
    }

    @Override
    public void run() {
        Log.e("Log","Rodando THREAD DETERMINADO");
        lerLinha();
        capturarVideoATOcar();
        if(semComercial) {
            validarSeOComercialInterrompe();
        }
        Log.e("Log", "run TaskPlayer.playlist SIZE = " + TaskPlayer.playlist.size());
        handler.postDelayed(this,15000);
    }

    private void validarSeOComercialInterrompe(){
        listaAux = new ArrayList<String>();
        if(comercialATocarAgora != null && !comercialATocarAgora.isEmpty()) {
            for (String comercial : comercialATocarAgora) {
                Log.e("Log", comercial);
                String comercialInterrompe = comercial.split("\\|")[2];

                if (comercialInterrompe.contains("0") || comercialInterrompe.equals("0")) {
                    listaAux.add(comercial);
                } else {
                    videoView = (VideoView) main.findViewById(R.id.video);
                    if (videoView.isPlaying()) {

                    }
                }
            }
        }
        Log.e("Log", "validarSeOComercialInterrompe LISTA AUX SIZE = " + listaAux.size());
        Log.e("Log", "validarSeOComercialInterrompe TaskPlayer.playlist SIZE = " + TaskPlayer.playlist.size());

        listaAux.addAll(TaskPlayer.playlist);
        TaskPlayer.playlist = listaAux;
        Log.e("Log", "validarSeOComercialInterrompe DEPOIS TaskPlayer.playlist SIZE = " + TaskPlayer.playlist.size());
        comercialATocarAgora.clear();
        listaAux.clear();
        semComercial = false;
    }

    private void capturarVideoATOcar () {
        if (playlistDeterminado != null && !playlistDeterminado.isEmpty()) {
            for (String comercialDeterminado : playlistDeterminado) {
                String horarioQueDeteTocar = comercialDeterminado.split("\\|")[1];
                String horaAtual = new SimpleDateFormat("HH:mm").format(new Date());
                if (horarioQueDeteTocar.equals(horaAtual) || horarioQueDeteTocar.contains(horaAtual)) {
                    comercialATocarAgora.add(comercialDeterminado);
                    semComercial = true;
                }
            }
        }
        playlistDeterminado.clear();
    }

    private void lerLinha(){
        String caminnhoPlaylistDet = caminho.concat(barraDoSistema).concat(ConfiguaracaoUtils.diretorio.getDiretorioPlaylist()).concat(barraDoSistema).concat("playlistDet.exp");
        File arquivoPlaylistDet = new File(caminnhoPlaylistDet);
        if(arquivoPlaylistDet.exists()){
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(arquivoPlaylistDet);
            } catch (FileNotFoundException e) {
                ImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class,e);
            }
            BufferedReader bf = new BufferedReader(fileReader);
            String line = "";
            try {
                while(null != (line = bf.readLine())){
                    if(line.contains("semVideo") || line.equals("semVideo")){
                       playlistDeterminado.clear();
                        break;
                    } else {
                        playlistDeterminado.add(line);
                    }
                }
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class,e);
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
