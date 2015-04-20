package com.banco;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.Tarefas.TaskDiretorios;
import com.bean.ComercialDependencia;
import com.br.instore.exp.bean.ComercialExp;
import com.br.instore.exp.bean.ProgramacaoExp;
import com.br.instore.utils.Banco;
import com.br.instore.utils.ImprimirUtils;
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
    private List<ComercialExp> listaComercialDeterminados = new ArrayList<ComercialExp>();
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
            categorias();
        } else {
            listaProgramacao.clear();
            listaDeArquivos.clear();
            criarArquivoPlaylist(true);
            return;
        }
    }

    protected void categorias() {
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

        if (listaDeArquivos.size() == 0 || listaDeArquivos == null) {
            listaProgramacao.clear();
            listaDeArquivos.clear();
            criarArquivoPlaylist(true);
            return;
        } else {
            criarArquivoPlaylist(false);
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
                String arquivo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Arquivo")));
                String titulo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                String velocidade = nuloToVazio(cursor.getString(cursor.getColumnIndex("Velocidade")));
                String resultado = validarExistenciaDoVideo(arquivo);
                if (null != resultado) {
                    listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + resultado + "|" + titulo + "|" + codigoCategoria + "|" + velocidade + "|" + "1");
                    return;
                } else {
                    registrarLog.escrever(" Desprezado com tipo 99. O video " + arquivo + " não foi encontrado em nenhum diretório");
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
                String titulo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                String dependencia1 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Dependencia1")));
                String dependencia2 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Dependencia2")));
                String dependencia3 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Dependencia3")));
                boolean resultado = validarDiasComercial(arquivo, titulo, dependencia1, dependencia2, dependencia3, horaInicial, horaFinal, codigoCategoria, dataStr, diasAlternados);
                if (resultado) {
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
                boolean resultado = validarDependenciasComercial(arquivo, titulo, dependencia1, dependencia2, dependencia3, horaInicial, horaFinal, codigoCategoria);
                if (resultado) {
                    return true;
                }
            } else {
                if (diaQueTocou.get(Calendar.DAY_OF_MONTH) == diaAtual.get(Calendar.DAY_OF_MONTH)) {
                    registrarLog.escrever(" Desprezado com tipo 99. O comercial " + arquivo + " não pode ser tocado devido ter sido cadastrado como dias alternados");
                    return false;
                } else {
                    boolean resultado = validarDependenciasComercial(arquivo, titulo, dependencia1, dependencia2, dependencia3, horaInicial, horaFinal, codigoCategoria);
                    if (resultado) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean validarDependenciasComercial(String arquivo, String titulo, String dependencia1, String dependencia2, String dependencia3, String horaInicial, String horaFinal, String codigoCategoria) {
         if (!dependencia1.trim().toLowerCase().equals("nenhuma")) {
            String resultado = validarExistenciaDoVideo(dependencia1);
            if (resultado == null) {
                registrarLog.escrever(" Desprezado com tipo 99. A Dependência " + dependencia1 + " do arquivo " + arquivo + " não foi encontrado em nenhum diretório");
            } else {
                ComercialDependencia cd = validarDependenciaNoBanco(dependencia1);
                if (cd == null) {
                    registrarLog.escrever(" 99 Desprezada com tipo. A dependência " + dependencia1 + " do arquivo " + arquivo + " não foi encontrada no banco");
                } else {
                    listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + resultado + "|" + cd.getTituloDependencia() + "|" + cd.getCategoriaDependencia() + "|0|2");
                }
            }
        }

        if (!dependencia2.trim().toLowerCase().equals("nenhuma")) {
            String resultado = validarExistenciaDoVideo(dependencia2);
            if (resultado == null) {
                registrarLog.escrever(" Desprezado com tipo 99. A Dependência " + dependencia2 + " do arquivo " + arquivo + " não foi encontrado em nenhum diretório" + "|0|2");
            } else {
                ComercialDependencia cd = validarDependenciaNoBanco(dependencia2);
                if (cd == null) {
                    registrarLog.escrever(" 99 Desprezada com tipo. A dependência " + dependencia2 + " do arquivo " + arquivo + " não foi encontrada no banco");
                } else {
                    listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + resultado + "|" + cd.getTituloDependencia() + "|" + cd.getCategoriaDependencia() + "|0|2");
                }
            }
        }

        if (!dependencia3.trim().toLowerCase().equals("nenhuma")) {
            String resultado = validarExistenciaDoVideo(dependencia2);
            if (resultado == null) {
                registrarLog.escrever(" Desprezado com tipo 99. A Dependência " + dependencia3 + " do arquivo " + arquivo + " não foi encontrado em nenhum diretório");
            } else {
                ComercialDependencia cd = validarDependenciaNoBanco(dependencia3);
                if (cd == null) {
                    registrarLog.escrever(" 99 Desprezada com tipo. A dependência " + dependencia3 + " do arquivo " + arquivo + " não foi encontrada no banco");
                } else {
                    listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + resultado + "|" + cd.getTituloDependencia() + "|" + cd.getCategoriaDependencia() + "|0|2");
                }
            }
        }

        String resultado = validarExistenciaDoVideo(arquivo);
        if (resultado == null) {
            registrarLog.escrever(" Desprezado com tipo 99. O arquivo " + arquivo + " não foi encontrado em nenhum diretório");
        } else {
            listaDeArquivos.add("normal|" + horaInicial + "|" + horaFinal + "|" + resultado + "|" + titulo + "|" + codigoCategoria + "|0|2");
            return true;
        }

        return false;
    }

    private ComercialDependencia validarDependenciaNoBanco(String nome) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String script = "SELECT * FROM VIEW_CARREGAR_COMERCIAL WHERE Arquivo = '" + nome + "'";
        cursor = db.rawQuery(script, new String[]{});
        if (cursor.moveToFirst()) {
            String arquivo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Arquivo")));
            String titulo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
            String categoria = nuloToVazio(cursor.getString(cursor.getColumnIndex("Categoria")));

            ComercialDependencia cd = new ComercialDependencia();
            cd.setNomeDependencia(arquivo);
            cd.setTituloDependencia(titulo);
            cd.setCategoriaDependencia(categoria);
            return cd;
        }
        return null;
    }

    public void criarArquivoPlaylist(boolean vazio) {
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

        if (vazio) {
            try {
                fileWriter.write("semVideo");
            } catch (IOException e) {
                ImprimirUtils.imprimirErro(BancoDAO.this, e);
                return;
            }
        } else {
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
            cursor.moveToFirst();

            do {
                ComercialExp comercialExp = new ComercialExp();
                comercialExp.arquivo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Arquivo")));
                comercialExp.cliente = nuloToVazio(cursor.getString(cursor.getColumnIndex("Cliente")));
                comercialExp.titulo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                comercialExp.tipoInterprete = cursor.getInt(cursor.getColumnIndex("TipoInterprete"));
                comercialExp.categoria = cursor.getInt(cursor.getColumnIndex("Categoria"));
                comercialExp.dataInicial = nuloToVazio(cursor.getString(cursor.getColumnIndex("PeriodoInicial")));
                comercialExp.dataFinal = nuloToVazio(cursor.getString(cursor.getColumnIndex("PeriodoFinal")));
                comercialExp.tipoHorario = cursor.getInt(cursor.getColumnIndex("TipoHorario"));
                comercialExp.horario1 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario1")));
                comercialExp.horario2 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario2")));
                comercialExp.horario3 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario3")));
                comercialExp.horario4 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario4")));
                comercialExp.horario5 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario5")));
                comercialExp.horario6 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario6")));
                comercialExp.horario7 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario7")));
                comercialExp.horario8 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario8")));
                comercialExp.horario9 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario9")));
                comercialExp.horario10 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario10")));
                comercialExp.horario11 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario11")));
                comercialExp.horario12 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario12")));
                comercialExp.horario13 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario13")));
                comercialExp.horario14 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario14")));
                comercialExp.horario15 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario15")));
                comercialExp.horario16 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario16")));
                comercialExp.horario17 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario17")));
                comercialExp.horario18 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario18")));
                comercialExp.horario19 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario19")));
                comercialExp.horario20 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario20")));
                comercialExp.horario21 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario21")));
                comercialExp.horario22 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario22")));
                comercialExp.horario23 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario23")));
                comercialExp.horario24 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Horario24")));
                comercialExp.semana1 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana1")));
                comercialExp.semana2 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana2")));
                comercialExp.semana3 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana2")));
                comercialExp.semana4 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana4")));
                comercialExp.semana5 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana5")));
                comercialExp.semana6 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana6")));
                comercialExp.semana7 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana7")));
                comercialExp.semana8 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana8")));
                comercialExp.semana9 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana9")));
                comercialExp.semana10 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana10")));
                comercialExp.semana11 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana11")));
                comercialExp.semana12 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana12")));
                comercialExp.semana13 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana13")));
                comercialExp.semana14 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana14")));
                comercialExp.semana15 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana15")));
                comercialExp.semana16 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana16")));
                comercialExp.semana17 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana17")));
                comercialExp.semana18 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana18")));
                comercialExp.semana19 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana19")));
                comercialExp.semana20 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana20")));
                comercialExp.semana21 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana21")));
                comercialExp.semana22 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana22")));
                comercialExp.semana23 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana23")));
                comercialExp.semana24 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Semana24")));
                comercialExp.diaSemana = nuloToVazio(cursor.getString(cursor.getColumnIndex("DiaSemana")));
                comercialExp.diasAlternados = (cursor.getString(cursor.getColumnIndex("DiasAlternados")) == "1") ? true : false;
                comercialExp.data = nuloToVazio(cursor.getString(cursor.getColumnIndex("Data")));
                comercialExp.ultimaExecucao = nuloToVazio(cursor.getString(cursor.getColumnIndex("UltimaExecucao")));
                comercialExp.tempoTotal = nuloToVazio(cursor.getString(cursor.getColumnIndex("TempoTotal")));
                comercialExp.random = cursor.getInt(cursor.getColumnIndex("Random"));
                comercialExp.qtdePlayer = cursor.getInt(cursor.getColumnIndex("QtdePlayer"));
                comercialExp.qtdePlayer = cursor.getInt(cursor.getColumnIndex("Qtde"));
                comercialExp.dataVencimento = nuloToVazio(cursor.getString(cursor.getColumnIndex("DataVencto")));
                comercialExp.dependencia1 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Dependencia1")));
                comercialExp.dependencia2 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Dependencia2")));
                comercialExp.dependencia3 = nuloToVazio(cursor.getString(cursor.getColumnIndex("Dependencia3")));

                listaComercialDeterminados.add(comercialExp);
            } while (cursor.moveToNext());
        }
    }

    public void horariosComercialDeterminado() {
        if (null != listaComercialDeterminados && listaComercialDeterminados.size() > 0 && !listaComercialDeterminados.isEmpty()) {
            for (ComercialExp comercialExp : listaComercialDeterminados) {
                String arquivo = validarExistenciaDoVideo(comercialExp.arquivo);
                if (null != arquivo) {
                    semanaAndHorario(comercialExp.semana1, comercialExp.horario1, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana2, comercialExp.horario2, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana3, comercialExp.horario3, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana4, comercialExp.horario4, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana5, comercialExp.horario5, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana6, comercialExp.horario6, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana7, comercialExp.horario7, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana8, comercialExp.horario8, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana9, comercialExp.horario9, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana10, comercialExp.horario10, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana11, comercialExp.horario11, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana12, comercialExp.horario12, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana13, comercialExp.horario13, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana14, comercialExp.horario14, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana15, comercialExp.horario15, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana16, comercialExp.horario16, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana17, comercialExp.horario17, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana18, comercialExp.horario18, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana19, comercialExp.horario19, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana20, comercialExp.horario20, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana21, comercialExp.horario21, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana22, comercialExp.horario22, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana23, comercialExp.horario23, arquivo, comercialExp.titulo, comercialExp.categoria);
                    semanaAndHorario(comercialExp.semana24, comercialExp.horario24, arquivo, comercialExp.titulo, comercialExp.categoria);
                } else {
                    registrarLog.escrever(" 99 Desprezada com tipo. O arquivo " + comercialExp.arquivo + " não foi encontrada em nenhum diretório");
                }
            }
        }
    }

    private void semanaAndHorario(String semana, String horario, String arquivo, String titulo, int categoria) {
        if (semana.equals("1") || semana.equals("0")) {
            linhasPlaylistDet.add("det|" + horario + "|" + semana + "|" + arquivo + "|" + titulo + "|" + categoria + "|1|2");
        }
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
                String titulo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                String dataVencto = nuloToVazio(cursor.getString(cursor.getColumnIndex("DataVencto")));
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
                String titulo = nuloToVazio(cursor.getString(cursor.getColumnIndex("Titulo")));
                String dataVencto = nuloToVazio(cursor.getString(cursor.getColumnIndex("DataVencto")));
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

    private String nuloToVazio(String texto) {
        if (null == texto) {
            return "";
        }
        return texto;
    }
}
