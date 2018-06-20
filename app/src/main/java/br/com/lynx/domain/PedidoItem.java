package br.com.lynx.domain;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;

import br.com.lynx.util.MathUtil;
import br.com.lynx.util.StringUtil;

public class PedidoItem {

    private int quantidade;
    private float precoUnitario;
    private float desconto;
    private float descontoAdicional;
    private float acrescimo;
    private boolean repasse;
    private Produto item;
    private NumberFormat currencyFormat;
    private DecimalFormat percentFormat;
    private Locale local;

    public PedidoItem(Context context) {
        item = new Produto(context);

        local = new Locale("pt", "BR");
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        percentFormat = new DecimalFormat("#,##0,00%", new DecimalFormatSymbols(local));
    }

    public Produto getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPrecoUnitario() {
        return (float) MathUtil.round(precoUnitario, 2);
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public float getDesconto() {
        return (float) MathUtil.round(desconto, 2);
    }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public float getDescontoAdicional() { return descontoAdicional; }

    public void setDescontoAdicional(float descontoAdicional) { this.descontoAdicional = descontoAdicional; }

    public float getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(float acrescimo) {
        this.acrescimo = acrescimo;
    }

    public float getValorDesconto() {
        float result = 0;
        float descontoTotal = desconto + descontoAdicional;

        result = getPrecoUnitario() * descontoTotal / 100;
        result = (float) MathUtil.round((double)result, 2);

        return result;
    }

    public float getValorAcrescimo() {
        return getPrecoUnitario() * (getAcrescimo() / 100);
    }

    public float getPrecoLiquido() {
        if (getValorDesconto() > 0)
            return (float) MathUtil.round(getPrecoUnitario() - getValorDesconto(), 2);
        else if (getValorAcrescimo() > 0)
            return (float) MathUtil.round(getPrecoUnitario() + getValorAcrescimo(), 2);
        else
            return getPrecoUnitario();
    }

    public float getValorTotal() {
        return (float) MathUtil.round(getQuantidade() * getPrecoLiquido(), 2);

        /*
        if (getAcrescimo() > 0)
            return (float) MathUtil.round((getPrecoLiquido() + getAcrescimo()) * getQuantidade(), 2);
        else
            return (float) MathUtil.round(getQuantidade() * getPrecoLiquido(), 2);
            */
    }

    public void setRepasse(boolean repasse) {
        this.repasse = repasse;
    }

    public boolean isRepasse() {

        return this.repasse;
    }

    public String toString(long numPedido) {
        String linha;

        linha = StringUtil.padLeft(String.valueOf(numPedido), 6, "0");
        linha += StringUtil.padLeft(String.valueOf(item.getProdutoID()), 7, " ");
        linha += StringUtil.padLeft(String.valueOf(quantidade), 8, "0");
        linha += StringUtil.padLeft(String.valueOf((int) (precoUnitario * 100)), 8, "0");
        linha += StringUtil.padLeft(String.valueOf((int) (desconto * 100)), 5, "0");
        linha += StringUtil.padLeft(String.valueOf((int) (getValorTotal() * 100)), 6, "0");

        if (isRepasse())
            linha += "S";
        else
            linha += "N";

        if (getAcrescimo() > 0)
            linha += StringUtil.padLeft(String.valueOf((int) (getAcrescimo() * 100)), 5, "0");
        else
            linha += "00000";

        linha += "\r\n";

        return linha;
    }

    public String validar(int tipoDesconto) {

        String retorno = "";

        if (this.quantidade == 0)
            retorno = "É necessário informar uma quantidade.";
        else if (this.desconto == 100 && tipoDesconto == 0)
            retorno = "O desconto não deve ser igual a 100%.";
        else if (this.desconto > 100 && tipoDesconto == 0)
            retorno = "O desconto não deve ser maior que 100%.";
        else if (this.getValorDesconto() > this.precoUnitario)
            retorno = "O valor do desconto não deve ser maior do que o valor unitário.";

        return retorno;
    }

    public String getFormattedItem() {
        return String.valueOf(this.item.getProdutoID()) + " - " + this.item.getDescricao();
    }

    public String getFormattedQuantidade() {
        return String.valueOf(this.quantidade);
    }

    public String getFormattedValorUnitario() {
        return currencyFormat.format(this.precoUnitario);
    }

    public String getFormattedValorDesconto() {
        return currencyFormat.format(this.getDesconto());
    }

    public String getFormattedValorLiquido() {
        return currencyFormat.format(this.getPrecoLiquido());
    }

    public String getFormattedValorTotal() {
        return currencyFormat.format(this.getValorTotal());
    }

    public String getFormattedDesconto() {
        return percentFormat.format(this.desconto);
    }

    public void validaDesconto(int clienteID) throws Exception {
        float descontoMaximo = item.getDescontoMaximo(clienteID);

        if (descontoMaximo != -1) {
            if (descontoMaximo < desconto)
              throw new Exception("O desconto dado é maior que o desconto permitido.");
        }
        else if (item.getDescontoMaximo() < desconto)
            throw new Exception("O desconto dado é maior que o desconto permitido.");
    }

    public void validaDescontoCampanhaColgate() throws Exception {
        if (item.getDescontoMaximoCampanhaColgate() < desconto)
            throw new Exception("O desconto dado é maior que o desconto permitido.");
    }

    public void validaItemCampanhaColgate()  throws Exception {
        if (!item.inCampanhaColgate())
            throw new Exception("Esse item não faz parte da campanha colgate.");
    }

    public void validaDescontoMontesClaros() throws Exception{
        double descontoMaximo = 0;

        if (desconto == 0)
            return;

        if (quantidade >= 1 && quantidade < 5)
            descontoMaximo = 2.5;
        else if (quantidade >= 5 && quantidade < 10)
            descontoMaximo = 3.5;
        else if (quantidade >= 10 && quantidade < 20)
            descontoMaximo = 4.5;
        else if (quantidade >= 20 && quantidade < 30)
            descontoMaximo = 5.5;
        else if (quantidade >= 30)
            descontoMaximo = 6.5;

        if (desconto != descontoMaximo)
            throw new Exception("O desconto dado não é permitido.");
    }
}
