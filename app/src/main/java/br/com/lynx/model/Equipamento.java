package br.com.lynx.model;

public class Equipamento {
	
	private String geko;
	private String modelo;
	private String logomarca;
	private int voltagem;
	private float meta;
	private float realizado;
	private float percentual;
	private String analise;
	
  public Equipamento(String geko, String modelo, String logomarca, int voltagem, float meta, float realizado, float percentual, String analise){
  	this.geko = geko;
  	this.modelo = modelo;
  	this.logomarca = logomarca;
  	this.voltagem = voltagem;
  	this.meta = meta;
  	this.realizado = realizado;
  	this.percentual = percentual;
  	this.analise = analise;
  }
  
  public String getGeko(){
  	return geko;
  }
  
  public String getModelo(){
  	return modelo;
  }
  
  public String getLogomarca(){
  	return logomarca;
  }
  
  public int getVoltagem(){
  	return voltagem;
  }
  
  public float getMeta(){
  	return meta;
  }
  
  public float getRealizado(){
  	return realizado;
  }
  
  public float getPercentual(){
  	return percentual;
  }
  
  public String getAnalise(){
  	return analise;
  }
  
  public float getDiferenca(){
  	return meta - realizado;
  }

}
