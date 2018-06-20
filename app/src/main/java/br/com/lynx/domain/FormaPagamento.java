package br.com.lynx.domain;

import android.content.Context;

import br.com.lynx.dao.FormaPagamentoDAO;

/**
 * Created by Rogerio on 12/07/2017.
 */

public class FormaPagamento {

    private FormaPagamentoDAO daoObject;
    private int formaPagamentoID;
    private String descricao;
    private int parcelas;
    private int prazoMedio;
    private float taxa;
    private float adicional;

    public FormaPagamento(Context context){
        daoObject = new FormaPagamentoDAO(context);
    }

    public FormaPagamento(Context context, int id, String descricao, int parcelas, int prazoMedio, float taxa, float adicional){
        formaPagamentoID = id;
        this.descricao = descricao;
        this.parcelas = parcelas;
        this.prazoMedio = prazoMedio;
        this.taxa = taxa;
        this.adicional = adicional;

        daoObject = new FormaPagamentoDAO(context);
    }

    public int getFormaPagamentoID() {
        return formaPagamentoID;
    }

    public void setFormaPagamentoID(int formaPagamentoID) {
        this.formaPagamentoID = formaPagamentoID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setParcelas(int value){
        this.parcelas = value;
    }

    public int getParcelas(){
        return parcelas;
    }

    public void setPrazoMedio(int value){
        this.prazoMedio = value;
    }

    public int getPrazoMedio(){
        return prazoMedio;
    }

    public void setTaxa(float value){
        this.taxa = value;
    }

    public float getTaxa(){
        return taxa;
    }

    public void setAdicional(float value){
        this.adicional = value;
    }

    public float getAdicional(){
        return adicional;
    }

    public void load(){
        daoObject.load(this);
    }

    public void load(int id){
        this.formaPagamentoID = id;
        daoObject.load(this);
    }
}
