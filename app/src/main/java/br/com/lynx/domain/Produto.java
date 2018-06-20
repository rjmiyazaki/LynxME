package br.com.lynx.domain;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

import br.com.lynx.dao.ProdutoDAO;
import br.com.lynx.model.TabelaPreco;
import br.com.lynx.util.MathUtil;

public class Produto {
    private String produtoID;
    private String descricao;
    private String unidade;
    private int fracionador;
    private int familia;
    private int categoria;
    private int uniCaixa;
    private int estoque;
    private float descontoMaximo;
    private Boolean destacado;
    private Boolean itemSugestao;
    private String grupo;
    private ArrayList<TabelaPreco> tabelasPreco;
    private ProdutoDAO daoObject;

    public Produto(Context context) {
        daoObject = new ProdutoDAO(context);

        tabelasPreco = new ArrayList<TabelaPreco>();
        this.destacado = false;
    }

    public Produto(Context context, String produtoID, String descricao,
                   String unidade, int fracionador, int categoria, int uniCaixa, int familia, int estoque, float descontoMaximo) {
        daoObject = new ProdutoDAO(context);

        this.produtoID = produtoID;
        this.destacado = false;
        setValues(descricao, unidade, fracionador, categoria, uniCaixa, familia, estoque, descontoMaximo);

        tabelasPreco = new ArrayList<TabelaPreco>();
    }

    public void setValues(String descricao, String unidade, int fracionador,
                          int categoria, int uniCaixa, int familia, int estoque, float descontoMaximo) {
        this.descricao = descricao;
        this.unidade = unidade;
        this.fracionador = fracionador;
        this.categoria = categoria;
        this.uniCaixa = uniCaixa;
        this.familia = familia;
        this.estoque = estoque;
        this.descontoMaximo = descontoMaximo;
    }

    public void setProdutoID(String produtoID) {
        this.produtoID = produtoID;
    }

    public String getProdutoID() {
        return produtoID;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public int getFracionador() {
        return fracionador;
    }

    public int getFamilia() {
        return familia;
    }

    public int getEstoque() {
        return estoque;
    }

    public float getDescontoMaximo() {
        return descontoMaximo;
    }

    public float getDescontoMaximo(int clienteID) {
        return daoObject.getDescontoCliente(clienteID, produtoID);
    }

    public ArrayList<TabelaPreco> getTabelasPreco() {
        return tabelasPreco;
    }

    public void load() {
        daoObject.load(this);
    }

    public void carregaTabelasPreco() {
        daoObject.carregaTabelaPreco(this);
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String value) {
        this.grupo = value;
    }

    public Boolean getItemSugestao() {
        return itemSugestao;
    }

    public void setItemSugestao(Boolean value) {
        itemSugestao = value;
    }

    public float retornaValorPorTabela(int tabelaPrincipalID,
                                       int tabelaSecundariaID) {
        float result = 0;

        if (tabelasPreco.size() == 0)
            carregaTabelasPreco();

        for (TabelaPreco item : tabelasPreco) {
            if (item.getID() == tabelaPrincipalID) {
                result = (float) MathUtil.round(item.getValor() * fracionador, 2);
                break;
            }
        }

        if (result == 0)
            for (TabelaPreco item : tabelasPreco) {
                if (item.getID() == tabelaSecundariaID) {
                    result = (float) MathUtil.round(item.getValor() * fracionador, 2);
                    break;
                }
            }

        return result;
    }

    public float retornaValorPorUnidadePorTabela(int tabelaPrincipalID, int tabelaSecundariaID) {
        float result = 0;

        for (TabelaPreco item : tabelasPreco) {
            if (item.getID() == tabelaPrincipalID) {
                result = (float) MathUtil.round(item.getValor(), 2);
                break;
            }
        }

        if (result == 0)
            for (TabelaPreco item : tabelasPreco) {
                if (item.getID() == tabelaSecundariaID) {
                    result = (float) MathUtil.round(item.getValor(), 2);
                    break;
                }
            }

        return result;
    }

    public float getDescontoMaximoNGCampanhaColgate(){
        float desconto = daoObject.descontoCampanhaColgate(produtoID);

        return desconto;
    }

    public float getDescontoMaximoCampanhaColgate(){
        float desconto = daoObject.descontoCampanhaColgate(produtoID);
        return desconto;
    }

    public boolean inCampanhaColgate(){
        return daoObject.inCampanhaColgate(produtoID);
    }

    public float getCaixas(float quantidade){
        return quantidade / uniCaixa;
    }
}
