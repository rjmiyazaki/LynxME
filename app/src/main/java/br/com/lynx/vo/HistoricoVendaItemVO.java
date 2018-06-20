package br.com.lynx.vo;

import br.com.lynx.domain.Produto;

public class HistoricoVendaItemVO {

	private Produto item;
	private int quantidade;
	private float desconto;
	private float valorLiquido;

	public Produto getItem() {
		return item;
	}

	public void setItem(Produto item) {
		this.item = item;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getDesconto() {
		return desconto;
	}

	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}

	public float getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(float valorLiquido) {
		this.valorLiquido = valorLiquido;
	}
}