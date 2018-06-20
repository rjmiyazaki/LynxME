package br.com.lynx.dao;

import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.FlashBig4;
import br.com.lynx.model.FlashCampanha;
import br.com.lynx.model.FlashCobertura;
import br.com.lynx.model.FlashDevolucao;
import br.com.lynx.model.FlashDevolucaoCliente;
import br.com.lynx.model.FlashEquipamento;
import br.com.lynx.model.FlashMeta;
import br.com.lynx.model.FlashPositivacao;
import br.com.lynx.model.FlashProjeto;
import br.com.lynx.model.FlashQueda;
import br.com.lynx.model.FlashTop10;

/**
 * Created by Rogerio on 25/04/2016.
 */
public class FlashDAO extends ObjectDAO {

    public FlashDAO(Context context) {
        super(context);
    }

    public List<FlashMeta> getFlashMetaCategoria() {
        List<FlashMeta> lista = new ArrayList<FlashMeta>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashMeta");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashMeta(c.getInt(0), c.getString(1), c.getDouble(2), c.getDouble(3), c.getDouble(4), c.getDouble(5), c.getDouble(6), c.getDouble(7), c.getDouble(8)));
                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);
        return lista;
    }

    public List<FlashCobertura> getFlashMetaCobertura() {
        List<FlashCobertura> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashCobertura");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashCobertura(c.getString(0), c.getString(1)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashDevolucao> getFlashDevolucao() {
        List<FlashDevolucao> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashDevolucao");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashDevolucao(c.getString(0), c.getInt(1), c.getFloat(2), c.getFloat(3)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashTop10> getFlashTop10() {
        List<FlashTop10> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashTop10");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashTop10(c.getInt(0), c.getString(1), c.getInt(2), c.getFloat(3), c.getFloat(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashQueda> getFlashQuedas() {
        List<FlashQueda> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashQuedas");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashQueda(c.getInt(0), c.getString(1), c.getInt(2), c.getFloat(3), c.getFloat(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashProjeto> getFlashPontoEconomico() {
        List<FlashProjeto> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashPE");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashProjeto(c.getInt(0), c.getString(1), c.getInt(2), c.getFloat(3), c.getFloat(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashProjeto> getFlashZat() {
        List<FlashProjeto> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashZat");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashProjeto(c.getInt(0), c.getString(1), c.getInt(2), c.getFloat(3), c.getFloat(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashProjeto> getFlashGondola() {
        List<FlashProjeto> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashGondola");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashProjeto(c.getInt(0), c.getString(1), c.getInt(2), c.getFloat(3), c.getFloat(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashCampanha> getFlashPrazoFemsa() {
        List<FlashCampanha> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashPrazoFemsa");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashCampanha(c.getString(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashCampanha> getFlashArrastao() {
        List<FlashCampanha> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashArrastao");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashCampanha(c.getString(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashCampanha> getFlashDesafio() {
        List<FlashCampanha> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashDesafio");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashCampanha(c.getString(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashEquipamento> getFlashEquipamentoPorDia(String dia) {
        List<FlashEquipamento> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashEquipamento", "Dia = ?", new String[]{dia});
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashEquipamento(c.getString(0), c.getString(1), c.getInt(2), c.getInt(3), c.getFloat(4)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashPositivacao> getFlashPositivacao() {
        List<FlashPositivacao> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashPositivacao");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashPositivacao(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getFloat(5)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashDevolucaoCliente> getFlashUltimasDevolucoes() {
        List<FlashDevolucaoCliente> lista = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Cursor c = SQLiteHelper.getCursor(context, "FlashDevolucoes");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashDevolucaoCliente(c.getString(0), c.getString(1), c.getString(2), dateFormat.parse(c.getString(3)), dateFormat.parse(c.getString(4)), c.getString(5), c.getFloat(6), c.getFloat(7)));

                } while (c.moveToNext());

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashBig4> getFlashBig4Item(){
        List<FlashBig4> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashBig4", "Item != ?", new String[]{"TOTAL"});
        try {
            if (c.moveToFirst())
                do {
                    lista.add(new FlashBig4(c.getString(0), c.getFloat(1), c.getFloat(2), c.getFloat(3), c.getFloat(4)));

                } while (c.moveToNext());

        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public List<FlashBig4> getFlashBig4Total(){
        List<FlashBig4> lista = new ArrayList<>();

        Cursor c = SQLiteHelper.getCursor(context, "FlashBig4", "Item = ?", new String[]{"TOTAL"});
        try {
            if (c.moveToFirst()) {
                lista.add(new FlashBig4("Meta", 0, c.getFloat(1), 0, 0));
                lista.add(new FlashBig4("Realizado", 0, c.getFloat(2), 0, 0));
                lista.add(new FlashBig4("Faltantes", 0, c.getFloat(3), 0, 0));
                lista.add(new FlashBig4("Tendência", 0, c.getFloat(4), 0, 0));
            }
        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }
}
