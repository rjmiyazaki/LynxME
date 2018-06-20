package br.com.lynx.model;

/**
 * Created by Rogerio on 03/05/2016.
 */
public class FlashCampanha {

    private String categoria;
    private int meta;
    private int realizado;
    private int falta;
    private int diario;
    private float percentual;

    public FlashCampanha(String categoria, int meta, int realizado, int falta, int diario, float percentual){
        this.categoria = categoria;
        this.meta = meta;
        this.realizado = realizado;
        this.falta = falta;
        this.diario = diario;
        this.percentual = percentual;
    }

    public String getCategoria(){
        return categoria;
    }

    public int getMeta(){
        return meta;
    }

    public int getRealizado(){
        return realizado;
    }

    public int getFalta(){
        return falta;
    }

    public int getDiario(){
        return diario;
    }

    public float getPercentual(){
        return percentual;
    }
}
