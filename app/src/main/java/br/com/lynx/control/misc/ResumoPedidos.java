package br.com.lynx.control.misc;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import br.com.lynx.R;
import br.com.lynx.dao.PedidoDAO;

public class ResumoPedidos extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PedidoDAO pedido = new PedidoDAO(this);
		setListAdapter(new ResumoPedidoAdapter(this, pedido.resumoPedido()));
	}

	public void onRestart() {
		super.onRestart();

		PedidoDAO pedido = new PedidoDAO(this);
		setListAdapter(new ResumoPedidoAdapter(this, pedido.resumoPedido()));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		switch (position) {
		case 0:
			listaClientes();
			break;
		}
	}
	
	private void listaClientes(){
		//startActivity(new Intent(this, ListaClientes.class));
		finish();
	}
}