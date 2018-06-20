package br.com.lynx.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.util.Log;

import br.com.lynx.domain.FormaPagamento;
import br.com.lynx.domain.Questao;
import br.com.lynx.model.Equipamento;
import br.com.lynx.model.EquipamentoPesquisa;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.model.ItemCampanha;
import br.com.lynx.model.ItemSugestao;
import br.com.lynx.model.PesquisaCerveja;
import br.com.lynx.domain.Produto;
import br.com.lynx.model.RespostaCerveja;
import br.com.lynx.model.Titulo;
import br.com.lynx.domain.Cliente;
import br.com.lynx.model.Devolucao;
import br.com.lynx.model.Campanha;
import br.com.lynx.model.HistoricoVenda;
import br.com.lynx.domain.PedidoItem;
import br.com.lynx.domain.Pedido;
import br.com.lynx.vo.RegistroNaoVendaVO;

public class ClienteDAO {

    private final String SQLSELECTCLIENTE = "SELECT ClienteID, RazaoSocial, Fantasia, Endereco, Bairro, Complemento, CEP, Cidade, Fone, "
            + "CNPJ, IE, Rota, Ordem, Tipo, Subcanal, TabelaPadraoID, TabelaSecundariaID, Referencia, "
            + "VolumeAnterior, VolumeAtual, Variacao, VendaZero, Positivado, NaoVenda, FormaPagamentoID, FormaPagamento, VolumeTop10, ExibePesquisa, "
            + "Condicao1x1, Televendas, MotivoNaoVenda, SEFAZRestricao, Atendido, SubcanalConfirmado, PesquisaCerveja, PesqCervEnviado, PesqTBEnviado, "
            + "PesqEQEnviado, PesqSCEnviado, CampanhaColgate, IsentoTaxaBoleto, IsentoAdicional, Adicional, Classificacao, SubcanalID, IsentoTaxaCartao, "
            + "RepasseRefPet, RepasseLS, ExibeMedalha, NovaCampanhaColgate, NotaPDVNG "
            + "FROM viwCliente";

    private final String SQLSELECTCLIENTEORDENADA = "SELECT ClienteID, RazaoSocial, Fantasia, Endereco, Bairro, Complemento, CEP, Cidade, Fone, "
            + "CNPJ, IE, Rota, Ordem, Tipo, Subcanal, TabelaPadraoID, TabelaSecundariaID, Referencia, "
            + "VolumeAnterior, VolumeAtual, Variacao, VendaZero, Positivado, NaoVenda, FormaPagamentoID, FormaPagamento, VolumeTop10, ExibePesquisa, "
            + "Condicao1x1, Televendas, MotivoNaoVenda, SEFAZRestricao, Atendido, SubcanalConfirmado, PesquisaCerveja, PesqCervEnviado, PesqTBEnviado, "
            + "PesqEQEnviado, PesqSCEnviado, CampanhaColgate, IsentoTaxaBoleto, IsentoAdicional, Adicional, Classificacao, SubcanalID, IsentoTaxaCartao, "
            + "RepasseRefPet, RepasseLS, ExibeMedalha, NovaCampanhaColgate, NotaPDVNG "
            + "FROM viwCliente ORDER BY Ordem";

    private final String SQLSELECTITENSARRASTAO = "SELECT Campanha, Categoria FROM ClienteArrastao WHERE ";
    private final String SQLATUALIZATELEFONE = "UPDATE Cliente SET NovoTelefone = ";
    private final String SQLEQUIPAMENTOPESQUISADEL = "DELETE EquipamentoPesquisa WHERE Geko = ?";
    private final String SQLEQUIPAMENTOPESQUISAINS = "INSERT EquipamentoPesquisa VALUES (?, ?, ?, ?, ?)";
    private final String SQLSELECTPRODUTOCOMODATO = "SELECT ProdutoID, Descricao, Unidade, Fracionador, Categoria, UniCaixa, "
            + "FamiliaID, Estoque, Desconto FROM Produto WHER Categoria = 7";

    private Context context;

    public ClienteDAO(Context context) {
        this.context = context;
    }

    public List<ItemCampanha> carregaListaArrastao(int clienteID) {
        String sql = SQLSELECTITENSARRASTAO + " ClienteID = " + clienteID;
        List<ItemCampanha> result = new ArrayList<ItemCampanha>();

        Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);
        try {
            if (c.moveToFirst())
                do {
                    result.add(new ItemCampanha(c.getString(0), c.getString(1)));
                } while (c.moveToNext());

        } finally {
            c.close();
        }

        return result;
    }

    public List<Cliente> listaClientes() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente;

        Cursor c = SQLiteHelper.getDB(context).rawQuery(SQLSELECTCLIENTEORDENADA, null);
        try {
            if (c.moveToFirst())
                do {

                    cliente = new Cliente(context);

                    cliente.setClienteID(c.getInt(0));
                    cliente.setRazaoSocial(c.getString(1));
                    cliente.setFantasia(c.getString(2));
                    cliente.setEndereco(c.getString(3));
                    cliente.setBairro(c.getString(4));
                    cliente.setComplemento(c.getString(5));
                    cliente.setCep(c.getLong(6));
                    cliente.setCidade(c.getString(7));
                    cliente.setFone(c.getString(8));
                    cliente.setCnpj(c.getLong(9));
                    cliente.setIe(c.getString(10));
                    cliente.setCodRota(c.getInt(11));
                    cliente.setOrdem(c.getInt(12));

                    if (c.getString(13).equals("CPF"))
                      cliente.setTipo(1);
                    else
                       cliente.setTipo(2);

                    cliente.setSubcanal(c.getString(14));
                    cliente.setTabelaPadraoID(c.getInt(15));
                    cliente.setTabelaSecundariaID(c.getInt(16));
                    cliente.setReferencia(c.getString(17));
                    cliente.setVolumeAnterior(c.getInt(18));
                    cliente.setVolumeAtual(c.getInt(19));
                    cliente.setVariacao(c.getInt(20));

                    cliente.getFormaPagamento().setFormaPagamentoID(c.getInt(24));
                    cliente.getFormaPagamento().setDescricao(c.getString(25));

                    if (c.getInt(21) == 0)
                        cliente.setVendaZero(false);
                    else
                        cliente.setVendaZero(true);

                    if (c.getInt(22) == 0)
                        cliente.setPositivado(false);
                    else
                        cliente.setPositivado(true);

                    if (c.getInt(23) == 0)
                        cliente.setNaoVenda(false);
                    else
                        cliente.setNaoVenda(true);

                    cliente.getFormaPagamento().setFormaPagamentoID(c.getInt(24));
                    cliente.getFormaPagamento().setDescricao(c.getString(25));
                    cliente.setVolumeTop10(c.getInt(26));

                    if (c.getInt(27) == 0)
                        cliente.setExibePesquisa(false);
                    else
                        cliente.setExibePesquisa(true);

                    cliente.setCliente1x1(c.getInt(28) == 1);
                    cliente.setTelevendas(c.getInt(29) == 1);
                    cliente.setMotivoNaoVenda(c.getString(30));
                    cliente.setSEFAZRestricao(c.getInt(31) == 1);
                    cliente.setAtendido(c.getInt(32) == 1);
                    cliente.setSubcanalConfirmado(c.getInt(33) == 1);
                    cliente.setPesquisaCerveja(c.getInt(34) == 1);

                    cliente.setPesquisaCervejaEnviada(c.getInt(35) == 1);
                    cliente.setPesquisaEquipamentoEnviada(c.getInt(37) == 1);
                    cliente.setPesquisaSubcanalEnviada(c.getInt(38) == 1);
                    cliente.setCampanhaColgate(c.getInt(39) == 1);
                    cliente.setIsentoTaxaBoleto(c.getInt(40) == 1);
                    cliente.setIsentoAdicional(c.getInt(41) == 1);
                    cliente.setAdicional(c.getFloat(42));
                    cliente.setClassificacao(c.getString(43));
                    cliente.setSubcanalID(c.getInt(44));
                    cliente.setIsentoTaxaCartao(c.getInt(45) == 1);
                    cliente.setRepasseRefPet(c.getInt(46) == 1);
                    cliente.setRepasseLS(c.getInt(47) == 1);
                    cliente.setExibeMedalha(c.getInt(48) == 1);
                    cliente.setNovaCampanhaColgate(c.getInt(49) == 1);
                    cliente.setNotaPDVNG(c.getFloat(50));

                    clientes.add(cliente);
                } while (c.moveToNext());
        } finally {
            c.close();
        }
        SQLiteHelper.closeDB(this.context);
        return clientes;
    }

    public List<Cliente> pesquisaNome(String razaoSocial) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente;

        String sql = SQLSELECTCLIENTE + " WHERE RazaoSocial LIKE '%" + razaoSocial + "%' ORDER BY Ordem";

        Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);
        try {
            if (c.moveToFirst())
                do {

                    cliente = new Cliente(context);

                    cliente.setClienteID(c.getInt(0));
                    cliente.setRazaoSocial(c.getString(1));
                    cliente.setFantasia(c.getString(2));
                    cliente.setEndereco(c.getString(3));
                    cliente.setBairro(c.getString(4));
                    cliente.setComplemento(c.getString(5));
                    cliente.setCep(c.getLong(6));
                    cliente.setCidade(c.getString(7));
                    cliente.setFone(c.getString(8));
                    cliente.setCnpj(c.getLong(9));
                    cliente.setIe(c.getString(10));
                    cliente.setCodRota(c.getInt(11));
                    cliente.setOrdem(c.getInt(12));

                    if (c.getString(13).equals("CPF"))
                        cliente.setTipo(1);
                    else
                        cliente.setTipo(2);

                    cliente.setSubcanal(c.getString(14));
                    cliente.setTabelaPadraoID(c.getInt(15));
                    cliente.setTabelaSecundariaID(c.getInt(16));
                    cliente.setReferencia(c.getString(17));
                    cliente.setVolumeAnterior(c.getInt(18));
                    cliente.setVolumeAtual(c.getInt(19));
                    cliente.setVariacao(c.getInt(20));

                    cliente.getFormaPagamento().setFormaPagamentoID(c.getInt(24));
                    cliente.getFormaPagamento().setDescricao(c.getString(25));
                    cliente.setVolumeTop10(c.getInt(26));

                    if (c.getInt(21) == 0)
                        cliente.setVendaZero(false);
                    else
                        cliente.setVendaZero(true);

                    if (c.getInt(22) == 0)
                        cliente.setPositivado(false);
                    else
                        cliente.setPositivado(true);

                    if (c.getInt(23) == 0)
                        cliente.setNaoVenda(false);
                    else
                        cliente.setNaoVenda(true);

                    if (c.getInt(27) == 0)
                        cliente.setExibePesquisa(false);
                    else
                        cliente.setExibePesquisa(true);

                    cliente.setCliente1x1(c.getInt(28) == 1);
                    cliente.setTelevendas(c.getInt(29) == 1);
                    cliente.setMotivoNaoVenda(c.getString(30));
                    cliente.setSEFAZRestricao(c.getInt(31) == 1);
                    cliente.setAtendido(c.getInt(32) == 1);
                    cliente.setSubcanalConfirmado(c.getInt(33) == 1);
                    cliente.setPesquisaCerveja(c.getInt(34) == 1);

                    cliente.setPesquisaCervejaEnviada(c.getInt(35) == 1);
                    cliente.setPesquisaEquipamentoEnviada(c.getInt(37) == 1);
                    cliente.setPesquisaSubcanalEnviada(c.getInt(38) == 1);
                    cliente.setCampanhaColgate(c.getInt(39) == 1);
                    cliente.setIsentoTaxaBoleto(c.getInt(40) == 1);
                    cliente.setIsentoAdicional(c.getInt(41) == 1);
                    cliente.setAdicional(c.getFloat(42));
                    cliente.setClassificacao(c.getString(43));
                    cliente.setSubcanalID(c.getInt(44));
                    cliente.setIsentoTaxaCartao(c.getInt(45) == 1);
                    cliente.setRepasseRefPet(c.getInt(46) == 1);
                    cliente.setRepasseLS(c.getInt(47) == 1);
                    cliente.setExibeMedalha(c.getInt(48) == 1);
                    cliente.setNovaCampanhaColgate(c.getInt(49) == 1);
                    cliente.setNotaPDVNG(c.getFloat(50));

                    clientes.add(cliente);
                } while (c.moveToNext());
        } finally {
            c.close();
        }
        SQLiteHelper.closeDB(this.context);

        return clientes;
    }

    public List<Cliente> pesquisaCodigo(int codigo) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente;

        String sql = SQLSELECTCLIENTE + " WHERE ClienteID = " + codigo;

        Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);
        try {
            if (c.moveToFirst())
                do {

                    cliente = new Cliente(context);

                    cliente.setClienteID(c.getInt(0));
                    cliente.setRazaoSocial(c.getString(1));
                    cliente.setFantasia(c.getString(2));
                    cliente.setEndereco(c.getString(3));
                    cliente.setBairro(c.getString(4));
                    cliente.setComplemento(c.getString(5));
                    cliente.setCep(c.getLong(6));
                    cliente.setCidade(c.getString(7));
                    cliente.setFone(c.getString(8));
                    cliente.setCnpj(c.getLong(9));
                    cliente.setIe(c.getString(10));
                    cliente.setCodRota(c.getInt(11));
                    cliente.setOrdem(c.getInt(12));

                    if (c.getString(13).equals("CPF"))
                        cliente.setTipo(1);
                    else
                        cliente.setTipo(2);

                    cliente.setSubcanal(c.getString(14));
                    cliente.setTabelaPadraoID(c.getInt(15));
                    cliente.setTabelaSecundariaID(c.getInt(16));
                    cliente.setReferencia(c.getString(17));
                    cliente.setVolumeAnterior(c.getInt(18));
                    cliente.setVolumeAtual(c.getInt(19));
                    cliente.setVariacao(c.getInt(20));

                    cliente.getFormaPagamento().setFormaPagamentoID(c.getInt(24));
                    cliente.getFormaPagamento().setDescricao(c.getString(25));
                    cliente.setVolumeTop10(c.getInt(26));

                    if (c.getInt(21) == 0)
                        cliente.setVendaZero(false);
                    else
                        cliente.setVendaZero(true);

                    if (c.getInt(22) == 0)
                        cliente.setPositivado(false);
                    else
                        cliente.setPositivado(true);

                    if (c.getInt(23) == 0)
                        cliente.setNaoVenda(false);
                    else
                        cliente.setNaoVenda(true);

                    if (c.getInt(27) == 0)
                        cliente.setExibePesquisa(false);
                    else
                        cliente.setExibePesquisa(true);

                    cliente.setCliente1x1(c.getInt(28) == 1);
                    cliente.setTelevendas(c.getInt(29) == 1);
                    cliente.setMotivoNaoVenda(c.getString(30));
                    cliente.setSEFAZRestricao(c.getInt(31) == 1);
                    cliente.setAtendido(c.getInt(32) == 1);
                    cliente.setSubcanalConfirmado(c.getInt(33) == 1);
                    cliente.setPesquisaCerveja(c.getInt(34) == 1);

                    cliente.setPesquisaCervejaEnviada(c.getInt(35) == 1);
                    cliente.setPesquisaEquipamentoEnviada(c.getInt(37) == 1);
                    cliente.setPesquisaSubcanalEnviada(c.getInt(38) == 1);
                    cliente.setCampanhaColgate(c.getInt(39) == 1);
                    cliente.setIsentoTaxaBoleto(c.getInt(40) == 1);
                    cliente.setIsentoAdicional(c.getInt(41) == 1);
                    cliente.setAdicional(c.getFloat(42));
                    cliente.setClassificacao(c.getString(43));
                    cliente.setSubcanalID(c.getInt(44));
                    cliente.setIsentoTaxaCartao(c.getInt(45) == 1);
                    cliente.setRepasseRefPet(c.getInt(46) == 1);
                    cliente.setRepasseLS(c.getInt(47) == 1);
                    cliente.setExibeMedalha(c.getInt(48) == 1);
                    cliente.setNovaCampanhaColgate(c.getInt(49) == 1);
                    cliente.setNotaPDVNG(c.getFloat(50));

                    clientes.add(cliente);
                } while (c.moveToNext());
        } finally {
            c.close();
        }
        SQLiteHelper.closeDB(this.context);

        return clientes;
    }

    public List<Cliente> pesquisaRota(int rota) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente;

        String sql = SQLSELECTCLIENTE + " WHERE Rota = " + rota;

        Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);
        try {
            if (c.moveToFirst())
                do {

                    cliente = new Cliente(context);

                    cliente.setClienteID(c.getInt(0));
                    cliente.setRazaoSocial(c.getString(1));
                    cliente.setFantasia(c.getString(2));
                    cliente.setEndereco(c.getString(3));
                    cliente.setBairro(c.getString(4));
                    cliente.setComplemento(c.getString(5));
                    cliente.setCep(c.getLong(6));
                    cliente.setCidade(c.getString(7));
                    cliente.setFone(c.getString(8));
                    cliente.setCnpj(c.getLong(9));
                    cliente.setIe(c.getString(10));
                    cliente.setCodRota(c.getInt(11));
                    cliente.setOrdem(c.getInt(12));

                    if (c.getString(13).equals("CPF"))
                        cliente.setTipo(1);
                    else
                        cliente.setTipo(2);

                    cliente.setSubcanal(c.getString(14));
                    cliente.setTabelaPadraoID(c.getInt(15));
                    cliente.setTabelaSecundariaID(c.getInt(16));
                    cliente.setReferencia(c.getString(17));
                    cliente.setVolumeAnterior(c.getInt(18));
                    cliente.setVolumeAtual(c.getInt(19));
                    cliente.setVariacao(c.getInt(20));

                    cliente.getFormaPagamento().setFormaPagamentoID(c.getInt(24));
                    cliente.getFormaPagamento().setDescricao(c.getString(25));
                    cliente.setVolumeTop10(c.getInt(26));

                    if (c.getInt(21) == 0)
                        cliente.setVendaZero(false);
                    else
                        cliente.setVendaZero(true);

                    if (c.getInt(22) == 0)
                        cliente.setPositivado(false);
                    else
                        cliente.setPositivado(true);

                    if (c.getInt(23) == 0)
                        cliente.setNaoVenda(false);
                    else
                        cliente.setNaoVenda(true);

                    if (c.getInt(27) == 0)
                        cliente.setExibePesquisa(false);
                    else
                        cliente.setExibePesquisa(true);

                    cliente.setCliente1x1(c.getInt(28) == 1);
                    cliente.setTelevendas(c.getInt(29) == 1);
                    cliente.setMotivoNaoVenda(c.getString(30));
                    cliente.setSEFAZRestricao(c.getInt(31) == 1);
                    cliente.setAtendido(c.getInt(32) == 1);
                    cliente.setSubcanalConfirmado(c.getInt(33) == 1);
                    cliente.setPesquisaCerveja(c.getInt(34) == 1);

                    cliente.setPesquisaCervejaEnviada(c.getInt(35) == 1);
                    cliente.setPesquisaEquipamentoEnviada(c.getInt(37) == 1);
                    cliente.setPesquisaSubcanalEnviada(c.getInt(38) == 1);
                    cliente.setCampanhaColgate(c.getInt(39) == 1);
                    cliente.setIsentoTaxaBoleto(c.getInt(40) == 1);
                    cliente.setIsentoAdicional(c.getInt(41) == 1);
                    cliente.setAdicional(c.getFloat(42));
                    cliente.setClassificacao(c.getString(43));
                    cliente.setSubcanalID(c.getInt(44));
                    cliente.setIsentoTaxaCartao(c.getInt(45) == 1);
                    cliente.setRepasseRefPet(c.getInt(46) == 1);
                    cliente.setRepasseLS(c.getInt(47) == 1);
                    cliente.setExibeMedalha(c.getInt(48) == 1);
                    cliente.setNovaCampanhaColgate(c.getInt(49) == 1);
                    cliente.setNotaPDVNG(c.getFloat(50));

                    clientes.add(cliente);
                } while (c.moveToNext());
        } finally {
            c.close();
        }
        SQLiteHelper.closeDB(this.context);

        return clientes;
    }

    public Cliente retornaCliente(int clienteID) {
        Cliente cliente = new Cliente(context);

        String sql = SQLSELECTCLIENTE + " WHERE ClienteID = " + clienteID;

        Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {

                cliente = new Cliente(context);

                cliente.setClienteID(c.getInt(0));
                cliente.setRazaoSocial(c.getString(1));
                cliente.setFantasia(c.getString(2));
                cliente.setEndereco(c.getString(3));
                cliente.setBairro(c.getString(4));
                cliente.setComplemento(c.getString(5));
                cliente.setCep(c.getLong(6));
                cliente.setCidade(c.getString(7));
                cliente.setFone(c.getString(8));
                cliente.setCnpj(c.getLong(9));
                cliente.setIe(c.getString(10));
                cliente.setCodRota(c.getInt(11));
                cliente.setOrdem(c.getInt(12));

                if (c.getString(13).equals("CPF"))
                    cliente.setTipo(1);
                else
                    cliente.setTipo(2);

                cliente.setSubcanal(c.getString(14));
                cliente.setTabelaPadraoID(c.getInt(15));
                cliente.setTabelaSecundariaID(c.getInt(16));
                cliente.setReferencia(c.getString(17));
                cliente.setVolumeAnterior(c.getInt(18));
                cliente.setVolumeAtual(c.getInt(19));
                cliente.setVariacao(c.getInt(20));

                cliente.getFormaPagamento().setFormaPagamentoID(c.getInt(24));
                cliente.getFormaPagamento().setDescricao(c.getString(25));
                cliente.setVolumeTop10(c.getInt(26));

                if (c.getInt(21) == 0)
                    cliente.setVendaZero(false);
                else
                    cliente.setVendaZero(true);

                if (c.getInt(22) == 0)
                    cliente.setPositivado(false);
                else
                    cliente.setPositivado(true);

                if (c.getInt(23) == 0)
                    cliente.setNaoVenda(false);
                else
                    cliente.setNaoVenda(true);

                if (c.getInt(27) == 0)
                    cliente.setExibePesquisa(false);
                else
                    cliente.setExibePesquisa(true);

                cliente.setCliente1x1(c.getInt(28) == 1);
                cliente.setTelevendas(c.getInt(29) == 1);
                cliente.setMotivoNaoVenda(c.getString(30));
                cliente.setSEFAZRestricao(c.getInt(31) == 1);
                cliente.setAtendido(c.getInt(32) == 1);
                cliente.setSubcanalConfirmado(c.getInt(33) == 1);
                cliente.setPesquisaCerveja(c.getInt(34) == 1);

                cliente.setPesquisaCervejaEnviada(c.getInt(35) == 1);
                cliente.setPesquisaEquipamentoEnviada(c.getInt(37) == 1);
                cliente.setPesquisaSubcanalEnviada(c.getInt(38) == 1);
                cliente.setCampanhaColgate(c.getInt(39) == 1);
                cliente.setIsentoTaxaBoleto(c.getInt(40) == 1);
                cliente.setIsentoAdicional(c.getInt(41) == 1);
                cliente.setAdicional(c.getFloat(42));
                cliente.setClassificacao(c.getString(43));
                cliente.setSubcanalID(c.getInt(44));
                cliente.setIsentoTaxaCartao(c.getInt(45) == 1);
                cliente.setRepasseRefPet(c.getInt(46) == 1);
                cliente.setRepasseLS(c.getInt(47) == 1);
                cliente.setExibeMedalha(c.getInt(48) == 1);
                cliente.setNovaCampanhaColgate(c.getInt(49) == 1);
                cliente.setNotaPDVNG(c.getFloat(50));
            }

        } finally {
            c.close();
        }
        SQLiteHelper.closeDB(this.context);
        return cliente;
    }

    public void load(Cliente cliente) {

        String sql = SQLSELECTCLIENTE + " WHERE ClienteID = " + cliente.getClienteID();

        Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                cliente.setClienteID(c.getInt(0));
                cliente.setRazaoSocial(c.getString(1));
                cliente.setFantasia(c.getString(2));
                cliente.setEndereco(c.getString(3));
                cliente.setBairro(c.getString(4));
                cliente.setComplemento(c.getString(5));
                cliente.setCep(c.getLong(6));
                cliente.setCidade(c.getString(7));
                cliente.setFone(c.getString(8));
                cliente.setCnpj(c.getLong(9));
                cliente.setIe(c.getString(10));
                cliente.setCodRota(c.getInt(11));
                cliente.setOrdem(c.getInt(12));

                if (c.getString(13).equals("CPF"))
                    cliente.setTipo(1);
                else
                    cliente.setTipo(2);

                cliente.setSubcanal(c.getString(14));
                cliente.setTabelaPadraoID(c.getInt(15));
                cliente.setTabelaSecundariaID(c.getInt(16));
                cliente.setReferencia(c.getString(17));
                cliente.setVolumeAnterior(c.getInt(18));
                cliente.setVolumeAtual(c.getInt(19));
                cliente.setVariacao(c.getInt(20));

                cliente.getFormaPagamento().setFormaPagamentoID(c.getInt(24));
                cliente.getFormaPagamento().setDescricao(c.getString(25));
                cliente.setVolumeTop10(c.getInt(26));

                if (c.getInt(21) == 0)
                    cliente.setVendaZero(false);
                else
                    cliente.setVendaZero(true);

                if (c.getInt(22) == 0)
                    cliente.setPositivado(false);
                else
                    cliente.setPositivado(true);

                if (c.getInt(23) == 0)
                    cliente.setNaoVenda(false);
                else
                    cliente.setNaoVenda(true);

                if (c.getInt(27) == 0)
                    cliente.setExibePesquisa(false);
                else
                    cliente.setExibePesquisa(true);

                cliente.setCliente1x1(c.getInt(28) == 1);
                cliente.setTelevendas(c.getInt(29) == 1);
                cliente.setMotivoNaoVenda(c.getString(30));
                cliente.setSEFAZRestricao(c.getInt(31) == 1);
                cliente.setAtendido(c.getInt(32) == 1);
                cliente.setSubcanalConfirmado(c.getInt(33) == 1);
                cliente.setPesquisaCerveja(c.getInt(34) == 1);

                cliente.setPesquisaCervejaEnviada(c.getInt(35) == 1);
                cliente.setPesquisaEquipamentoEnviada(c.getInt(37) == 1);
                cliente.setPesquisaSubcanalEnviada(c.getInt(38) == 1);
                cliente.setCampanhaColgate(c.getInt(39) == 1);
                cliente.setIsentoTaxaBoleto(c.getInt(40) == 1);
                cliente.setIsentoAdicional(c.getInt(41) == 1);
                cliente.setAdicional(c.getFloat(42));
                cliente.setClassificacao(c.getString(43));
                cliente.setSubcanalID(c.getInt(44));
                cliente.setIsentoTaxaCartao(c.getInt(45) == 1);
                cliente.setRepasseRefPet(c.getInt(46) == 1);
                cliente.setRepasseLS(c.getInt(47) == 1);
                cliente.setExibeMedalha(c.getInt(48) == 1);
                cliente.setNovaCampanhaColgate(c.getInt(49) == 1);
                cliente.setNotaPDVNG(c.getFloat(50));
            }

        } finally {
            c.close();
        }
        SQLiteHelper.closeDB(this.context);
    }

    public Cliente retornaClientePorOrdem(int ordem) {
        Cliente cliente = new Cliente(context);

        String sql = SQLSELECTCLIENTE + " WHERE Ordem = " + ordem;

        Cursor c = SQLiteHelper.getDB(context).rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                cliente.setClienteID(c.getInt(0));
                cliente.setRazaoSocial(c.getString(1));
                cliente.setFantasia(c.getString(2));
                cliente.setEndereco(c.getString(3));
                cliente.setBairro(c.getString(4));
                cliente.setComplemento(c.getString(5));
                cliente.setCep(c.getLong(6));
                cliente.setCidade(c.getString(7));
                cliente.setFone(c.getString(8));
                cliente.setCnpj(c.getLong(9));
                cliente.setIe(c.getString(10));
                cliente.setCodRota(c.getInt(11));
                cliente.setOrdem(c.getInt(12));
                if (c.getString(13).equals("CPF"))
                    cliente.setTipo(1);
                else
                    cliente.setTipo(2);
                cliente.setSubcanal(c.getString(14));
                cliente.setTabelaPadraoID(c.getInt(15));
                cliente.setTabelaSecundariaID(c.getInt(16));
                cliente.setReferencia(c.getString(17));
                cliente.setVolumeAnterior(c.getInt(18));
                cliente.setVolumeAtual(c.getInt(19));
                cliente.setVariacao(c.getInt(20));

                cliente.getFormaPagamento().setFormaPagamentoID(c.getInt(24));
                cliente.getFormaPagamento().setDescricao(c.getString(25));
                cliente.setVolumeTop10(c.getInt(26));

                if (c.getInt(21) == 0)
                    cliente.setVendaZero(false);
                else
                    cliente.setVendaZero(true);

                if (c.getInt(22) == 0)
                    cliente.setPositivado(false);
                else
                    cliente.setPositivado(true);

                if (c.getInt(23) == 0)
                    cliente.setNaoVenda(false);
                else
                    cliente.setNaoVenda(true);

                if (c.getInt(27) == 0)
                    cliente.setExibePesquisa(false);
                else
                    cliente.setExibePesquisa(true);

                cliente.setCliente1x1(c.getInt(28) == 1);
                cliente.setTelevendas(c.getInt(29) == 1);
                cliente.setMotivoNaoVenda(c.getString(30));
                cliente.setSEFAZRestricao(c.getInt(31) == 1);
                cliente.setAtendido(c.getInt(32) == 1);
                cliente.setSubcanalConfirmado(c.getInt(33) == 1);
                cliente.setPesquisaCerveja(c.getInt(34) == 1);

                cliente.setPesquisaCervejaEnviada(c.getInt(35) == 1);
                cliente.setPesquisaEquipamentoEnviada(c.getInt(37) == 1);
                cliente.setPesquisaSubcanalEnviada(c.getInt(38) == 1);
                cliente.setCampanhaColgate(c.getInt(39) == 1);
                cliente.setIsentoTaxaBoleto(c.getInt(40) == 1);
                cliente.setIsentoAdicional(c.getInt(41) == 1);
                cliente.setAdicional(c.getFloat(42));
                cliente.setClassificacao(c.getString(43));
                cliente.setSubcanalID(c.getInt(44));
                cliente.setIsentoTaxaCartao(c.getInt(45) == 1);
                cliente.setRepasseRefPet(c.getInt(46) == 1);
                cliente.setRepasseLS(c.getInt(47) == 1);
                cliente.setExibeMedalha(c.getInt(48) == 1);
                cliente.setNovaCampanhaColgate(c.getInt(49) == 1);
                cliente.setNotaPDVNG(c.getFloat(50));
            }

        } finally {
            c.close();
        }
        SQLiteHelper.closeDB(this.context);

        return cliente;
    }

    @SuppressLint("SimpleDateFormat")
    public List<Titulo> carregaTitulos(int clienteID) throws ParseException {
        List<Titulo> titulos = new ArrayList<Titulo>();
        Titulo titulo;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Cursor c = SQLiteHelper.getCursor(this.context, "TituloCliente", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});
        try {
            if (c.moveToFirst())
                do {
                    titulo = new Titulo(c.getString(1), c.getInt(2), c.getInt(3), dateFormat.parse(c.getString(4)),
                            dateFormat.parse(c.getString(5)), c.getInt(6), c.getFloat(7), c.getFloat(8),
                            c.getString(9));
                    titulos.add(titulo);
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return titulos;
    }

    public List<FormaPagamento> getFormasPagamento(int clienteID) {
        List<FormaPagamento> formasPagamento = new ArrayList<FormaPagamento>();
        FormaPagamento formaPagamento;

        Cursor c = SQLiteHelper.getCursor(this.context, "ClienteCondicao", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});
        try {
            if (c.moveToFirst())
                do {
                    formaPagamento = new FormaPagamento(this.context);
                    formaPagamento.load(c.getInt(1));

                    formasPagamento.add(formaPagamento);
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return formasPagamento;
    }

    public List<Equipamento> getEquipamentos(int clienteID) {
        List<Equipamento> equipamentos = new ArrayList<Equipamento>();
        Equipamento equipamento;

        Cursor c = SQLiteHelper.getCursor(this.context, "Equipamento", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});

        try {
            if (c.moveToFirst())
                do {
                    equipamento = new Equipamento(c.getString(1), c.getString(2), c.getString(3), c.getInt(4),
                            c.getFloat(5), c.getFloat(6), c.getFloat(7), c.getString(8));

                    equipamentos.add(equipamento);
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return equipamentos;
    }

    @SuppressLint("SimpleDateFormat")
    public List<EquipamentoPesquisa> getEquipamentosPesquisa(int clienteID) throws ParseException {
        List<EquipamentoPesquisa> equipamentos = new ArrayList<EquipamentoPesquisa>();
        EquipamentoPesquisa equipamento;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd kk:mm");
        ;
        String data;

        Cursor c = SQLiteHelper.getCursor(this.context, "EquipamentoPesquisa", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});

        try {
            if (c.moveToFirst())
                do {
                    equipamento = new EquipamentoPesquisa(c.getString(1), c.getString(2), c.getString(4));

                    if (!c.getString(3).isEmpty()) {
                        data = c.getString(3);
                        equipamento.setData(dateFormat.parse(c.getString(3)));
                    }

                    equipamentos.add(equipamento);
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return equipamentos;
    }

    @SuppressLint("SimpleDateFormat")
    public List<Devolucao> getDevolucoes(int clienteID) throws ParseException {
        List<Devolucao> devolucoes = new ArrayList<Devolucao>();
        Devolucao devolucao;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Cursor c = SQLiteHelper.getCursor(this.context, "Devolucao", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});

        try {
            if (c.moveToFirst())
                do {
                    devolucao = new Devolucao(dateFormat.parse(c.getString(1)), c.getString(2), c.getString(3),
                            c.getInt(4), c.getInt(5), c.getString(6));

                    devolucoes.add(devolucao);
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return devolucoes;
    }

    public List<Campanha> getCampanhas(int clienteID) throws ParseException {
        List<Campanha> campanhas = new ArrayList<Campanha>();
        Campanha campanha;

        Cursor c = SQLiteHelper.getCursor(this.context, "Devolucao", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});

        try {
            if (c.moveToFirst())
                do {
                    campanha = new Campanha(c.getString(0), c.getInt(1), c.getInt(2));

                    campanhas.add(campanha);
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return campanhas;
    }

    public List<HistoricoVenda> loadHistoricoVenda(int clienteID) throws ParseException {
        List<HistoricoVenda> historico = new ArrayList<HistoricoVenda>();
        HistoricoVenda historicoVenda;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Cursor c = SQLiteHelper.getCursor(this.context, "HistoricoVenda", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});

        try {
            if (c.moveToFirst())
                do {
                    historicoVenda = new HistoricoVenda(this.context, dateFormat.parse(c.getString(1)), c.getFloat(2));

                    historicoVenda.loadItens(clienteID);
                    historico.add(historicoVenda);
                } while (c.moveToNext());
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return historico;
    }

    public void salvarPesquisaEquipamento(int clienteID, List<EquipamentoPesquisa> respostas) {
        ContentValues values = new ContentValues();

        SQLiteHelper.getDB(context).beginTransaction();
        try {
            SQLiteHelper.delete(context, "EquipamentoPesquisa", "ClienteID = ?",
                    new String[]{String.valueOf(clienteID)});

            for (EquipamentoPesquisa resposta : respostas) {
                values.put("ClienteID", clienteID);
                values.put("Geko", resposta.getGeko());
                values.put("Data", DateFormat.format("yyyyMMdd kk:mm", resposta.getData()).toString());
                values.put("Presente", resposta.getPresente());
                values.put("Logomarca", resposta.getLogomarca());
                values.put("Enviado", 0);

                SQLiteHelper.insert(context, "EquipamentoPesquisa", values);
            }

            SQLiteHelper.getDB(context).setTransactionSuccessful();
        } finally {
            if (SQLiteHelper.getDB(context).inTransaction())
                SQLiteHelper.getDB(context).endTransaction();
            SQLiteHelper.closeDB(context);
        }
    }

    public void registraTransmissaoPesquisaEquipamento(int clienteID) {
        //SQLiteHelper.getDB(context).execSQL("UPDATE EquipamentoPesquisa SET Enviado = 1 WHERE ClienteID = " + clienteID);

        SQLiteHelper.getDB(context).execSQL("UPDATE Cliente SET PesqEQEnviado = 1 WHERE ClienteID = " + clienteID);
        SQLiteHelper.closeDB(context);
    }

    public void registraTransmissaoPesquisaSubcanal(int clienteID) {
        SQLiteHelper.getDB(context).execSQL("UPDATE Cliente SET PesqSCEnviado = 1 WHERE ClienteID = " + clienteID);
        SQLiteHelper.closeDB(context);
    }

    public void registraTransmissaoPesquisaTrembala(int clienteID) {
        SQLiteHelper.getDB(context).execSQL("UPDATE Cliente SET PesqTBEnviado = 1 WHERE ClienteID = " + clienteID);
        SQLiteHelper.closeDB(context);
    }

    public void registraTransmissaoPesquisaCerveja(int clienteID) {
        SQLiteHelper.getDB(context)
                .execSQL("UPDATE PesqCervEnviado SET Enviado = 1 WHERE ClienteID = " + clienteID);
        SQLiteHelper.closeDB(context);
    }

    public void confirmaSubcanal(int clienteID, String subcanal) {
        String[] args = {String.valueOf(clienteID)};
        ContentValues values = new ContentValues();

        values.put("ClienteID", clienteID);
        values.put("Subcanal", subcanal);

        SQLiteHelper.getDB(context).beginTransaction();
        try {
            SQLiteHelper.delete(context, "ClienteSubcanalPesquisa", "ClienteID = ?", args);
            SQLiteHelper.insert(context, "ClienteSubcanalPesquisa", values);
            SQLiteHelper.getDB(context).setTransactionSuccessful();
        } finally {
            if (SQLiteHelper.getDB(context).inTransaction())
                SQLiteHelper.getDB(context).endTransaction();

            SQLiteHelper.closeDB(context);
        }
    }

    public String retornaSubcanalConfirmado(int clienteID) {
        String result = "";
        Cursor c = SQLiteHelper.getCursor(this.context, "ClienteSubcanalPesquisa", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});

        try {
            if (c.moveToFirst())
                result = c.getString(1);
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return result;

    }

    public List<Pedido> retornaListaPedidos(int clienteID) {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        Pedido pedido;
        PedidoItem pedidoItem;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String produtoid;

        Cursor p = SQLiteHelper.getCursor(context, "PedidoVenda", "ClienteID = ?", new String[]{String.valueOf(clienteID)});
        try {
            if (p.moveToFirst())
                do {
                    pedido = new Pedido(context, p.getInt(11));
                    pedido.setPedidoID(p.getInt(0));
                    pedido.getCliente().load(p.getInt(1));
                    pedido.getTipoPedido().load(p.getInt(2));
                    pedido.getFormaPagamento().load(p.getInt(3));

                    if (!p.isNull(4))
                        pedido.setDataAbertura(dateFormat.parse(p.getString(4)));

                    if (!p.isNull(5))
                        pedido.setDataEncerramento(dateFormat.parse(p.getString(5)));

                    if (!p.isNull(6))
                        pedido.setDataTransmissao(dateFormat.parse(p.getString(6)));

                    pedido.setObs(p.getString(8));

                    Cursor i = SQLiteHelper.getCursor(context, "PedidoVendaItem", new String[]{"ProdutoID", "Quantidade", "PrecoUnitario", "Desconto", "Repasse"},
                            "PedidoID = ?", new String[]{String.valueOf(p.getInt(0))});
                    try {
                        if (i.moveToFirst())
                            do {

                                produtoid = i.getString(0);
                                pedidoItem = new PedidoItem(context);
                                pedidoItem.getItem().setProdutoID(produtoid);
                                pedidoItem.getItem().load();
                                pedidoItem.setQuantidade(i.getInt(1));
                                pedidoItem.setPrecoUnitario(i.getFloat(2));
                                pedidoItem.setDesconto(i.getFloat(3));

                                if (i.getString(4).equals("0"))
                                    pedidoItem.setRepasse(false);
                                else
                                    pedidoItem.setRepasse(true);

                                pedido.getItens().add(pedidoItem);
                            } while (i.moveToNext());
                    } finally {
                        i.close();
                    }

                    pedidos.add(pedido);

                } while (p.moveToNext());
        } catch (Exception e) {
            Log.i("LynxME", e.getMessage().toString());

        } finally {
            p.close();
        }

        SQLiteHelper.closeDB(context);

        return pedidos;
    }

    public List<RegistroNaoVendaVO> retornaListaNaoVenda(int clienteID) {
        List<RegistroNaoVendaVO> lista = new ArrayList<RegistroNaoVendaVO>();
        RegistroNaoVendaVO naoVenda;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Cursor c = SQLiteHelper.getCursor(context, "RegistroNaoVenda", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});
        try {
            if (c.moveToFirst())
                do {
                    if (!c.isNull(4))
                        naoVenda = new RegistroNaoVendaVO(context, c.getInt(0), c.getInt(1), c.getInt(2), dateFormat.parse(c.getString(3)), dateFormat.parse(c.getString(4)));
                    else
                        naoVenda = new RegistroNaoVendaVO(context, c.getInt(0), c.getInt(1), c.getInt(2), dateFormat.parse(c.getString(3)), null);

                    lista.add(naoVenda);
                } while (c.moveToNext());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            c.close();
        }

        SQLiteHelper.closeDB(context);

        return lista;
    }

    public PesquisaCerveja retornaPesquisaCerveja(int clienteID) throws ParseException {
        PesquisaCerveja retorno;
        RespostaCerveja resposta;

        retorno = new PesquisaCerveja(context, clienteID);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cursor c = SQLiteHelper.getCursor(this.context, "PesquisaCerveja", "ClienteID = ?",
                new String[]{String.valueOf(clienteID)});

        try {
            if (c.moveToFirst()) {
                retorno.setData(dateFormat.parse(c.getString(4)));
                do {
                    resposta = new RespostaCerveja(c.getString(1), c.getFloat(2), c.getFloat(3));
                    retorno.getRespostas().add(resposta);

                } while (c.moveToNext());
            }
        } finally {
            c.close();
            SQLiteHelper.closeDB(this.context);
        }

        return retorno;
    }

    public List<Produto> retornaListaProdutos(int clienteID, int subcanalID, int tipoPedidoID, int vendedorID) {
        List<Produto> produtos = new ArrayList<Produto>();
        List<ItemSugestao> itens = new ArrayList<ItemSugestao>();
        String grupo;
        Cursor c;
        int grandeGrupo = 0;

        if (vendedorID >= 9001 && vendedorID <= 9099)
            grandeGrupo = 507;
        else if (vendedorID >= 9101 && vendedorID <= 9199)
            grandeGrupo = 510;
        else if (vendedorID >= 9201 && vendedorID <= 9299)
            grandeGrupo = 508;
        else if (vendedorID >= 9301 && vendedorID <= 9399)
            grandeGrupo = 509;
        else if (vendedorID >= 9501 && vendedorID <= 9599)
            grandeGrupo = 507;

        if (tipoPedidoID == 30)
            c = SQLiteHelper.getCursor(context, "Produto", "FamiliaID = ?", new String[]{String.valueOf(7)});
        else if (tipoPedidoID == 32)
            c = SQLiteHelper.getCursor(context, "Produto", "Categoria = ?", new String[]{String.valueOf(grandeGrupo)});
        else
            c = SQLiteHelper.getCursor(context, "Produto");

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

        c = SQLiteHelper.getCursor(context, "SugestaoPedidoCliente", "ClienteID = " + String.valueOf(clienteID));
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

        for (Produto produto : produtos) {
            for (ItemSugestao item : itens) {
                produto.setItemSugestao(true);

                if (produto.getProdutoID().equals(item.getProdutoID())) {
                    if (item.getDestacado())
                      produto.setDestacado(true);

                    grupo = item.getGrupo();
                    produto.setGrupo(grupo);
                }
            }
        }

        return produtos;
    }

    public List<Questao> getListaPerguntasPDVNG(int subcanalID) {
        List<Questao> listaPerguntas = new ArrayList<Questao>();

        Cursor questionario = SQLiteHelper.getCursor(context, "PDVQuestionario", "SubcanalID = " + String.valueOf(subcanalID));
        try {
            if (questionario.moveToFirst()) {
                Cursor perguntas = SQLiteHelper.getCursor(context, "PDVPergunta", "QuestionarioID = " + String.valueOf(questionario.getInt(0)));
                try {
                    if (perguntas.moveToFirst()) {
                        do {
                            listaPerguntas.add(new Questao(perguntas.getInt(1), perguntas.getString(2), perguntas.getFloat(3), perguntas.getInt(0), perguntas.getString(4)));
                        } while (perguntas.moveToNext());
                    }
                } finally {
                    perguntas.close();
                }
            }
        } finally {
            questionario.close();
            SQLiteHelper.closeDB(context);
        }

        return listaPerguntas;
    }
}