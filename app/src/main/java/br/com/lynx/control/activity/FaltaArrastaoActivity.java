package br.com.lynx.control.activity;

import br.com.lynx.R;
import br.com.lynx.domain.Cliente;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class FaltaArrastaoActivity extends ListActivity {

	private Cliente cliente;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_itens_falta_arrastao);
		
		Intent intent = getIntent();
		cliente = new Cliente(this);
		int clienteID = intent.getIntExtra("ClienteID", 0);
		cliente.load(clienteID);
		
		ListView lvwItens = getListView();

	    //setListAdapter(new LinhaAdapterCompatibility(this, cliente.getItensArrastao()));
		
		exibirInformacoes();
	}
	
	private void exibirInformacoes() {
		TextView txtIdentificacao = (TextView) findViewById(R.id.idFaltaArrastao_Cliente);
		TextView txtSubcanal = (TextView) findViewById(R.id.idFaltaArrastao_Subcanal);

		txtIdentificacao.setText(String.valueOf(cliente.getClienteID()) + " - "
		    + cliente.getRazaoSocial());
		txtSubcanal.setText(cliente.getSubcanal());
	}
}
