package br.com.lynx.util;

public class StringUtil {

	public static String padRight(String valor, int tamanho, String caracter){
		String retorno;
		
		if (valor == null)
			valor = "";
		
		retorno = valor.trim();
		
		for(int i = 0; i < tamanho - valor.trim().length(); i++)
			retorno += caracter;
		
		return retorno;
	}
	
	public static String padLeft(String valor, int quantidade, String caracter){
		String retorno = "";
		
		for(int i = 0; i < quantidade - valor.trim().length(); i++)
			retorno += caracter;
		
		retorno += valor.trim();
		
		return retorno;
	}
}
