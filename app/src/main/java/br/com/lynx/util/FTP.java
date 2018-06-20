package br.com.lynx.util;

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
	
	public FTP(){
		
	}
	
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
	public boolean connect(String host, String user, String password, int port) throws SocketException, IOException {
		boolean status = false;

		client = new FTPClient();

		// conectando no host
		client.connect(host, port);

		// verifica se a conexÃ£o está ok
		if (FTPReply.isPositiveCompletion(client.getReplyCode())) {

			// efetua login
			status = client.login(user, password);

			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			client.enterLocalPassiveMode();
		}

		return status;
	}
	
	public boolean connect() throws SocketException, IOException {
		boolean status = false;
		
		client = new FTPClient();
		
	// conectando no host
			client.connect(host, port);

			// verifica se a conexÃ£o está ok
			if (FTPReply.isPositiveCompletion(client.getReplyCode())) {

				// efetua login
				status = client.login(user, password);

				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				client.enterLocalPassiveMode();
			}

			return status;
	}
}

/*
 * public FTP(String hostname, String username, String password, int port,
 * Context context) {
 * 
 * this.hostname = hostname; this.username = username; this.password = password;
 * this.port = port; this.context = context;
 * 
 * this.client = new FTPClient(); }
 * 
 * private void open() throws SocketException, IOException {
 * this.client.connect(hostname, port); this.client.login(username, password);
 * this.client.enterLocalPassiveMode(); }
 * 
 * public void close() throws IOException { this.client.logout();
 * this.client.disconnect(); }
 * 
 * public boolean getFile(String ftpPath, String localPath, String filename)
 * throws IOException, SocketException { FileOutputStream stream; boolean result
 * = false; configuracao = Configuracao.getInstance(context);
 * 
 * File file = new File(localPath); if (!file.exists()) file.mkdir();
 * 
 * stream = new FileOutputStream(localPath + filename);
 * 
 * if (!this.client.isConnected()) open();
 * 
 * this.client.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
 * this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
 * this.client.changeWorkingDirectory(ftpPath);
 * 
 * if (!ftpPath.equals("Sistema")) if
 * (!this.client.printWorkingDirectory().equals( "/" +
 * configuracao.getVendedorID())) this.client.changeWorkingDirectory("/" +
 * configuracao.getVendedorID());
 * 
 * if (this.client.retrieveFile(filename, stream)) { stream.flush();
 * stream.close();
 * 
 * result = true; }
 * 
 * close();
 * 
 * return result; }
 * 
 * public boolean sendFile(String ftpPath, String localPath, String fileName)
 * throws IOException, SocketException { boolean result = false; configuracao =
 * Configuracao.getInstance(context);
 * 
 * if (!ftpPath.equals("")) { FileInputStream stream = new
 * FileInputStream(localPath + fileName);
 * 
 * if (!this.client.isConnected()) open();
 * 
 * this.client.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
 * this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
 * 
 * if (this.client.storeFile("/" + ftpPath + "/" + fileName, stream)) {
 * stream.close(); this.client.changeWorkingDirectory("/" + ftpPath); result =
 * validarArquivo(localPath, fileName); } }
 * 
 * return result; }
 * 
 * public boolean sendFile(String ftpPath, String localPath, String fileName,
 * String targetFileName) throws IOException, SocketException { boolean result =
 * false; configuracao = Configuracao.getInstance(context); String
 * tmpTargetFileName = targetFileName + "_";
 * 
 * if (!ftpPath.equals("")) { FileInputStream stream = new
 * FileInputStream(localPath + fileName);
 * 
 * if (!this.client.isConnected()) open();
 * 
 * this.client.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
 * this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
 * 
 * if (this.client .storeFile("/" + ftpPath + "/" + tmpTargetFileName, stream))
 * { stream.close(); this.client.changeWorkingDirectory("/" + ftpPath); result =
 * validarArquivo(localPath, fileName, tmpTargetFileName);
 * 
 * if (result) this.client.rename("/" + ftpPath + "/" + tmpTargetFileName, "/" +
 * ftpPath + "/" + targetFileName); } }
 * 
 * return result; }
 * 
 * public boolean validarArquivo(String localPath, String fileName) { boolean
 * result = false; try { File file = new File(localPath + fileName); FTPFile[]
 * arquivos = this.client.listFiles(); long tamanho = file.length();
 * 
 * for (int i = 0; i < arquivos.length; i++) if
 * (arquivos[i].getName().toString().equals(fileName)) if (arquivos[i].getSize()
 * == tamanho) { result = true; file.delete(); } } catch (IOException e) { }
 * return result; }
 * 
 * public boolean validarArquivo(String localPath, String fileName, String
 * targetFileName) { boolean result = false; try { File file = new
 * File(localPath + fileName); FTPFile[] arquivos = this.client.listFiles();
 * long tamanho = file.length();
 * 
 * for (int i = 0; i < arquivos.length; i++) if
 * (arquivos[i].getName().toString().equals(targetFileName)) if
 * (arquivos[i].getSize() == tamanho) { result = true; file.delete(); } } catch
 * (IOException e) { } return result; } }
 */
