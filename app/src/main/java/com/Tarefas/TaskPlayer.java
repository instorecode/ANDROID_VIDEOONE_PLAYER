package com.Tarefas;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class TaskPlayer implements Runnable {

    private MainActivity main;
    private Handler handler;
    private VideoView videoView;
    private BancoDAO bancoDAO;
    private Context context;
    private RegistrarLog registrarLog;
    private File arquivoVideo = null;
    public List<String> playlist = new ArrayList<String>();
    private final String barraDoSistema = System.getProperty("file.separator");
    private final String caminho = Environment.getExternalStorageDirectory().toString();
    private boolean videoVazio = true;
    private int duracao = 60000;

    public TaskPlayer() {
    }

    public TaskPlayer(MainActivity mainActivity, Handler handler, Context context) {
        this.main = mainActivity;
        this.handler = handler;
        this.context = context;
        this.registrarLog = new RegistrarLog(context);
    }

    @Override
    public void run() {
        Log.e("Log", "Rodando a thread do player");
        Log.e("Log", "TaskPlayer playlist SIZE = " + playlist.size());
        if (playlist == null || playlist.isEmpty() || playlist.size()  == 0) {
            Log.e("Log", "1");
            lerLinhas();
            if (videoVazio) {
                Log.e("Log", "2");
                executar(null);
                return;
            } else {
                Log.e("Log", "3");
                String linha = playlist.get(0);
                String flag = linha.split("\\|")[0];
                if (flag.equals("det") || flag.contains("det")) {
                    Log.e("Log", "4");
                    playlist = new ArrayList(playlist.subList(1, playlist.size()));
                    executar(linha);
                    return;
                } else {
                    Log.e("Log", "5");
                    boolean retornoHorarioDaLinha = verificarHorarioProgramacao(linha.split("\\|")[1], linha.split("\\|")[2]);
                    if (retornoHorarioDaLinha) {
                        Log.e("Log", "6");
                        playlist = new ArrayList(playlist.subList(1, playlist.size()));
                        executar(linha);
                        return;
                    } else {
                        Log.e("Log", "7");
                        playlist = new ArrayList(playlist.subList(1, playlist.size()));
                        executar(null);
                        return;
                    }
                }
            }
        } else {
            Log.e("Log", "8");
            String linha = playlist.get(0);
            String flag = linha.split("\\|")[0];
            if (flag.equals("det") || flag.contains("det")) {
                Log.e("Log", "9");
                playlist = new ArrayList(playlist.subList(1, playlist.size()));
                executar(linha);
                return;
            } else {
                Log.e("Log", "10");
                boolean retornoHorarioDaLinha = verificarHorarioProgramacao(linha.split("\\|")[1], linha.split("\\|")[2]);
                if (retornoHorarioDaLinha) {
                    Log.e("Log", "11");
                    playlist = new ArrayList(playlist.subList(1, playlist.size()));
                    executar(linha);
                    return;
                } else {
                    Log.e("Log", "12");
                    playlist = new ArrayList(playlist.subList(1, playlist.size()));
                    executar(null);
                    return;
                }
            }
        }
    }

    private void lerLinhas() {
        String caminhoPlaylist = caminho.concat(barraDoSistema).concat(ConfiguaracaoUtils.diretorio.getDiretorioPlaylist()).concat(barraDoSistema).concat("playlist.exp");
        File arquivoPlaylist = new File(caminhoPlaylist);
        if (arquivoPlaylist.exists()) {
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(arquivoPlaylist);
            } catch (FileNotFoundException e) {
                ImprimirUtils.imprimirErro(TaskPlayer.this, e);
            }
            BufferedReader bf = new BufferedReader(fileReader);
            String line = "";
            try {
                while (null != (line = bf.readLine())) {
                    if (line.contains("semVideo") && line.equals("semVideo")) {
                        videoVazio = true;
                        break;
                    } else {
                        playlist.add(line);
                        videoVazio = false;
                    }
                }
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(TaskPlayer.this, e);
                return;
            } finally {
                try {
                    bf.close();
                } catch (IOException e) {
                    ImprimirUtils.imprimirErro(TaskPlayer.this, e);
                    return;
                }
                try {
                    fileReader.close();
                } catch (IOException e) {
                    ImprimirUtils.imprimirErro(TaskPlayer.this, e);
                    return;
                }
            }
        } else {
            try {
                arquivoPlaylist.createNewFile();
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(TaskPlayer.this, e);
                return;
            }
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(arquivoPlaylist, true);
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(TaskPlayer.this, e);
                return;
            }
            try {
                fileWriter.write("semVideo");
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(TaskPlayer.this, e);
                return;
            }
            try {
                fileWriter.close();
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(TaskPlayer.this, e);
                return;
            }
        }
    }

    private boolean verificarHorarioProgramacao(String horaInicial, String horaFinal) {
        Date horaInicialProgramação = null;
        Date horaFinalProgramacao = null;
        String dataAtual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        try {
            horaInicialProgramação = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(dataAtual.concat(horaInicial));
            horaFinalProgramacao = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(dataAtual.concat(horaFinal));
        } catch (ParseException e) {
            ImprimirUtils.imprimirErro(ConfiguaracaoUtils.class, e);
            return false;
        }
        if (horaFinalProgramacao.before(new Date())) {
            return false;
        }
        if (horaInicialProgramação.after(new Date())) {
            return false;
        }
        return true;
    }

    public void determinado(List<String> lista) {
        if (lista != null && !lista.isEmpty()) {
            String linha = lista.get(0);
            Log.e("Log", linha + " Determinado a interromper");
            videoView.stopPlayback();
            videoView.pause();
            videoView.clearAnimation();
            videoView.clearFocus();
            videoView.destroyDrawingCache();
            videoView.setVisibility(View.INVISIBLE);
            videoView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
            executar(linha);
        }
    }

    public void executar(String line) {
        Log.e("Log", "Executar");
        if (null == line) {
            videoView = (VideoView) main.findViewById(R.id.video);
            videoView.destroyDrawingCache();
            videoView.setVisibility(View.INVISIBLE);
            videoView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
            handler.postDelayed(this, 1000);

        } else {

            Log.e("Log", "Video não é nulo, Playlist = " + playlist.size());
            final String video = line.split("\\|")[3];
            final String titulo = line.split("\\|")[7];
            final String categoria = line.split("\\|")[8];
            final String velocidade = line.split("\\|")[9];
            final String tipoCategoria = line.split("\\|")[10];
            arquivoVideo = new File(video);
            videoView = (VideoView) main.findViewById(R.id.video);
            videoView.setVisibility(View.VISIBLE);
            videoView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
            videoView.setVideoPath(arquivoVideo.getAbsolutePath());
            videoView.requestFocus();
            videoView.clearAnimation();

            bancoDAO = new BancoDAO(context);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    duracao = videoView.getDuration();
                    Log.e("Log", "Esta tocando o video " + arquivoVideo);
                    Toast.makeText(context, arquivoVideo + " " + new SimpleDateFormat("HH:mm:ss").format(new Date()), Toast.LENGTH_LONG).show();
                    bancoDAO.atualizarBanco(arquivoVideo, duracao, tipoCategoria);
                    bancoDAO.close();
                    videoView.requestFocus();
                    videoView.start();
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                    String duracaoDoVideo = df.format(new Date(duracao));
                    registrarLog.escrever(" 02 Tocou o video: " + video + "@" + titulo + "@" + categoria + "@" + velocidade + "@" + duracaoDoVideo);
                    run();
                    return;
                }
            });
        }
        Log.e("Log", "Fim do executar");
    }

    public void setPlaylist(String line) {
        this.playlist.add(line);
    }

    public List<String> getPlaylist() {
        return playlist;
    }


}
