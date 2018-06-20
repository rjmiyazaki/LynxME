package br.com.lynx.domain;

import android.content.Context;
import br.com.lynx.dao.TipoPedidoDAO;

public class TipoPedido {

	private int tipoPedidoID;
	private String descricao;
	private TipoPedidoDAO daoObject;

	public TipoPedido(Context context) {
		daoObject = new TipoPedidoDAO(context);
	}

	public TipoPedido(Context context, int tipoPedidoID, String descricao) {
		daoObject = new TipoPedidoDAO(context);

		this.tipoPedidoID = tipoPedidoID;
		this.descricao = descricao;
	}

	public int getTipoPedidoID() {
		return tipoPedidoID;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String value){
		descricao = value;
	}

	public void load(int id) {
		this.tipoPedidoID = id;
		
		daoObject.load(this);
	}
}
