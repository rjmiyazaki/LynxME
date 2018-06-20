package br.com.lynx.vo;

public class ResumoPedidoVO {

	private String descricao;
	private String texto;
	private String nomeImagem;
	
	public ResumoPedidoVO(String descricao, float valor){
		this.descricao = descricao;
		this.texto = String.valueOf(valor);
	}
	
	public ResumoPedidoVO(String descricao, float valor, String nomeImagem){
		this.descricao = descricao;
		this.texto = String.valueOf(valor);
		this.nomeImagem = nomeImagem;
	}
	
	public ResumoPedidoVO(String descricao, String texto){
		this.descricao = descricao;
		this.texto = texto;
	}
	
	public ResumoPedidoVO(String descricao, String texto, String nomeImagem){
		this.descricao = descricao;
		this.texto = texto;
		this.nomeImagem = nomeImagem;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getValor() {
		return texto;
	}  	
	
	public String getNomeImagem(){
		return nomeImagem;
	}
}
