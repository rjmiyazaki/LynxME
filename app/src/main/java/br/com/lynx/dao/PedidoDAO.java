package br.com.lynx.dao;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.text.format.DateFormat;
import android.util.Log;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.domain.FormaPagamento;
import br.com.lynx.domain.PedidoItem;
import br.com.lynx.domain.Pedido;
import br.com.lynx.domain.Produto;
import br.com.lynx.vo.RegistroNaoVendaVO;
import br.com.lynx.vo.ResumoPedidoVO;

public class PedidoDAO {

	private Context context;

	public PedidoDAO(Context context) {
		this.context = context;
	}

	public void save(Pedido pedido) {
		long id = 0;
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		values.put("CLienteID", pedido.getCliente().getClienteID());
		values.put("TipoPedidoID", pedido.getTipoPedido().getTipoPedidoID());
		values.put("FormaPagamentoID", pedido.getFormaPagamento().getFormaPagamentoID());		
		values.put("DataAbertura", dateFormat.format(pedido.getDataAbertura()));
		values.put("DataEncerramento", dateFormat.format(pedido.getDataEncerramento()));
		values.put("ValorPedido", pedido.getValorPedido());
		values.put("Obs", pedido.getObs());
		values.put("Latitude", pedido.getLatitude());
		values.put("Longitude", pedido.getLatitude());
		values.put("VendedorID", pedido.getVendedorID());

		SQLiteHelper.getDB(context).beginTransaction();
		try {
			id = SQLiteHelper.insert(context, "PedidoVenda", values);
			pedido.setPedidoID(id);

			values.clear();

			inserirItemPedido(id, pedido.getItens());
			SQLiteHelper.getDB(context).setTransactionSuccessful();
		} catch (SQLException e) {
			Log.i("LynxME", "Erro na hora de inserir o pedido: " + e.getMessage().toString());
		} finally {
			if (SQLiteHelper.getDB(context).inTransaction())
				SQLiteHelper.getDB(context).endTransaction();

			SQLiteHelper.closeDB(context);
		}
	}

	public boolean update(Pedido pedido) {
		boolean retorno = false;
		long id = pedido.getPedidoID();
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		values.put("PedidoID", id);
		values.put("CLienteID", pedido.getCliente().getClienteID());
		values.put("TipoPedidoID", pedido.getTipoPedido().getTipoPedidoID());
		values.put("FormaPagamentoID", pedido.getFormaPagamento().getFormaPagamentoID());
		values.put("DataAbertura", dateFormat.format(pedido.getDataAbertura()));
		values.put("DataEncerramento", dateFormat.format(pedido.getDataEncerramento()));
		values.put("DataTransmissao", dateFormat.format(pedido.getDataTransmissao()));
		values.put("ValorPedido", pedido.getValorPedido());
		values.put("Obs", pedido.getObs());
		values.put("Latitude", pedido.getLatitude());
		values.put("Longitude", pedido.getLatitude());
		values.put("VendedorID", pedido.getVendedorID());

		SQLiteHelper.getDB(context).beginTransaction();
		try {
			SQLiteHelper.update(context, "PedidoVenda", values, "PedidoID = ?", new String[] { String.valueOf(id) });
			SQLiteHelper.delete(context, "PedidoVendaItem", "PedidoID = ?", new String[] { String.valueOf(id) });
			values.clear();
			inserirItemPedido(id, pedido.getItens());
			SQLiteHelper.getDB(context).setTransactionSuccessful();
		} catch (SQLException e) {
			retorno = false;
		} finally {
			if (SQLiteHelper.getDB(context).inTransaction())
				SQLiteHelper.getDB(context).endTransaction();
			SQLiteHelper.closeDB(context);
			retorno = true;
		}
		return retorno;
	}

	public void registrarNaoVenda(Pedido pedido, int motivoID) {
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		values.put("CLienteID", pedido.getCliente().getClienteID());
		values.put("MotivoID", motivoID);
		values.put("DataRegistro", dateFormat.format(pedido.getDataEncerramento()));
		values.put("Latitude", pedido.getLatitude());
		values.put("Longitude", pedido.getLatitude());

		try {
			SQLiteHelper.insert(context, "RegistroNaoVenda", values);
			values.clear();
		} finally {
			SQLiteHelper.closeDB(context);
		}
	}

	public void inserirPedido(Pedido pedido) {
		long id = 0;
		ContentValues values = new ContentValues();
		
		values.put("CLienteID", pedido.getCliente().getClienteID());		
		values.put("TipoPedidoID", pedido.getTipoPedido().getTipoPedidoID());
		values.put("FormaPagamentoID", pedido.getFormaPagamento().getFormaPagamentoID());
		values.put("DataAbertura", DateFormat.format("ddMMyyyy kk:mm", pedido.getDataAbertura()).toString());
		values.put("DataEncerramento", DateFormat.format("ddMMyyyy kk:mm", pedido.getDataEncerramento()).toString());
		values.put("ValorPedido", pedido.getValorPedido());
		values.put("Obs", pedido.getObs());
		values.put("Latitude", pedido.getLatitude());
		values.put("Longitude", pedido.getLatitude());
		values.put("VendedorID", pedido.getVendedorID());

		SQLiteHelper.getDB(context).beginTransaction();
		try {
			id = SQLiteHelper.insert(context, "PedidoVenda", values);
			pedido.setPedidoID(id);

			values.clear();

			inserirItemPedido(id, pedido.getItens());
			SQLiteHelper.getDB(context).setTransactionSuccessful();
		} catch (SQLException e) {
			Log.i("LynxME", "Erro na hora de inserir o pedido: " + e.getMessage().toString());
		} finally {
			if (SQLiteHelper.getDB(context).inTransaction())
				SQLiteHelper.getDB(context).endTransaction();

			SQLiteHelper.closeDB(context);
		}
	}

	public void inserirItemPedido(long id, List<PedidoItem> pedidoItens) {
		ContentValues values = new ContentValues();

		for (PedidoItem pedidoItem : pedidoItens) {
			values.put("PedidoID", id);
			values.put("ProdutoID", pedidoItem.getItem().getProdutoID());
			values.put("Quantidade", pedidoItem.getQuantidade());
			values.put("PrecoUnitario", pedidoItem.getPrecoUnitario());
			values.put("Desconto", pedidoItem.getDesconto() + pedidoItem.getDescontoAdicional());
			values.put("Acrescimo", pedidoItem.getAcrescimo());
			values.put("ValorDesconto", pedidoItem.getValorDesconto());
			values.put("ValorAcrescimo", pedidoItem.getValorAcrescimo());
			values.put("PrecoLiquido", pedidoItem.getPrecoLiquido());
			values.put("ValorTotal", pedidoItem.getValorTotal());
			values.put("Repasse", pedidoItem.isRepasse());
			try {
				SQLiteHelper.insert(context, "PedidoVendaItem", values);
			} catch (SQLException e) {
				Log.i("LynxME", "Erro na hora de inserir os itens do pedido " + id + ", " + e.getMessage().toString());
			}
		}
	}

	public List<Pedido> listaPedidos() {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		Pedido pedido;
		PedidoItem pedidoItem;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String produtoid;
		
		Cursor p = SQLiteHelper.getCursor(context, "PedidoVenda");
		try {
			if (p.moveToFirst())
				do {
					pedido = new Pedido(context, p.getInt(11));
					pedido.setPedidoID(p.getInt(0));
					pedido.getCliente().load(p.getInt(1));
					pedido.getTipoPedido().load(p.getInt(2));
					pedido.getFormaPagamento().load(p.getInt(3));

					if (!p.isNull(4))
						pedido.setDataAbertura(dateFormat.parse(p.getString(4)));

					if (!p.isNull(5))
						pedido.setDataEncerramento(dateFormat.parse(p.getString(5)));

					if (!p.isNull(6))
						pedido.setDataTransmissao(dateFormat.parse(p.getString(6)));

					pedido.setObs(p.getString(8));

					Cursor i = SQLiteHelper.getCursor(context, "PedidoVendaItem", new String[] { "ProdutoID", "Quantidade", "PrecoUnitario", "Desconto", "Acrescimo", "Repasse" },
					    "PedidoID = ?", new String[] { String.valueOf(p.getInt(0)) });
					try {
						if (i.moveToFirst())
							do {
								
								produtoid = i.getString(0);
								pedidoItem = new PedidoItem(context);

								pedidoItem.getItem().setProdutoID(produtoid);
								pedidoItem.getItem().load();

								pedidoItem.setQuantidade(i.getInt(1));
								pedidoItem.setPrecoUnitario(i.getFloat(2));
								pedidoItem.setDesconto(i.getFloat(3));
								pedidoItem.setAcrescimo(i.getFloat(4));
								
								if(i.getString(5).equals("0"))
									pedidoItem.setRepasse(false);
								else
									pedidoItem.setRepasse(true);

								pedido.getItens().add(pedidoItem);
							} while (i.moveToNext());
					} finally {
						i.close();
					}

					pedidos.add(pedido);

				} while (p.moveToNext());
		} catch (Exception e) {
			Log.i("LynxME", e.getMessage().toString());

		} finally {
			p.close();
		}

		SQLiteHelper.closeDB(context);

		return pedidos;
	}

	public boolean deletePedido(int pedidoID) {
		boolean retorno = false;
		SQLiteHelper.getDB(context).beginTransaction();
		try {
			SQLiteHelper.delete(context, "PedidoVendaItem", "PedidoID = ?", new String[] { String.valueOf(pedidoID) });
			SQLiteHelper.delete(context, "PedidoVenda", "PedidoID = ?", new String[] { String.valueOf(pedidoID) });
			SQLiteHelper.getDB(context).setTransactionSuccessful();
		} catch (SQLException e) {
			Log.i("LynxME", "Erro ao deletar o pedido: " + pedidoID + ", " + e.getMessage().toString());
		} finally {
			if (SQLiteHelper.getDB(context).inTransaction())
				SQLiteHelper.getDB(context).endTransaction();
			SQLiteHelper.closeDB(context);
			retorno = true;
		}
		return retorno;
	}

	public FormaPagamento retornaFormaPagamento(int formaPagamentoID) {
		FormaPagamento formaPagamento = new FormaPagamento(this.context);
		Cursor c;
		c = SQLiteHelper.getCursor(context, "FormaPagamento", "FormaPagamentoID = ?", new String[] { String.valueOf(formaPagamento) });
		try {
			if (c.moveToFirst()) {
				formaPagamento.setFormaPagamentoID(formaPagamentoID);
				formaPagamento.setDescricao(c.getString(1));
			}
		} finally {
			c.close();
		}
		return formaPagamento;
	}

	public List<ResumoPedidoVO> resumoPedido() {
		List<ResumoPedidoVO> resumoPedido = new ArrayList<ResumoPedidoVO>();
		Cursor c;
		float positivados, clientes, positivacao, naoVenda, caixas, pedidosNaoTransmitidos;
		String positivacaoInfo;
				
		NumberFormat formatPercent = new DecimalFormat("0.00%");
		NumberFormat formatInteger = new DecimalFormat("0"); 
		
		String naoTransmitidos = "SELECT COUNT(*) FROM PedidoVenda WHERE DataEncerramento IS NOT NULL AND DataTransmissao IS NULL";

		c = SQLiteHelper.getCursor(context, "Cliente", new String[] { "ClienteID" });
		try {
			clientes = c.getCount();									
		} finally {
			c.close();
		}
		
		c = SQLiteHelper.getCursor(context, true, "PedidoVenda", new String[] { "ClienteID" });
		try {
			positivados = c.getCount();	
		} finally {
			c.close();
		}
		
		if (clientes > 0){
			positivacao = positivados / clientes;								
		}
		else
			positivacao = 0;	
		
		c = SQLiteHelper.getCursor(context, true, "RegistroNaoVenda", new String[] { "ClienteID" });
		try {
			naoVenda = c.getCount();			
		} finally {
			c.close();
		}		
		
		c = SQLiteHelper.getCursor(context, "RetornaCaixas");
		try {
			if (c.moveToFirst())
				caixas = c.getFloat(0);				
			else
				caixas = 0;
		} finally {
			c.close();
		}
		
		c = SQLiteHelper.execSQL(context, naoTransmitidos);
		try{
			if(c.moveToFirst())
				pedidosNaoTransmitidos = c.getFloat(0);				
			else
				pedidosNaoTransmitidos = 0;
		}finally{
			c.close();
		}
		
		SQLiteHelper.closeDB(context);
		
		positivacaoInfo = formatInteger.format(positivados) + "/" + formatPercent.format(positivacao);
		
		resumoPedido.add(new ResumoPedidoVO("Cliente", formatInteger.format(clientes), "customers"));		
		resumoPedido.add(new ResumoPedidoVO("Positivaçãoo", positivacaoInfo, "orders"));
		resumoPedido.add(new ResumoPedidoVO("Não venda", formatInteger.format(naoVenda), "nosale"));
		resumoPedido.add(new ResumoPedidoVO("Caixas", caixas, "sku"));
		resumoPedido.add(new ResumoPedidoVO("Não transmitidos", formatInteger.format(pedidosNaoTransmitidos), "data_transfer"));

		return resumoPedido;
	}

	public List<RegistroNaoVendaVO> listaNaoVenda() {
		List<RegistroNaoVendaVO> lista = new ArrayList<RegistroNaoVendaVO>();
		RegistroNaoVendaVO naoVenda;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Cursor c = SQLiteHelper.getCursor(context, "RegistroNaoVenda");
		try {
			if (c.moveToFirst())
				do {
					if (!c.isNull(4))
						naoVenda = new RegistroNaoVendaVO(context, c.getInt(0), c.getInt(1), c.getInt(2), dateFormat.parse(c.getString(3)), dateFormat.parse(c.getString(4)));
					else
						naoVenda = new RegistroNaoVendaVO(context, c.getInt(0), c.getInt(1), c.getInt(2), dateFormat.parse(c.getString(3)), null);

					lista.add(naoVenda);
				} while (c.moveToNext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
		}

		SQLiteHelper.closeDB(context);

		return lista;
	}

	public void registroTransmissaoNaoVenda(int id) {
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		values.put("DataTransmissao", dateFormat.format(new Date()));

		try {
			SQLiteHelper.update(context, "RegistroNaoVenda", values, "ID = ?", new String[] { String.valueOf(id) });
			values.clear();
		} finally {
			SQLiteHelper.closeDB(context);
		}
	}

	public List<PedidoItem> retornaListaItens(int pedidoID) {
		List<PedidoItem> itens = new ArrayList<PedidoItem>();
		PedidoItem pedidoItem;

		Cursor i = SQLiteHelper.getCursor(context, "PedidoVendaItem", new String[] { "ProdutoID", "Quantidade", "PrecoUnitario", "Desconto", "Repasse" },
		    "PedidoID = ?", new String[] { String.valueOf(pedidoID) });
		try {
			if (i.moveToFirst())
				do {
					pedidoItem = new PedidoItem(context);
					//pedidoItem.getItem().load(i.getInt(0));
					pedidoItem.getItem().setProdutoID(i.getString(0));
					pedidoItem.getItem().load();
					pedidoItem.setQuantidade(i.getInt(1));
					pedidoItem.setPrecoUnitario(i.getFloat(2));
					pedidoItem.setDesconto(i.getFloat(3));

					if (i.getString(4).equals("0"))
						pedidoItem.setRepasse(false);
					else
						pedidoItem.setRepasse(true);

					itens.add(pedidoItem);
				} while (i.moveToNext());
		} finally {
			i.close();
		}

		SQLiteHelper.closeDB(context);
		return itens;
	}

	// Retorna lista de pedidos não transmitidos
	public List<Pedido> retornaListaPedidoNaoTransmitidos() {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		Pedido pedido;
		PedidoItem pedidoItem;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String produtoid;

		Cursor p = SQLiteHelper.getCursor(context, "PedidoVenda");
		try {
			if (p.moveToFirst())
				do {
					pedido = new Pedido(context, p.getInt(11));
					pedido.setPedidoID(p.getInt(0));
					pedido.getCliente().load(p.getInt(1));
					pedido.getTipoPedido().load(p.getInt(2));
					pedido.getFormaPagamento().load(p.getInt(3));

					if (!p.isNull(4))
						pedido.setDataAbertura(dateFormat.parse(p.getString(4)));

					if (!p.isNull(5))
						pedido.setDataEncerramento(dateFormat.parse(p.getString(5)));

					if (!p.isNull(6))
						pedido.setDataTransmissao(dateFormat.parse(p.getString(6)));

					pedido.setObs(p.getString(8));

					Cursor i = SQLiteHelper.getCursor(context, "PedidoVendaItem", new String[] { "ProdutoID", "Quantidade", "PrecoUnitario", "Desconto", "Repasse" },
							"PedidoID = ?", new String[] { String.valueOf(p.getInt(0)) });
					try {
						if (i.moveToFirst())
							do {

								produtoid = i.getString(0);
								pedidoItem = new PedidoItem(context);
								pedidoItem.getItem().setProdutoID(produtoid);
								pedidoItem.getItem().load();
								pedidoItem.setQuantidade(i.getInt(1));
								pedidoItem.setPrecoUnitario(i.getFloat(2));
								pedidoItem.setDesconto(i.getFloat(3));

								if(i.getString(4).equals("0"))
									pedidoItem.setRepasse(false);
								else
									pedidoItem.setRepasse(true);

								pedido.getItens().add(pedidoItem);
							} while (i.moveToNext());
					} finally {
						i.close();
					}

					if (pedido.getSituacao() == "Finalizado")
    					pedidos.add(pedido);
				} while (p.moveToNext());
		} catch (Exception e) {
			Log.i("LynxME", e.getMessage().toString());
		} finally {
			p.close();
		}

		SQLiteHelper.closeDB(context);

		return pedidos;
	}

	// Retorna lista de não vendas não transmitidos
	public List<RegistroNaoVendaVO> retornaListaNaoVendaNaoTransmitidos() {
		List<RegistroNaoVendaVO> lista = new ArrayList<RegistroNaoVendaVO>();
		RegistroNaoVendaVO naoVenda;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Cursor c = SQLiteHelper.getCursor(context, "RegistroNaoVenda");
		try {
			if (c.moveToFirst())
				do {
					if (!c.isNull(4))
						naoVenda = new RegistroNaoVendaVO(context, c.getInt(0), c.getInt(1), c.getInt(2), dateFormat.parse(c.getString(3)), dateFormat.parse(c.getString(4)));
					else
						naoVenda = new RegistroNaoVendaVO(context, c.getInt(0), c.getInt(1), c.getInt(2), dateFormat.parse(c.getString(3)), null);

					if (!naoVenda.isEnviado())
					  lista.add(naoVenda);
				} while (c.moveToNext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
		}

		SQLiteHelper.closeDB(context);

		return lista;
	}

	public List<String> retornaListaPedagioCampanhaColgateGrupo1(){
		List<String> lista = new ArrayList<String>();

		Cursor c = SQLiteHelper.getCursor(context, "ItemPedagioCampanhaColgate", "Grupo = 1");
		try{
			if (c.moveToFirst()){
				do {
					lista.add(c.getString(0));
				} while (c.moveToNext());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
		}

		SQLiteHelper.closeDB(context);

		return lista;
	}

	public List<String> retornaListaPedagioCampanhaColgateGrupo2(){
		List<String> lista = new ArrayList<String>();

		Cursor c = SQLiteHelper.getCursor(context, "ItemPedagioCampanhaColgate", "Grupo = 2");
		try{
			if (c.moveToFirst()){
				do {
					lista.add(c.getString(0));
				} while (c.moveToNext());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
		}

		SQLiteHelper.closeDB(context);

		return lista;
	}

	public List<String> retornaListaPedagioCampanhaColgateGrupo3(){
		List<String> lista = new ArrayList<String>();

		Cursor c = SQLiteHelper.getCursor(context, "ItemPedagioCampanhaColgate", "Grupo = 3");
		try{
			if (c.moveToFirst()){
				do {
					lista.add(c.getString(0));
				} while (c.moveToNext());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}

		return lista;
	}

	public double retornaMinimoCampanhaColgate(){
		double result = 0;

		Cursor c = SQLiteHelper.getCursor(context, "ConfiguracaoCampanhaColgate");
		try{
			if (c.moveToFirst()){
				result = c.getDouble(0);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}

		return result;
	}

	public double retornaMaximoCampanhaColgate(){
		double result = 0;

		Cursor c = SQLiteHelper.getCursor(context, "ConfiguracaoCampanhaColgate");
		try{
			if (c.moveToFirst()){
				result = c.getDouble(1);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}

		return result;
	}

	public double retornaDescontoCampanhaColgate(){
		double result = 0;

		Cursor c = SQLiteHelper.getCursor(context, "ConfiguracaoCampanhaColgate");
		try{
			if (c.moveToFirst()){
				result = c.getDouble(2);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}

		return result;
	}




}
