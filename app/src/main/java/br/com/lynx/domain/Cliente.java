package br.com.lynx.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import br.com.lynx.dao.ClienteDAO;
import br.com.lynx.model.Campanha;
import br.com.lynx.model.Devolucao;
import br.com.lynx.model.Equipamento;
import br.com.lynx.model.EquipamentoPesquisa;
import br.com.lynx.model.HistoricoVenda;
import br.com.lynx.model.ItemCampanha;
import br.com.lynx.model.PesquisaCerveja;
import br.com.lynx.model.TabelaPreco;
import br.com.lynx.model.Titulo;
import br.com.lynx.vo.RegistroNaoVendaVO;

public class Cliente {

	private Context context;
	private int clienteID;
	private String fantasia;
	private String razaoSocial;
	private String endereco;
	private String bairro;
	private String complemento;
	private long cep;
	private String cidade;
	private String fone;
	private long cnpj;
	private String ie;
	private String referencia;
	private int rota;
	private int ordem;
	private int tipo;
	private String subcanal;
	private String obs;
	private boolean positivado;
	private boolean naoVenda;
	private boolean vendaZero;
	private int volumeAnterior;
	private int volumeAtual;
	private int variacao;
	private int volumeTop10;
	private ClienteDAO daoObject;
	private List<FormaPagamento> formasPagamento;
	private List<Titulo> titulos;
	private List<Equipamento> equipamentos;
	private List<EquipamentoPesquisa> equipamentosPesquisa;
	private List<Devolucao> devolucoes;
	private List<Campanha> campanhas;
	private int tabelaPadraoID;
	private int tabelaSecundariaID;
	private boolean equipamento;
	private FormaPagamento formaPagamento;
	private List<HistoricoVenda> historicoVenda;
	private boolean exibePesquisa;
	private boolean cliente1x1;
	private boolean televendas;
	private List<ItemCampanha> itensArrastao;
	private String motivoNaoVenda;
	private boolean sefazRestricao;
	private String novoTelefone;
	private boolean atendido;
	private boolean subcanalConfirmado;
    private boolean pesquisaCerveja;
	private boolean pesquisaCervejaEnviada;
	private boolean pesquisaTBEnviada;
	private boolean pesquisaEquipamentoEnviada;
	private boolean pesquisaSubcanalEnviada;
	private boolean campanhaColgate;
	private boolean isentoTaxaBoleto;
	private boolean isentoAdicional;
	private double adicional;
    private String classificacao;
    private int subcanalID;
    private boolean isentoTaxaCartao;
	private boolean repasseRefPet;
	private boolean repasseLS;
	private boolean novaCampanhaColgate;
	private float notaPDVNG;

    public Cliente(Context context) {
        daoObject = new ClienteDAO(context);

        formasPagamento = new ArrayList<FormaPagamento>();
        equipamentos = new ArrayList<Equipamento>();
        equipamentosPesquisa = new ArrayList<EquipamentoPesquisa>();
        titulos = new ArrayList<Titulo>();
        formaPagamento = new FormaPagamento(context);
        historicoVenda = new ArrayList<HistoricoVenda>();
        itensArrastao = new ArrayList<ItemCampanha>();

        this.context = context;
    }

    public Cliente(Context context, int clienteID) {
        daoObject = new ClienteDAO(context);

        this.clienteID = clienteID;
        formasPagamento = new ArrayList<FormaPagamento>();
        equipamentos = new ArrayList<Equipamento>();
        equipamentosPesquisa = new ArrayList<EquipamentoPesquisa>();
        titulos = new ArrayList<Titulo>();
        formaPagamento = new FormaPagamento(context);
        historicoVenda = new ArrayList<HistoricoVenda>();
        itensArrastao = new ArrayList<ItemCampanha>();

        this.context = context;
    }

	public float getNotaPDVNG(){
		return notaPDVNG;
	}

	public void setNotaPDVNG(float value){
		this.notaPDVNG = value;
	}

	public boolean isExibeMedalha() {
		return exibeMedalha;
	}

	public void setExibeMedalha(boolean exibeMedalha) {
		this.exibeMedalha = exibeMedalha;
	}

	private boolean exibeMedalha;

	public boolean getRepasseRefPet() { return repasseRefPet; }

	public void setRepasseRefPet(boolean value){
		this.repasseRefPet = value;
	}

	public boolean getRepasseLS(){ return repasseLS; }

	public void setRepasseLS(boolean value){
		this.repasseLS = value;
	}

    public boolean getIsentoTaxaCartao() {
        return isentoTaxaCartao;
    }

    public void setIsentoTaxaCartao(boolean isentoTaxaCartao) {
        this.isentoTaxaCartao = isentoTaxaCartao;
    }

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public List<ItemCampanha> getItensArrastao(){

		if (itensArrastao.size() == 0)
			loadItensArrastao();

		return itensArrastao;
	}

	public List<HistoricoVenda> getHistoricoVenda(){
		return historicoVenda;
	}

	public int getClienteID() {
		return clienteID;
	}

	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public long getCep() {
		return cep;
	}

	public void setCep(long cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public long getCnpj() {
		return cnpj;
	}

	public void setCnpj(long cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getCodRota() {
		return rota;
	}

	public void setCodRota(int rota) {
		this.rota = rota;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

    public void setPesquisaCerveja(boolean pesquisaCerveja){
        this.pesquisaCerveja = pesquisaCerveja;
    }

    public boolean getPesquisaCerveja(){
        return pesquisaCerveja;
    }

    public void setPesquisaTBEnviada(boolean pesquisaTBEnviada){
		this.pesquisaTBEnviada = pesquisaTBEnviada;
	}

	public boolean getPesquisaTBEnviada(){
		return pesquisaTBEnviada;
	}

	public void setPesquisaEquipamentoEnviada(boolean pesquisaEquipamentoEnviada){
		this.pesquisaEquipamentoEnviada = pesquisaEquipamentoEnviada;
	}

	public boolean getPesquisaEquipamentoEnviada(){
		return pesquisaEquipamentoEnviada;
	}

	public void setPesquisaSubcanalEnviada(boolean pesquisaSubcanalEnviada){
		this.pesquisaSubcanalEnviada = pesquisaSubcanalEnviada;
	}

	public boolean getPesquisaSubcanalEnviada(){
		return pesquisaSubcanalEnviada;
	}

	public String getNaturezaJuridica() {
		if (tipo == 1)
			return "CPF";
		else
			return "CNPJ";
	}

	public boolean isPositivado() {
		return positivado;
	}

	public void setPositivado(boolean positivado) {
		this.positivado = positivado;
	}

	public boolean isNaoVenda() {
		return naoVenda;
	}

	public boolean isVendaZero() {
		return vendaZero;
	}

	public void setVendaZero(boolean vendaZero) {
		this.vendaZero = vendaZero;
	}

	public void setNaoVenda(boolean naoVenda) {
		this.naoVenda = naoVenda;
	}

	public void setTabelaPadraoID(int tabelaPadraoID) {
		this.tabelaPadraoID = tabelaPadraoID;
	}

	public void setTabelaSecundariaID(int tabelaSecundariaID) {
		this.tabelaSecundariaID = tabelaSecundariaID;
	}

	public String getStatus() {
		if (naoVenda)
			return motivoNaoVenda; //   "Não venda";
		else if (positivado)
			return "Positivado";
		else
			return "Não visitado";
	}

	public boolean isEquipamento() {
		return equipamento;
	}

	public void setEquipamento(boolean equipamento) {
		this.equipamento = equipamento;
	}

	public void load(int id) {
		this.clienteID = id;

		daoObject.load(this);
	}

	public List<TabelaPreco> tabelasDisponiveis(Produto produto) {
		ArrayList<TabelaPreco> tabelas = new ArrayList<TabelaPreco>();
		float valor = produto.retornaValorPorTabela(tabelaPadraoID, tabelaSecundariaID);

		tabelas.add(new TabelaPreco(1, "Tabela base", valor));

		if (produto.retornaValorPorTabela(8, 8) != 0)
			tabelas.add(new TabelaPreco(8, "Tabela repasse", produto.retornaValorPorTabela(8, 8)));

		return tabelas;
	}

	public String getSubcanal(){
		return subcanal;
	}

	public void setSubcanal(String subcanal){
		this.subcanal = subcanal;
	}

	public int getVolumeAnterior(){
		return volumeAnterior;
	}

	public void setVolumeAnterior(int volumeAnterior){
		this.volumeAnterior = volumeAnterior;
	}

	public int getVolumeAtual(){
		return volumeAtual;
	}

	public void setVolumeAtual(int volumeAtual){
		this.volumeAtual = volumeAtual;
	}

	public int getVariacao(){
		return variacao;
	}

	public void setVariacao(int variacao){
		this.variacao = variacao;
	}

	public void setVolumeTop10(int volumeTop10){
		this.volumeTop10 = volumeTop10;
	}

	public int getVolumeTop10(){
		return volumeTop10;
	}

	public int getVariacaoVolumeTop10(){
		return this.volumeAtual - this.volumeTop10;
	}

	public void setExibePesquisa(boolean exibePesquisa){
		this.exibePesquisa = exibePesquisa;
	}

	public boolean getExibePesquisa(){
		return this.exibePesquisa;
	}

	public void setCliente1x1(boolean cliente1x1){
		this.cliente1x1 = cliente1x1;
	}

	public boolean getPossuiCondicao1x1(){
		return this.cliente1x1;
	}

	public void setTelevendas(boolean televendas){
		this.televendas = televendas;
	}

	public boolean getPossuiAtendimentoTelevendas(){
		return this.televendas;
	}

	public void setPesquisaCervejaEnviada(boolean value){
		pesquisaCervejaEnviada = value;
	}

	public boolean getPesquisaCervejaEnviada(){
		return  pesquisaCervejaEnviada;
	}

	public List<FormaPagamento> getFormasPagamento() {
		if (formasPagamento.isEmpty())
			formasPagamento = daoObject.getFormasPagamento(clienteID);

		return formasPagamento;
	}

	public List<Titulo> getTitulos() throws ParseException {
		titulos = daoObject.carregaTitulos(this.clienteID);

		return titulos;
	}

	public List<Equipamento> getEquipamentos() {
		equipamentos = daoObject.getEquipamentos(this.clienteID);

		return equipamentos;
	}

	public List<EquipamentoPesquisa> getEquipamentosPesquisa(){
		try {
			if (equipamentosPesquisa.size() == 0)
				equipamentosPesquisa = daoObject.getEquipamentosPesquisa(clienteID);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return equipamentosPesquisa;
	}

	public FormaPagamento getFormaPagamento(){
		return formaPagamento;
	}

	public List<Devolucao> getDevolucoes(){
		try {
			devolucoes = daoObject.getDevolucoes(this.clienteID);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return devolucoes;
	}

	public void loadHistoricoVenda(){
		try {
			historicoVenda = daoObject.loadHistoricoVenda(clienteID);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadItensArrastao(){
		ClienteDAO clienteDAO;
		clienteDAO = new ClienteDAO(this.context);

		itensArrastao.clear();
		itensArrastao = clienteDAO.carregaListaArrastao(clienteID);
	}

	public void setMotivoNaoVenda(String motivoNaoVenda){
		this.motivoNaoVenda = motivoNaoVenda;
	}

	public String getMotivoNaoVenda(){
		return motivoNaoVenda;
	}

	public void setSEFAZRestricao(boolean sefazRestricao){
		this.sefazRestricao = sefazRestricao;
	}

	public boolean getSEFAZRestricao(){
		return sefazRestricao;
	}

	public void setNovoTelefone(String novoTelefone){
		this.novoTelefone = novoTelefone;
	}

	public String getNovoTelefone(){
		return this.novoTelefone;
	}

	public void setAtendido(boolean atendido){
		this.atendido = atendido;
	}

	public boolean getAtendido(){
		return this.atendido;
	}

	public void setSubcanalConfirmado(boolean confirmacao){
		this.subcanalConfirmado = confirmacao;
	}

	public boolean getSubcanalConfirmado() { return this.subcanalConfirmado; }

	public boolean getCampanhaColgate(){
		return campanhaColgate;
	}

	public void setCampanhaColgate(boolean value){
		this.campanhaColgate = value;
	}

	public void salvarPesquisa(){
		ClienteDAO clienteDAO;
		clienteDAO = new ClienteDAO(this.context);

		clienteDAO.salvarPesquisaEquipamento(clienteID, equipamentosPesquisa);
	}

	public void registrarEnvioPesquisa(){
		ClienteDAO clienteDAO;
		clienteDAO = new ClienteDAO(this.context);

		clienteDAO.registraTransmissaoPesquisaEquipamento(clienteID);
	}

	public void confirmaSubcanal(String subcanal){
		ClienteDAO clienteDAO = new ClienteDAO(this.context);
		clienteDAO.confirmaSubcanal(clienteID, subcanal);
	}

	public String retornaSubcanalConfirmadoPesquisa(){
		ClienteDAO clienteDAO = new ClienteDAO(this.context);

		return  clienteDAO.retornaSubcanalConfirmado(clienteID);
	}

	public String getPontodeReferencia(){
		String retorno = "";

		if (complemento.trim().length() > 0 && referencia.trim().length() > 0)
			retorno = complemento + "\r\n" + referencia;
		else if (complemento.trim().length() > 0 && referencia.equals(""))
			retorno = complemento;
		else if (referencia.trim().length() > 0 && complemento.equals(""))
			retorno = referencia;

		return retorno;
	}

	public String getIdentificacao() {
		return String.valueOf(clienteID) + " - " + razaoSocial;
	}

	public void setIsentoTaxaBoleto(boolean value){
		this.isentoTaxaBoleto = value;
	}

	public boolean getIsentoTaxaBoleto(){
		return isentoTaxaBoleto;
	}

	public void setIsentoAdicional(boolean value){
		this.isentoAdicional = value;
	}

	public boolean getIsentoAdicional(){
		return isentoAdicional;
	}

	public void setAdicional(double value){
		this.adicional = value;
	}

	public double getAdicional(){
		return adicional;
	}

	public int getTabelaPadraoID(){
		return tabelaPadraoID;
	}

	public int getTabelaSecundariaID(){
		return tabelaSecundariaID;
	}

    public int getSubcanalID() {
        return subcanalID;
    }

    public void setSubcanalID(int subcanalID) {
        this.subcanalID = subcanalID;
    }

    public void registraTransmissaoPesquisaCerveja(){
		ClienteDAO clienteDAO;
		clienteDAO = new ClienteDAO(this.context);

		clienteDAO.registraTransmissaoPesquisaEquipamento(clienteID);
	}

    public void registraTransmissaoPesquisaEquipamento(){
        ClienteDAO clienteDAO;
        clienteDAO = new ClienteDAO(this.context);

        clienteDAO.registraTransmissaoPesquisaEquipamento(clienteID);
    }

    public void registraTransmissaoPesquisaSubcanal(){
        ClienteDAO clienteDAO;
        clienteDAO = new ClienteDAO(this.context);

        clienteDAO.registraTransmissaoPesquisaSubcanal(clienteID);
    }

    public void registraTransmissaoPesquisaTrembala(){
        ClienteDAO clienteDAO;
        clienteDAO = new ClienteDAO(this.context);

        clienteDAO.registraTransmissaoPesquisaTrembala(clienteID);
    }

	public PesquisaCerveja retornaPesquisaCerveja(){
		ClienteDAO clienteDAO;
		clienteDAO = new ClienteDAO(this.context);

		try {
			return clienteDAO.retornaPesquisaCerveja(clienteID);
		} catch (ParseException e) {
			return new PesquisaCerveja(context, clienteID);
		}
	}

	public List<Pedido> retornaListaPedidos() {
		ClienteDAO clienteDAO;
		clienteDAO = new ClienteDAO(this.context);

		return clienteDAO.retornaListaPedidos(clienteID);
	}

	public List<RegistroNaoVendaVO> retornaListaNaoVenda() {
		ClienteDAO clienteDAO;
		clienteDAO = new ClienteDAO(this.context);

		return clienteDAO.retornaListaNaoVenda(clienteID);
	}

	public List<Produto> retornaListaProdutos(int tipoPedidoID, int vendedorID){
		return daoObject.retornaListaProdutos(clienteID, subcanalID, tipoPedidoID, vendedorID);
	}

	public void load(){
		daoObject.load(this);
	}

	public boolean isNovaCampanhaColgate(){
		return novaCampanhaColgate;
	}

	public void setNovaCampanhaColgate(Boolean value){
		this.novaCampanhaColgate = value;
	}

    public List<Questao> getListaPerguntasPDVNG(){
        return daoObject.getListaPerguntasPDVNG(subcanalID);
    }
}