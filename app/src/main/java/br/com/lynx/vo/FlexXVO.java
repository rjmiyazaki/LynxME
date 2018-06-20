package br.com.lynx.vo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;
import br.com.lynx.model.Configuracao;

public class FlexXVO {

	protected Context context;
	private String clienteID;
	private String latitude;
	private String longitude;
	private String horaAbertura;
	private String motivoNaoVenda;
	private String pedidoID;
	private String formaPagamento;
	private String valorPedido;
	private String quantidadeItens;

	public FlexXVO(Context context) {
		this.context = context;
	}

	public String getVendedorID() {
		String result;
		
		result = Configuracao.getInstance(context).getVendedorID();
		
		while (result.equalsIgnoreCase(""))
		  result = Configuracao.getInstance(context).getVendedorID();
		
		return result;
	}

	public String getClienteID() {
		return clienteID;
	}

	public void setClienteID(String clienteID) {
		this.clienteID = clienteID;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getData() {
		return (String) DateFormat.format("dd/MM/yyyy", new Date());
	}

	public String getHoraAbertura() {
		return horaAbertura;
	}

	public void setHoraAbertura(String dataAbertura) {
		this.horaAbertura = dataAbertura;
	}

	public String getHoraFechamento() {
		return (String) DateFormat.format("kk:mm:ss", new Date());
	}

	public String getMotivoNaoVenda() {
		return motivoNaoVenda;
	}

	public void setMotivoNaoVenda(String motivoNaoVenda) {
		this.motivoNaoVenda = motivoNaoVenda;
	}

	public String getPedidoID() {
		return pedidoID;
	}

	public void setPedidoID(String pedidoID) {
		this.pedidoID = pedidoID;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getValorPedido() {

		NumberFormat nbFormat = new DecimalFormat("###,###.##");
		return nbFormat.format(Double.parseDouble(valorPedido));
	}

	public void setValorPedido(String valorPedido) {
		this.valorPedido = valorPedido;
	}

	public String getQuantidadeItens() {
		return quantidadeItens;
	}

	public void setQuantidadeItens(String quantidadeItens) {
		this.quantidadeItens = quantidadeItens;
	}
}