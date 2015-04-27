package com.banco;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.Tarefas.TaskDiretorios;
import com.bean.ComercialDependencia;
import com.bean.ComercialDet;
import com.br.instore.exp.bean.ProgramacaoExp;
import com.br.instore.utils.Banco;
import com.br.instore.utils.DataUtils;
import com.br.instore.utils.ImprimirUtils;
import com.br.instore.utils.StringUtils;
import com.utils.RegistrarLog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class BancoDAO {
    private static final String VIEW_PROGRAMACAO = "SELECT * FROM VIEW_CARREGAR_PROGRAMACAO";
    private List<ProgramacaoExp> listaProgramacao = new ArrayList<ProgramacaoExp>();
    private List<String> listaDeArquivos = new ArrayList<String>();
    private List<ComercialDet> listaComercialDeterminados = new ArrayList<ComercialDet>();
    private List<String> linhasPlaylistDet = new ArrayList<String>();
    private Banco banco = new Banco();
    private Cursor cursor;
    private RegistrarLog registrarLog;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public BancoDAO(Context context) {
        this.helper = new DatabaseHelper(context);
        this.registrarLog = new RegistrarLog(context);
    }

    public SQLiteDatabase getDb() {
        if (null == db) {
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public void close() {
        helper.close();
    }

    ///------------------ VIDEOS COMERCIAIS -------------------///
    public void programacoes() {
        SQLiteDatabase db = helper.getWritableDatabase();
        cursor = db.rawQuery(VIEW_PROGRAMACAO, new String[]{});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ProgramacaoExp programacaoExp = new ProgramacaoExp();
                programacaoExp.descricao = cursor.getString(cursor.getColumnIndex("Descricao"));
                String ano = cursor.getString(cursor.getColumnIndex("Ano")).trim();
                String mes = cursor.getString(cursor.getColumnIndex("mesInicialFormatado")).trim();
                String dia = cursor.getString(cursor.getColumnIndex("diaInicialFormatado")).trim();
                programacaoExp.dataInicial = dia + "-" + mes + "-" + ano;

                String anoF = cursor.getString(cursor.getColumnIndex("Anof")).trim();
                String mesF = cursor.getString(cursor.getColumnIndex("mesfInicialFormatado")).trim();
                String diaF = cursor.getString(cursor.getColumnIndex("diafInicialFormatado")).trim();
                programacaoExp.dataFinal = diaF + "-" + mesF + "-" + anoF;

                programacaoExp.horarioInicio = cursor.getString(cursor.getColumnIndex("HoraInicio"));
                programacaoExp.horarioFinal = cursor.getString(cursor.getColumnIndex("HoraFinal"));

                programacaoExp.diaDaSemana = cursor.getString(cursor.getColumnIndex("DiaSemana"));
                programacaoExp.categoria1 = cursor.getShort(cursor.getColumnIndex("Categoria1"));
                programacaoExp.categoria2 = cursor.getShort(cursor.getColumnIndex("Categoria2"));
                programacaoExp.categoria3 = cursor.getShort(cursor.getColumnIndex("Categoria3"));
                programacaoExp.categoria4 = cursor.getShort(cursor.getColumnIndex("Categoria4"));
                programacaoExp.categoria5 = cursor.getShort(cursor.getColumnIndex("Categoria5"));
                programacaoExp.categoria6 = cursor.getShort(cursor.getColumnIndex("Categoria6"));
                programacaoExp.categoria7 = cursor.getShort(cursor.getColumnIndex("Categoria7"));
                programacaoExp.categoria8 = cursor.getShort(cursor.getColumnIndex("Categoria8"));
                programacaoExp.categoria9 = cursor.getShort(cursor.getColumnIndex("Categoria9"));
                programacaoExp.categoria10 = cursor.getShort(cursor.getColumnIndex("Categoria10"));
                programacaoExp.categoria11 = cursor.getShort(cursor.getColumnIndex("Categoria11"));
                programacaoExp.categoria12 = cursor.getShort(cursor.getColumnIndex("Categoria12"));
                programacaoExp.categoria13 = cursor.getShort(cursor.getColumnIndex("Categoria13"));
                programacaoExp.categoria14 = cursor.getShort(cursor.getColumnIndex("Categoria14"));
                programacaoExp.categoria15 = cursor.getShort(cursor.getColumnIndex("Categoria15"));
                programacaoExp.categoria16 = cursor.getShort(cursor.getColumnIndex("Categoria16"));
                programacaoExp.categoria17 = cursor.getShort(cursor.getColumnIndex("Categoria17"));
                programacaoExp.categoria18 = cursor.getShort(cursor.getColumnIndex("Categoria18"));
                programacaoExp.categoria19 = cursor.getShort(cursor.getColumnIndex("Categoria19"));
                programacaoExp.categoria20 = cursor.getShort(cursor.getColumnIndex("Categoria20"));
                programacaoExp.categoria21 = cursor.getShort(cursor.getColumnIndex("Categoria21"));
                programacaoExp.categoria22 = cursor.getShort(cursor.getColumnIndex("Categoria22"));
                programacaoExp.categoria23 = cursor.getShort(cursor.getColumnIndex("Categoria23"));
                programacaoExp.categoria24 = cursor.getShort(cursor.getColumnIndex("Categoria24"));
                programacaoExp.conteudo = cursor.getString(cursor.getColumnIndex("Conteudo"));

                listaProgramacao.add(programacaoExp);
            }
        } else {
            listaDeArquivos.add("semVideo");
            listaProgramacao.clear();
            return;
        }
    }

    public void categorias() {
        if(null != listaProgramacao && !listaProgramacao.isEmpty()) {
            for (ProgramacaoExp p : listaProgramacao) {
                codigoCategoria(p.categoria1, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria2, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria3, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria4, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria5, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria6, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria7, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria8, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria9, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria10, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria11, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria12, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria13, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria14, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria15, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria16, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria17, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria18, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria19, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria20, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria21, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria22, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria23, p.horarioInicio, p.horarioFinal);
                codigoCategoria(p.categoria24, p.horarioInicio, p.horarioFinal);
            }
        } else {
            return;
        }

        if(null == listaDeArquivos || !listaDeArquivos.isEmpty()){
            listaDeArquivos.add("semVideo");
            listaProgramacao.clear();
            return;
        }

    }

    private void codigoCategoria(short codigo, String horaInicialProgramacao, String horaFinalProgramacao) {
        SQLiteDatabase db1 = helper.getWritableDatabase();
        if (codigo != 0) {
            String scriptCategoria = "SELECT * FROM Categoria WHERE Codigo = '" + codigo + "' AND date('now') >= dataInicio AND date('now') < dataFinal";
            Cursor cursorT = db1.rawQuery(scriptCategoria, new String[]{});
            if (cursorT.getCount() > 0) {
                while (cursorT.moveToNext()) {
                    String codigoCategoria = cursorT.getString(cursorT.getColumnIndex("Codigo"));
                    short tipoCategoria = cursorT.getShort(cursorT.getColumnIndex("Tipo"));
                    if (tipoCategoria == 1) {
                        video(codigoCategoria, horaInicialProgramacao, horaFinalProgramacao);
                    } else if (tipoCategoria == 2) {
                        comercial(codigoCategoria, horaInicialProgramacao, horaFinalProgramacao, tipoCategoria);
                    }
                }
            }
            cursorT.close();
            db1.close();
        }
    }

    private void video(String codigoCategoria, String horaInicial, String horaFinal) {
        String scriptVideo = "SELECT * FROM VIEW_CARREGAR_VIDEOS WHERE (Categoria1 = " + codigoCategoria + " OR Categoria2 = " + codigoCategoria + " OR Categoria3 = " + codigoCategoria + " )";
        SQLiteDatabase db = helper.getWritableDatabase();
        cursor = db.rawQuery(scriptVideo, new String[]{});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String arquivo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Arquivo")));
                String titulo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                String velocidade = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Velocidade")));
                String caminhoDoArquivoDeVideo = validarExistenciaDoVideo(arquivo);
                if (null != caminhoDoArquivoDeVideo) {
                    listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + caminhoDoArquivoDeVideo + "|0|0|" + caminhoDoArquivoDeVideo + "|" + titulo + "|" + codigoCategoria + "|" + velocidade + "|1");
                    return;
                } else {
                    //registrarLog.escrever(" Desprezada com tipo 99. O video " + arquivo + " não foi encontrado em nenhum diretório");
                }
            }
        }
    }

    private void comercial(String codigoCategoria, String horaInicial, String horaFinal, Short tipoCategoria) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String scriptCategoria = "SELECT * FROM VIEW_CARREGAR_COMERCIAL WHERE (QtdePlayer is null OR QtdePlayer > Qtde) AND  Categoria = " + codigoCategoria;
        cursor = db.rawQuery(scriptCategoria, new String[]{});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String arquivo = cursor.getString(cursor.getColumnIndex("Arquivo"));
                String diasAlternados = cursor.getString(cursor.getColumnIndex("DiasAlternados"));
                String dataStr = cursor.getString(cursor.getColumnIndex("Data"));
                String titulo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                String dependencia1 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Dependencia1")));
                String dependencia2 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Dependencia2")));
                String dependencia3 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Dependencia3")));
                boolean comercialEvalido = validarDiasComercial(arquivo, titulo, dependencia1, dependencia2, dependencia3, horaInicial, horaFinal, codigoCategoria, dataStr, diasAlternados);
                if (comercialEvalido) {
                    break;
                }
            }
        }
    }

    private boolean validarDiasComercial(String arquivo, String titulo, String dependencia1, String dependencia2, String dependencia3, String horaInicial, String horaFinal, String codigoCategoria, String dataStr, String diasAlternados) {
        Date data = null;
        if (null != dataStr) {
            try {
                data = new SimpleDateFormat("yyyy-MM-dd").parse(dataStr);
            } catch (ParseException e) {
                ImprimirUtils.imprimirErro(BancoDAO.this, e);
                return false;
            }

            Calendar diaQueTocou = Calendar.getInstance();
            diaQueTocou.setTime(data);
            diaQueTocou.add(Calendar.DAY_OF_MONTH, 1);

            Calendar diaAtual = Calendar.getInstance();
            diaAtual.setTime(new Date());

            if (diasAlternados.equals("0")) {
                boolean comercialEDependenciasExistemNosDiretoriosEBanco = validarDependenciasComercial(arquivo, titulo, dependencia1, dependencia2, dependencia3, horaInicial, horaFinal, codigoCategoria);
                if (comercialEDependenciasExistemNosDiretoriosEBanco) {
                    return true;
                }
            } else {
                if (diaQueTocou.get(Calendar.DAY_OF_MONTH) == diaAtual.get(Calendar.DAY_OF_MONTH)) {
                    //registrarLog.escrever(" Desprezada com tipo 99. O comercial " + arquivo + " não pode ser tocado devido ter sido cadastrado como dias alternados");
                    return false;
                } else {
                    boolean comercialEDependenciasExistemNosDiretoriosEBanco = validarDependenciasComercial(arquivo, titulo, dependencia1, dependencia2, dependencia3, horaInicial, horaFinal, codigoCategoria);
                    if (comercialEDependenciasExistemNosDiretoriosEBanco) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean validarDependenciasComercial(String arquivo, String titulo, String dependencia1, String dependencia2, String dependencia3, String horaInicial, String horaFinal, String codigoCategoria) {
        if (!dependencia1.trim().toLowerCase().equals("nenhuma")) {
            String caminhoDoArquivo = validarExistenciaDoVideo(dependencia1);
            if (caminhoDoArquivo == null) {
                //registrarLog.escrever(" Desprezada com tipo 99. A Dependência " + dependencia1 + " do arquivo " + arquivo + " não foi encontrado em nenhum diretório");
            } else {
                ComercialDependencia cd = validarDependenciaNoBanco(dependencia1);
                if (cd == null) {
                   /// registrarLog.escrever(" 99 Desprezada com tipo. A dependência " + dependencia1 + " do arquivo " + arquivo + " não foi encontrada no banco");
                } else {
                    listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + caminhoDoArquivo + "|1|0|" + arquivo + "|" + cd.titulo + "|" + cd.categoria + "|0|2");
                }
            }
        }

        if (!dependencia2.trim().toLowerCase().equals("nenhuma")) {
            String caminhoDoArquivo = validarExistenciaDoVideo(dependencia2);
            if (caminhoDoArquivo == null) {
                //registrarLog.escrever(" Desprezada com tipo 99. A Dependência " + dependencia2 + " do arquivo " + arquivo + " não foi encontrado em nenhum diretório" + "|0|2");
            } else {
                ComercialDependencia cd = validarDependenciaNoBanco(dependencia2);
                if (cd == null) {
                   // registrarLog.escrever(" 99 Desprezada com tipo. A dependência " + dependencia2 + " do arquivo " + arquivo + " não foi encontrada no banco");
                } else {
                    listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + caminhoDoArquivo + "|1|0|" + arquivo + "|" + cd.titulo + "|" + cd.categoria + "|0|2");
                }
            }
        }

        if (!dependencia3.trim().toLowerCase().equals("nenhuma")) {
            String caminhoDoArquivo = validarExistenciaDoVideo(dependencia2);
            if (caminhoDoArquivo == null) {
                //registrarLog.escrever(" Desprezada com tipo 99. A Dependência " + dependencia3 + " do arquivo " + arquivo + " não foi encontrado em nenhum diretório");
            } else {
                ComercialDependencia cd = validarDependenciaNoBanco(dependencia3);
                if (cd == null) {
                    //registrarLog.escrever(" 99 Desprezada com tipo. A dependência " + dependencia3 + " do arquivo " + arquivo + " não foi encontrada no banco");
                } else {
                    listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + caminhoDoArquivo + "|1|0|" + arquivo + "|" + cd.titulo + "|" + cd.categoria + "|0|2");
                }
            }
        }

        String caminhoDoArquivo = validarExistenciaDoVideo(arquivo);
        if (caminhoDoArquivo == null) {
            //registrarLog.escrever(" Desprezada com tipo 99. O arquivo " + arquivo + " não foi encontrado em nenhum diretório");
        } else {
            listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + caminhoDoArquivo + "|0|0|" + arquivo + "|" + titulo + "|" + codigoCategoria + "|0|2");
            return true;
        }

        return false;
    }

    private ComercialDependencia validarDependenciaNoBanco(String nome) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String script = "SELECT * FROM VIEW_CARREGAR_COMERCIAL WHERE Arquivo = '" + nome + "'";
        cursor = db.rawQuery(script, new String[]{});
        if (cursor.moveToFirst()) {
            String arquivo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Arquivo")));
            String titulo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
            String categoria = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Categoria")));

            ComercialDependencia cd = new ComercialDependencia();
            cd.arquivo = arquivo;
            cd.titulo = titulo;
            cd.categoria = categoria;
            return cd;
        }
        return null;
    }

    public void criarArquivoPlaylist() {
        File playlistAntiga = new File(TaskDiretorios.diretorioPlaylist.concat("playlist.exp"));
        if (playlistAntiga.exists()) {
            playlistAntiga.delete();
        }

        File playlistNova = new File(TaskDiretorios.diretorioPlaylist.concat("playlist.exp"));
        try {
            playlistNova.createNewFile();
        } catch (IOException e) {
            ImprimirUtils.imprimirErro(BancoDAO.this, e);
            return;
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(playlistNova, true);
        } catch (IOException e) {
            ImprimirUtils.imprimirErro(BancoDAO.this, e);
            return;
        }

        if (null != listaDeArquivos && !listaDeArquivos.isEmpty()) {
            for (String linha : listaDeArquivos) {
                try {
                    fileWriter.write(linha.concat("\n"));
                } catch (IOException e) {
                    ImprimirUtils.imprimirErro(BancoDAO.this, e);
                    return;
                }
            }

        }

        listaDeArquivos.clear();
        listaProgramacao.clear();

        try {
            fileWriter.close();
        } catch (IOException e) {
            ImprimirUtils.imprimirErro(BancoDAO.this, e);
            return;
        }
    }

    ///----------------- COMERCIAL DETERMINADO ----------///
    public void comerciaisDeterminados() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String script = "SELECT * FROM VIEW_CARREGAR_COMERCIAL_DET WHERE (QtdePlayer is null OR QtdePlayer > Qtde)";
        cursor = db.rawQuery(script, new String[]{});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ComercialDet comercialDet = new ComercialDet();
                comercialDet.arquivo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Arquivo")));
                comercialDet.cliente = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Cliente")));
                comercialDet.titulo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                comercialDet.categoria = cursor.getString(cursor.getColumnIndex("Categoria"));
                comercialDet.dataInicial = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("PeriodoInicial")));
                comercialDet.dataFinal = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("PeriodoFinal")));
                comercialDet.horario1 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario1")));
                comercialDet.horario2 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario2")));
                comercialDet.horario3 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario3")));
                comercialDet.horario4 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario4")));
                comercialDet.horario5 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario5")));
                comercialDet.horario6 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario6")));
                comercialDet.horario7 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario7")));
                comercialDet.horario8 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario8")));
                comercialDet.horario9 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario9")));
                comercialDet.horario10 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario10")));
                comercialDet.horario11 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario11")));
                comercialDet.horario12 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario12")));
                comercialDet.horario13 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario13")));
                comercialDet.horario14 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario14")));
                comercialDet.horario15 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario15")));
                comercialDet.horario16 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario16")));
                comercialDet.horario17 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario17")));
                comercialDet.horario18 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario18")));
                comercialDet.horario19 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario19")));
                comercialDet.horario20 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario20")));
                comercialDet.horario21 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario21")));
                comercialDet.horario22 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario22")));
                comercialDet.horario23 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario23")));
                comercialDet.horario24 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Horario24")));
                comercialDet.semana1 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana1")));
                comercialDet.semana2 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana2")));
                comercialDet.semana3 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana2")));
                comercialDet.semana4 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana4")));
                comercialDet.semana5 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana5")));
                comercialDet.semana6 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana6")));
                comercialDet.semana7 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana7")));
                comercialDet.semana8 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana8")));
                comercialDet.semana9 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana9")));
                comercialDet.semana10 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana10")));
                comercialDet.semana11 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana11")));
                comercialDet.semana12 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana12")));
                comercialDet.semana13 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana13")));
                comercialDet.semana14 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana14")));
                comercialDet.semana15 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana15")));
                comercialDet.semana16 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana16")));
                comercialDet.semana17 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana17")));
                comercialDet.semana18 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana18")));
                comercialDet.semana19 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana19")));
                comercialDet.semana20 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana20")));
                comercialDet.semana21 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana21")));
                comercialDet.semana22 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana22")));
                comercialDet.semana23 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana23")));
                comercialDet.semana24 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Semana24")));
                comercialDet.diaSemana = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("DiaSemana")));
                comercialDet.diasAlternados = (cursor.getString(cursor.getColumnIndex("DiasAlternados")) == "1") ? true : false;
                comercialDet.data = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Data")));
                comercialDet.ultimaExecucao = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("UltimaExecucao")));
                comercialDet.tempoTotal = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("TempoTotal")));
                comercialDet.random = cursor.getInt(cursor.getColumnIndex("Random"));
                comercialDet.qtdePlayer = cursor.getInt(cursor.getColumnIndex("QtdePlayer"));
                comercialDet.qtdePlayer = cursor.getInt(cursor.getColumnIndex("Qtde"));
                comercialDet.dataVencimento = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("DataVencto")));
                comercialDet.dependencia1 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Dependencia1")));
                comercialDet.dependencia2 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Dependencia2")));
                comercialDet.dependencia3 = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Dependencia3")));
                listaComercialDeterminados.add(comercialDet);
            }
        }
    }

    public void controladorComercialDependencia() {
        if (null != listaComercialDeterminados && listaComercialDeterminados.size() > 0 && !listaComercialDeterminados.isEmpty()) {
            for (ComercialDet comercialDet : listaComercialDeterminados) {
                ComercialDet comercialDetComDepencias = dependenciaDeterminados(comercialDet);
                if (null != comercialDetComDepencias) {
                    semanaAndHorario(comercialDet.semana1, comercialDet.horario1, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana2, comercialDet.horario2, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana3, comercialDet.horario3, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana4, comercialDet.horario4, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana5, comercialDet.horario5, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana6, comercialDet.horario6, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana7, comercialDet.horario7, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana8, comercialDet.horario8, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana9, comercialDet.horario9, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana10, comercialDet.horario10, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana11, comercialDet.horario11, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana12, comercialDet.horario12, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana13, comercialDet.horario13, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana14, comercialDet.horario14, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana15, comercialDet.horario15, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana16, comercialDet.horario16, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana17, comercialDet.horario17, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana18, comercialDet.horario18, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana19, comercialDet.horario19, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana20, comercialDet.horario20, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana21, comercialDet.horario21, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana22, comercialDet.horario22, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana23, comercialDet.horario23, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                    semanaAndHorario(comercialDet.semana24, comercialDet.horario24, comercialDet.arquivo, comercialDet.titulo, comercialDet.categoria, comercialDet.listaDependencias, comercialDet.data);
                }
            }
        } else {
           linhasPlaylistDet.add("semVideo");
        }

    }

    private ComercialDet dependenciaDeterminados(ComercialDet comercialDet) {
        String nomeComercial = comercialDet.arquivo;
        String dependencia1 = comercialDet.dependencia1;
        String dependencia2 = comercialDet.dependencia2;
        String dependencia3 = comercialDet.dependencia3;

        String arquivoComercialDetermidado = validarExistenciaDoVideo(nomeComercial);
        if (null != arquivoComercialDetermidado) {
            comercialDet.arquivo = arquivoComercialDetermidado;
            ///-----------------------DEPENDENCOA 1 ---------------------------------------------------------------///
            if (!dependencia1.toLowerCase().equals("nenhuma") && !dependencia1.toLowerCase().contains("nenhuma")) {
                String resultadoDependencia1 = validarExistenciaDoVideo(dependencia1);
                if (null != resultadoDependencia1) {
                    ComercialDependencia resultadoExisteNoBanco = validarDependenciaDeUmDeterminadoNoBanco(dependencia1);
                    if (null != resultadoExisteNoBanco) {
                        resultadoExisteNoBanco.arquivo = resultadoDependencia1;
                        comercialDet.listaDependencias.add(resultadoExisteNoBanco);
                    } else {
               /*TODO
                    Colocar a mensagem de desprezada no banco tipo 99
                */
                    }
                } else {
               /*TODO
                    Colocar a mensagem de desprezada no diretorio tipo 99
                */
                }
            }
            ///-----------------------DEPENDENCOA 2 ---------------------------------------------------------------///
            if (!dependencia2.toLowerCase().equals("nenhuma") && !dependencia2.toLowerCase().contains("nenhuma")) {
                String resultadoDependencia2 = validarExistenciaDoVideo(dependencia2);
                if (null != resultadoDependencia2) {
                    ComercialDependencia resultadoExisteNoBanco = validarDependenciaDeUmDeterminadoNoBanco(dependencia2);
                    if (null != resultadoExisteNoBanco) {
                        resultadoExisteNoBanco.arquivo = resultadoDependencia2;
                        comercialDet.listaDependencias.add(resultadoExisteNoBanco);
                    } else {
                /*TODO
                    Colocar a mensagem de desprezada no banco tipo 99
                */
                    }
                } else {
            /*TODO
                Colocar a mensagem de desprezada no diretorio tipo 99
             */
                }
            }
            ///-----------------------DEPENDENCOA 3 ---------------------------------------------------------------///
            if (!dependencia3.toLowerCase().equals("nenhuma") && !dependencia3.toLowerCase().contains("nenhuma")) {
                String resultadoDependencia3 = validarExistenciaDoVideo(dependencia3);
                if (null != resultadoDependencia3) {
                    ComercialDependencia resultadoExisteNoBanco = validarDependenciaDeUmDeterminadoNoBanco(dependencia3);
                    if (null != resultadoExisteNoBanco) {
                        resultadoExisteNoBanco.arquivo = resultadoDependencia3;
                        comercialDet.listaDependencias.add(resultadoExisteNoBanco);
                    } else {
                /*TODO
                    Colocar a mensagem de desprezada no banco tipo 99
                 */
                    }
                } else {
                /*TODO
                    Colocar a mensagem de desprezada no diretorio tipo 99
                 */
                }
            }
        } else {
            /*TODO
                Colocar a mensagem de desprezada no diretorio tipo 99
             */
            return null;
        }
        return comercialDet;
    }

    private ComercialDependencia validarDependenciaDeUmDeterminadoNoBanco(String nome){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "SELECT * FROM VIEW_CARREGAR_COMERCIAL WHERE (QtdePlayer is null OR QtdePlayer > Qtde) AND Arquivo = '" + nome + "';";
        Cursor cursorDependencia = db.rawQuery(sql , new String[]{});
        if (cursorDependencia.getCount() > 0) {
            ComercialDependencia comercialDependencia = new ComercialDependencia();
            while (cursorDependencia.moveToNext()) {
                comercialDependencia.arquivo = StringUtils.nuloParaVazio(cursorDependencia.getString(cursorDependencia.getColumnIndex("Arquivo")));
                comercialDependencia.cliente = StringUtils.nuloParaVazio(cursorDependencia.getString(cursorDependencia.getColumnIndex("Cliente")));
                comercialDependencia.categoria = StringUtils.nuloParaVazio(cursorDependencia.getString(cursorDependencia.getColumnIndex("Categoria")));
                comercialDependencia.data = StringUtils.nuloParaVazio(cursorDependencia.getString(cursorDependencia.getColumnIndex("Data")));
                comercialDependencia.dataInicial = StringUtils.nuloParaVazio(cursorDependencia.getString(cursorDependencia.getColumnIndex("PeriodoInicial")));
                comercialDependencia.dataFinal = StringUtils.nuloParaVazio(cursorDependencia.getString(cursorDependencia.getColumnIndex("PeriodoFinal")));
                comercialDependencia.dataVencimento = StringUtils.nuloParaVazio(cursorDependencia.getString(cursorDependencia.getColumnIndex("DataVencto")));
                comercialDependencia.titulo = StringUtils.nuloParaVazio(cursorDependencia.getString(cursorDependencia.getColumnIndex("Titulo")));
            }
            return comercialDependencia;
        }
        cursorDependencia.close();
        return null;
    }

    private void semanaAndHorario(String semana, String horario, String arquivo, String titulo, String categoria, List<ComercialDependencia> listaDependenciasComercialDeterminado, String dataUltimaExecucao) {
        if (!semana.equals("") && !horario.equals("")) {
            if (dataUltimaExecucao.isEmpty() || dataUltimaExecucao.equals("")) {
                popularListaDeLinhasPlaylistDet(listaDependenciasComercialDeterminado, horario, semana, arquivo, titulo, categoria);
            } else {
                int diaDaUltimaExecucaoEmNumero = DataUtils.dataDaUltimaExecucaoParaNumero(dataUltimaExecucao);
                int diaAtualEmNumero = DataUtils.diaDaSemana();
                List<String> lista = new ArrayList<String>();

                for (int i = 0; i <= (semana.length() - 1); i++) {
                    if (!StringUtils.verificarSeVazio("" + semana.charAt(i))) {
                        lista.add("" + (i + 1));
                    }
                }

                int posicaoNaListaDoDiaQueTocou = 8;
                int posicaoNaListaDoDiaAtual = 8;

                for (String string : lista) {
                    if (Integer.parseInt(string) == diaDaUltimaExecucaoEmNumero) {
                        posicaoNaListaDoDiaQueTocou = lista.indexOf(string);
                    }

                    if (Integer.parseInt(string) == diaAtualEmNumero) {
                        posicaoNaListaDoDiaAtual = lista.indexOf(string);
                    }
                }

                if (posicaoNaListaDoDiaAtual != 8 && posicaoNaListaDoDiaQueTocou != 8) {
                    if (posicaoNaListaDoDiaQueTocou != (posicaoNaListaDoDiaAtual - 1)) {
                        popularListaDeLinhasPlaylistDet(listaDependenciasComercialDeterminado, horario, semana, arquivo, titulo, categoria);
                    }
                }
            }
        }
    }

    private void popularListaDeLinhasPlaylistDet(List<ComercialDependencia> listaDependenciasComercialDeterminado, String horario, String semana, String arquivo, String titulo, String categoria){
        if (("" + semana.charAt(DataUtils.diaDaSemana())).trim().toLowerCase().equals("n") || ("" + semana.charAt(DataUtils.diaDaSemana())).trim().toLowerCase().contains("n")) {
            semana = "0";
        } else {
            semana = "1";
        }

        if (null != listaDependenciasComercialDeterminado && listaDependenciasComercialDeterminado.size() > 0 && !listaDependenciasComercialDeterminado.isEmpty()) {
            for (ComercialDependencia comercialDependencia : listaDependenciasComercialDeterminado) {
                String linha = "det|" + horario + "|" + semana + "|" + comercialDependencia.arquivo + "|1|0|" + arquivo + "|" + comercialDependencia.titulo + "|" + comercialDependencia.categoria + "|1|2";
                linhasPlaylistDet.add(linha);
            }
        }
        String linha = "det|" + horario + "|" + semana + "|" + arquivo + "|0|1|" + arquivo + "|" + titulo + "|" + categoria + "|1|2";
        linhasPlaylistDet.add(linha);

    }

    public void criarPlaylistDeterminados() {
        File playlistAntiga = new File(TaskDiretorios.diretorioPlaylist.concat("playlistDet.exp"));
        if (playlistAntiga.exists()) {
            playlistAntiga.delete();
        }

        File playlistDeterminados = new File(TaskDiretorios.diretorioPlaylist.concat("playlistDet.exp"));

        try {
            playlistDeterminados.createNewFile();
        } catch (IOException e) {
            ImprimirUtils.imprimirErro(BancoDAO.this, e);
            return;
        }


        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(playlistAntiga, true);
        } catch (IOException e) {
            ImprimirUtils.imprimirErro(BancoDAO.this, e);
        }

        if (null != linhasPlaylistDet && !linhasPlaylistDet.isEmpty()) {
            for (String linha : linhasPlaylistDet) {
                //Log.e("Log", linha);
                try {
                    fileWriter.write(linha.concat("\n"));
                } catch (IOException e) {
                    ImprimirUtils.imprimirErro(BancoDAO.this, e);
                    return;
                }
            }
        }

        linhasPlaylistDet.clear();
        listaComercialDeterminados.clear();

        try {
            fileWriter.close();
        } catch (IOException e) {
            ImprimirUtils.imprimirErro(BancoDAO.this, e);
            return;
        }
    }

    //---------------------ok---------------------------//
    private String validarExistenciaDoVideo(String nomeDoArquivo) {
        File video = null;
        if (new File(TaskDiretorios.diretorioVideoPrimario.concat(nomeDoArquivo)).exists()) {
            video = new File(TaskDiretorios.diretorioVideoPrimario.concat(nomeDoArquivo));
            return video.getAbsolutePath();
        } else if (new File(TaskDiretorios.diretorioVideoSecundario.concat(nomeDoArquivo)).exists()) {
            video = new File(TaskDiretorios.diretorioVideoSecundario.concat(nomeDoArquivo));
            return video.getAbsolutePath();
        } else if (new File(TaskDiretorios.diretorioVideoTerciario.concat(nomeDoArquivo)).exists()) {
            video = new File(TaskDiretorios.diretorioVideoTerciario.concat(nomeDoArquivo));
            return video.getAbsolutePath();
        } else {
            return null;
        }
    }

    public void excluirComercialDoBanco() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String script = "SELECT * FROM Comercial WHERE DataVencto <= date('now')";
        cursor = db.rawQuery(script, new String[]{});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String arquivo = cursor.getString(cursor.getColumnIndex("Arquivo"));
                String titulo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                String dataVencto = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("DataVencto")));
                String resuldado = validarExistenciaDoVideo(arquivo);
                if (resuldado != null) {
                    File file = new File(resuldado);
                    file.delete();
                    db.delete("Comercial", "Arquivo='" + arquivo + "'", null);
                    registrarLog.escrever(" 10 Excluindo comercial " + titulo + "-" + arquivo + "-" + dataVencto + "==" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
                }
            }
        }
    }

    public void excluirVideosDoBanco() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String script = "SELECT * FROM Video WHERE DataVencto <= date('now')";
        cursor = db.rawQuery(script, new String[]{});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String arquivo = cursor.getString(cursor.getColumnIndex("Arquivo"));
                String titulo = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                String dataVencto = StringUtils.nuloParaVazio(cursor.getString(cursor.getColumnIndex("DataVencto")));
                String resuldado = validarExistenciaDoVideo(arquivo);

                if (resuldado != null) {
                    File file = new File(resuldado);
                    file.delete();
                    db.delete("Video", "Arquivo='" + arquivo + "'", null);
                    registrarLog.escrever(" 10 Excluindo video " + titulo + "-" + arquivo + "-" + dataVencto + "==" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
                }
            }
        }
    }

    public void criarViewProgramacao() {
        SQLiteDatabase db = helper.getWritableDatabase();
        banco.criarViewProgramacao();
        db.execSQL(banco.scriptProgramacaoView);
    }

    public void criarViewComercial() {
        SQLiteDatabase db = helper.getWritableDatabase();
        banco.criarViewComercial();
        db.execSQL(banco.scriptComercialView);
    }

    public void criarViewComercialDetermidos() {
        SQLiteDatabase db = helper.getWritableDatabase();
        banco.criarViewComercialDet();
        db.execSQL(banco.scriptComercialDetView);
    }

    public void criarViewVideo() {
        SQLiteDatabase db = helper.getWritableDatabase();
        banco.criarViewVideo();
        db.execSQL(banco.scriptVideoView);
    }

    public String quantidadeComerciaisNoBanco() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Arquivo FROM Comercial", new String[]{});
        String comerciaisNoBanco = "";
        comerciaisNoBanco = String.valueOf(cursor.getCount());
        cursor.close();
        return comerciaisNoBanco;
    }

    public String quantidadeVideoNoBanco() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Arquivo FROM Video", new String[]{});
        String videoNoBanco = "";
        videoNoBanco = String.valueOf(cursor.getCount());
        cursor.close();
        return videoNoBanco;
    }

    public void atualizarBanco(File video, int tempoDoVideo, String tipoCategoria) {
        int quantidadePlayer = pegarQtdePlayer(video.getName().trim(), tipoCategoria);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        String duracaoDoVideo = df.format(new Date(tempoDoVideo));
        String data = data();
        String ultimaExecucao = ultimaExecucao();
        int valorRandom = validarRandom(tipoCategoria);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "";
        if (tipoCategoria.equals("1")) {
            sql = "UPDATE Video SET Data ='" + data + "', UltimaExecucao = '" + ultimaExecucao + "', TempoTotal = '" + duracaoDoVideo + "', Random = " + valorRandom + ", Qtde = " + quantidadePlayer + " WHERE Arquivo = '" + video.getName().trim() + "'";
        } else {
            sql = "UPDATE Comercial SET Data ='" + data + "', UltimaExecucao = '" + ultimaExecucao + "', TempoTotal = '" + duracaoDoVideo + "', Random = " + valorRandom + ", Qtde = " + quantidadePlayer + " WHERE Arquivo = '" + video.getName().trim() + "'";
        }
        db.execSQL(sql);
        Log.e("Log", "ATUALIZOU O BANCO " + sql);
    }

    private int pegarQtdePlayer(String arquivo, String tipoVideo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int quantidade = 0;
        String sql = "";

        if (tipoVideo.equals("1")) {
            sql = "Select * from Video where Arquivo = '" + arquivo + "' LIMIT 1;";
        } else {
            sql = "Select * from Comercial where Arquivo = '" + arquivo + "' LIMIT 1;";
        }

        cursor = db.rawQuery(sql, new String[]{});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (null == cursor.getString(cursor.getColumnIndex("Qtde"))) {
                quantidade = 1;
            } else {
                quantidade = cursor.getInt(cursor.getColumnIndex("Qtde")) + 1;
            }
        }
        return quantidade;
    }

    private String ultimaExecucao() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private String data() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private int validarRandom(String tipoCategoria) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Random random = new Random();
        int valor = random.nextInt(9999);

        String sql = "";
        if (tipoCategoria.equals("1")) {
            sql = "SELECT Arquivo FROM Video where Random = " + valor + " LIMIT 1";

        } else {
            sql = "SELECT Arquivo FROM Comercial where Random = " + valor + " LIMIT 1";
        }

        cursor = db.rawQuery(sql, new String[]{});

        while ((cursor.getCount() > 0)) {
            registrarLog.escrever(" 70 Ja existem o random " + valor);
            validarRandom(tipoCategoria);
        }
        return valor;
    }


}
