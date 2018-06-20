package br.com.lynx.control.misc;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import br.com.lynx.R;
import br.com.lynx.domain.Cliente;

public class ListaDevolucao extends ListActivity {

	private Cliente cliente;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lista_titulo);

		Intent intent = getIntent();
		cliente = new Cliente(this);
		int clienteID = intent.getIntExtra("ClienteID", 0);
		cliente.load(clienteID);

		setListAdapter(new DevolucaoAdapter(this, cliente.getDevolucoes()));
	}

}
