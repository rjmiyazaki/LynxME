package br.com.lynx.domain;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import br.com.lynx.R;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.model.Configuracao;
import br.com.lynx.util.FlexxGPS;
import br.com.lynx.util.MathUtil;
import br.com.lynx.util.StringUtil;

public class Pedido {

    private long pedidoID;
    private Date dataAbertura;
    private Date dataEncerramento;
    private Date dataTransmissao;
    private String obs;
    private Cliente cliente;
    private TipoPedido tipoPedido;
    private FormaPagamento formaPagamento;
    private List<PedidoItem> itens;
    private PedidoDAO daoObject;
    private String latitude;
    private String longitude;
    private int vendedorID;

    public Pedido(Context context, int vendedorID) {
        this.tipoPedido = new TipoPedido(context);
        this.cliente = new Cliente(context);
        this.formaPagamento = new FormaPagamento(context);
        this.itens = new ArrayList<PedidoItem>();
        this.vendedorID = vendedorID;

        try {
            latitude = FlexxGPS.getLatitude();
            longitude = FlexxGPS.getLongitude();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        daoObject = new PedidoDAO(context);
    }

    public Pedido(Context context, TipoPedido tipoPedido, Cliente cliente, int vendedorID) {
        this.tipoPedido = tipoPedido;
        this.cliente = cliente;
        this.itens = new ArrayList<PedidoItem>();
        this.dataAbertura = new Date();
        this.vendedorID = vendedorID;

        try {
            latitude = FlexxGPS.getLatitude();
            longitude = FlexxGPS.getLongitude();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        daoObject = new PedidoDAO(context);
    }

    public int getVendedorID() {
        return vendedorID;
    }

    public long getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(long pedidoID) {
        this.pedidoID = pedidoID;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento value) {
        this.formaPagamento = value;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date value) {
        dataAbertura = value;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date value) {
        dataEncerramento = value;
    }

    public Date getDataTransmissao() {
        return dataTransmissao;
    }

    public void setDataTransmissao(Date value) {
        dataTransmissao = value;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        obs = obs.replace("\n", "/");
        this.obs = obs;
    }

    public String getSituacao() {
        if (dataEncerramento != null && dataTransmissao != null)
            return "Transmitido";
        else if (dataEncerramento != null)
            return "Finalizado";
        else
            return "Aberto";
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    private float getValorTaxas() {
        float result = 0, taxa = 0, adicional = 0, taxaCartao = 0;

        if (formaPagamento != null) {
            if (formaPagamento.getFormaPagamentoID() != 133 && formaPagamento.getPrazoMedio() > 1) {
                if (!cliente.getIsentoTaxaBoleto())
                    taxa = formaPagamento.getTaxa() * formaPagamento.getParcelas();

                if (!cliente.getIsentoAdicional()) {
                    adicional = (getValorPedido() * formaPagamento.getAdicional() / 100) * formaPagamento.getPrazoMedio();
                }
            } /*else if ((formaPagamento.getFormaPagamentoID() == 133) && (!cliente.getIsentoTaxaCartao())) {
                if (cliente.getSubcanalID() == 11)
                    taxaCartao = (getValorPedido() * 1 / 100);
                else
                    taxaCartao = (getValorPedido() * 2 / 100);
            }*/
        }

        if (taxa > 0)
            result = taxa;
        else if (adicional > 0)
            result = adicional;
        else if (taxaCartao > 0)
            result = taxaCartao;
        else
            result = 0;

        return result;
    }

    public float getValorPedido() {
        float valorPedido = 0;

        for (PedidoItem pedidoItem : itens)
            valorPedido += pedidoItem.getValorTotal();

        return valorPedido;
    }

    public float getValorPedidoTotal() {
        return getValorPedido() + getValorTaxas();
    }

    public float getValorPedidoCorrigido() {
        float valorPedido = 0;
        float valorUnitario, valorDesconto;

        for (PedidoItem pedidoItem : itens) {
            valorUnitario = (float) MathUtil.round(pedidoItem.getPrecoUnitario() / pedidoItem.getItem().getFracionador(), 2);
            valorDesconto = valorUnitario * pedidoItem.getDesconto() / 100;
            valorDesconto = (float) MathUtil.round(valorDesconto, 2);
            valorUnitario = valorUnitario - valorDesconto;
            valorPedido = valorPedido + (valorUnitario * pedidoItem.getQuantidade() * pedidoItem.getItem().getFracionador());
        }

        return valorPedido + getValorTaxas();
    }

    public void finalizar(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
        dataEncerramento = new Date();

        daoObject.save(this);
    }

    public void finalizarNaoVenda(int motivoID, String motivo) {
        dataEncerramento = new Date();
        daoObject.registrarNaoVenda(this, motivoID);
    }

    public void registrarTransmissao() {
        dataTransmissao = new Date();
        daoObject.update(this);
    }

    public String getLatitude() {
        return latitude.replace(",", ".");
    }

    public String getLongitude() {
        return longitude.replace(",", ".");
    }

    public String toString() {
        String linha;
        SimpleDateFormat dateFormat, hourFormat;

        dateFormat = new SimpleDateFormat("yyyyMMdd");
        hourFormat = new SimpleDateFormat("kkmm");

        linha = StringUtil.padLeft(String.valueOf(pedidoID), 6, "0");
        linha += StringUtil.padLeft(String.valueOf(cliente.getClienteID()), 6, "0");
        linha += StringUtil.padLeft(String.valueOf(tipoPedido.getTipoPedidoID()), 4, "0");
        linha += StringUtil.padLeft(String.valueOf(formaPagamento.getFormaPagamentoID()), 4, "0");
        linha += dateFormat.format(dataAbertura);
        linha += hourFormat.format(dataAbertura);
        linha += StringUtil.padLeft(String.valueOf((int) (getValorPedido() * 100)), 8, "0");
        linha += StringUtil.padRight(obs, 80, " ");
        linha += "\r\n";

        return linha;
    }

    public String getFlexxGPSInfo() {
        String linha;
        SimpleDateFormat hourFormat, dateFormat;

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        hourFormat = new SimpleDateFormat("HH:mm:ss");

        linha = "1607|" +
                String.valueOf(vendedorID) + "|" +
                String.valueOf(cliente.getClienteID()) + "|" +
                this.getLatitude() + "|" +
                this.getLongitude() + "|" +
                this.getLatitude() + "|" +
                this.getLongitude() + "|" +
                dateFormat.format(dataAbertura) + "|" +
                hourFormat.format(dataAbertura) + "|" +
                hourFormat.format(dataEncerramento) + "|" +
                "|" +
                String.valueOf(this.pedidoID) + "|" +
                this.formaPagamento.getDescricao() + "|" +
                this.getValorPedido() + "|" +
                this.getCaixas() + "[#]";

        return linha;
    }

    public String getFlexxGPSInfoNaoVenda(String motivo) {
        String linha;
        SimpleDateFormat hourFormat, dateFormat;

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        hourFormat = new SimpleDateFormat("HH:mm:ss");

        linha = "1607|" +
                String.valueOf(vendedorID) + "|" +
                String.valueOf(cliente.getClienteID()) + "|" +
                this.getLatitude() + "|" +
                this.getLongitude() + "|" +
                this.getLatitude() + "|" +
                this.getLongitude() + "|" +
                dateFormat.format(dataAbertura) + "|" +
                hourFormat.format(dataAbertura) + "|" +
                hourFormat.format(dataEncerramento) + "|" +
                motivo + "|" +
                String.valueOf(this.pedidoID) + "|" +
                "|" +
                "|" +
                "[#]";

        return linha;
    }

    public List<String> getConteudoArquivo(Context context) {
        List<String> arquivo = new ArrayList<String>();
        String version = "";
        Configuracao configuracao;

        version = context.getString(R.string.app_versao);
        configuracao = Configuracao.getInstance(context);

        // Monta o cabeçalho do pedido
        arquivo.add("<PEDIDOS.OUT>\r\n");
        arquivo.add(toString());
        arquivo.add("<\\PEDIDOS.OUT>\r\n");

        // Insere os itens do pedido
        arquivo.add("<IPEDIDOS.OUT>\r\n");

        for (PedidoItem item : getItens())
            arquivo.add(item.toString(pedidoID));

        arquivo.add("<\\IPEDIDOS.OUT>\r\n");

        // Informa a versão do sistema
        arquivo.add("<Version>\r\n");
        arquivo.add(version + "\r\n");
        arquivo.add("<\\Version>\r\n");

        // Informa o vendedor que esta operando o sistema
        arquivo.add("<VendedorID>\r\n");
        arquivo.add(configuracao.getVendedorID() + "\r\n");
        arquivo.add("<\\VendedorID>\r\n");

        return arquivo;
    }

    public void adicionaItem(PedidoItem item) throws Exception {
        // Verifica se o item é permitido para o cliente
        if (item.getItem().getProdutoID().equals("2800001") || item.getItem().getProdutoID().equals("2800002")) {
            if (itens.size() == 1 && itens.get(0).getItem().getProdutoID() != item.getItem().getProdutoID())
                throw new Exception("Esse pedido só pode conter um item de combo.");

            if (!getCliente().getCampanhaColgate())
                throw new Exception("Esse combo não é permitido para esse cliente.");

            for (int i = 0; i < itens.size(); i++) {
                if (!itens.get(i).getItem().getProdutoID().equals("2800001") && !item.getItem().getProdutoID().equals("2800002"))
                    throw new Exception("Um item de combo dever ser exclusivo dentro do pedido.");
            }

        } else {
            for (int i = 0; i < itens.size(); i++) {
                if (itens.get(i).getItem().getProdutoID().equals("2800001") || item.getItem().getProdutoID().equals("2800002"))
                    throw new Exception("Esse pedido só pode conter itens de combo.");
            }
        }

        // Verifica se o produto já existe no pedido. Caso exista, ocorrerá uma substituição
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getItem().getProdutoID() == item.getItem().getProdutoID()) {
                itens.remove(i);
                break;
            }
        }

        itens.add(item);
    }

    public void removeItem(PedidoItem item) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getItem().getProdutoID() == item.getItem().getProdutoID()) {
                itens.remove(i);
                break;
            }
        }
    }

    public int getCaixas() {
        int result = 0;

        for (PedidoItem item : itens)
            result += item.getQuantidade() * item.getItem().getFracionador() / 1;

        return result;
    }

    public Boolean isComboColgate() {
        Boolean result = false;

        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getItem().getProdutoID().equals("2800001") || itens.get(i).getItem().getProdutoID().equals("2800002")) {
                result = true;
                break;
            }
        }

        return result;
    }

    public Boolean validarCampanhaColgatePedagio() {
        Boolean result = false;

        Boolean grupo1 = false;
        Boolean grupo2 = false;
        Boolean grupo3 = false;

        List<String> listaPedagioGrupo1 = daoObject.retornaListaPedagioCampanhaColgateGrupo1();
        List<String> listaPedagioGrupo2 = daoObject.retornaListaPedagioCampanhaColgateGrupo2();
        List<String> listaPedagioGrupo3 = daoObject.retornaListaPedagioCampanhaColgateGrupo3();

        for (String item : listaPedagioGrupo1)
            for (PedidoItem pedidoItem : itens)
                if (pedidoItem.getItem().getProdutoID().equals(item))
                    grupo1 = true;

        if (grupo1) {
            for (String item : listaPedagioGrupo2)
                for (PedidoItem pedidoItem : itens)
                    if (pedidoItem.getItem().getProdutoID().equals(item))
                        grupo2 = true;

            if (grupo2) {
                for (String item : listaPedagioGrupo3)
                    for (PedidoItem pedidoItem : itens)
                        if (pedidoItem.getItem().getProdutoID().equals(item))
                            grupo3 = true;
            }
        }

        if (grupo1 && grupo2 && grupo3)
            result = true;

        return result;
    }

    public void aplicarAcaoCampanhaColgate(){
        double valorPedido = 0;
        double valorDesconto = 0;
        double valorMaximoCampanhaColgate = daoObject.retornaMaximoCampanhaColgate();
        double taxaDescontoCampanhaColgate = daoObject.retornaDescontoCampanhaColgate();

        for (PedidoItem item : itens) {
            item.setDescontoAdicional(0);

            valorDesconto = item.getPrecoUnitario() * item.getDesconto() / 100;
            valorPedido = valorPedido + ((item.getPrecoUnitario() - valorDesconto) * item.getQuantidade());
        }

        if (valorPedido > valorMaximoCampanhaColgate)
            valorDesconto =  MathUtil.round(valorMaximoCampanhaColgate * taxaDescontoCampanhaColgate / 100, 2);
        else
            valorDesconto =  valorPedido * taxaDescontoCampanhaColgate / 100;

        for (PedidoItem item : itens){
            double valorAbater = valorDesconto / valorPedido * item.getValorTotal();
            double valorPorItem = valorAbater / item.getQuantidade();
            double descontoTotal = (item.getPrecoUnitario() - item.getPrecoLiquido() + valorPorItem) / item.getPrecoUnitario() * 100;
            double descontoItem = item.getDesconto();
            double descontoAdicional = descontoTotal - descontoItem;

            item.setDescontoAdicional((float)descontoAdicional);
        }
    }

    public Boolean validaValorMinimoCampanhaColgate(){
        Boolean result = false;
        double valor = 0;
        double desconto = 0;
        double descontoMaximo = 0;
        double valorMinimo = daoObject.retornaMinimoCampanhaColgate();

        for (PedidoItem item : itens){
            descontoMaximo = item.getItem().getDescontoMaximoNGCampanhaColgate();

            if (item.getDesconto() > 0){
                desconto = item.getPrecoUnitario() * descontoMaximo / 100;
                valor = valor + ((item.getPrecoUnitario() - desconto) * item.getQuantidade());
            } else {
                valor = valor + (item.getPrecoLiquido() * item.getQuantidade());
            }
        }

        result = (valor >= valorMinimo);

        return result;
    }

    public Boolean validaValorDescontoCampanhaColgate(){
        Boolean result = false;
        double valorPedido = 0;
        double descontoMaximo = 0;
        double valorDescontoNG = 0;
        double desconto = 0;
        double totalDesconto = 0;
        double descontoPermitido = 0;
        double descontoAcao = 0;
        double valorMaximoCampanhaColgate = daoObject.retornaMaximoCampanhaColgate();
        double taxaDescontoCampanhaColgate = daoObject.retornaDescontoCampanhaColgate();

        for (PedidoItem item : itens){
            descontoMaximo = item.getItem().getDescontoMaximoNGCampanhaColgate();

            if (item.getDesconto() > 0){
                desconto = item.getPrecoUnitario() * descontoMaximo / 100;
                valorPedido = valorPedido + ((item.getPrecoUnitario() - desconto) * item.getQuantidade());
                valorDescontoNG = valorDescontoNG + (desconto * item.getQuantidade());
                totalDesconto = totalDesconto + (item.getValorDesconto() * item.getQuantidade());
            } else {
                valorPedido = valorPedido + (item.getPrecoLiquido() * item.getQuantidade());
            }
        }

        if (valorPedido > valorMaximoCampanhaColgate)
            descontoPermitido = valorMaximoCampanhaColgate * taxaDescontoCampanhaColgate / 100;
        else
            descontoPermitido = valorPedido * taxaDescontoCampanhaColgate / 100;

        descontoAcao = totalDesconto - valorDescontoNG;

        result = (descontoAcao < descontoPermitido);

        return result;
    }
}