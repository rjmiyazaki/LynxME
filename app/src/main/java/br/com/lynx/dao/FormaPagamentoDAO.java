package br.com.lynx.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.domain.FormaPagamento;

public class FormaPagamentoDAO {

    private Context context;

    public FormaPagamentoDAO(Context context) {
        this.context = context;
    }

    public FormaPagamento retornaFormaPagamento(SQLiteDatabase db, int formaPagamentoID) {
        FormaPagamento formaPagamento = new FormaPagamento(context);
        Cursor c = SQLiteHelper.getCursor(this.context, "FormaPagamento", "FormaPagamentoID = ?", new String[]{String.valueOf(formaPagamento)});
        try {
            if (c.moveToFirst()) {
                formaPagamento.setFormaPagamentoID(formaPagamentoID);
                formaPagamento.setDescricao(c.getString(1));
                formaPagamento.setParcelas(c.getInt(2));
                formaPagamento.setPrazoMedio(c.getInt(3));
                formaPagamento.setTaxa(c.getFloat(4));
                formaPagamento.setAdicional(c.getFloat(5));
            }
        } finally {
            c.close();
        }
        return formaPagamento;
    }

    public void load(FormaPagamento formaPagamento) {
        Cursor c = SQLiteHelper.getCursor(this.context, "FormaPagamento", "FormaPagamentoID = ?", new String[]{String.valueOf(formaPagamento.getFormaPagamentoID())});
        try {
            if (c.moveToFirst()) {
                formaPagamento.setDescricao(c.getString(1));
                formaPagamento.setParcelas(c.getInt(2));
                formaPagamento.setPrazoMedio(c.getInt(3));
                formaPagamento.setTaxa(c.getFloat(4));
                formaPagamento.setAdicional(c.getFloat(5));
            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }
    }
}
