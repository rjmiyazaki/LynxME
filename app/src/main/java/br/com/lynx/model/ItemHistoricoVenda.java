package br.com.lynx.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class ItemHistoricoVenda {
  
	private String itemID;
	private String item;
	private int quantidade;
	private float valorUnitario;
	private float desconto;
	private float valorDesconto;
	private float valorLiquido;
	private float valorTotal;
	private NumberFormat currencyFormat;
	private DecimalFormat percentFormat;
	private Locale local;
	
	public ItemHistoricoVenda(String itemID, String item, int quantidade, float valorUnitario, float desconto, float valorDesconto, float valorLiquido, float valorTotal){
		this.itemID = itemID;
		this.item = item;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.desconto = desconto;
		this.valorDesconto = valorDesconto;
		this.valorLiquido = valorLiquido;
		this.valorTotal = valorTotal;
		
		local = new Locale("pt", "BR");
		
		currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		percentFormat = new DecimalFormat("#,##0,00%", new DecimalFormatSymbols(local));
	}
	
	public String getFormattedItem(){
		return String.valueOf(this.itemID) + " - " + item; 
	}
	
	public String getFormattedQuantidade(){
		return String.valueOf(this.quantidade);
	}
	
	public String getFormattedValorUnitario(){
		return currencyFormat.format(this.valorUnitario);
	}
	
	public String getFormattedValorDesconto(){
		return currencyFormat.format(this.valorDesconto);
	}
	
	public String getFormattedValorLiquido(){
		return currencyFormat.format(this.valorLiquido);
	}
	
	public String getFormattedValorTotal(){
		return currencyFormat.format(this.valorTotal);
	}
	
	public String getFormattedDesconto(){
		return percentFormat.format(this.desconto);
	}		
}
