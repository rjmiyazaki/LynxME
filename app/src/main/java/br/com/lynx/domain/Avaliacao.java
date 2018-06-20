package br.com.lynx.domain;

import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.lynx.misc.Communication;

/**
 * Created by Rogerio on 28/07/2017.
 */

public class Avaliacao {

    private int questionarioID;
    private int tipoID;
    private int clienteID;
    private Date data;
    private List<Questao> respostas;

    public Avaliacao(int questionarioID, int tipoID, int clienteID, Date data){
        this.questionarioID = questionarioID;
        this.tipoID = tipoID;
        this.clienteID = clienteID;
        this.data = data;
        respostas = new ArrayList<>();
    }

    public int getTipo(){ return tipoID; }

    public int getClienteID() { return clienteID;}

    public Date getDate() { return data; }

    public List<Questao> getRespostas(){
        return respostas;
    }

    public String getNomeArquivo(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = dateFormat.format(this.data);
        String nomeArquivo = (String) DateFormat.format("ddMMyyyykkmms", new Date());
        nomeArquivo = "PDVNG-" + String.valueOf(clienteID) + nomeArquivo + ".out";

        return nomeArquivo;
    }
}
