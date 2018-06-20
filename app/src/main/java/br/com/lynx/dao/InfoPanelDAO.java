package br.com.lynx.dao;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.InfoPanel;

public class InfoPanelDAO {

	private Context context;

	public InfoPanelDAO(Context context) {
		this.context = context;
	}

	public void load(InfoPanel infoPanel) {
		Cursor c;
		float positivados, clientes, positivacao, naoVenda, caixas, pedidosNaoTransmitidos, pedidos;
		String positivacaoInfo;

		String naoTransmitidos = "SELECT COUNT(*) FROM PedidoVenda WHERE DataEncerramento IS NOT NULL AND DataTransmissao IS NULL";

		c = SQLiteHelper
		    .getCursor(context, "Cliente", new String[] { "ClienteID" });
		try {
			clientes = c.getCount();
		} finally {
			c.close();
		}

		c = SQLiteHelper.getCursor(context, true, "PedidoVenda",
		    new String[] { "PedidoID" });
		try {
			pedidos = c.getCount();
		} finally {
			c.close();
		}

		c = SQLiteHelper.getCursor(context, true, "PedidoVenda",
		    new String[] { "ClienteID" });
		try {
			positivados = c.getCount();
		} finally {
			c.close();
		}

		if (clientes > 0) {
			positivacao = positivados / clientes * 100;
		} else
			positivacao = 0;

		c = SQLiteHelper.getCursor(context, true, "RegistroNaoVenda",
		    new String[] { "ClienteID" });
		try {
			naoVenda = c.getCount();
		} finally {
			c.close();
		}

		c = SQLiteHelper.getCursor(context, "RetornaCaixas");
		try {
			if (c.moveToFirst())
				caixas = c.getInt(0);
			else
				caixas = 0;
		} finally {
			c.close();
		}

		c = SQLiteHelper.execSQL(context, naoTransmitidos);
		try {
			if (c.moveToFirst())
				pedidosNaoTransmitidos = c.getInt(0);
			else
				pedidosNaoTransmitidos = 0;
		} finally {
			c.close();
		}

		SQLiteHelper.closeDB(context);

		infoPanel.setValues(Math.round(clientes), Math.round(positivados), Math.round(positivacao), Math.round(caixas), Math.round(pedidos),
				Math.round(pedidosNaoTransmitidos));
	}
}
