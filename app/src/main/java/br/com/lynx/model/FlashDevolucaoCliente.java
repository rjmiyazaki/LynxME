package br.com.lynx.model;

import java.util.Date;

/**
 * Created by Rogerio on 05/05/2016.
 */
public class FlashDevolucaoCliente {
    private String cliente;
    private String rota;
    private String motivo;
    private Date dataPedido;
    private Date dataDevolucao;
    private String tipo;
    private float caixas;
    private float valor;

    public FlashDevolucaoCliente(String cliente, String rota, String motivo, Date dataPedido, Date dataDevolucao, String tipo, float caixas, float valor){
        this.cliente = cliente;
        this.rota = rota;
        this.motivo = motivo;
        this.dataPedido = dataPedido;
        this.dataDevolucao = dataDevolucao;
        this.tipo = tipo;
        this.caixas = caixas;
        this.valor = valor;
    }

    public String getCliente() {
        return cliente;
    }

    public String getRota() {
        return rota;
    }

    public String getMotivo() {
        return motivo;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public String getTipo() {
        return tipo;
    }

    public float getCaixas() {
        return caixas;
    }

    public float getValor() {
        return valor;
    }
}
