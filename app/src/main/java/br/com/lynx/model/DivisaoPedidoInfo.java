package br.com.lynx.model;

/**
 * Created by rogerio on 13/10/2016.
 */

public class DivisaoPedidoInfo {
    private String nome;
    private Double faturamento;
    private Double volume;
    private Double cobertura;

    public DivisaoPedidoInfo(String nome, Double faturamento, Double volume, Double cobertura){
        this.nome = nome;
        this.faturamento = faturamento;
        this.volume = volume;
        this.cobertura = cobertura;
    }

    public String getNome(){
        return nome;
    }

    public Double getFaturamento(){
        return faturamento;
    }

    public Double getVolume(){
        return volume;
    }

    public Double getCobertura(){
        return cobertura;
    }
}
