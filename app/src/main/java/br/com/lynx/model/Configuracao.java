package br.com.lynx.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences;

import br.com.lynx.dao.ConfiguracaoDAO;

public class Configuracao {

    private static final String PREFS_NAME = "Preferences";

    private String vendedorID;
    private String nomeVendedor;
    private int divisaoID;
    private String ftpHost;
    private String ftpUsuario;
    private String ftpSenha;
    private String startTime;
    private String endTime;
    private ConfiguracaoDAO daoObject;

    private static Configuracao instance;

    private Configuracao(Context context) {
        daoObject = new ConfiguracaoDAO(context);
        daoObject.load(this);

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        ftpHost = settings.getString("Host", "189.114.224.122");
        ftpUsuario = settings.getString("User", "cotenge");
        ftpSenha = settings.getString("Password", "abc123_");
    }

    public static Configuracao getInstance(Context context) {
        if (instance == null) {
            instance = new Configuracao(context);
        }

        return instance;
    }

    public void setVendedorID(String vendedorID) {
        this.vendedorID = vendedorID;
    }

    public String getVendedorID() {
        if (this.vendedorID != null)
            return this.vendedorID;
        else
            return "0000";
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    public String getNomeVendedor() {

        if (nomeVendedor != null)
            return this.nomeVendedor;
        else
            return "(Não definido)";
    }

    public String getftpHost() {
        return this.ftpHost;
    }

    public String getftpUsuario() {
        return this.ftpUsuario;
    }

    public String getftpSenha() {
        return this.ftpSenha;
    }

    public void load() {
        daoObject.load(this);
    }

    public void save() {
        daoObject.save(this);
    }

    public boolean isEmpty() {
        return vendedorID.equals("");
    }

    public String toString() {
        return this.vendedorID + " - " + getNomeVendedor();
    }

    public int getDivisaoID() {
        return divisaoID;
    }

    public void setDivisaoID(int divisaoID) {
        this.divisaoID = divisaoID;
    }

    public void setStartTime(String value) { this.startTime = value; }

    public void setEndTime(String value) { this.endTime = value; }

    public String getStartTime() { return startTime; }

    public String getEndTime() { return endTime; }
}
