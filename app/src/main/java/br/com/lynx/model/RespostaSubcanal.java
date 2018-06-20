package br.com.lynx.model;

/**
 * Created by Rogerio on 18/04/2016.
 */
public class RespostaSubcanal {

    private int cliente;
    private String subcanal;

    public RespostaSubcanal(int cliente, String subcanal){
        this.cliente = cliente;
        this.subcanal = subcanal;
    }

    public int getCliente() {
        return cliente;
    }

    public String getSubcanal(){
        return subcanal;
    }
}
