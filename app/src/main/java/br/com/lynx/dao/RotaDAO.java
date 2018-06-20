package br.com.lynx.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.vo.RotaVO;

public class RotaDAO {

	private Context context;
	
	public RotaDAO(Context context) {
		this.context = context;
	}

	public List<RotaVO> retornaListaRota() {
		List<RotaVO> rotas = new ArrayList<RotaVO>();
		RotaVO rota;
		Cursor c = SQLiteHelper.getCursor(context, "Rota");
		try {
			if (c.moveToFirst())
				do {
					rota = new RotaVO();
					rota.setRotaID(c.getInt(0));
					rota.setDescricao(c.getString(1));
					rotas.add(rota);
				} while (c.moveToNext());
		} finally {
			SQLiteHelper.closeDB(context);
			c.close();
		}
		return rotas;
	}
}
