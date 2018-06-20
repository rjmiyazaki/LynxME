package br.com.lynx.model;

/**
 * Created by Rogerio on 22/04/2016.
 */
public class FlashMeta {

    private int id;
    private String categoria;
    private double meta;
    private double realizado;
    private double falta;
    private double tendencia;
    private double metaDiaria;
    private double variavel;
    private double percentual;

    public FlashMeta(int id, String categoria, double meta, double realizado, double falta, double tendencia, double metaDiaria, double variavel, double percentual){
        this.categoria = categoria;
        this.meta = meta;
        this.realizado = realizado;
        this.falta = falta;
        this.tendencia = tendencia;
        this.metaDiaria = metaDiaria;
        this.variavel = variavel;
        this.percentual = percentual;
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public String getCategoria(){
        return categoria;
    }

    public double getMeta(){
        return meta;
    }

    public double getRealizado(){
        return realizado;
    }

    public double getFalta(){
        return falta;
    }

    public double getTendencia(){
        return tendencia;
    }

    public double getMetaDiaria(){
        return metaDiaria;
    }

    public double getVariavel(){
        return variavel;
    }

    public double getPercentual(){
        return percentual;
    }
}
