package br.com.lynx.control.misc;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import br.com.lynx.R;
import br.com.lynx.domain.Cliente;
import br.com.lynx.model.ItemHistoricoVenda;

public class HistoricoVenda extends ListActivity {

	private int clienteID;
	private Cliente cliente;
	private Spinner spnListaCabHistorico;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_historicopedido);

		Intent intent = getIntent();
		clienteID = intent.getIntExtra("ClienteID", 0);

		cliente = new Cliente(this);
		cliente.load(clienteID);
		cliente.loadHistoricoVenda();

		exibirInformacoes();

		spnListaCabHistorico = (Spinner) findViewById(R.id.idHistoricoPedido_ListaPedidos);

		spnListaCabHistorico
		    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			    public void onItemSelected(AdapterView<?> parent, View v, int posicao,
		          long id) {
				    exibeItemsPedido(posicao);
			    }

			    public void onNothingSelected(AdapterView<?> parent) {

			    }
		    });

		String[] datas = new String[cliente.getHistoricoVenda().size()];

		for (int i = 0; i < cliente.getHistoricoVenda().size(); i++)
			datas[i] = (String) DateFormat.format("dd/MM/yyyy",
			    cliente.getHistoricoVenda().get(i).getData()) + " - "
			    + cliente.getHistoricoVenda().get(i).getValorCurrency();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		    R.layout.custom_spinner_item, datas);
		adapter
		    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnListaCabHistorico.setAdapter(adapter);
	}

	private void exibirInformacoes() {
		TextView txtIdentificacao = (TextView) findViewById(
		    R.id.idHistorioPedido_IdentificacaoCliente);
		TextView txtSubcanal = (TextView) findViewById(
		    R.id.idHistoricoPedido_SubcanalCliente);

		txtIdentificacao.setText(String.valueOf(cliente.getClienteID()) + " - "
		    + cliente.getRazaoSocial());
		txtSubcanal.setText(cliente.getSubcanal());
	}

	private void exibeItemsPedido(int posicao) {

		List<ItemHistoricoVenda> itens = cliente.getHistoricoVenda().get(posicao)
		    .getItens();
		setListAdapter(new HistoricoVendaItemAdapter(this, itens));
	}
}