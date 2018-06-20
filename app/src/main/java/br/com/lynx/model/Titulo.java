package br.com.lynx.model;

import java.util.Date;

public class Titulo {
	
	private String tipo;
	private int numero;
	private int parcela;
	private Date dataEmissao;
	private Date dataVencimento;
	private int diasAtraso;
	private double valor;
	private double valorAtual;
	private String divisao;
	
	public Titulo(String tipo, int numero, int parcela, Date dataEmissao, Date dataVencimento, int diasAtraso, double valor, double valorAtual, String divisao){
		this.tipo = tipo;
		this.numero = numero;
		this.parcela = parcela;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.diasAtraso = diasAtraso;
		this.valor = valor;
		this.valorAtual = valorAtual;
		this.divisao = divisao;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public int getNumero(){
		return numero;
	}
	
	public int getParcela(){
		return parcela;
	}
	
	public Date getDataEmissao(){
		return dataEmissao;
	}
	
	public Date getDataVencimento(){
		return dataVencimento;
	}
	
	public int getDiasAtraso(){
		return diasAtraso;
	}
	
	public double getValor(){
		return valor;
	}
	
	public double getValorAtual(){
		return valorAtual;
	}
	
	public String getDivisao(){
		return divisao;
	}
	
	public boolean isVencido(){
		Date data = new Date();

		return dataVencimento.before(new Date());
	}
}


