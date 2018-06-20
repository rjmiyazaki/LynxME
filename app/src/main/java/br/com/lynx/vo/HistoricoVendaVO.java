package br.com.lynx.vo;

import java.util.Date;
import java.util.List;

public class HistoricoVendaVO {

	private int pedidoID;
	private int clienteID;
	private Date dataPedido;
	private float valor;
	private List<HistoricoVendaItemVO> item;

	public int getPedidoID() {
		return pedidoID;
	}

	public void setPedidoID(int pedidoID) {
		this.pedidoID = pedidoID;
	}

	public int getClienteID() {
		return clienteID;
	}

	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public List<HistoricoVendaItemVO> getItem() {
	  return item;
  }

	public void setItem(List<HistoricoVendaItemVO> item) {
	  this.item = item;
  }
}