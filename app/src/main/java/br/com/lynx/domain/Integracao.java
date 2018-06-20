package br.com.lynx.domain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import br.com.lynx.dbutility.SQLiteHelper;

public final class Integracao {

    private final String SQLINSERTCLIENTE = "INSERT INTO Cliente VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTPRODUTO = "INSERT INTO Produto VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFORMAPAGAMENTO = "INSERT INTO FormaPagamento VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTTIPOPEDIDO = "INSERT INTO TipoPedido VALUES (?, ?)";
    private final String SQLINSERTROTA = "INSERT INTO Rota VALUES(?, ?)";
    private final String SQLINSERTSUBCANAL = "INSERT INTO Subcanal VALUES (?, ?)";
    private final String SQLINSERTMOTIVONAOVENDA = "INSERT INTO MotivoNaoVenda VALUES(?, ?, ?, ?, ?)";
    private final String SQLINSERTTITULOCLIENTE = "INSERT INTO TituloCliente VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTHISTORICOVENDA = "INSERT INTO HistoricoVenda VALUES(?, ?, ?)";
    private final String SQLINSERTHISTORICOVENDAITEM = "INSERT INTO HistoricoVendaItem VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTCATEGORIA = "INSERT INTO Categoria VALUES (?, ?)";
    private final String SQLINSERTMETA = "INSERT INTO Meta VALUES (?, ?, ?, ?, ?)";
    private final String SQLINSERTPRODUTOPRECO = "INSERT INTO ProdutoPreco VALUES (?, ?, ?)";
    private final String SQLINSERTCLIENTECONDICAO = "INSERT INTO ClienteCondicao VALUES (?, ?)";
    private final String SQLINSERTEQUIPAMENTO = "INSERT INTO Equipamento VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTCONFIGURACAO = "INSERT INTO Configuracao VALUES (?, ?, ?, ?, ?)";
    private final String SQLINSERTDEVOLUCAO = "INSERT INTO Devolucao VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTCAMPANHA = "INSERT INTO Campanha VALUES (?, ?, ?, ?)";
    private final String SQLINSERTCLIENTEARRASTAO = "INSERT INTO ClienteArrastao VALUES (?, ?, ?)";
    private final String SQLINSERTCLIENTEFAMILIARESTRICAO = "INSERT INTO ClienteFamiliaRestricao VALUES (?, ?)";
    private final String SQLINSERTEQUIPAMENTOPESQUISA = "INSERT INTO EquipamentoPesquisa VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTSUBCANALPESQUISA = "INSERT INTO SubcanaisPesquisa VALUES (?, ?)";
    private final String SQLINSERTFLASHMETA = "INSERT INTO FlashMeta VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private final String SQLINSERTFLASHCOBERTURA = "INSERT INTO FlashCobertura VALUES (?, ?)";
    private final String SQLINSERTFLASHDEVOLUCAO = "INSERT INTO FlashDevolucao VALUES (?, ?, ?, ?)";
    private final String SQLINSERTFLASHTOP10 = "INSERT INTO FlashTop10 VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHQUEDA = "INSERT INTO FlashQuedas VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHPE = "INSERT INTO FlashPE VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHZAT = "INSERT INTO FlashZat VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHGONDOLA = "INSERT INTO FlashGondola VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHPRAZOFEMSA = "INSERT INTO FlashPrazoFemsa VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHDESAFIO = "INSERT INTO FlashDesafio VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHARRASTAO = "INSERT INTO FlashArrastao VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHEQUIPAMENTO = "INSERT INTO FlashEquipamento VALUES (?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHPOSITIVACAO = "INSERT INTO FlashPositivacao VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHDEVOLUCOES = "INSERT INTO FlashDevolucoes VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQLINSERTFLASHBIG4 = "INSERT INTO FlashBig4 VALUES (?, ?, ?, ?, ?)";
    private final String SQLINSERTIMEI = "INSERT INTO Imei VALUES (?)";
    private final String SQLINSERTSUGESTAOPEDIDO = "INSERT INTO SugestaoPedido VALUES (?, ?, ?, ?)";
    private final String SQLINSERTSUGESTAOPEDIDOCLIENTE = "INSERT INTO SugestaoPedidoCliente VALUES (?, ?, ?, ?)";
    private final String SQLCHECKPEDIDOVENDA = "SELECT COUNT(*) FROM PedidoVenda";
    private final String SQLCHECKPEDIDOVENDAITEM = "SELECT COUNT(*) FROM PedidoVendaItem";
    private final String SQLINSERTCONFIGURACAOCAMPANHACOLGATE = "INSERT INTO ConfiguracaoCampanhaColgate VALUES (?, ?, ?)";
    private final String SQLINSERTITEMCAMPANHACOLGATE = "INSERT INTO ItemCampanhaColgate VALUES (?, ?)";
    private final String SQLINSERTITEMPEDAGIOCAMPANHACOLGATE = "INSERT INTO ItemPedagioCampanhaColgate VALUES (?, ?)";
    private final String SQLINSERTPDVQUESTIONARIO = "INSERT INTO PDVQuestionario VALUES (?, ?, ?)";
    private final String SQLDELETEALLPDVQUESTIONARIO = "DELETE FROM PDVQuestionario";
    private final String SQLINSERTPDVPERGUNTA = "INSERT INTO PDVPergunta VALUES (?, ?, ?, ?, ?)";
    private final String SQLDELETEALLPDVPERGUNTA = "DELETE FROM PDVPergunta";
    private final String SQLINSERTITEMDESCONTOCLIENTE = "INSERT INTO ItemDescontoCliente VALUES (?, ?, ?)";
    private final String SQLDELETEALLITEMDESCONTOCLIENTE = "DELETE FROM ItemDescontoCliente";

    private Context context;
    private List<String> arquivo;

    public Integracao(Context context) {
        this.context = context;
    }

    public void carregarArquivo(String caminho, String nomeArquivo) throws Exception {
        arquivo = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(caminho + nomeArquivo), "ISO-8859-1"));

        try {
            while (reader.ready())
                arquivo.add(reader.readLine());
        } finally {
            reader.close();
        }
    }

    public void integraPDVQuestionario() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<Questionario>", "</Questionario>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTPDVQUESTIONARIO);
        SQLiteStatement delete = db.compileStatement(SQLDELETEALLPDVQUESTIONARIO);

        db.beginTransaction();

        try {
            delete.execute();

            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0].trim()));
                insert.bindString(2, campos[1].trim());
                insert.bindLong(3, Integer.parseInt(campos[2].trim()));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void integraPDVPergunta() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<Pergunta>", "</Pergunta>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTPDVPERGUNTA);
        SQLiteStatement delete = db.compileStatement(SQLDELETEALLPDVPERGUNTA);

        db.beginTransaction();

        try {
            delete.execute();

            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0].trim()));
                insert.bindLong(2, Integer.parseInt(campos[1].trim()));
                insert.bindString(3, campos[2].trim());
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));
                insert.bindString(5, campos[4].trim());

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void executar(String caminho, String nomeArquivo) throws Exception {
        arquivo = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(caminho + nomeArquivo), "ISO-8859-1"));

        try {
            while (reader.ready())
                arquivo.add(reader.readLine());
        } finally {
            reader.close();
        }

        SQLiteHelper.onRecreate(this.context);

        if (!checkPedido()){
            throw new Exception("Ocorreu um erro na recriação do banco de dados");
        } else {
            integraConfiguracao();
            integraCliente();
            integraProduto();
            integraFormaPagamento();
            integraTipoPedido();
            integraRota();
            integraSubcanal();
            integraMotivoNaoVenda();
            integraTituloCli();
            integraHistoricoVenda();
            integraHistoricoVendaItem();
            integraCategorias();
            integraMeta();
            integraProdutoPreco();
            integraClienteCondicao();
            integraEquipamentos();
            integraDevolucao();
            integraCampanha();
            integraClienteArrastao();
            integraClienteFamiliaRestricao();
            integraEquipamentoPesquisa();
            integraSubcanalPesquisa();

            integraFlashMeta();
            integraFlashCobertura();
            integraFlashDevolucao();
            integraFlashTop10();
            integraFlashQuedas();
            integraFlashPE();
            integraFlashZAT();
            integraFlashGondola();
            integraFlashPrazoFemsa();
            integraFlashArrastao();
            integraFlashDesafio();
            integraFlashEquipamento();
            integraFlashPositivacao();
            integraFlashDevolucoes();
            integraFlashBig4();

            integraIMEI();
            integraSugestaoPedido();
            integraSugestaoPedidoCliente();

            integraConfiguracaoCampanhaColgate();
            integraItemCampanhaColgate();
            integraItemPedagioCampanhaColgate();

            integraItemDescontoCliente();
        }
    }

    private boolean checkPedido(){
        Boolean result = false;
        int count;
        SQLiteDatabase db = SQLiteHelper.getDB(context);

        Cursor c = db.rawQuery(SQLCHECKPEDIDOVENDA, null);
        try {
            if (c.moveToFirst()) {
                count = c.getInt(0);

                if (count == 0){
                    c = db.rawQuery(SQLCHECKPEDIDOVENDAITEM, null);

                    if (c.moveToFirst()) {
                        count = c.getInt(0);

                        if (count == 0)
                            result = true;
                    }
                }
            }
        } finally {
            c.close();
        }

        return result;
    }

    private void integraIMEI(){
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<IMEI>", "<\\IMEI>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTIMEI);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0].trim());
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraConfiguracao() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<vendedor.in>", "<\\vendedor.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTCONFIGURACAO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0].trim());
                insert.bindString(2, campos[1].trim());
                insert.bindLong(3, Integer.parseInt(campos[2].trim()));
                insert.bindString(4, campos[3].trim());
                insert.bindString(5, campos[4].trim());
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraCliente() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<clientes.in>", "<\\clientes.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTCLIENTE);

        db.beginTransaction();
        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                // ClienteID
                insert.bindLong(1, Integer.parseInt(campos[0].trim()));

                // Razao
                insert.bindString(2, campos[1].trim());

                // Fantasia
                insert.bindString(3, campos[2].trim());

                // Endereco
                insert.bindString(4, campos[3].trim());

                // Bairro
                insert.bindString(5, campos[4].trim());

                // Complemento
                insert.bindString(6, campos[5].trim());

                // CEP
                insert.bindLong(7, Integer.parseInt(campos[6].trim()));

                // Cidade
                insert.bindString(8, campos[7].trim());

                // Telefone
                insert.bindString(9, campos[8].trim());

                // CNPJ
                insert.bindLong(10, Long.parseLong(campos[9].trim()));

                // IE
                insert.bindString(11, campos[10].trim());

                // Rota
                insert.bindLong(12, Integer.parseInt(campos[11].trim()));

                // Rota
                insert.bindLong(13, Integer.parseInt(campos[12].trim()));

                // Tipo
                insert.bindString(14, campos[13].trim());

                // Subcanal
                insert.bindString(15, campos[14].trim());

                // Tabela Padrão
                insert.bindLong(16, Integer.parseInt(campos[15].trim()));

                // Tabela Secundária
                insert.bindLong(17, Integer.parseInt(campos[16].trim()));

                // Referencia
                insert.bindString(18, campos[17].trim());

                // Volume anterior, atual e variação
                insert.bindLong(19, Integer.parseInt(campos[18]));
                insert.bindLong(20, Integer.parseInt(campos[19]));
                insert.bindLong(21, Integer.parseInt(campos[20]));

                // Venda zero
                insert.bindLong(22, Integer.parseInt(campos[21]));

                // Condição de venda
                insert.bindLong(23, Integer.parseInt(campos[22]));

                // Volume top 10
                insert.bindLong(24, Integer.parseInt(campos[23]));

                // Flag para exibição de pesquisa
                insert.bindLong(25, Integer.parseInt(campos[24]));

                // Flag para activity_cliente_detalhe com condição 1x1
                insert.bindLong(26, Integer.parseInt(campos[25]));

                // Flag para activity_cliente_detalhe atendido pelo Televendas
                insert.bindLong(27, Integer.parseInt(campos[26]));

                // Flag para activity_cliente_detalhe com restrição na Sefaz
                insert.bindLong(28, Integer.parseInt(campos[27]));

                // Flag para activity_cliente_detalhe já atendido
                insert.bindLong(29, Integer.parseInt(campos[28]));

                // Flag para activity_cliente_detalhe com subcanal pesquisado
                insert.bindLong(30, Integer.parseInt(campos[29]));

                // Flag para activity_cliente_detalhe com pesquisa de cerveja já realizado
                insert.bindLong(31, Integer.parseInt(campos[30]));

                // Flag para activity_cliente_detalhe com pesquisa de cerveja já enviado
                insert.bindLong(32, 0);
                insert.bindLong(33, 0);
                insert.bindLong(34, 0);
                insert.bindLong(35, 0);

                insert.bindLong(36, Integer.parseInt(campos[31]));
                insert.bindLong(37, Integer.parseInt(campos[32]));
                insert.bindLong(38, Integer.parseInt(campos[33]));
                insert.bindDouble(39, Double.parseDouble(campos[34].replace(",", ".")));

                // Classificação
                insert.bindString(40, campos[35].trim());

                // Código do subcanal
                insert.bindLong(41, Integer.parseInt(campos[36]));

                // Isenção da taxa de cartão
                insert.bindLong(42, Integer.parseInt(campos[37]));

                // Repasse REFPET
                insert.bindLong(43, Integer.parseInt(campos[38]));

                // Repasse LS
                insert.bindLong(44, Integer.parseInt(campos[39]));

                // Exibe medalha
                insert.bindLong(45, Integer.parseInt(campos[40]));

                // Nova campanha Colgate
                insert.bindLong(46, Integer.parseInt(campos[41]));

                // Nota do PDV-NG
                insert.bindDouble(47, Float.parseFloat(campos[42]));

                insert.executeInsert();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraProduto() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<produtos.in>", "<\\produtos.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTPRODUTO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0].trim());
                insert.bindString(2, campos[1].trim());
                insert.bindString(3, campos[2]);
                insert.bindLong(4, Integer.parseInt(campos[3].trim()));
                insert.bindLong(5, Integer.parseInt(campos[4]));
                insert.bindLong(6, Integer.parseInt(campos[5]));
                insert.bindLong(7, Integer.parseInt(campos[6]));
                insert.bindLong(8, Integer.parseInt(campos[7]));
                insert.bindDouble(9, Double.parseDouble(campos[8].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFormaPagamento() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<formapagamento.in>", "<\\formapagamento.in>",
                "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTFORMAPAGAMENTO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1].trim());
				insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindLong(4, Integer.parseInt(campos[3]));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraTipoPedido() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<tipopedido.in>", "<\\tipopedido.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTTIPOPEDIDO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1].trim());

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraRota() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<rota.in>", "<\\rota.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTROTA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1].trim());

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraSubcanal() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<subcanal.in>", "<\\subcanal.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTSUBCANAL);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1].trim());

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraSubcanalPesquisa() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<subcanalpesquisa.in>", "<\\subcanalpesquisa.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTSUBCANALPESQUISA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1].trim());

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraMotivoNaoVenda() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<nvenda.in>", "<\\nvenda.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTMOTIVONAOVENDA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1].trim());
                insert.bindLong(3, Integer.parseInt(campos[4]));
                insert.bindString(4, campos[3].trim());
                insert.bindLong(5, Integer.parseInt(campos[2]));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraTituloCli() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<cobranca.in>", "<\\cobranca.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTTITULOCLIENTE);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0])); // Código do activity_cliente_detalhe
                insert.bindString(2, campos[1].trim()); // Tipo
                insert.bindLong(3, Integer.parseInt(campos[2])); // Número do título
                insert.bindLong(4, Integer.parseInt(campos[3])); // Número da parcela
                insert.bindString(5, campos[4]); // Data de emissão
                insert.bindString(6, campos[5]); // Data de vencimento
                insert.bindLong(7, Integer.parseInt(campos[6])); // Dias de atraso
                insert.bindDouble(8, Double.parseDouble(campos[7].replace(",", "."))); // Valor  do título
                insert.bindDouble(9, Double.parseDouble(campos[8].replace(",", "."))); // Saldo do título
                insert.bindString(10, campos[9]); // Divisão

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraHistoricoVenda() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<histped.in>", "<\\histped.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTHISTORICOVENDA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraHistoricoVendaItem() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<ithistped.in>", "<\\ithistped.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTHISTORICOVENDAITEM);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindString(3, campos[2]);
                insert.bindString(4, campos[3]);
                insert.bindLong(5, Integer.parseInt(campos[4]));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));
                insert.bindDouble(7, Double.parseDouble(campos[6].replace(",", ".")));
                insert.bindDouble(8, Double.parseDouble(campos[7].replace(",", ".")));
                insert.bindDouble(9, Double.parseDouble(campos[8].replace(",", ".")));
                insert.bindDouble(10, Double.parseDouble(campos[9].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraCategorias() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<categoria.in>", "<\\categoria.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTCATEGORIA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1].trim());

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraMeta() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<objetivos.in>", "<\\objetivos.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTMETA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindDouble(2, Double.parseDouble(campos[1].replace(",", ".")));
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraProdutoPreco() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<tabelapreco.in>", "<\\tabelapreco.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTPRODUTOPRECO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0].trim());
                insert.bindLong(2, Integer.parseInt(campos[1]));
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraClienteCondicao() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<condicaocliente.in>", "<\\condicaocliente.in>",
                "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTCLIENTECONDICAO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindLong(2, Integer.parseInt(campos[1]));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraEquipamentos() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<equipamento.in>", "<\\equipamento.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTEQUIPAMENTO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindString(3, campos[2]);
                insert.bindString(4, campos[3]);
                insert.bindLong(5, Integer.parseInt(campos[4]));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));
                insert.bindDouble(7, Double.parseDouble(campos[6].replace(",", ".")));
                insert.bindDouble(8, Double.parseDouble(campos[7].replace(",", ".")));
                insert.bindString(9, campos[8]);

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraDevolucao() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<devolucoes.in>", "<\\devolucoes.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTDEVOLUCAO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindString(3, campos[2]);
                insert.bindString(4, campos[3]);
                insert.bindLong(5, Integer.parseInt(campos[4]));
                insert.bindLong(6, Integer.parseInt(campos[5]));
                insert.bindString(7, campos[6]);

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraCampanha() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<campanhas.in>", "<\\campanhas.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTCAMPANHA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindLong(2, Integer.parseInt(campos[1]));
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindLong(4, Integer.parseInt(campos[3]));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraClienteArrastao() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<vzarrastao.in>", "<\\vzarrastao.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db.compileStatement(SQLINSERTCLIENTEARRASTAO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindString(3, campos[2]);

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraClienteFamiliaRestricao() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<familiarestricao.in>", "<\\familiarestricao.in>",
                "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTCLIENTEFAMILIARESTRICAO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindLong(2, Integer.parseInt(campos[1]));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraEquipamentoPesquisa() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<equipamentopesquisa.in>", "<\\equipamentopesquisa.in>",
                "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTEQUIPAMENTOPESQUISA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindString(3, campos[2]);

                if ((campos.length > 3) && (campos[3] != ""))
                    insert.bindString(4, campos[3]);
                else
                    insert.bindString(4, "");

                if ((campos.length > 4) && (campos[4] != ""))
                    insert.bindString(5, campos[4]);
                else
                    insert.bindString(5, "");

                insert.bindLong(6, 0);
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashMeta() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashmeta>", "<\\flashmeta>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHMETA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0])); // ID
                insert.bindString(2, campos[1]); // Categoria
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", "."))); // Meta
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", "."))); // Realizado
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", "."))); // Falta
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", "."))); // Tendencia
                insert.bindDouble(7, Double.parseDouble(campos[7].replace(",", "."))); // Meta diária
                insert.bindDouble(8, Double.parseDouble(campos[8].replace(",", "."))); // Variável
                insert.bindDouble(9, Double.parseDouble(campos[6].replace(",", "."))); // Percentual

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashCobertura() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashcobertura>", "<\\flashcobertura>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHCOBERTURA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindString(2, campos[1]);

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashDevolucao() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashdevolucao>", "<\\flashdevolucao>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHDEVOLUCAO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindLong(2, Integer.parseInt(campos[1]));
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashTop10() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashtop10>", "<\\flashtop10>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHTOP10);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashQuedas() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashquedas>", "<\\flashquedas>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHQUEDA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashPE() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashpontoeconomico>", "<\\flashpontoeconomico>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHPE);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashZAT() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashzat>", "<\\flashzat>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHZAT);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashGondola() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashgondola>", "<\\flashgondola>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHGONDOLA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashPrazoFemsa() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashprazofemsa>", "<\\flashprazofemsa>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHPRAZOFEMSA);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindLong(2, Integer.parseInt(campos[1]));
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindLong(4, Integer.parseInt(campos[3]));
                insert.bindLong(5, Integer.parseInt(campos[4]));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashArrastao() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flasharrastao>", "<\\flasharrastao>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHARRASTAO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindLong(2, Integer.parseInt(campos[1]));
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindLong(4, Integer.parseInt(campos[3]));
                insert.bindLong(5, Integer.parseInt(campos[4]));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashDesafio() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashdesafio>", "<\\flashdesafio>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHDESAFIO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindLong(2, Integer.parseInt(campos[1]));
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindLong(4, Integer.parseInt(campos[3]));
                insert.bindLong(5, Integer.parseInt(campos[4]));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashEquipamento() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashequipamento>", "<\\flashequipamento>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHEQUIPAMENTO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindString(2, campos[1]);
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindLong(4, Integer.parseInt(campos[3]));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashPositivacao() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashpositivacao>", "<\\flashpositivacao>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHPOSITIVACAO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindLong(3, Integer.parseInt(campos[2]));
                insert.bindLong(4, Integer.parseInt(campos[3]));
                insert.bindLong(5, Integer.parseInt(campos[4]));
                insert.bindDouble(6, Double.parseDouble(campos[5].replace(",", ".")));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashDevolucoes() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashultimasdevolucoes>", "<\\flashultimasdevolucoes>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHDEVOLUCOES);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindString(2, campos[1]);
                insert.bindString(3, campos[2]);
                insert.bindString(4, campos[3]);
                insert.bindString(5, campos[4]);
                insert.bindString(6, campos[5]);
                insert.bindDouble(7, Double.parseDouble(campos[6].replace(",", ".")));
                insert.bindDouble(8, Double.parseDouble(campos[7].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraFlashBig4() {
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<flashbig4>", "<\\flashbig4>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTFLASHBIG4);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindDouble(2, Double.parseDouble(campos[1].replace(",", ".")));
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));
                insert.bindDouble(4, Double.parseDouble(campos[3].replace(",", ".")));
                insert.bindDouble(5, Double.parseDouble(campos[4].replace(",", ".")));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraSugestaoPedido(){
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<sugestao>", "<\\sugestao>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTSUGESTAOPEDIDO);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindString(2, campos[1]);
                insert.bindString(3, campos[2]);
                insert.bindLong(4, Integer.parseInt(campos[3]));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraSugestaoPedidoCliente(){
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<sugestaocliente>", "<\\sugestaocliente>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTSUGESTAOPEDIDOCLIENTE);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindString(2, campos[1]);
                insert.bindString(3, campos[2]);
                insert.bindLong(4, Integer.parseInt(campos[3]));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraConfiguracaoCampanhaColgate(){
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<configcc>", "<\\configcc>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTCONFIGURACAOCAMPANHACOLGATE);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindDouble(1, Double.parseDouble(campos[0].replace(",", ".")));
                insert.bindDouble(2, Double.parseDouble(campos[1].replace(",", ".")));
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraItemCampanhaColgate(){
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<itenscc>", "<\\itenscc>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTITEMCAMPANHACOLGATE);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindDouble(2, Double.parseDouble(campos[1].replace(",", ".")));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraItemPedagioCampanhaColgate(){
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<pedagiocc>", "<\\pedagiocc>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTITEMPEDAGIOCAMPANHACOLGATE);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindString(1, campos[0]);
                insert.bindLong(2, Integer.parseInt(campos[1]));
                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void integraItemDescontoCliente(){
        List<String[]> linhas;
        String[] campos;

        linhas = retornaCampos("<clientedesconto.in>", "<\\clientedesconto.in>", "\\|");

        SQLiteDatabase db = SQLiteHelper.getDB(context);
        SQLiteStatement insert = db
                .compileStatement(SQLINSERTITEMDESCONTOCLIENTE);

        db.beginTransaction();

        try {
            for (int i = 0; i < linhas.size(); i++) {
                campos = linhas.get(i);

                insert.bindLong(1, Integer.parseInt(campos[0]));
                insert.bindString(2, campos[1]);
                insert.bindDouble(3, Double.parseDouble(campos[2].replace(",", ".")));

                insert.executeInsert();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private List<String[]> retornaCampos(String tagAbertura, String tagFechamento,
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