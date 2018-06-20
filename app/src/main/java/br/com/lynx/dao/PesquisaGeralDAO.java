package br.com.lynx.dao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.RespostaSubcanal;
import br.com.lynx.domain.Cliente;

/**
 * Created by Rogerio on 18/04/2016.
 */
public class PesquisaGeralDAO {

    private final String SQLLISTAPESQUISASUBCANAL = "SELECT Pe.ClienteID, Pe.Subcanal FROM ClienteSubcanalPesquisa Pe JOIN Cliente Cl ON Pe.ClienteID = Cl.ClienteID WHERE PesqSCEnviado = 0";
    private final String SQLLISTAPESQUISAEQUIPAMENTO = "SELECT DISTINCT Pe.ClienteID FROM EquipamentoPesquisa Pe JOIN Cliente Cl ON Pe.ClienteID = Cl.ClienteID WHERE PesqEQEnviado = 0";
    private final String SQLLISTAPESQUISACERVEJA = "SELECT DISTINCT Pe.ClienteID FROM PesquisaCerveja Pe JOIN Cliente Cl ON Pe.ClienteID = Cl.ClienteID WHERE PesqCervEnviado = 0";

    private Context context;

    public PesquisaGeralDAO(Context context) {
        this.context = context;
    }

    public List<RespostaSubcanal> carregaListaRespostas() {
        List<RespostaSubcanal> result = new ArrayList<RespostaSubcanal>();

        Cursor c = SQLiteHelper.getDB(context).rawQuery(SQLLISTAPESQUISASUBCANAL, null);
        try {
            if (c.moveToFirst())
                do {
                    result.add(new RespostaSubcanal(c.getInt(0), c.getString(1)));
                } while (c.moveToNext());

        } finally {
            c.close();
        }

        return result;
    }

    public List<Cliente> carregaPesquisaEquipamentosNaoEnviado(){
        List<Cliente> result = new ArrayList<Cliente>();
        Cliente cliente;
        ClienteDAO daoCliente;

        daoCliente = new ClienteDAO(context);

        Cursor c = SQLiteHelper.getDB(context).rawQuery(SQLLISTAPESQUISAEQUIPAMENTO, null);
        try {
            if (c.moveToFirst())
                do {
                    cliente = daoCliente.retornaCliente(c.getInt(0));

                    result.add(cliente);
                } while (c.moveToNext());

        } finally {
            c.close();
        }

        return result;
    }

    public List<Cliente> carregaPesquisaCervejaNaoEnviado(){
        List<Cliente> result = new ArrayList<Cliente>();
        Cliente cliente;
        ClienteDAO daoCliente;

        daoCliente = new ClienteDAO(context);

        Cursor c = SQLiteHelper.getDB(context).rawQuery(SQLLISTAPESQUISACERVEJA, null);
        try {
            if (c.moveToFirst())
                do {
                    cliente = daoCliente.retornaCliente(c.getInt(0));

                    result.add(cliente);
                } while (c.moveToNext());

        } finally {
            c.close();
        }

        return result;
    }
}
