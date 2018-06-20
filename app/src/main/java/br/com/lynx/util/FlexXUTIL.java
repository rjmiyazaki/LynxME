package br.com.lynx.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import br.com.lynx.vo.FlexXVO;
import br.com.lynx.model.Configuracao;

public class FlexXUTIL {

	BufferedReader reader;
	FlexXVO flexx;
	Context context;

	public FlexXUTIL(Context context) {
		flexx = new FlexXVO(context);
		this.context = context;
	}

	public void setCliente(String clienteID) {

		flexx.setClienteID(clienteID);
	}

	public void setHoraAbertura(String horaAbertura) {

		flexx.setHoraAbertura(horaAbertura);
	}

	public void setPedidoID(String pedidoID) {

		flexx.setPedidoID(pedidoID);
	}

	public void setMotivoNaoVenda(String naoVenda) {

		flexx.setMotivoNaoVenda(naoVenda);
	}

	public void setFormaPagamento(String formaPagamento) {

		flexx.setFormaPagamento(formaPagamento);
	}

	public void setValorPedido(String valor) {

		flexx.setValorPedido(valor);
	}

	public void setQuantidadeItens(String quantidade) {

		flexx.setQuantidadeItens(quantidade);
	}

	public BufferedReader fileLoadPOSATUAL() {

		if (reader == null)
			try {
				reader = new BufferedReader(new FileReader("/sdcard/Gps/PosAtual.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		return reader;
	}

	public void setLatitudeLongitude() throws FileNotFoundException {

		String[] latitudeLongitude = null;
		try {
			latitudeLongitude = fileLoadPOSATUAL().readLine().split(";");
			flexx.setLatitude(latitudeLongitude[0].replace(",", "."));
			flexx.setLongitude(latitudeLongitude[1].replace(",", "."));
			reader.close();
		} catch (IOException e) {
			Log.i("LynxME", e.getMessage().toString());
			e.printStackTrace();
		}

	}

	public void salvarVendaFlexX(long pedidoID) {

		FileWriter arquivo;
		try {
			arquivo = new FileWriter("/sdcard/Gps/Pedidos/Venda"
			    + DateFormat.format("ddMMyykkmmss", new Date()) + ".txt");
			arquivo.write(conteudoVenda());
			arquivo.flush();
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String conteudoVenda() {

		String conteudo = "1607|";

		conteudo += flexx.getVendedorID() + "|";
		conteudo += flexx.getClienteID() + "|";
		conteudo += flexx.getLatitude() + "|";
		conteudo += flexx.getLongitude() + "|||";
		conteudo += flexx.getData() + "|";
		conteudo += flexx.getHoraAbertura() + "|";
		conteudo += flexx.getHoraFechamento() + "||";
		conteudo += flexx.getPedidoID() + "|";
		conteudo += flexx.getFormaPagamento() + "|";
		conteudo += flexx.getValorPedido() + "|";
		conteudo += flexx.getQuantidadeItens() + "[#]";

		return conteudo.replace("null", "");
	}

	public void salvarNaoVendaFlexX(long pedidoID) {

		try {
			FileWriter arquivo = new FileWriter("/sdcard/Gps/Pedidos/NaoVenda"
			    + DateFormat.format("ddMMyykkmmss", new Date()) + ".txt");
			arquivo.write(conteudoNaoVenda());
			arquivo.flush();
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String conteudoNaoVenda() {

		String conteudo = "1607|";

		conteudo += flexx.getVendedorID() + "|";
		conteudo += flexx.getClienteID() + "|";
		conteudo += flexx.getLatitude() + "|";
		conteudo += flexx.getLongitude() + "|||";
		conteudo += flexx.getData() + "|";
		conteudo += flexx.getHoraAbertura() + "|";
		conteudo += flexx.getHoraFechamento() + "|";
		conteudo += flexx.getMotivoNaoVenda() + "|";
		conteudo += flexx.getPedidoID() + "||";
		conteudo += "|0.00|0[#]";

		return conteudo.replace("null", "");
	}

	public String retornaImei() {
		TelephonyManager telephonyManager = (TelephonyManager) context
		    .getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	public static void flexxSendFiles(Context context) {

		String arquivoOrigem;
		TelephonyManager telephonyManager = (TelephonyManager) context
		    .getSystemService(Context.TELEPHONY_SERVICE);
		Configuracao configuracao = Configuracao.getInstance(context);

		// Faz o envio das posiÃ§Ãµes do GPS
		// FTP ftp = new FTP("189.114.224.122", "cotenge", "abc123_", 21, context);

		FTP ftp = new FTP();

		/* Compacta o arquivo de posiÃ§Ãµes */
		File sdCard = Environment.getExternalStorageDirectory();

		String filename = telephonyManager.getDeviceId() + "_" + String.valueOf(configuracao.getVendedorID()) + "_Posicoes_"
		    + DateFormat.format("ddMMyykkmmss", new Date()) + ".pos";

		/*
		 * File posFile = new File(sdCard.getPath() + "/Gps/",
		 * String.valueOf(configuracao.getVendedorID()) + "_Posicoes.txt");
		 */
		
				
		File posFile = new File(sdCard.getPath() + "/Gps/", "Posicoes.txt");
		File destFile = new File(sdCard.getPath() + "/Gps/", filename);
		
		if (posFile.exists())			
			posFile.renameTo(destFile);
			
		/*
		String sourceFile = sdCard.getPath() + "/Gps/" + filename;
				String zipFile = sdCard.getPath() + "/Gps/"
				    + telephonyManager.getDeviceId() + "_"
				    + configuracao.getVendedorID() + "_Posicoes_"
				    + DateFormat.format("ddMMyykkmmss", new Date()) + ".zip";

				if (zipfile(sourceFile, zipFile))
					destFile.delete();
			}
		}
		*/

		try {
			if (ftp.connect(configuracao.getftpHost(), configuracao.getftpUsuario(),
			    configuracao.getftpSenha(), 21)) {

				try {
					for (File file : new File(Environment.getExternalStorageDirectory()
					    .getPath() + "/Gps/").listFiles()) {
						if (file.getName().contains(".pos")) {
							String targetFileName = file.getName();
							arquivoOrigem = Environment.getExternalStorageDirectory()
							    .getPath() + "/Gps/" + file.getName();

							if (ftp.upload(arquivoOrigem, targetFileName, "FlexXGPS"))
								file.delete();
						}
					}

					for (File file : new File(Environment.getExternalStorageDirectory()
					    .getPath() + "/Gps/Pedidos/").listFiles()) {
						String targetFileName = telephonyManager.getDeviceId() + "_"  + file.getName();
						arquivoOrigem = Environment.getExternalStorageDirectory()
						    .getPath() + "/Gps/Pedidos/" + file.getName();
						
						if (ftp.upload(arquivoOrigem, targetFileName, "FlexXGPS"))
							file.delete();
					}
				} catch (Exception ex) {

				}

				ftp.disconnect();
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean zipfile(String filename, String zipFile) {
		try {
			final int BUFFER = 8192;

			BufferedInputStream origin = null;

			File outzip = new File(zipFile);
			if (outzip.exists())
				outzip.delete();

			FileOutputStream dest = new FileOutputStream(zipFile);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

			byte data[] = new byte[BUFFER];
			String nomeArquivo = filename.substring(filename.lastIndexOf("/") + 1);
			FileInputStream fi = new FileInputStream(filename);

			origin = new BufferedInputStream(fi, BUFFER);

			ZipEntry entry = new ZipEntry(nomeArquivo);
			out.putNextEntry(entry);

			int count;

			while ((count = origin.read(data, 0, BUFFER)) != -1)
				out.write(data, 0, count);

			origin.close();
			fi.close();
			out.flush();
			out.finish();
			out.close();
		} catch (Exception ex) {
			return false;
		}

		return true;
	}
}