package br.com.lynx.model;

/**
 * Created by Rogerio on 06/05/2016.
 */
public class FlashBig4 {
    private String item;
    private float meta;
    private float realizado;
    private float faltante;
    private float tendencia;

    public FlashBig4(String item, float meta, float realizado, float faltante, float tendencia) {
        this.item = item;
        this.meta = meta;
        this.realizado = realizado;
        this.faltante = faltante;
        this.tendencia = tendencia;
    }

    public String getItem() {
        return item;
    }

    public float getMeta() {
        return meta;
    }

    public float getRealizado() {
        return realizado;
    }

    public float getFaltante() {
        return faltante;
    }

    public float getTendencia() {
        return tendencia;
    }

}
