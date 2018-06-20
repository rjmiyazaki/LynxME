package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import br.com.lynx.R;

public class Relatorios extends ListActivity{
	
	private String[] opcoes = new String[] {"Meta"};
	private int[] imagens = new int[] { R.drawable.meta	};
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		List<OpcaoMenu> opcoesMenu = new ArrayList<OpcaoMenu>(); 
		
		for (int i = 0; i < opcoes.length; i++){
			//OpcaoMenu opcao = new OpcaoMenu(opcoes[i], imagens[i]);
			//opcoesMenu.add(opcao);			
		}
		
		//setListAdapter(new OpcaoMenuAdapter(this, opcoesMenu));
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		switch(position){
		case 0:
			startActivity(new Intent(this, Meta.class));
		}
	}

}
