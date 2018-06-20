package br.com.lynx.model;

public class TabelaPreco {
	
	private int id;
	private String descricao;
	private float valor;
	
	public TabelaPreco(int id, float valor){
		this.id = id;
		this.valor = valor;
	}
	
	public TabelaPreco(int id, String descricao, float valor){
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
	}
	
	public int getID(){
		return id;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public float getValor(){
		return valor;
	}
}
