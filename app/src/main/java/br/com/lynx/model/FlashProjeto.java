package br.com.lynx.model;

/**
 * Created by Rogerio on 29/04/2016.
 */
public class FlashProjeto {

    private int clienteID;
    private String razaoSocial;
    private float volume;
    private float anoAnterior;
    private float variacao;
    private int rota;

    public FlashProjeto(int clienteID, String razaoSocial, int rota, float volume, float anoAnterior, float variacao){
        this.clienteID = clienteID;
        this.razaoSocial = razaoSocial;
        this.volume = volume;
        this.variacao = variacao;
        this.anoAnterior = anoAnterior;
        this.rota = rota;
    }

    public int getClienteID(){
        return this.clienteID;
    }

    public String getRazaoSocial(){
        return razaoSocial;
    }

    public float getVolume(){
        return volume;
    }

    public float getVariacao(){
        return variacao;
    }

    public float getAnoAnterior(){
        return anoAnterior;
    }

    public int getRota(){
        return rota;
    }

    public String getCliente(){
        return String.valueOf(clienteID) + " - " + razaoSocial;
    }
}
