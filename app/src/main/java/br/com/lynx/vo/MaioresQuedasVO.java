package br.com.lynx.vo;

import br.com.lynx.domain.Produto;

public class MaioresQuedasVO {

	private Produto item;
	private int volumeAnterior;
	private int volumeAtual;

	public Produto getItem() {
		return item;
	}

	public void setItem(Produto item) {
		this.item = item;
	}

	public int getVolumeAnterior() {
		return volumeAnterior;
	}

	public void setVolumeAnterior(int volumeAnterior) {
		this.volumeAnterior = volumeAnterior;
	}

	public int getVolumeAtual() {
		return volumeAtual;
	}

	public void setVolumeAtual(int volumeAtual) {
		this.volumeAtual = volumeAtual;
	}
}