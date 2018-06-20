package br.com.lynx.vo;

import android.content.Context;
import br.com.lynx.dao.SubcanalDAO;

public class SubcanalVO {

	private int subcanalID;
	private String descricao;
	private SubcanalDAO daoObject;
	
	public SubcanalVO(Context context){
		daoObject = new SubcanalDAO(context);
	}

	public int getSubcanalID() {
		return subcanalID;
	}

	public void setSubcanalID(int subcanalID) {
		this.subcanalID = subcanalID;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void load(){
		daoObject.load(this);
	}
}
