package br.com.lynx.vo;

public class MotivoNaoVendaVO {

	private int id;
	private String descricao;
	private Boolean solicitaInfo;
	private String pergunta;

	public MotivoNaoVendaVO(int id, String descricao, Boolean solicitaInfo, String pergunta) {
		this.id = id;
		this.descricao = descricao;
		this.solicitaInfo = solicitaInfo;
		this.pergunta = pergunta;
	}

	public int getID() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean getSolicitaInfo(){ return solicitaInfo; }
}
