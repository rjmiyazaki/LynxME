package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import br.com.lynx.R;
import br.com.lynx.model.TBResposta;

public class PesquisaPrecoActivity extends AppCompatActivity {
	
	private int clienteID;
	private int checkouts;
	private Date data;
	private ArrayList<TBResposta> lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisa_preco_checkout);

		Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
		toolbar.setTitle("Trem Bala");
		setSupportActionBar(toolbar);
		
		clienteID = getIntent().getIntExtra("ClienteID", 0);
		checkouts = getIntent().getIntExtra("Checkouts", 0);
		data = new Date();
		
		lista = new ArrayList<TBResposta>();
	}
	
	public void Enviar(View v){
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 1, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta1)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 2, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta2)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 3, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta3)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 4, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta4)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 5, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta5)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 6, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta6)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 7, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta7)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 8, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta8)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 9, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta9)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 10, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta10)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 11, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta11)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 12, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta12)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 2, checkouts, 0, 13, ((EditText)findViewById(R.id.idCheckout_Preco_Resposta13)).getText().toString()));

		for (TBResposta resposta : lista){
			resposta.save();
		}
		
		finish();
			
	}
	
	public void Cancelar(View v){
		finish();
	}
}
