package com.Tarefas;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.banco.BancoDAO;
import com.br.instore.utils.ConfiguaracaoUtils;
import com.player.MainActivity;
import com.player.R;
import com.utils.AndroidImprimirUtils;
import com.utils.RegistrarLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskPlayer implements Runnable {

    private MainActivity main;
    private Handler handler;
    private VideoView videoView;
    private Context context;
    private File arquivoVideo = null;
    public List<String> playlist = new ArrayList<String>();
    private final String barraDoSistema = System.getProperty("file.separator");
    private final String caminho = Environment.getExternalStorageDirectory().toString();
    private boolean videoVazio = true;
    private int duracao = 60000;
    private Object obj;
    private boolean vaiReproduzir = true;

    public TaskPlayer(MainActivity mainActivity, Handler handler, Context context, Object obj) {
        this.main = mainActivity;
        this.handler = handler;
        this.context = context;
        this.obj = obj;
    }


    @Override
    public void run() {
        if (playlist == null || playlist.isEmpty() || playlist.size() == 0) {
            RegistrarLog.imprimirMsg("Log", "PlayList não tem linhas");
            lerLinhas();
            if (videoVazio) {
                RegistrarLog.imprimirMsg("Log", "Sem video para tocar");
                executar(null);
                return;
            } else {
                RegistrarLog.imprimirMsg("Log", "Tem video a tocar");
                String linha = playlist.get(0);

                try {
                    String flag = linha.split("\\|")[0];
                    if (flag.equals("det") || flag.contains("det")) {
                        RegistrarLog.imprimirMsg("Log", "Video que vai tocar é Determinado");
                        playlist = new ArrayList(playlist.subList(1, playlist.size()));
                        executar(linha);
                        return;
                    } else {
                        RegistrarLog.imprimirMsg("Log", "Video que vai tocar é normal");
                        boolean retornoHorarioDaLinha = verificarHorarioProgramacao(linha.split("\\|")[1], linha.split("\\|")[2]);
                        if (retornoHorarioDaLinha) {
                            RegistrarLog.imprimirMsg("Log", "Video está com horario valido pode tocar");
                            playlist = new ArrayList(playlist.subList(1, playlist.size()));
                            executar(linha);
                            return;
                        } else {
                            RegistrarLog.imprimirMsg("Log", "Video não está com horario valido");
                            playlist = new ArrayList(playlist.subList(1, playlist.size()));
                            executar(null);
                            return;
                        }
                    }
                } catch (InvalidParameterException e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                    run();
                } catch (NullPointerException e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                    run();
                } catch (Exception e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                    run();
                }
            }
        } else {
            RegistrarLog.imprimirMsg("Log", "PlayList tem itens ");
            String linha = playlist.get(0);
            try {
                String flag = linha.split("\\|")[0];
                if (flag.equals("det") || flag.contains("det")) {
                    RegistrarLog.imprimirMsg("Log", "PlayList com itens || Video que vai tocar é Determinado");
                    playlist = new ArrayList(playlist.subList(1, playlist.size()));
                    executar(linha);
                    return;
                } else {
                    RegistrarLog.imprimirMsg("Log", "PlayList com itens || Video que vai tocar é normal");

                    boolean retornoHorarioDaLinha = verificarHorarioProgramacao(linha.split("\\|")[1], linha.split("\\|")[2]);
                    if (retornoHorarioDaLinha) {
                        RegistrarLog.imprimirMsg("Log", "PlayList com itens || Video está com horario valido pode tocar");
                        playlist = new ArrayList(playlist.subList(1, playlist.size()));
                        executar(linha);
                        return;
                    } else {
                        RegistrarLog.imprimirMsg("Log", "PlayList com itens || Video não está com horario valido");
                        playlist = new ArrayList(playlist.subList(1, playlist.size()));
                        executar(null);
                        return;
                    }
                }
            } catch (InvalidParameterException e) {
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                run();
            } catch (NullPointerException e) {
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                run();
            } catch (Exception e) {
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                run();
            }
        }
    }

    private void lerLinhas() {
        if (null != ConfiguaracaoUtils.diretorio.getDiretorioPlaylist() && !ConfiguaracaoUtils.diretorio.getDiretorioPlaylist().trim().replaceAll("\\s", "").isEmpty()) {

            String caminhoPlaylist = caminho.concat(barraDoSistema).concat(ConfiguaracaoUtils.diretorio.getDiretorioPlaylist()).concat(barraDoSistema).concat("playlist.exp");
            RegistrarLog.imprimirMsg("Log", caminhoPlaylist);
            if (null != caminhoPlaylist && !caminhoPlaylist.trim().replaceAll("\\s", "").isEmpty()) {

                File arquivoPlaylist = new File(caminhoPlaylist);

                if (arquivoPlaylist.exists()) {
                    RegistrarLog.imprimirMsg("Log", "ARQUIVO PLAYLIST EXISTE");
                    FileReader fileReader = null;
                    try {
                        fileReader = new FileReader(arquivoPlaylist);
                    } catch (FileNotFoundException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                    } catch (NullPointerException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                    } catch (InvalidParameterException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                    } catch (Exception e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                    }

                    BufferedReader bf = new BufferedReader(fileReader);
                    String line = "";
                    try {
                        while (null != (line = bf.readLine())) {
                            if (line.contains("semVideo") || line.equals("semVideo") || line.trim().replaceAll("\\s", "").isEmpty()) {
                                videoVazio = true;
                                break;
                            } else {
                                playlist.add(line);
                                videoVazio = false;
                            }
                        }
                    } catch (IOException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (NullPointerException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (InvalidParameterException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (Exception e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } finally {
                        try {
                            bf.close();
                        } catch (IOException e) {
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                            return;
                        } catch (NullPointerException e) {
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                            return;
                        } catch (InvalidParameterException e) {
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                            return;
                        } catch (Exception e) {
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                            return;
                        }

                        try {
                            fileReader.close();
                        } catch (IOException e) {
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                            return;
                        } catch (NullPointerException e) {
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                            return;
                        } catch (InvalidParameterException e) {
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                            return;
                        } catch (Exception e) {
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                            AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                            return;
                        }
                    }
                } else {
                    try {
                        arquivoPlaylist.createNewFile();
                    } catch (IOException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (NullPointerException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (InvalidParameterException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (Exception e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    }

                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter(arquivoPlaylist, true);
                    } catch (IOException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (NullPointerException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (InvalidParameterException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (Exception e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    }

                    try {
                        fileWriter.write("semVideo");
                    } catch (IOException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (NullPointerException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (InvalidParameterException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (Exception e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    }

                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (NullPointerException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (InvalidParameterException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    } catch (Exception e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 90);
                        return;
                    }
                }
            }
        } else {
            RegistrarLog.imprimirMsg("Log", "Ainda não tem a informação do diretorio de playList");
            handler.postDelayed(this, 2000);
        }
    }

    private boolean verificarHorarioProgramacao(String horaInicial, String horaFinal) {
        Date horaInicialProgramação = null;
        Date horaFinalProgramacao = null;
        String dataAtual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        try {
            horaInicialProgramação = new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(dataAtual.concat(horaInicial));
            horaFinalProgramacao = new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(dataAtual.concat(horaFinal));
        } catch (ParseException e) {
            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
            return false;
        } catch (NullPointerException e) {
            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
            return false;
        } catch (InvalidParameterException e) {
            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
            return false;
        } catch (Exception e) {
            AndroidImprimirUtils.imprimirErro(TaskPlayer.this, e);
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
            videoView.stopPlayback();
            videoView.pause();
            videoView.clearAnimation();
            videoView.clearFocus();
            videoView.destroyDrawingCache();
            videoView.setVisibility(View.INVISIBLE);
            videoView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
            Toast.makeText(context, linha, Toast.LENGTH_LONG).show();
            executar(linha);
        }
    }

    public void executar(String line) {
        if (null == line) {
            videoView = (VideoView) main.findViewById(R.id.video);
            videoView.destroyDrawingCache();
            videoView.setVisibility(View.INVISIBLE);
            videoView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
            handler.postDelayed(this, 10000);

        } else {
            try {
                final String video = line.split("\\|")[3];
                final String titulo = line.split("\\|")[7];
                final String categoria = line.split("\\|")[8];
                final String velocidade = line.split("\\|")[9];
                final String tipoCategoria = line.split("\\|")[10];
                arquivoVideo = new File(video);

                if (!arquivoVideo.exists()) {
                    Toast.makeText(context, "Video " + arquivoVideo.getAbsolutePath() + " não existe", Toast.LENGTH_LONG).show();
                    handler.postDelayed(this, 10000);
                } else {
                    RegistrarLog.imprimirMsg("Log", arquivoVideo.getAbsolutePath());
                    videoView = (VideoView) main.findViewById(R.id.video);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
                    videoView.setVideoPath(arquivoVideo.getAbsolutePath());
                    videoView.requestFocus();
                    videoView.clearAnimation();
                    videoView.requestFocus();
                    videoView.start();

                    videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            vaiReproduzir = false;
                            RegistrarLog.imprimirMsg("Log", "DEU ERRO" + arquivoVideo.getAbsolutePath());
                            videoView.destroyDrawingCache();
                            videoView.setVisibility(View.INVISIBLE);
                            videoView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
                            run();
                            return true;
                        }
                    });


                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer mp) {
                            duracao = videoView.getDuration();
                        }
                    });

                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            RegistrarLog.imprimirMsg("Log", "Atualiza o banco " + arquivoVideo.getAbsolutePath());
                            BancoDAO.atualizarBanco(arquivoVideo, duracao, tipoCategoria);
                            run();
                        }
                    });
                }
            } catch (InvalidParameterException e) {
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 20);
                handler.postDelayed(this, 10000);
            } catch (NullPointerException e) {
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 20);
                handler.postDelayed(this, 10000);
            } catch (Exception e) {
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e);
                AndroidImprimirUtils.imprimirErro(TaskPlayer.class, e, 20);
                handler.postDelayed(this, 10000);
            }
        }
    }

    public void setPlaylist(String line) {
        this.playlist.add(line);
    }

    public List<String> getPlaylist() {
        return playlist;
    }
}
