package br.com.lynx.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.vo.EquipamentoVO;

public class EquipamentoDAO {

	private Context context;

	public EquipamentoDAO(Context context) {

		this.context = context;
	}

	public List<EquipamentoVO> carregaEquipamentos(int clienteID) {

		List<EquipamentoVO> equipamento = new ArrayList<EquipamentoVO>();
		EquipamentoVO equipamentoVO;
		ClienteDAO cliente = new ClienteDAO(context);

		Cursor c = SQLiteHelper.getCursor(context, "EquipamentoActivity", "ClienteID = ?", new String[] { String.valueOf(clienteID) });

		try {

			if (c.moveToFirst())
				do {

					equipamentoVO = new EquipamentoVO();

					equipamentoVO.setCliente(cliente.retornaCliente(c.getInt(0)));
					equipamentoVO.setGeko(c.getString(1));

					equipamento.add(equipamentoVO);

				} while (c.moveToNext());

		} finally {
			c.close();
		}
		SQLiteHelper.closeDB(context);

		return equipamento;
	}

	public void save(EquipamentoVO equipamento) {

		ContentValues values = new ContentValues();

		values.put("ClienteID", equipamento.getCliente().getClienteID());
		values.put("Geko", equipamento.getGeko());

		SQLiteHelper.insert(context, "EquipamentoActivity", values);
		SQLiteHelper.closeDB(context);
	}

	public void delete(EquipamentoVO equipamento) {
		
		SQLiteHelper.getDB(context).execSQL("DELETE FROM Equipamento WHERE ClienteID = " + equipamento.getCliente().getClienteID() + " AND Geko = '" + equipamento.getGeko() + "'");
		SQLiteHelper.closeDB(context);
	}
}