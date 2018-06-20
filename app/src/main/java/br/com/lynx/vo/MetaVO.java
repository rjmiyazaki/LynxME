package br.com.lynx.vo;

public class MetaVO {

	private String categoria;
	private double meta;
	private double realizado;
	private double tendencia;
	private double porcentagem;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}

	public double getRealizado() {
		return realizado;
	}

	public void setRealizado(double realizado) {
		this.realizado = realizado;
	}

	public double getTendencia() {
		return tendencia;
	}

	public void setTendencia(double tendencia) {
		this.tendencia = tendencia;
	}

	public double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(double porcentagem) {
		this.porcentagem = porcentagem;
	}
}