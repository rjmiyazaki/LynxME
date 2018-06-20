package br.com.lynx.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.util.Log;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.vo.HistoricoVendaItemVO;
import br.com.lynx.vo.HistoricoVendaVO;
import br.com.lynx.model.ItemHistoricoVenda;

public class HistoricoVendaDAO {

	private Context context;

	public HistoricoVendaDAO(Context context) {
		this.context = context;
	}
	
	public void loadItens(int clienteID, Date data, List<ItemHistoricoVenda> itens){
		ItemHistoricoVenda item;
		String dataParametro = (String) DateFormat.format("yyyyMMdd", data);
		
		Cursor c = SQLiteHelper.getCursor(context, "HistoricoVendaItem", "ClienteID = ? AND DataPedido = ?", new String[] { String.valueOf(clienteID),  dataParametro }); 
		try{
			if (c.moveToFirst())
				do{
					item = new ItemHistoricoVenda(c.getString(2), c.getString(3), c.getInt(4), c.getFloat(5), c.getFloat(6), c.getFloat(7), c.getFloat(8), c.getFloat(9));
					
				  itens.add(item);
				}while (c.moveToNext());
				
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}
	}

	public List<HistoricoVendaVO> retornaHistoricoVenda(int clienteID) {

		List<HistoricoVendaVO> historico = new ArrayList<HistoricoVendaVO>();
		HistoricoVendaVO historicoVO;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		int pedidoID = 0;

		Cursor c = SQLiteHelper.getCursor(context, "HistoricoVenda", "ClienteID = ?", new String[] { String.valueOf(clienteID) });

		try {
			if (c.moveToFirst())
				do {

					historicoVO = new HistoricoVendaVO();

					pedidoID = c.getInt(0);

					historicoVO.setPedidoID(pedidoID);
					historicoVO.setClienteID(c.getInt(1));

					if (!c.isNull(2))
						historicoVO.setDataPedido(dateFormat.parse(c.getString(2)));

					historicoVO.setValor(c.getFloat(3));

					historico.add(historicoVO);

				} while (c.moveToNext());
		} catch (Exception e) {
			Log.i("LynxME", e.getMessage().toString());
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}

		return historico;
	}

	public List<HistoricoVendaItemVO> retornaHistoricoVendaItem(int pedidoID) {

		List<HistoricoVendaItemVO> itens = new ArrayList<HistoricoVendaItemVO>();
		HistoricoVendaItemVO historicoItemVO;
		ProdutoDAO produto = new ProdutoDAO(context);
		
		Cursor i = SQLiteHelper.getCursor(context, "HistoricoVendaItem", "PedidoID = ?", new String[] { String.valueOf(pedidoID) });

		try {
			if (i.moveToFirst())
				do {

					historicoItemVO = new HistoricoVendaItemVO();
					
					//historicoItemVO.setItem(produto.retornaProduto(i.getString(1)));
					historicoItemVO.setQuantidade(i.getInt(2));
					historicoItemVO.setDesconto(i.getFloat(3));
					historicoItemVO.setValorLiquido(i.getFloat(4));

					itens.add(historicoItemVO);
				} while (i.moveToNext());
		} finally {
			i.close();
			SQLiteHelper.closeDB(context);
		}

		return itens;
	}

}


