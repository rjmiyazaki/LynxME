package br.com.lynx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.Configuracao;

public class ConfiguracaoDAO {
	
	private Context context;

	public ConfiguracaoDAO(Context context) {
		this.context = context;
	}

	public void load(Configuracao configuracao) {
		Cursor c = SQLiteHelper.getCursor(context, "Configuracao");
		try {
			if (c.getCount() > 0) {
				c.moveToFirst();
				configuracao.setVendedorID(c.getString(0));
				configuracao.setNomeVendedor(c.getString(1));
				try {
					configuracao.setDivisaoID(c.getInt(2));
				}
				catch (Exception e){
					configuracao.setDivisaoID(0);
				}

				configuracao.setStartTime(c.getString(3));
				configuracao.setEndTime(c.getString(4));
			} else
				configuracao.setVendedorID("");
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}
	}

	public void save(Configuracao configuracao) {
		ContentValues values;

		values = new ContentValues();
		values.put("VendedorID", configuracao.getVendedorID());

		SQLiteHelper.delete(context, "Configuracao");
		SQLiteHelper.insert(context, "Configuracao", values);
		SQLiteHelper.closeDB(context);
	}
}