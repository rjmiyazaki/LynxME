package br.com.lynx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.PesquisaCerveja;
import br.com.lynx.model.RespostaCerveja;
import br.com.lynx.util.MathUtil;

/**
 * Created by Rogerio on 12/04/2016.
 */
public class PesquisaCervejaDAO {

    private Context context;

    public PesquisaCervejaDAO(Context context) {
        this.context = context;
    }

    public void save(PesquisaCerveja pesquisa) {
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        SQLiteHelper.getDB(context).beginTransaction();

        try {
            SQLiteHelper.delete(context, "PesquisaCerveja", "ClienteID = ?",
                    new String[]{String.valueOf(pesquisa.getClienteID())});

            for (RespostaCerveja resposta : pesquisa.getRespostas()) {
                values.put("ClienteID", pesquisa.getClienteID());
                values.put("Marca", resposta.getMarca());
                values.put("Caixas", resposta.getCaixas());
                values.put("Preco", resposta.getPreco());
                values.put("Data", dateFormat.format(pesquisa.getData()).toString());

                SQLiteHelper.insert(context, "PesquisaCerveja", values);
            }

            SQLiteHelper.getDB(context).setTransactionSuccessful();
        } finally {
            if (SQLiteHelper.getDB(context).inTransaction())
                SQLiteHelper.getDB(context).endTransaction();
            SQLiteHelper.closeDB(context);
        }
    }

    public void registraTransmissao(int clienteID){
        SQLiteHelper.getDB(context)
                .execSQL("UPDATE Cliente SET PesqCervEnviado = 1 WHERE ClienteID = " + clienteID);
        SQLiteHelper.closeDB(context);
    }
}


