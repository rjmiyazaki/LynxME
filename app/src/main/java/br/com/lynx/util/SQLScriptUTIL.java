package br.com.lynx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.AssetManager;


public class SQLScriptUTIL {
	
	protected AssetManager assetManager;
	
	public SQLScriptUTIL(AssetManager assetManager){
		this.assetManager = assetManager;
	}
	
	public String[] lerScript(String fileName){
		InputStream input = null;
		StringBuilder builder = new StringBuilder();
		try {
			input = this.assetManager.open(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while(reader.ready()){
				builder.append(reader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString().split("\\|");
	}

}
