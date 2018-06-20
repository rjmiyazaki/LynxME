package br.com.lynx.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import br.com.lynx.domain.Pedido;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.format.DateFormat;

public class FlexxGPS {

	public static String getLatitude() {
		String latitude;
		String[] campos;
		BufferedReader reader;

		String caminho = Environment.getExternalStorageDirectory().toString()
		    + "/FLAGPS_BD/";
		String nomeArquivo = "POSATUAL.TXT";
		latitude = "";
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(
			    caminho + nomeArquivo), "ISO-8859-1"));
			try {
				if (reader.ready()) {
					campos = reader.readLine().split(";");
					latitude = campos[2];
				}
			} finally {
				reader.close();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return latitude;
	}

	public static String getLongitude() throws IOException {
		String longitude;
		String[] campos;
		BufferedReader reader;

		String caminho = Environment.getExternalStorageDirectory().toString()
		    + "/FLAGPS_BD/";
		String nomeArquivo = "POSATUAL.TXT";
		longitude = "";
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(
			    caminho + nomeArquivo), "ISO-8859-1"));
			try {
				if (reader.ready()) {
					campos = reader.readLine().split(";");
					longitude = campos[3];
				}
			} finally {
				reader.close();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return longitude;
	}

	@SuppressLint("SdCardPath")
	public static void saveVenda(Pedido pedido)  {
		FileWriter arquivo;

		try {
	    arquivo = new FileWriter("/sdcard/FLAGPS_BD/ENVIAR/Venda"
	        + DateFormat.format("ddMMyykkmmss", new Date()) + ".txt");
	    
	    arquivo.write(pedido.getFlexxGPSInfo());
			arquivo.flush();
			arquivo.close();	    
    } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }		
	}
	
	@SuppressLint("SdCardPath")
  public static void saveNaoVenda(Pedido pedido, String motivo){
		FileWriter arquivo;

		try {
	    arquivo = new FileWriter("/sdcard/FLAGPS_BD/ENVIAR/Venda"
	        + DateFormat.format("ddMMyykkmmss", new Date()) + ".txt");
	    
	    arquivo.write(pedido.getFlexxGPSInfoNaoVenda(motivo));
			arquivo.flush();
			arquivo.close();	    
    } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	}

}
