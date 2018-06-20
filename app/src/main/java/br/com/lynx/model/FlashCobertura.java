package br.com.lynx.model;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashCobertura {

    private String titulo;
    private String valor;

    public FlashCobertura(String titulo, String valor) {
        this.titulo = titulo;
        this.valor = valor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getValor() {
        return valor;
    }
}
