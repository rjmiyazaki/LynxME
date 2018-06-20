package br.com.lynx.model;

import java.util.Date;

public class EquipamentoPesquisa {
	
	private String geko;
	private String logomarca;
	private Date data;
	private String presente;
	private Boolean enviado;
	
	public EquipamentoPesquisa(String geko, String logomarca, String presente){
		this.geko = geko;
		this.presente = presente;
		this.logomarca = logomarca;
	}
	
	public String getGeko(){
		return this.geko;
	}
	
	public void setGeko(String geko){
		this.geko = geko;
	}
	
	public Date getData(){
		return this.data;
	}
	
	public void setData(Date data){
		this.data = data;
	}
	
	public String getPresente(){
		return this.presente;
	}
	
	public void setPresente(String presente){
		this.presente = presente;
	}
	
	public String getLogomarca(){
		return this.logomarca;
	}
	
	public Boolean getEnviado(){
		return this.enviado;
	}
	
	public void setEnviado(Boolean enviado){
		this.enviado = enviado;
	}
}
