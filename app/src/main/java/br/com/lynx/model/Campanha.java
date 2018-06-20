package br.com.lynx.model;

public class Campanha {
	private String linha;
	private int meta;
	private int realizado;
	
	public Campanha(String linha, int meta, int realizado){
		this.linha = linha;
		this.meta = meta;
		this.realizado = realizado;
	}
	
	public String getLinha(){
		return linha;
	}
	
	public int getMeta(){
		return meta;
	}
	
	public int getRealizado(){
		return realizado;
	}
	
	public int getFaltantes(){
		return meta - realizado;
	}
}


