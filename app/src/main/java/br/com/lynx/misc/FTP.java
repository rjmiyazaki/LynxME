package br.com.lynx.misc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public final class FTP {

    private FTPClient client;
    private String host;
    private String user;
    private String password;
    private int port;

    public FTP(){}

    public FTP(String host, String user, String password, int port){
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public FTPFile[] dir(String diretorio) throws IOException {
        FTPFile[] ftpFiles = client.listFiles(diretorio);

        return ftpFiles;
    }

    public String workingDir() throws IOException {
        String workingDir = client.printWorkingDirectory();

        return workingDir;
    }

    public boolean changeWorkingDirectory(String diretorio) throws IOException {
        return client.changeWorkingDirectory(diretorio);
    }

    public boolean makeDirectory(String diretorio) throws IOException {
        return client.makeDirectory(diretorio);
    }

    public boolean removeDirectory(String diretorio) throws IOException {
        return client.removeDirectory(diretorio);
    }

    public boolean deleteFile(String arquivo) throws IOException {
        return client.deleteFile(arquivo);
    }

    public boolean renameFile(String from, String to) throws IOException {
        return client.rename(from, to);
    }

    public boolean download(String diretorioOrigem, String arquivoOrigem,
                            String arquivoDestino) throws FileNotFoundException, IOException {
        boolean status = false;

        if (changeWorkingDirectory(diretorioOrigem)) {
            // Cria o outputStream para ser passado como parametro
            FileOutputStream desFileStream = new FileOutputStream(arquivoDestino);

            // Tipo de arquivo
            client.setFileType(FTPClient.BINARY_FILE_TYPE);

            // http://commons.apache.org/net/apidocs/org/apache/commons/net/ftp/FTPClient.html#enterLocalActiveMode()
            client.enterLocalPassiveMode();

            // Faz o download do arquivo
            status = client.retrieveFile(arquivoOrigem, desFileStream);

            // Fecho o output
            desFileStream.close();
        }

        return status;
    }

    // Enviar arquivo para o servidor FTP
    public boolean upload(String arquivoOrigem, String arquivoDestino,
                          String diretorioDestino) throws FileNotFoundException, IOException {
        boolean status = false;

        FileInputStream srcFileStream = new FileInputStream(arquivoOrigem);

        // muda o diretório para o destino especifico
        if (changeWorkingDirectory(diretorioDestino))
            status = client.storeFile(arquivoDestino, srcFileStream);

        if (!status)
            client.deleteFile(arquivoDestino);

        srcFileStream.close();

        return status;
    }

    // Encerrar a conexão com o servidor FTP
    public void disconnect() throws IOException {

        client.disconnect();
        client = null;
    }

    // Efetuar conexão com o servidor FTP
    public boolean connect(String host, String user, String password, int port) {
        boolean status = false;

        client = new FTPClient();
        // conectando no host
        try {
            client.setConnectTimeout(5000);
            client.connect(host, port);
            client.setConnectTimeout(30000);
            client.setDataTimeout(30000);
        } catch (SocketException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }

        // verifica se a conexãoo está ok
        try {
            if (FTPReply.isPositiveCompletion(client.getReplyCode())) {

                // efetua login
                status = client.login(user, password);

                client.setFileType(FTPClient.BINARY_FILE_TYPE);
                client.enterLocalPassiveMode();
            }
        } catch (Exception ex) {
            return false;
        }

        return status;
    }

    public boolean connect() throws SocketException, IOException {
        boolean status = false;

        client = new FTPClient();

        client.setConnectTimeout(5000);

        // conectando no host
        client.connect(host, port);

        // verifica se a conexãoo está ok
        if (FTPReply.isPositiveCompletion(client.getReplyCode())) {

            // efetua login
            status = client.login(user, password);

            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
        }

        client.setConnectTimeout(0);

        return status;
    }
}
