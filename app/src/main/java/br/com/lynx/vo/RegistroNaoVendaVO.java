package br.com.lynx.vo;

import java.util.Date;

import android.content.Context;
import br.com.lynx.R;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.model.Configuracao;
import br.com.lynx.util.StringUtil;

public class RegistroNaoVendaVO {

	private int id;
	private int clienteID;
	private int motivoID;
	private Date dataTransmissao;
	private Date dataRegistro;
	private Context context;

	public RegistroNaoVendaVO(Context context, int id, int clienteID, int motivoID, Date dataRegistro, Date dataTransmissao){
		this.id = id;
		this.clienteID = clienteID;
		this.motivoID = motivoID;
		this.dataRegistro = dataRegistro;
		this.dataTransmissao = dataTransmissao;
		this.context = context;
	}
	
	public int getID(){
		return id;
	}
	
	public Date getDataRegistro(){
		return dataRegistro;
	}
	
	public boolean isEnviado(){
		if (dataTransmissao == null)
			return false;
		else
			return true;
	}
	
	public void registrarTransmissao(Context context){
		PedidoDAO daoObject = new PedidoDAO(context);
		daoObject.registroTransmissaoNaoVenda(this.id);
	}
	
	public String toString(){
		String linha;
		Configuracao configuracao;
		String version;
		
		configuracao = Configuracao.getInstance(this.context);
		version = this.context.getString(R.string.app_versao);
		
		linha = "<NVENDA.OUT>\r\n" +
				    StringUtil.padLeft(String.valueOf(motivoID), 2, "0") +
				    "               " +
				    StringUtil.padLeft(String.valueOf(clienteID), 6, "0") +
				    "\r\n" +
				    "<\\NVENDA.OUT>\r\n" +
				    "<Version>\r\n" +
				    version + "\r\n" +
				    "<\\Version>\r\n" +
				    "<VendedorID>\r\n" +
				    configuracao.getVendedorID() + "\r\n" +
				    "<\\VendedorID>\r\n";
		
		return linha;
	}
}
