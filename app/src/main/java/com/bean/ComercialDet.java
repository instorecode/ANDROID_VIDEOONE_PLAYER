package com.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by usuario on 31/03/2015.
 */
public class ComercialDet {

    public String nomeComericial;
    public String tituloComercial;
    public String categoriaComercial;
    public String velocidadeComercial;
    public String duracaoComercial;
    public String diretorioComercial;
    public int tipoCategoria;

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
