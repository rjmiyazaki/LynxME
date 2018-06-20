package br.com.lynx.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.domain.TipoPedido;

public class TipoPedidoDAO {

	private Context context;

	public TipoPedidoDAO(Context context) {
		this.context = context;
	}

	public List<TipoPedido> getList() {
		List<TipoPedido> lista = new ArrayList<TipoPedido>();

		Cursor c = SQLiteHelper.getCursor(this.context, "TipoPedido");
		try {
			if (c.moveToFirst()) {
				do {
					lista.add(new TipoPedido(context, c.getInt(0), c.getString(1)));
				} while (c.moveToNext());
			}

		} finally {
			c.close();			
			SQLiteHelper.closeDB(context);
		}

		return lista;
	}
	
	public void load(TipoPedido tipoPedido){
		Cursor c = SQLiteHelper.getCursor(this.context, "TipoPedido", "TipoPedidoID = ?", new String[]{String.valueOf(tipoPedido.getTipoPedidoID())});
		try
		{
		  if (c.moveToFirst()){
		  	tipoPedido.setDescricao(c.getString(1));		  	
		  }
		}
		finally{
			c.close();
			SQLiteHelper.closeDB(this.context);
		}
	}

}
