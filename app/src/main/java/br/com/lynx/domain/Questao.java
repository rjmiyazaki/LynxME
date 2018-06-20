package br.com.lynx.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rogerio on 28/07/2017.
 */

public class Questao implements Parcelable {

    private int id;
    private String pergunta;
    private double valor;
    private String resposta;
    private int questionarioID;
    private String tipoResposta;

    public Questao(int id, String pergunta, double valor, int questionarioID, String tipoResposta){
        this.id = id;
        this.pergunta = pergunta;
        this.valor = valor;
        this.questionarioID = questionarioID;
        this.tipoResposta = tipoResposta;
    }

    protected Questao(Parcel in) {
        id = in.readInt();
        pergunta = in.readString();
        valor = in.readDouble();
        resposta = in.readString();
        questionarioID = in.readInt();
        tipoResposta = in.readString();
    }

    public void setResposta(String resposta){ this.resposta = resposta; }

    public String getResposta() { return resposta; }

    public String getPergunta() { return pergunta; }

    public double getValor(){
        double retorno;

        retorno = 0;

        if (tipoResposta.equals("Sim/Não") && resposta.equals("Sim"))
            retorno = valor;
        else if (tipoResposta.equals("0 a 10"))
            retorno = valor * Integer.parseInt(resposta) * 10 / 100;
        else if (tipoResposta.equals("0/50/100"))
            retorno = valor * Integer.parseInt(resposta) / 100;
        else if (tipoResposta.equals("0/25/50/100"))
            retorno = valor * Integer.parseInt(resposta) / 100;
        else if (tipoResposta.equals("0/20/80/100"))
            retorno = valor * Integer.parseInt(resposta) / 100;

        return retorno;
    }

    public int getQuestionarioID() { return questionarioID; }

    public int getID() { return id; }

    public String getTipoResposta() { return tipoResposta; }

    public void inverteResposta(){
        if (resposta.equals("Sim"))
            resposta = "Não";
        else if (resposta.equals("Não"))
            resposta = "Sim";
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(pergunta);
        dest.writeDouble(valor);
        dest.writeString(resposta);
        dest.writeInt(questionarioID);
        dest.writeString(tipoResposta);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Questao> CREATOR = new Creator<Questao>() {
        @Override
        public Questao createFromParcel(Parcel in) {
            return new Questao(in);
        }

        @Override
        public Questao[] newArray(int size) {
            return new Questao[size];
        }
    };

}
