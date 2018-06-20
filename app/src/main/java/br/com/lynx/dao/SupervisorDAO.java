package br.com.lynx.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.Divisao;
import br.com.lynx.model.DivisaoPedidoInfo;

/**
 * Created by rogerio on 25/10/2016.
 */

public class SupervisorDAO {

    private Context context;

    public SupervisorDAO(Context c){
        this.context = c;
    }

    public ArrayList<DivisaoPedidoInfo> pedidosPorDivisao(){
        ArrayList<DivisaoPedidoInfo> lista = new ArrayList<DivisaoPedidoInfo>();
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        Cursor res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoDivisao", null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            lista.add(new DivisaoPedidoInfo(res.getString(0), res.getDouble(1), res.getDouble(2), res.getDouble(3)));
            res.moveToNext();
        }

        return lista;
    }

    public ArrayList<DivisaoPedidoInfo> pedidosSetorCocaCola(){
        ArrayList<DivisaoPedidoInfo> lista = new ArrayList<DivisaoPedidoInfo>();
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        Cursor res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoSetor WHERE SUBSTR(Nome, 1, 2) IN ('01', '04', '05', '06', '08', '09')", null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            lista.add(new DivisaoPedidoInfo(res.getString(0), res.getDouble(1), res.getDouble(2), res.getDouble(3)));
            res.moveToNext();
        }

        return lista;
    }

    public ArrayList<DivisaoPedidoInfo> pedidosVendedor(int setor){
        ArrayList<DivisaoPedidoInfo> lista = new ArrayList<DivisaoPedidoInfo>();
        SQLiteDatabase db = SQLiteHelper.getDB(context);
        Cursor res = null;

        if (setor == 1)
          res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9001')", null);
        else if (setor == 5)
          res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9020', '9021', '9040', '9041', '9042', '9043', '9044', '9045', '9046', '9047', '9048', '9049')", null);
        else if (setor == 6)
          res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9051', '9052', '9053', '9054', '9055', '9056', '9057', '9058', '9059')", null);
        else if (setor == 4)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9030', '9031', '9032', '9033', '9034', '9035', '9036', '9037', '9038', '9039')", null);
        else if (setor == 8)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9071', '9072', '9073', '9074', '9075', '9076', '9077', '9078', '9079')", null);
        else if (setor == 31)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9231')", null);
        else if (setor == 32)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9201', '9204', '9205', '9206')", null);
        else if (setor == 33)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9211', '9213', '9214', '9215', '9216', '9217', '9218', '9220', '9221')", null);
        else if (setor == 41)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9101', '9102')", null);
        else if (setor == 42)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9103', '9104', '9105', '9106', '9109')", null);
        else if (setor == 43)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9121', '9122', '9123', '9124', '9125', '9125', '9126', '9127')", null);
        else if (setor == 44)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9108', '9130')", null);
        else if (setor == 51)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9300', '9301', '9302')", null);
        else if (setor == 52)
            res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoVendedor WHERE SUBSTR(Nome, 1, 4) IN ('9303', '9304', '9305', '9306', '9307', '9308', '9309')", null);

        if (res != null) {
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                lista.add(new DivisaoPedidoInfo(res.getString(0), res.getDouble(1), res.getDouble(2), res.getDouble(3)));
                res.moveToNext();
            }
        }

        return lista;
    }

    public ArrayList<DivisaoPedidoInfo> pedidosSetorMondelez(){
        ArrayList<DivisaoPedidoInfo> lista = new ArrayList<DivisaoPedidoInfo>();
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        Cursor res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoSetor WHERE SUBSTR(Nome, 1, 2) IN ('31', '32', '33')", null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            lista.add(new DivisaoPedidoInfo(res.getString(0), res.getDouble(1), res.getDouble(2), res.getDouble(3)));
            res.moveToNext();
        }

        return lista;
    }

    public ArrayList<DivisaoPedidoInfo> pedidosSetorAlimentar(){
        ArrayList<DivisaoPedidoInfo> lista = new ArrayList<DivisaoPedidoInfo>();
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        Cursor res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoSetor WHERE SUBSTR(Nome, 1, 2) IN ('41', '42', '43',  '44')", null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            lista.add(new DivisaoPedidoInfo(res.getString(0), res.getDouble(1), res.getDouble(2), res.getDouble(3)));
            res.moveToNext();
        }

        return lista;
    }

    public ArrayList<DivisaoPedidoInfo> pedidosSetorRM(){
        ArrayList<DivisaoPedidoInfo> lista = new ArrayList<DivisaoPedidoInfo>();
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        Cursor res = db.rawQuery("SELECT Nome, Faturamento, Volume, Cobertura FROM PedidoSetor WHERE SUBSTR(Nome, 1, 2) IN ('51', '52')", null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            lista.add(new DivisaoPedidoInfo(res.getString(0), res.getDouble(1), res.getDouble(2), res.getDouble(3)));
            res.moveToNext();
        }

        return lista;
    }
}
