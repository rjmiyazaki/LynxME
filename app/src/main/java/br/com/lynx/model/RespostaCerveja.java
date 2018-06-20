package br.com.lynx.model;

import java.util.Date;

/**
 * Created by Rogerio on 12/04/2016.
 */
public class RespostaCerveja {

    private String marca;
    private float caixas;
    private float preco;

    public RespostaCerveja(String marca, float caixas, float preco){
        this.marca = marca;
        this.caixas = caixas;
        this.preco = preco;
    }

    public String getMarca(){
        return this.marca;
    }

    public float getCaixas(){
        return this.caixas;
    }

    public float getPreco(){
        return this.preco;
    }
}
