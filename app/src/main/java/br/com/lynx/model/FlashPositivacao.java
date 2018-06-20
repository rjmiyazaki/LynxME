package br.com.lynx.model;

/**
 * Created by Rogerio on 05/05/2016.
 */
public class FlashPositivacao {

    private int dia;
    private String diaSemana;
    private int clientes;
    private int positivados;
    private int vendaZero;
    private float percentual;

    public FlashPositivacao(int dia, String diaSemana, int clientes, int positivados, int vendaZero, float percentual){
        this.dia = dia;
        this.diaSemana = diaSemana;
        this.clientes = clientes;
        this.positivados = positivados;
        this.vendaZero = vendaZero;
        this.percentual = percentual;
    }

    public int getDia(){
        return dia;
    }

    public int getClientes(){
        return clientes;
    }

    public int getPositivados(){
        return positivados;
    }

    public int getVendaZero(){
        return vendaZero;
    }

    public float getPercentual(){
        return percentual;
    }

    public String getDiaSemana(){
        return diaSemana.substring(0, 3).toUpperCase();
    }
}
