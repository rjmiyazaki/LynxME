package br.com.lynx.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.TBResposta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class PesquisaDAO {

	private Context context;

	public PesquisaDAO(Context context) {
		this.context = context;
	}

	public void save(TBResposta resposta) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		ContentValues values = new ContentValues();

		values.put("ClienteID", resposta.getClienteID());
		values.put("Data", dateFormat.format(resposta.getData()));
		values.put("Tipo", resposta.getTipo());
		values.put("Checkouts", resposta.getCheckouts());
		values.put("CheckoutID", resposta.getCheckoutID());
		values.put("PerguntaID", resposta.getPerguntaID());
		values.put("Resposta", resposta.getResposta());

		SQLiteHelper.getDB(context).beginTransaction();
		try {
			SQLiteHelper.insert(context, "TBQuestionario", values);
			values.clear();
			SQLiteHelper.getDB(context).setTransactionSuccessful();
		} catch (SQLException e) {
			Log.i("LynxME", "Erro na hora de inserir a resposta: "
			    + e.getMessage().toString());
		} finally {
			if (SQLiteHelper.getDB(context).inTransaction())
				SQLiteHelper.getDB(context).endTransaction();

			SQLiteHelper.closeDB(context);
		}
	}

	public List<String> listaRespostasParaExportacao() {
		List<String> respostas = new ArrayList<String>();
		String linha;

		Cursor p = SQLiteHelper.getCursor(context, "TBQuestionario");
		try {
			if (p.moveToFirst())
				do {
					linha = p.getString(0) + "|" + p.getString(1) + "|" + p.getString(2)
					    + "|" + p.getString(3) + "|" + p.getString(4) + "|"
					    + p.getString(5) + "|" + p.getString(6) + "\r\n";
					
					respostas.add(linha);
				} while (p.moveToNext());
		} finally {
			p.close();
		}

		return respostas;
	}
}
