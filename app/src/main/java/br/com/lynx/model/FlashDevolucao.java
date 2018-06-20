package br.com.lynx.model;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashDevolucao {

    private String motivo;
    private int quantidade;
    private float caixas;
    private float participacao;

    public FlashDevolucao(String motivo, int quantidade, float caixas, float participacao){
        this.motivo = motivo;
        this.quantidade = quantidade;
        this.caixas = caixas;
        this.participacao = participacao;
    }

    public String getMotivo(){
        return motivo;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public float getCaixas(){
        return caixas;
    }

    public float getParticipacao(){
        return participacao;
    }
}
