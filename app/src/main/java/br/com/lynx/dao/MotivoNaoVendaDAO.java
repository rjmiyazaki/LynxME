package br.com.lynx.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.vo.MotivoNaoVendaVO;

public class MotivoNaoVendaDAO {

	private Context context;

	public MotivoNaoVendaDAO(Context context) {
		this.context = context;
	}

	public List<MotivoNaoVendaVO> getList() {
		List<MotivoNaoVendaVO> lista = new ArrayList<MotivoNaoVendaVO>();

		Cursor c = SQLiteHelper.getCursor(this.context, "MotivoNaoVenda");
		try {
			if (c.moveToFirst()) {
				do {
					if (c.getInt(2) == 1)
					  lista.add(new MotivoNaoVendaVO(c.getInt(0), c.getString(1), c.getInt(3) == 1, c.getString(4)));
				} while (c.moveToNext());
			}
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}

		return lista;
	}
}