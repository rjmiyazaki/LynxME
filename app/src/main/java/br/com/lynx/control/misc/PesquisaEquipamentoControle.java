package br.com.lynx.control.misc;

public class PesquisaEquipamentoControle {

	private String geko;
	private int radSim;
	private int radNao;

	public PesquisaEquipamentoControle(String geko, int radSim, int radNao) {
		this.geko = geko;
		this.radSim = radSim;
		this.radNao = radNao;
	}

	public int getradSim() {
		return this.radSim;
	}

	public int getradNao() {
		return this.radNao;
	}
	
	public String getGeko(){
		return this.geko;
	}
}
