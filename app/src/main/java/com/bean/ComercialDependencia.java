package com.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by usuario on 23/03/2015.
 */
public class ComercialDependencia {
    private String nomeDependencia;
    private String tituloDependencia;
    private String categoriaDependencia;

    public String getNomeDependencia() {
        return nomeDependencia;
    }

    public void setNomeDependencia(String nomeDependencia) {
        this.nomeDependencia = nomeDependencia;
    }

    public String getTituloDependencia() {
        return tituloDependencia;
    }

    public void setTituloDependencia(String tituloDependencia) {
        this.tituloDependencia = tituloDependencia;
    }

    public String getCategoriaDependencia() {
        return categoriaDependencia;
    }

    public void setCategoriaDependencia(String categoriaDependencia) {
        this.categoriaDependencia = categoriaDependencia;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
