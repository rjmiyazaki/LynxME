package br.com.lynx.model;

import br.com.lynx.domain.Produto;

public class PedidoItem {
	
	private Produto item;
	private int quantidade;
	private float precoUnitario;
	private float desconto;
	private boolean repasse;
	
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

	public float getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(float precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public float getDesconto() {
		return desconto;
	}

	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}

	public float getValorDesconto() {
		return getPrecoUnitario() * (getDesconto() / 100);
	}

	public float getPrecoLiquido() {
		return getPrecoUnitario() - getValorDesconto();
	}

	public float getValorTotal() {
		return getQuantidade() * getPrecoLiquido();
	}

	public void setRepasse(boolean repasse) {
		this.repasse = repasse;
	}

	public boolean isRepasse() {
		return repasse;
	}
	
	public void validaDesconto() throws Exception{
		if (item.getDescontoMaximo() < desconto)
			throw new Exception("O desconto dado é maior que o desconto permitido.");

		if (desconto >= 100)
			throw new Exception("O desconto dado é maior que o desconto permitido.");
	}
}
