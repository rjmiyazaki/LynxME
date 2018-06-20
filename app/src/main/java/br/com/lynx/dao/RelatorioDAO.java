package br.com.lynx.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.vo.MaioresQuedasVO;
import br.com.lynx.vo.MetaVO;
import br.com.lynx.model.Campanha;

public class RelatorioDAO {

	private Context context;

	public RelatorioDAO(Context context) {

		this.context = context;
	}

	public List<MetaVO> listaMeta() {

		List<MetaVO> meta = new ArrayList<MetaVO>();
		MetaVO metaVO;

		String sql = "SELECT * FROM Meta Me";

		Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);

		SQLiteHelper.getCursor(context, "Categoria");

		try {
			if (c.moveToFirst())
				do {

					metaVO = new MetaVO();

					metaVO.setCategoria(c.getString(0));
					metaVO.setMeta(c.getDouble(1));
					metaVO.setRealizado(c.getDouble(2));
					metaVO.setTendencia(c.getDouble(3));
					metaVO.setPorcentagem(c.getDouble(4));

					meta.add(metaVO);

				} while (c.moveToNext());

		} finally {
			c.close();
		}
		SQLiteHelper.closeDB(context);

		return meta;
	}

	public List<Campanha> listaCampanha() {
		List<Campanha> meta = new ArrayList<Campanha>();
		Campanha campanha;

		String sql = "SELECT * FROM Campanha";

		Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);
		try {
			if (c.moveToFirst())
				do {

					campanha = new Campanha(c.getString(0), c.getInt(1), c.getInt(2));
					meta.add(campanha);

				} while (c.moveToNext());

		} finally {
			c.close();
		}
		SQLiteHelper.closeDB(context);

		return meta;
	}

	public List<MaioresQuedasVO> listaMaioresQuedas(int clienteID) {

		List<MaioresQuedasVO> maioresQuedas = new ArrayList<MaioresQuedasVO>();
		MaioresQuedasVO maioresQuedasVO;
		ProdutoDAO produtoDAO = new ProdutoDAO(context);

		Cursor c = SQLiteHelper.getCursor(context, "MaioresQuedas",
		    "ClienteID = ?", new String[] { String.valueOf(clienteID) });

		try {

			if (c.moveToFirst())
				do {

					maioresQuedasVO = new MaioresQuedasVO();

					// maioresQuedasVO.setItem(produtoDAO.retornaProduto(c.getString(1)));
					maioresQuedasVO.setVolumeAnterior(c.getInt(2));
					maioresQuedasVO.setVolumeAtual(c.getInt(3));

					maioresQuedas.add(maioresQuedasVO);

				} while (c.moveToNext());
		} finally {
			c.close();
			SQLiteHelper.closeDB(context);
		}

		return maioresQuedas;
	}
}