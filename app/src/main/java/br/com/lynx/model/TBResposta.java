package br.com.lynx.model;

import java.util.Date;

import android.content.Context;
import br.com.lynx.dao.PesquisaDAO;

public class TBResposta {

	private int clienteID;
	private Date data;
	private int tipo;
	private int checkouts;
	private int checkoutid;
	private int perguntaid;
	private String resposta;
	private PesquisaDAO daoObject;
	
	public TBResposta(Context context, int clienteID, Date data, int tipo, int checkouts, int checkoutid, int perguntaid, String resposta){
		this.clienteID = clienteID;
		this.data = data;
		this.tipo = tipo;
		this.checkouts = checkouts;
		this.checkoutid = checkoutid;
		this.perguntaid = perguntaid;
		this.resposta = resposta;
		
		daoObject = new PesquisaDAO(context);
	}
	
	public int getClienteID(){
		return clienteID;
	}
	
	public Date getData(){
		return data;
	}
	
	public int getTipo(){
		return tipo;
	}
	
	public int getCheckouts(){
		return checkouts;
	}
	
	public int getCheckoutID(){
		return checkoutid;
	}
	
	public int getPerguntaID(){
		return perguntaid;
	}
	
	public String getResposta(){
		return resposta;
	}
	
	public void save(){
		daoObject.save(this);
	}
}
