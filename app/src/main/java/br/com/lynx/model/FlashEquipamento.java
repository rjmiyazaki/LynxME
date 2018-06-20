package br.com.lynx.model;

/**
 * Created by Rogerio on 04/05/2016.
 */
public class FlashEquipamento {

    private String dia;
    private String tipo;
    private int quantidade;
    private int metaBatida;
    private float percentual;

    public FlashEquipamento(String dia, String tipo, int quantidade, int metaBatida, float percentual){
        this.dia = dia;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.metaBatida = metaBatida;
        this.percentual = percentual;
    }

    public String getDia(){
        return dia;
    }

    public String getTipo(){
        return tipo;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public int getMetaBatida(){
        return metaBatida;
    }

    public float getPercentual(){
        return percentual;
    }
}
