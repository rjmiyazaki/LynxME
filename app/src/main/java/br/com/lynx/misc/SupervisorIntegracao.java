package br.com.lynx.misc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import br.com.lynx.dbutility.SQLiteHelper;

/**
 * Created by rogerio on 25/10/2016.
 */

public class SupervisorIntegracao {

    private Context context;

    public SupervisorIntegracao(Context c){
        this.context = context;
    }

    public void importaDivisao(List<String> arquivo){
        List<String[]> linhas;
        String[] campos;
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        linhas = retornaCampos(arquivo, "<Divisao>", "<\\Divisao>", "\\|");

        SQLiteStatement insert = db.compileStatement("INSERT INTO PedidoDivisao VALUES (?, ?, ?, ?)");
        SQLiteStatement delete = db.compileStatement("DELETE FROM PedidoDivisao");

        db.beginTransaction();

        try {
            delete.execute();
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0].trim());
                insert.bindDouble(2, Double.parseDouble(campos[1].replace(",", ".")));
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void importaSetor(List<String> arquivo){
        List<String[]> linhas;
        String[] campos;
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        linhas = retornaCampos(arquivo, "<Setor>", "<\\Setor>", "\\|");

        SQLiteStatement insert = db.compileStatement("INSERT INTO PedidoSetor VALUES (?, ?, ?, ?)");
        SQLiteStatement delete = db.compileStatement("DELETE FROM PedidoSetor");

        db.beginTransaction();

        try {
            delete.execute();
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0].trim());
                insert.bindDouble(2, Double.parseDouble(campos[1].replace(",", ".")));
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void importaVendedor(List<String> arquivo){
        List<String[]> linhas;
        String[] campos;
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        linhas = retornaCampos(arquivo, "<Vendedor>", "<\\Vendedor>", "\\|");

        SQLiteStatement insert = db.compileStatement("INSERT INTO PedidoVendedor VALUES (?, ?, ?, ?)");
        SQLiteStatement delete = db.compileStatement("DELETE FROM PedidoVendedor");

        db.beginTransaction();

        try {
            delete.execute();
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0].trim());
                insert.bindDouble(2, Double.parseDouble(campos[1].replace(",", ".")));
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void importaArquivo(String caminho, String nomeArquivo) throws IOException {
        List<String> arquivo = new ArrayList<String>();
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(caminho + nomeArquivo), "ISO-8859-1"));

        try {
            while (reader.ready())
                arquivo.add(reader.readLine());
        } finally {
            reader.close();
        }

        importaDivisao(arquivo);
        importaSetor(arquivo);
        importaVendedor(arquivo);
    }

    private List<String[]> retornaCampos(List<String> arquivo, String tagAbertura, String tagFechamento,
                                         String charSplit) {
        List<String[]> campos;
        String linha;
        boolean leitura = false;

        campos = new ArrayList<String[]>();

        for (int i = 0; i < arquivo.size() - 1; i++) {
            linha = arquivo.get(i);
            linha = linha.replace("'", "");

            if (linha != "") {
                if (linha.equals(tagAbertura))
                    leitura = true;
                else if (linha.equals(tagFechamento))
                    leitura = false;
                else if (leitura) {
                    campos.add(linha.split(charSplit));
                }
            }
        }

        return campos;
    }
}
