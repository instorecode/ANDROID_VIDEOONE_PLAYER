package com.Tarefas;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import com.br.instore.utils.ConfiguaracaoUtils;
import com.utils.AndroidImprimirUtils;
import com.utils.RegistrarLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskPlayerComericiaisDeterminados implements Runnable {


    private Handler handler;

    private List<String> listaLerLinha = new ArrayList<String>();
    private List<String> listaCapturarVideoATocar = new ArrayList<String>();
    private List<String> listaValidarSeOComercialNaoInterrompe = new ArrayList<String>();
    private List<String> listaValidarSeOComercialInterrompe = new ArrayList<String>();
    private final String barraDoSistema = System.getProperty("file.separator");
    private final String caminho = Environment.getExternalStorageDirectory().toString();
    private TaskPlayer taskPlayer;
    private Context context;

    public TaskPlayerComericiaisDeterminados(Handler handler, TaskPlayer taskPlayer, Context context) {
        this.handler = handler;
        this.taskPlayer = taskPlayer;
        this.context = context;
    }

    @Override
    public void run() {
        controlador();
    }

    private void controlador() {
        List<String> listaDeterminados = new ArrayList<String>();
        lerLinha();
        capturarVideoATocar(listaLerLinha);
        validarSeOComercialInterrompe(listaCapturarVideoATocar);

        if (listaValidarSeOComercialInterrompe != null && !listaValidarSeOComercialInterrompe.isEmpty()) {
            taskPlayer.determinado(listaValidarSeOComercialInterrompe);
        }

        if (listaValidarSeOComercialNaoInterrompe != null && !listaValidarSeOComercialNaoInterrompe.isEmpty()) {
            for (String line : listaValidarSeOComercialNaoInterrompe) {
                listaDeterminados.add(line);
            }
            listaValidarSeOComercialNaoInterrompe.clear();

            if (taskPlayer.getPlaylist() != null && !taskPlayer.getPlaylist().isEmpty()) {
                for (String line : taskPlayer.getPlaylist()) {
                    listaDeterminados.add(line);
                }
            }
        }

        if (listaDeterminados != null && !listaDeterminados.isEmpty()) {
            taskPlayer.getPlaylist().clear();
            for (String line : listaDeterminados) {
                if (!taskPlayer.getPlaylist().contains(line)) {
                    taskPlayer.setPlaylist(line);
                }
            }
            listaDeterminados.clear();
            handler.postDelayed(this, 60000);
        } else {
            if (listaValidarSeOComercialInterrompe.size() > 0) {
                handler.postDelayed(this, 60000);
                listaValidarSeOComercialInterrompe.clear();
            } else {
                handler.postDelayed(this, 10000);
            }
        }

        listaDeterminados.clear();
    }

    private void validarSeOComercialInterrompe(List<String> listaDeComerciaisNoHorario) {
        if (listaDeComerciaisNoHorario != null && !listaDeComerciaisNoHorario.isEmpty()) {
            for (String comercial : listaDeComerciaisNoHorario) {
                String comercialInterrompe = comercial.split("\\|")[2];
                if (comercialInterrompe.contains("0") || comercialInterrompe.equals("0")) {
                    listaValidarSeOComercialNaoInterrompe.add(comercial);
                } else {
                    listaValidarSeOComercialInterrompe.add(comercial);
                    break;
                }
            }
        }
        listaCapturarVideoATocar.clear();
    }

    private void capturarVideoATocar(List<String> listaDeLinhas) {
        if (listaDeLinhas != null && !listaDeLinhas.isEmpty()) {
            for (String comercialDeterminado : listaDeLinhas) {
                String horarioQueDeteTocar = comercialDeterminado.split("\\|")[1];
                String horaAtual = new SimpleDateFormat("HH:mm").format(new Date());
                if (horarioQueDeteTocar.equals(horaAtual) || horarioQueDeteTocar.contains(horaAtual)) {
                    if (!listaCapturarVideoATocar.contains(comercialDeterminado)) {
                        listaCapturarVideoATocar.add(comercialDeterminado);
                    }
                }
            }
        }
        listaDeLinhas.clear();
    }

    private void lerLinha() {
        if(null != ConfiguaracaoUtils.diretorio.getDiretorioPlaylist() && !ConfiguaracaoUtils.diretorio.getDiretorioPlaylist().trim().replaceAll("\\s","").isEmpty()) {
            String caminnhoPlaylistDet = caminho.concat(barraDoSistema).concat(ConfiguaracaoUtils.diretorio.getDiretorioPlaylist()).concat(barraDoSistema).concat("playlistDet.exp");
            File arquivoPlaylistDet = new File(caminnhoPlaylistDet);
            if (arquivoPlaylistDet.exists()) {
                FileReader fileReader = null;
                try {
                    fileReader = new FileReader(arquivoPlaylistDet);
                } catch (FileNotFoundException e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                } catch (NullPointerException e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                } catch (InvalidParameterException e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                } catch (Exception e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                }

                BufferedReader bf = new BufferedReader(fileReader);
                String line = "";
                try {
                    while (null != (line = bf.readLine())) {
                        if (line.contains("semVideo") || line.equals("semVideo")) {
                            listaLerLinha.clear();
                            break;
                        } else {
                            listaLerLinha.add(line);
                        }
                    }
                } catch (IOException e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                } catch (NullPointerException e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                } catch (InvalidParameterException e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                } catch (Exception e) {
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                    AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                } finally {
                    try {
                        bf.close();
                    } catch (IOException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                    } catch (NullPointerException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                    } catch (InvalidParameterException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                    } catch (Exception e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                    }
                    try {
                        fileReader.close();
                    } catch (FileNotFoundException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                    } catch (NullPointerException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                    } catch (InvalidParameterException e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                    } catch (Exception e) {
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.this, e);
                        AndroidImprimirUtils.imprimirErro(TaskPlayerComericiaisDeterminados.class, e, 90);
                    }
                }
            }

        } else {
            RegistrarLog.imprimirMsg("Log", "Ainda não tem a informação do diretorio de playList");
            handler.postDelayed(this, 2000);
        }
    }

}
