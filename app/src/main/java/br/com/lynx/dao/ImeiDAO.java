package br.com.lynx.dao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.Titulo;

/**
 * Created by rogerio on 21/10/2016.
 */

public class ImeiDAO {

    private Context context;

    public ImeiDAO(Context context) {
        this.context = context;
    }

    public List<String> getList(){
        List<String> lista;

        lista = new ArrayList<String>();
        Cursor c = SQLiteHelper.getCursor(this.context, "IMEI");
        try {
            if (c.moveToFirst())
                do {
                    lista.add(c.getString(0));
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return lista;
    }
}
