package br.com.lynx.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.content.Context;
import br.com.lynx.dao.InfoPanelDAO;

public class InfoPanel {

	private int clientes;
	private int positivados;
	private int positivacao;
	private int caixas;
	private int naoEnviados;
	private int pedidos;
	private InfoPanelDAO daoObject; 
	
	private static InfoPanel instance;

	private InfoPanel(Context context) {
		daoObject = new InfoPanelDAO(context);
	}

	public static InfoPanel getInstance(Context context) {
		if (instance == null) {
			instance = new InfoPanel(context);
		}

		return instance;
	}

	public void load() {
		daoObject.load(this);
	}
	
	public void setValues(int clientes, int positivados, int positivacao, int caixas, int pedidos, int naoEnviados){
		this.clientes = clientes;
		this.positivados = positivados;
		this.positivacao = positivacao;
		this.caixas = caixas;
		this.pedidos = pedidos;
		this.naoEnviados = naoEnviados;
	}
	
	public String getClientes(){
		return String.valueOf(clientes);
	}
	
	public String getPositivacao(){
		NumberFormat formatInteger = new DecimalFormat("0");
		DecimalFormat formatPercent = new DecimalFormat("#0,00%");
		
		String positivacaoInfo = formatInteger.format(positivados) + "/" + formatPercent.format(positivacao);
		
		return positivacaoInfo;	
	}
	
	public String getCaixas(){
		return String.valueOf(caixas);
	}
	
	public String getNaoEnviados(){
		return String.valueOf(naoEnviados);
	}
	
	public String getNumeroPedidos(){
		return String.valueOf(pedidos);
	}
}
