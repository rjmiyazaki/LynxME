package br.com.lynx.vo;

import br.com.lynx.domain.Cliente;

public class EquipamentoVO {

	private Cliente cliente;
	private String geko;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getGeko() {
		return geko;
	}

	public void setGeko(String geko) {
		this.geko = geko;
	}

}