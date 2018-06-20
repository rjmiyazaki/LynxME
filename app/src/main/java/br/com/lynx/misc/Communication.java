package br.com.lynx.misc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.lynx.domain.Cliente;
import br.com.lynx.domain.Pedido;
import br.com.lynx.model.Configuracao;
import br.com.lynx.model.EquipamentoPesquisa;
import br.com.lynx.model.PesquisaCerveja;
import br.com.lynx.model.RespostaCerveja;
import br.com.lynx.vo.EquipamentoVO;
import br.com.lynx.vo.RegistroNaoVendaVO;

/**
 * Created by Rogerio on 18/05/2017.
 */

public class Communication {

    private static String nomePastaOrigem;
    private static String nomeArquivoTrabalho;
    private static String nomePastaDestino;
    private static final String path = Environment.getExternalStorageDirectory().toString() + "/lynxme/orders/";

    public static void executeDownload(String sourcePath, String sourceFile, String host, String user, String password, int port) throws Exception {
        FTP ftp = new FTP();

        File arquivoDados = new File(Environment.getExternalStorageDirectory(), sourceFile);
        String caminho = Environment.getExternalStorageDirectory().toString();

        if (connect(ftp)){
            ftp.download(sourcePath, sourceFile, arquivoDados.toString());
            ftp.disconnect();
        } else {
            throw new Exception("Não foi possivel se conectar ao servidor de FTP.");
        }
    }

    public static void executeDownload(String sourcePath, String sourceFile, String targetFile, String host, String user, String password, int port) throws Exception {
        FTP ftp = new FTP();

        targetFile = "lynxme.dat";
        File arquivoDados = new File(Environment.getExternalStorageDirectory(), targetFile);
        String caminho = Environment.getExternalStorageDirectory().toString();

        if (connect(ftp)){
            ftp.download(sourcePath, sourceFile, arquivoDados.toString());
            ftp.disconnect();
        } else {
            throw new Exception("Não foi possivel se conectar ao servidor de FTP.");
        }
    }

    public static void executeUpload(String sourceFolder, String sourceFile, String folder, String host, String user, String password, int port) throws IOException {
        FTP ftp = new FTP();
        File arquivo = new File(sourceFolder, sourceFile);

        if (ftp.connect(host, user, password, port)) {
            ftp.upload(arquivo.toString(), sourceFile, folder);
        }
    }

    public static void enviarArquivo(String pastaOrigem, String pastaDestino, String nomeArquivo) {
        nomePastaOrigem = pastaOrigem;
        nomePastaDestino = pastaDestino;
        nomeArquivoTrabalho = nomeArquivo;

        new Thread() {
            @Override
            public void run() {
                try {
                    Communication.executeUpload(nomePastaOrigem, nomeArquivoTrabalho, nomePastaDestino, "189.114.224.122", "cotenge", "abc123_", 21);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void enviarPedido(Context context, Pedido pedido) throws IOException {
        String nomeArquivo;
        FTP ftp  = new FTP();
        String arquivoOrigem, arquivoDestino;
        Configuracao configuracao = Configuracao.getInstance(context);

        nomeArquivo = geraArquivo(context, pedido);
        arquivoOrigem = path + nomeArquivo + ".out";
        arquivoDestino = nomeArquivo + ".out";

        if(connect(ftp)){
            ftp.upload(arquivoOrigem, arquivoDestino, configuracao.getVendedorID());
            ftp.disconnect();

            pedido.registrarTransmissao();
        }
    }

    public static void enviarNaoVenda(Context context, RegistroNaoVendaVO registro) throws Exception {
        String nomeArquivo;
        FTP ftp;
        Configuracao configuracao;
        String arquivoOrigem, arquivoDestino;

        configuracao = Configuracao.getInstance(context);

        nomeArquivo = geraArquivo(context, registro);
        arquivoOrigem = path + nomeArquivo + ".out";
        arquivoDestino = nomeArquivo + ".out";

        ftp = new FTP();

        if (connect(ftp)) {
            ftp.upload(arquivoOrigem, arquivoDestino, configuracao.getVendedorID());
            ftp.disconnect();

            registro.registrarTransmissao(context);
        }
    }

    public static void enviarTBPesquisa(Context context, List<String> respostas) throws IOException {
        String nomeArquivo = "TBRespostas";
        Configuracao configuracao;
        String arquivoOrigem, arquivoDestino;
        FTP ftp  = new FTP();

        nomeArquivo = nomeArquivo + (String) DateFormat.format("ddMMyyyykkmms", new Date());

        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out", false);

        for (String linha : respostas) {
            arquivo.write(linha);
        }

        arquivo.flush();
        arquivo.close();

        configuracao = Configuracao.getInstance(context);

        arquivoOrigem = path + nomeArquivo + ".out";
        arquivoDestino = nomeArquivo + ".out";

        if (connect(ftp)) {
            ftp.upload(arquivoOrigem, arquivoDestino, configuracao.getVendedorID());
            ftp.disconnect();
        }
    }

    public static void enviarPesquisaEquipamento(Context context, Cliente cliente) throws IOException {
        String nomeArquivo;
        String arquivoOrigem, arquivoDestino;
        FTP ftp;
        Configuracao configuracao = Configuracao.getInstance(context);

        nomeArquivo = geraArquivoPesquisaEquipamento(context, cliente);

        if (!nomeArquivo.isEmpty()) {
            arquivoOrigem = path + nomeArquivo + ".out";
            arquivoDestino = nomeArquivo + ".out";

            ftp = new FTP();

            if (connect(ftp)) {
                ftp.upload(arquivoOrigem, arquivoDestino, configuracao.getVendedorID());
                ftp.disconnect();

                cliente.registrarEnvioPesquisa();
                cliente.registraTransmissaoPesquisaEquipamento();
            }
        }
    }

    public static void enviarPesquisaSubcanal(Context context, Cliente cliente, String subcanal) throws IOException {
        String nomeArquivo;
        String arquivoOrigem, arquivoDestino;
        FTP ftp;
        Configuracao configuracao = Configuracao.getInstance(context);

        nomeArquivo = geraArquivoPesquisaSubcanal(context, cliente, subcanal);

        if (!nomeArquivo.isEmpty()) {
            arquivoOrigem = path + nomeArquivo + ".out";
            arquivoDestino = nomeArquivo + ".out";

            ftp = new FTP();

            if (connect(ftp)) {
                ftp.upload(arquivoOrigem, arquivoDestino, configuracao.getVendedorID());
                ftp.disconnect();

                cliente.registraTransmissaoPesquisaSubcanal();
            }
        }
    }

    public static void enviarPesquisaCerveja(Context context, PesquisaCerveja pesquisaCerveja) throws IOException {
        String nomeArquivo;
        String arquivoOrigem, arquivoDestino;
        FTP ftp;
        Configuracao configuracao = Configuracao.getInstance(context);

        nomeArquivo = geraArquivoPesquisaCerveja(context, pesquisaCerveja);

        if (!nomeArquivo.isEmpty()) {
            arquivoOrigem = path + nomeArquivo + ".out";
            arquivoDestino = nomeArquivo + ".out";

            ftp = new FTP();

            if (ftp.connect()) {
                ftp.upload(arquivoOrigem, arquivoDestino, configuracao.getVendedorID());
                ftp.disconnect();

                pesquisaCerveja.registraTransmissao();
            }
        }
    }

    public static void enviarNovoTelefone(Context context, int clienteID, String foneAntigo, String foneNovo)
            throws IOException {

        String nomeArquivo;
        String arquivoOrigem, arquivoDestino;
        FTP ftp;
        Configuracao configuracao = Configuracao.getInstance(context);

        nomeArquivo = geraArquivo(context, clienteID, foneAntigo, foneNovo);
        arquivoOrigem = path + nomeArquivo + ".out";
        arquivoDestino = nomeArquivo + ".out";

        ftp = new FTP();

        if (connect(ftp)) {
            ftp.upload(arquivoOrigem, arquivoDestino, configuracao.getVendedorID());
            ftp.disconnect();

        }
    }

    private static boolean connect(FTP ftp){
        Boolean status = false;
        String host = "10.0.0.5";
        String user = "cotenge";
        String password = "abc123_";
        int port = 21;

        status = ftp.connect(host, user, password, port);

        if (!status) {
            host = "189.114.224.122";
            status = ftp.connect(host, user, password, port);

            if (!status){
                host = "200.195.141.170";
                status = ftp.connect(host, user, password, port);

                if (!status){
                    host = "ftp.distribuidorang.com.br";
                    user = "distribuidorang";
                    password = "dng2k15";

                    status = ftp.connect(host, user, password, port);
                }
            }
        }

        return status;
    }

    private static String geraArquivoPesquisaEquipamento(Context context, Cliente cliente) throws IOException {
        String nomeArquivo = "PesqEquip_" + (String) DateFormat.format("ddMMyyyykkmms", new Date());

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");
        if (!file.exists()) {
            file.mkdirs();
        }


        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");

        Boolean temRegistro = false;

        for (EquipamentoPesquisa resposta : cliente.getEquipamentosPesquisa()) {
            if (resposta.getPresente().equalsIgnoreCase("Sim") || resposta.getPresente().equalsIgnoreCase("Não")) {
                arquivo.append(cliente.getClienteID() + ";" + resposta.getGeko() + ";"
                        + (String) DateFormat.format("yyyyMMddkkmm", resposta.getData()) + ";"
                        + resposta.getPresente());
                arquivo.append("\r\n");

                temRegistro = true;
            }
        }

        arquivo.flush();
        arquivo.close();

        if (!temRegistro)
            nomeArquivo = "";

        return nomeArquivo;
    }

    private static String geraArquivoPesquisaSubcanal(Context context, Cliente cliente, String subcanal)
            throws IOException {
        String nomeArquivo = "PesqSubcanal_" + (String) DateFormat.format("ddMMyyyykkmms", new Date());

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");
        if (!file.exists()) {
            file.mkdirs();
        }


        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");

        arquivo.append(cliente.getClienteID() + ";" + subcanal);
        arquivo.flush();
        arquivo.close();

        return nomeArquivo;
    }

    private static String geraArquivoPesquisaCerveja(Context context, PesquisaCerveja pesquisaCerveja) throws IOException {
        String linha;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String nomeArquivo = "PesquisaCerveja_" + (String) DateFormat.format("ddMMyyyykkmms", new Date());

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");
        if (!file.exists()) {
            file.mkdirs();
        }


        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");

        for (RespostaCerveja resposta : pesquisaCerveja.getRespostas()) {
            linha = String.valueOf(pesquisaCerveja.getClienteID()) + ";" +
                    resposta.getMarca() + ";" +
                    resposta.getCaixas() + ";" +
                    resposta.getPreco() + ";" +
                    (String) dateFormat.format(pesquisaCerveja.getData());

            arquivo.append(linha);
            arquivo.append("\r\n");
        }

        arquivo.flush();
        arquivo.close();

        return nomeArquivo;
    }

    public static String geraArquivo(Context context, Pedido pedido) throws IOException {
        String nomeArquivo;

        nomeArquivo = (String) DateFormat.format("ddMMyyyykkmms", pedido.getDataEncerramento());

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");
        if (!file.exists()) {
            file.mkdirs();
        }

        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");

        for (String linha : pedido.getConteudoArquivo(context))
            arquivo.append(linha);

        // Informa o vendedor que esta operando o sistema
        arquivo.append("<IMEI>\r\n");
        arquivo.append(retornaIMEI(context) + "\r\n");
        arquivo.append("<\\IMEI>\r\n");

        arquivo.flush();
        arquivo.close();

        return nomeArquivo;
    }

    public static String geraArquivo(Context context, RegistroNaoVendaVO registro) throws IOException {
        String nomeArquivo;

        nomeArquivo = (String) DateFormat.format("ddMMyyyykkmm", registro.getDataRegistro());

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");

        if (!file.exists()) {
            file.mkdirs();
        }

        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");
        arquivo.append(registro.toString());

        arquivo.flush();
        arquivo.close();

        return nomeArquivo;
    }

    public static String geraArquivo(Context context, Pedido pedido, String path) throws IOException {

        String nomeArquivo;

        nomeArquivo = (String) DateFormat.format("ddMMyyyykkmms", pedido.getDataEncerramento());

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");
        if (!file.exists()) {
            file.mkdirs();
        }


        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");

        for (String linha : pedido.getConteudoArquivo(context))
            arquivo.append(linha);

        arquivo.flush();
        arquivo.close();

        return nomeArquivo;
    }

    public static String geraArquivo(Context context, RegistroNaoVendaVO registro, String path) throws IOException {

        String nomeArquivo;

        nomeArquivo = (String) DateFormat.format("ddMMyyyykkmms", registro.getDataRegistro());

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");
        if (!file.exists()) {
            file.mkdirs();
        }


        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");
        arquivo.append(registro.toString());

        arquivo.flush();
        arquivo.close();

        return nomeArquivo;
    }

    public static String geraArquivo(Context context, List<EquipamentoVO> equipamento) throws IOException {

        String nomeArquivo = (String) DateFormat.format("ddMMyyyykkmms", new Date());
        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");
        String conteudo = "<equipamento.out>\r\n";

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");
        if (!file.exists()) {
            file.mkdirs();
        }


        for (int i = 0; i < equipamento.size(); i++)
            conteudo += equipamento.get(i).getCliente().getClienteID() + "|" + equipamento.get(i).getGeko() + "\r\n";

        conteudo += "<\\equipamento.out>\r\n";

        arquivo.write(conteudo);

        arquivo.flush();
        arquivo.close();

        return nomeArquivo;
    }

    public static String geraArquivo(Context context, int clienteID, String foneAntigo, String foneNovo)
            throws IOException {

        File file = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");
        if (!file.exists()) {
            file.mkdirs();
        }


        String nomeArquivo = (String) DateFormat.format("ddMMyyyykkmms", new Date());
        FileWriter arquivo = new FileWriter(path + nomeArquivo + ".out");
        arquivo.write(clienteID + ";" + foneAntigo + ";" + foneNovo);
        arquivo.flush();
        arquivo.close();
        return nomeArquivo;
    }

    @SuppressLint("MissingPermission")
    private static String retornaIMEI(Context context) {
        String IMEI = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();

        return IMEI;
    }

    public static void limpaPasta(String path) {

        File file = new File(path);
        if (!file.exists())
            file.mkdir();

        File files[] = file.listFiles();

        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }
}
