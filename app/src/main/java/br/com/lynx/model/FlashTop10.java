package br.com.lynx.model;

/**
 * Created by Rogerio on 27/04/2016.
 */
public class FlashTop10 {

    private int clienteID;
    private String razaoSocial;
    private float mestAtual;
    private float trimestreAnterior;
    private float anoAnterior;
    private int rota;

    public FlashTop10(int clienteID, String razaoSocial, int rota, float mestAtual, float trimestreAnterior, float anoAnterior){
        this.clienteID = clienteID;
        this.razaoSocial = razaoSocial;
        this.mestAtual = mestAtual;
        this.trimestreAnterior = trimestreAnterior;
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

    public float getTrimestreAnterior(){
        return trimestreAnterior;
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
