package br.com.lynx.control.misc;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.lynx.R;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.domain.Pedido;

public class ListaPedidos extends ListActivity {

	private List<Pedido> pedidos;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listapedidos);

		listaPedidos();

		ListView l = getListView();

		l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> l, View v, int position, long id) {

				onLongListItemClick(l, v, position, id);
				return false;
			}
		});
	}

	private void listaPedidos() {
		
		PedidoDAO pedidoDAO = new PedidoDAO(this);
		pedidos = pedidoDAO.listaPedidos();
		setListAdapter(new ListaPedidoAdapter(this, pedidos));
	}
	
	private void listaPedidosReload(){
		PedidoDAO pedidoDAO = new PedidoDAO(this);
		pedidos = pedidoDAO.listaPedidos();
		ListaPedidoAdapter adapter = new ListaPedidoAdapter(this, pedidos);
		
		adapter.notifyDataSetChanged();
		setListAdapter(adapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

		Pedido pedido = pedidos.get(position);

		Intent intent = new Intent(this, DetalhePedido.class);
		intent.putExtra("PedidoID", (int) pedido.getPedidoID());

		startActivity(intent);

	}

	protected void onLongListItemClick(AdapterView<?> l, View v, final int position, long id) {

		final Pedido pedido = pedidos.get(position);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setItems(new CharSequence[] { "Visualizar", "Remover" }, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				switch (item) {
				case 0:

					Intent intent = new Intent(ListaPedidos.this, DetalhePedido.class);
					intent.putExtra("PedidoID", (int) pedido.getPedidoID());
					startActivity(intent);

					break;

				case 1:
					if (pedido.getSituacao().equals("Transmitido"))
						MessageBox.show(ListaPedidos.this, "LynxME", "Não é possível remover pedidos finalizados.");
					else {
						removerPedido((int) pedido.getPedidoID());
					}

					break;

				default:
					onRestart();
				}
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void removerPedido(final int pedidoID) {

		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("LynxME");
		alertDialog.setMessage("Tem certeza que deseja excluir o pedido?");
		
		alertDialog.setButton("Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				PedidoDAO pedidoDAO = new PedidoDAO(ListaPedidos.this);
				pedidoDAO.deletePedido(pedidoID);
				listaPedidosReload();
			}
		});
		alertDialog.setButton2("Não", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				return;
			}
		});
		alertDialog.show();
	}
}