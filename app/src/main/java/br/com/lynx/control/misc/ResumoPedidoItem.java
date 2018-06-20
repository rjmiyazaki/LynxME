package br.com.lynx.control.misc;

public final class ResumoPedidoItem {

	private String descricao;
	private float valor;
	private String formato;
	
	public ResumoPedidoItem(String descricao, float valor, String formato){
		this.descricao = descricao;
		this.valor = valor;
		this.formato = formato;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public float getValor(){
		return valor;
	}
	
	public String getFormato(){
		return formato;
	}
}
