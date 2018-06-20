package br.com.lynx.model;

/**
 * Created by rogerio on 18/07/2016.
 */
public class ItemCampanha {

    private String campanha;
    private String categoria;

    public ItemCampanha(String campanha, String categoria){
        this.campanha = campanha;
        this.categoria = categoria;
    }

    public String getCampanha(){
        return this.campanha;
    }

    public String getCategoria(){
        return this.categoria;
    }

}
