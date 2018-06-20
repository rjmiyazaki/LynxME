package br.com.lynx.control.misc;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import br.com.lynx.R;
import br.com.lynx.dao.ProdutoDAO;
import br.com.lynx.domain.Produto;

public class PesquisaProduto extends ListActivity {

	private List<Produto> produtos;
	Produto produtoSelecionado;
	private String texto;
	private int clienteID;
	EditText txtPesquisa;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listaproduto);
		
		Intent intent = getIntent();
		texto = intent.getStringExtra("value");
		clienteID = intent.getIntExtra("clienteID", 0);
		
		txtPesquisa = (EditText) findViewById(R.id.idListaProduto_edtPesquisa);
		txtPesquisa.setText(texto);
		
		if (texto != "")
			localizar();
		
		TextWatcher onSearchFieldTextChanged = new TextWatcher(){
			public void beforeTextChanged(CharSequence s, int start, int count, int after){
				//your business logic before text is changed
			}

			public void onTextChanged(CharSequence s, int start, int before, int count){
				//your business logic while text has changed
			}
			@Override
      public void afterTextChanged(Editable arg0) {
	      // TODO Auto-generated method stub
				localizar();
      }
		};
		
		txtPesquisa.addTextChangedListener(onSearchFieldTextChanged);
	}
	
	public void localizar(){		
		ProdutoDAO produtoDAO = new ProdutoDAO(this);

		try {
			Integer.parseInt(txtPesquisa.getText().toString());
			produtos = produtoDAO.pesquisaCodigo(txtPesquisa.getText().toString(), clienteID);
		} catch (NumberFormatException e) {
			produtos = produtoDAO.pesquisaNome(txtPesquisa.getText().toString(), clienteID);
		}

		setListAdapter(new PesquisaProdutoAdapter(this, produtos));
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		produtoSelecionado = produtos.get(position);
		
		Intent intent = new Intent();
		intent.putExtra("ProdutoID", produtoSelecionado.getProdutoID());
		setResult(1, intent);
		finish();				
	}
}
