package br.com.lynx.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.domain.Avaliacao;
import br.com.lynx.domain.PDVNGResumo;
import br.com.lynx.domain.Questao;

/**
 * Created by Rogerio on 02/08/2017.
 */

public class PDVNGDAO {
    private final String SQLINSERTPDVNGCLIENTE = "INSERT INTO PDVResposta VALUES (?, ?, ? , ?, ?, ?)";
    private final String SQLSELECTPDVNG = "SELECT DISTINCT QuestionarioID, TipoID, ClienteID, Data FROM PDVResposta";

    private Context context;

    public PDVNGDAO(Context context) {
        this.context = context;
    }

    public void savePDVNG(int clienteID, int tipo, List<Questao> perguntas) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = dateFormat.format(new Date());

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTPDVNGCLIENTE);

        db.beginTransaction();
        try {
            db.delete("PDVResposta", "ClienteID = ?", new String[]{String.valueOf(clienteID)});

            for (Questao item : perguntas) {
                insert.bindLong(1, item.getQuestionarioID());
                insert.bindLong(2, tipo);
                insert.bindLong(3, clienteID);
                insert.bindLong(4, item.getID());
                insert.bindString(5, item.getResposta());
                insert.bindString(6, data);
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public List<Avaliacao> listaAvaliacoesPorCliente() {
        List<Avaliacao> retorno = new ArrayList<Avaliacao>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Cursor c = SQLiteHelper.getDB(context).rawQuery(SQLSELECTPDVNG, null);
        try {
            if (c.moveToFirst()) {
                do {
                    try {
                        Avaliacao avaliacao = new Avaliacao(c.getInt(0), c.getInt(1), c.getInt(2), dateFormat.parse(c.getString(3)));
                        retorno.add(avaliacao);

                        String[] columns = {"PerguntaID, Resposta"};
                        String whereClause = "QuestionarioID = ? AND TipoID = ? AND ClienteID = ? AND Data = ?";
                        String[] whereArgs = {String.valueOf(c.getInt(0)), String.valueOf(c.getInt(1)), String.valueOf(c.getInt(2)), c.getString(3)};

                        Cursor respostas = SQLiteHelper.getCursor(context, "PDVResposta", columns, whereClause, whereArgs);
                        if (respostas.moveToFirst()) {
                            do {
                                Questao questao = new Questao(respostas.getInt(0), "", 0, c.getInt(0), c.getString(4));
                                questao.setResposta(respostas.getString(1));
                                avaliacao.getRespostas().add(questao);

                            } while (respostas.moveToNext());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (c.moveToNext());
            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return retorno;
    }

    public List<PDVNGResumo> listaPDVNGResumo() {
        List<PDVNGResumo> retorno = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Cursor c = SQLiteHelper.getCursor(context, "viwPDGNGResposta");
        try {
            if (c.moveToFirst()) {
                do {
                    Date dataPesquisa = (Date)dateFormat.parse(c.getString(4));
                    Date dataAtual = dateFormat.parse(dateFormat.format(new Date()));

                    if (dataPesquisa.equals(dataAtual))
                      retorno.add(new PDVNGResumo(c.getInt(2), c.getString(3), c.getInt(1), dateFormat.parse(c.getString(4)), c.getFloat(5)));
                } while (c.moveToNext());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return retorno;
    }
}
