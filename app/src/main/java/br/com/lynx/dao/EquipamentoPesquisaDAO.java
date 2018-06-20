package br.com.lynx.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.EquipamentoPesquisa;

public class EquipamentoPesquisaDAO {

	private Context context;

	public EquipamentoPesquisaDAO(Context context) {
		this.context = context;
	}

	@SuppressWarnings("null")
	@SuppressLint("SimpleDateFormat")
	public void save(EquipamentoPesquisa resposta, int clienteID) {
		String[] args = null;
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		values.put("ClienteID", clienteID);
		values.put("Geko", resposta.getGeko());
		values.put("Data", dateFormat.format(new Date()));
		values.put("Presente", resposta.getPresente());

		args[0] = String.valueOf(clienteID);

		SQLiteHelper.getDB(context).beginTransaction();
		try {
			SQLiteHelper.delete(context, "EquipamentoPesquisa", "ClienteID", args);
			SQLiteHelper.insert(context, "EquipamentoPesquisa", values);
			SQLiteHelper.getDB(context).setTransactionSuccessful();
		} catch (SQLException e) {
			Log.i("LynxME", "Erro na hora de inserir o pedido: " + e.getMessage().toString());
		} finally {
			if (SQLiteHelper.getDB(context).inTransaction())
				SQLiteHelper.getDB(context).endTransaction();

			SQLiteHelper.closeDB(context);
		}
	}

	public List<EquipamentoPesquisa> getEquipamentosPesquisa() throws ParseException {
		List<EquipamentoPesquisa> equipamentos = new ArrayList<EquipamentoPesquisa>();
		EquipamentoPesquisa equipamento;
		Cursor c = SQLiteHelper.getCursor(this.context, "EquipamentoPesquisa");

		try {
			if (c.moveToFirst())
				do {
					equipamento = new EquipamentoPesquisa(c.getString(1), c.getString(2), c.getString(4));

					equipamentos.add(equipamento);
				} while (c.moveToNext());
		} finally {
			c.close();
			SQLiteHelper.closeDB(this.context);
		}

		return equipamentos;
	}

}
