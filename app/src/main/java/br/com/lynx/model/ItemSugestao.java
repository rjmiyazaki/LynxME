package br.com.lynx.model;

/**
 * Created by rogerio on 28/10/2016.
 */

public class ItemSugestao {

    private int subcanalID;
    private String produtoID;
    private String grupo;
    private Boolean destacado;

    public ItemSugestao(int subcanalID, String produtoID, String grupo, Boolean destacado){
        this.subcanalID = subcanalID;
        this.produtoID = produtoID;
        this.grupo = grupo;
        this.destacado = destacado;
    }

    public int getSubcanalID() {
        return subcanalID;
    }

    public String getProdutoID() {
        return produtoID;
    }

    public String getGrupo() {
        return grupo;
    }

    public Boolean getDestacado() {
        return destacado;
    }
}
