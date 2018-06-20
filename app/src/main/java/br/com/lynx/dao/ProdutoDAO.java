package br.com.lynx.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.domain.Questao;
import br.com.lynx.model.ItemSugestao;
import br.com.lynx.domain.Produto;
import br.com.lynx.model.TabelaPreco;

public class ProdutoDAO {

    private Context context;

    public ProdutoDAO(Context context) {
        this.context = context;
    }

    public List<Produto> listaProdutos(int clienteID) {
        List<Produto> produtos = new ArrayList<Produto>();

        Cursor c = SQLiteHelper.getCursor(context, "Produto", "ProdutoID");
        try {
            if (c.moveToFirst())
                do {
                    produtos
                            .add(new Produto(context, c.getString(0), c.getString(1), c
                                    .getString(2), c.getInt(3), c.getInt(4), c.getInt(5), c
                                    .getInt(6), c.getInt(7), c.getFloat(8)));
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        // Verifica se o cliente possui alguma restrição de familia de itens
        c = SQLiteHelper.getCursor(context, "ClienteFamiliaRestricao",
                "ClienteID = " + String.valueOf(clienteID));
        try {
            if (c.moveToFirst())
                do {
                    for (Iterator<Produto> iterator = produtos.iterator(); iterator
                            .hasNext(); ) {
                        Produto item = iterator.next();

                        if (item.getFamilia() == c.getInt(1))
                            iterator.remove();
                    }
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return produtos;
    }

    public List<Produto> pesquisaNome(String descricao, int clienteID) {
        List<Produto> produtos = new ArrayList<Produto>();

        Cursor c = SQLiteHelper.getCursor(context, "Produto", "Descricao LIKE '%" + descricao + "%' OR ProdutoID LIKE '%" + descricao + "%' ORDER BY descricao");
        try {
            if (c.moveToFirst())
                do {
                    produtos
                            .add(new Produto(context, c.getString(0), c.getString(1), c
                                    .getString(2), c.getInt(3), c.getInt(4), c.getInt(5), c
                                    .getInt(6), c.getInt(7), c.getFloat(8)));
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        // Verifica se o activity_cliente_detalhe possui alguma restrição de familia de itens
        c = SQLiteHelper.getCursor(context, "ClienteFamiliaRestricao",
                "ClienteID = " + String.valueOf(clienteID));
        try {
            if (c.moveToFirst())
                do {
                    for (Iterator<Produto> iterator = produtos.iterator(); iterator
                            .hasNext(); ) {
                        Produto item = iterator.next();

                        if (item.getFamilia() == c.getInt(1))
                            iterator.remove();
                    }
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return produtos;
    }

    public List<Produto> pesquisaCodigo(String codigo) {
        List<Produto> produtos = new ArrayList<Produto>();

        Cursor c = SQLiteHelper.getCursor(context, "Produto", "ProdutoID LIKE '%"
                + codigo + "%'");
        try {
            if (c.moveToFirst())
                do {
                    produtos
                            .add(new Produto(context, c.getString(0), c.getString(1), c
                                    .getString(2), c.getInt(3), c.getInt(4), c.getInt(5), c
                                    .getInt(6), c.getInt(7), c.getFloat(8)));
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }
        return produtos;
    }

    public List<Produto> pesquisaCodigo(String codigo, int clienteID) {
        List<Produto> produtos = new ArrayList<Produto>();

        Cursor c = SQLiteHelper.getCursor(context, "Produto", "ProdutoID LIKE '%"
                + codigo + "%'");
        try {
            if (c.moveToFirst())
                do {
                    produtos
                            .add(new Produto(context, c.getString(0), c.getString(1), c
                                    .getString(2), c.getInt(3), c.getInt(4), c.getInt(5), c
                                    .getInt(6), c.getInt(7), c.getFloat(8)));
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        // Verifica se o cliente possui alguma restrição de familia de itens
        c = SQLiteHelper.getCursor(context, "ClienteFamiliaRestricao",
                "ClienteID = " + String.valueOf(clienteID));
        try {
            if (c.moveToFirst())
                do {
                    for (Iterator<Produto> iterator = produtos.iterator(); iterator
                            .hasNext(); ) {
                        Produto item = iterator.next();

                        if (item.getFamilia() == c.getInt(1))
                            iterator.remove();
                    }
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return produtos;
    }

    public List<Produto> retornaListaProdutosCliente(int clienteID, int subcanalID) {
        List<Produto> produtos = new ArrayList<Produto>();
        List<ItemSugestao> itens = new ArrayList<ItemSugestao>();

        Cursor c = SQLiteHelper.getCursor(context, "Produto");
        try {
            if (c.moveToFirst()) {
                do {
                    produtos.add(new Produto(context, c.getString(0), c.getString(1), c
                            .getString(2), c.getInt(3), c.getInt(4), c.getInt(5), c
                            .getInt(6), c.getInt(7), c.getFloat(8)));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        //if (subcanalID != 0)
        ajustaItensSugestao(produtos, subcanalID, clienteID);

        return produtos;
    }

    private void ajustaItensSugestao(List<Produto> produtos, int subcanalID, int clienteID) {
        List<ItemSugestao> itens = new ArrayList<ItemSugestao>();

        Cursor c = SQLiteHelper.getCursor(context, "SugestaoPedidoCliente", "ClienteID = " + String.valueOf(clienteID));
        try {
            if (c.moveToFirst()) {
                do {
                    itens.add(new ItemSugestao(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3) == 1));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        if (itens.size() == 0) {
            c = SQLiteHelper.getCursor(context, "SugestaoPedido", "SubcanalID = " + String.valueOf(subcanalID));
            try {
                if (c.moveToFirst()) {
                    do {
                        itens.add(new ItemSugestao(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3) == 1));
                    } while (c.moveToNext());
                }
            } finally {
                c.close();
                SQLiteHelper.closeDB(context);
            }
        }

        for (Produto produto : produtos) {
            for (ItemSugestao item : itens) {
                if (produto.getProdutoID().equals(item.getProdutoID()) && item.getDestacado()) {
                    produto.setDestacado(true);
                    produto.setGrupo(item.getGrupo());
                }
            }
        }
    }

    public Produto retornaProduto(String codigo) {

        Produto produto = null;
        Cursor c = SQLiteHelper.getCursor(context, "Produto", "ProdutoID = '"
                + codigo + "'");
        try {
            if (c.moveToFirst()) {
                produto = new Produto(context, c.getString(0), c.getString(1),
                        c.getString(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7), c.getFloat(8));
            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return produto;
    }

    public void load(Produto produto) {
        Cursor c = SQLiteHelper.getCursor(context, "Produto", "ProdutoID = '"
                + produto.getProdutoID() + "'");
        try {
            if (c.moveToFirst()) {
                produto.setValues(c.getString(1), c.getString(2), c.getInt(3),
                        c.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7), c.getFloat(8));
            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }
    }

    public void carregaTabelaPreco(Produto produto) {
        Cursor c = SQLiteHelper.getCursor(context, "ProdutoPreco", "ProdutoID = '"
                + produto.getProdutoID() + "'");
        try {
            if (c.moveToFirst())
                do {
                    produto.getTabelasPreco().add(
                            new TabelaPreco(c.getInt(1), c.getFloat(2)));
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }
    }

    public float retornaPreco(String produtoID, int tabelaPadraoID,
                              int tabelaSecundariaID) {
        float result = 0;
        Cursor c = SQLiteHelper.getCursor(context, "ProdutoPreco", "ProdutoID = '"
                + produtoID + "' AND TabelaID = " + String.valueOf(tabelaPadraoID));

        try {
            if (c.moveToFirst()) {
                result = c.getFloat(2);
            } else {
                c.close();

                c = SQLiteHelper.getCursor(
                        context,
                        "ProdutoPreco",
                        "ProdutoID = '" + produtoID + "' AND TabelaID = "
                                + String.valueOf(tabelaSecundariaID));
                if (c.moveToFirst()) {
                    result = c.getFloat(2);
                }
            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return result;
    }

    public float descontoCampanhaColgate(String produtoID) {
        float result = 0;

        Cursor c = SQLiteHelper.getCursor(context, "ItemCampanhaColgate", "ItemID = " + String.valueOf(produtoID));
        try {
            if (c.moveToFirst()) {
                result = c.getFloat(1);

            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return result;
    }

    public boolean inCampanhaColgate(String produtoID) {
        boolean result = false;

        Cursor c = SQLiteHelper.getCursor(context, "ItemCampanhaColgate", "ItemID = " + String.valueOf(produtoID));
        try {
            if (c.moveToFirst()) {
                result = true;

            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(context);
        }

        return result;
    }

    public float getDescontoCliente(int clienteID, String produtoID) {
        float result = -1;

        String[] columns = {"Desconto"};
        String whereClause = "ItemID = ? AND ClienteID = ?";
        String[] whereArgs = {produtoID, String.valueOf(clienteID)};


        Cursor c = SQLiteHelper.getCursor(context, "ItemDescontoCliente", columns, whereClause, whereArgs);
        try {
            if (c.moveToFirst())
                result = c.getFloat(0);
        } finally {
            c.close();
        }

        return result;
    }
}