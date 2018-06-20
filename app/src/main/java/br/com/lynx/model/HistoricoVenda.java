package br.com.lynx.model;

import android.content.Context;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.lynx.dao.ClienteDAO;
import br.com.lynx.dao.HistoricoVendaDAO;
import br.com.lynx.model.ItemHistoricoVenda;

public class HistoricoVenda {
  private Date data;
  private float valor;
  private List<ItemHistoricoVenda> itens;
  private Context context;
  private HistoricoVendaDAO daoObject;
  
  public HistoricoVenda(Context context, Date data, float valor){
  	this.context = context;
  	daoObject = new HistoricoVendaDAO(context);
  	
  	this.data = data;
  	this.valor = valor;
  	itens = new ArrayList<ItemHistoricoVenda>();
  }
  
  public void loadItens(int clienteID){
  	daoObject.loadItens(clienteID, data, itens);
  }
  
  public List<ItemHistoricoVenda> getItens(){
  	return itens;
  }
  
  public Date getData(){
  	return data;
  }
  
  public String getValorCurrency(){
  	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
  	
  	return currencyFormat.format(this.valor);
  }
}
