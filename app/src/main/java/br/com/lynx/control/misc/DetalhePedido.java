package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.domain.PedidoItem;

public class DetalhePedido extends ListActivity {
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		List<PedidoItem> itens = new ArrayList<PedidoItem>();
		PedidoDAO pedidoDAO = new PedidoDAO(this);
		int pedidoID = intent.getIntExtra("PedidoID", 0);
		
		itens = pedidoDAO.retornaListaItens(pedidoID);
		
		setListAdapter(new DetalhePedidoAdapter(this, itens));
	}

}
