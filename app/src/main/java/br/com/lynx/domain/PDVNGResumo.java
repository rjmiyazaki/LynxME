package br.com.lynx.domain;

import java.util.Date;

/**
 * Created by Rogerio on 21/08/2017.
 */

public class PDVNGResumo {

    private int clienteID;
    private String cliente;
    private String tipo;
    private Date data;
    private float nota;

    public PDVNGResumo(int clienteID, String cliente, int tipo, Date data, float nota){
        this.clienteID = clienteID;
        this.cliente = cliente;
        this.data = data;
        this.nota = nota;

        if (tipo == 1)
            this.tipo = "PDV-NG Vendedor";
        else if (tipo == 2)
            this.tipo = "PDV-NG Promotor";
        else if (tipo == 3)
            this.tipo = "PDV-NG Supervisor";
        else if (tipo == 4)
            this.tipo = "Avaliação de rotina";
        else if (tipo == 5)
            this.tipo = "Check list de vendedor";
    }

    public int getClienteID() { return clienteID; }

    public String getCliente() { return cliente; }

    public String getTipo() { return tipo; }

    public Date getData() { return data; }

    public float getNota() { return nota; }
}

