package br.com.lynx.model;

/**
 * Created by Rogerio on 27/04/2016.
 */
public class FlashQueda {

    private int clienteID;
    private String razaoSocial;
    private float mestAtual;
    private float anoAnterior;
    private float queda;
    private int rota;

    public FlashQueda(int clienteID, String razaoSocial, int rota, float mestAtual, float anoAnterior, float queda){
        this.clienteID = clienteID;
        this.razaoSocial = razaoSocial;
        this.mestAtual = mestAtual;
        this.queda = queda;
        this.anoAnterior = anoAnterior;
        this.rota = rota;
    }

    public int getClienteID(){
        return this.clienteID;
    }

    public String getRazaoSocial(){
        return razaoSocial;
    }

    public float getMestAtual(){
        return mestAtual;
    }

    public float getQueda(){
        return queda;
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
