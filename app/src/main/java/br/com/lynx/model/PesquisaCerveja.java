package br.com.lynx.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.lynx.dao.PesquisaCervejaDAO;

/**
 * Created by Rogerio on 12/04/2016.
 */
public class PesquisaCerveja {

    private int clienteID;
    private List<RespostaCerveja> respostas;
    private Date data;

    private PesquisaCervejaDAO daoObject;

    public PesquisaCerveja(Context context, int clienteID){
        this.clienteID = clienteID;
        respostas = new ArrayList<RespostaCerveja>();
        data = new Date();

        daoObject = new PesquisaCervejaDAO(context);
    }

    public List<RespostaCerveja> getRespostas(){
        return respostas;
    }

    public int getClienteID(){
        return this.clienteID;
    }

    public Date getData(){
        return data;
    }

    public void setData(Date data){
        this.data = data;
    }

    public void save(){
        daoObject.save(this);
    }

    public void registraTransmissao(){
        daoObject.registraTransmissao(clienteID);
    }

}
