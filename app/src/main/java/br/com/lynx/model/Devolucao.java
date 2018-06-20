package br.com.lynx.model;


import java.util.Date;

public class Devolucao {
	private Date data;
	private String itemid;
	private String item;
	private int quantidade;
	private int devolvida;
	private String motivo;

	public Devolucao(Date date, String itemid, String item, int quantidade,
	    int devolvida, String motivo) {
		this.data = date;
		this.itemid = itemid;
		this.item = item;
		this.quantidade = quantidade;
		this.devolvida = devolvida;
		this.motivo = motivo;
	}

	public Date getData() {
		return data;
	}

	public String getItem() {
		return itemid + " - " + item;
	}
	
	public int getQuantidade(){
		return quantidade;
	}
	
	public int getDevolvida(){
		return devolvida;
	}
	
	public String getMotivo(){
		return motivo;
	}

}
