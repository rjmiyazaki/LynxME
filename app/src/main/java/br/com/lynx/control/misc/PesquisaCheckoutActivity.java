package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.model.TBResposta;

public class PesquisaCheckoutActivity extends Activity {
	private int clienteID;
	private int checkouts;
	private int checkoutID;
	private Date data;
	private ArrayList<TBResposta> lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisa_checkout);
		
		clienteID = getIntent().getIntExtra("ClienteID", 0);
		checkouts = getIntent().getIntExtra("Checkouts", 0);
		checkoutID = Integer.parseInt(getIntent().getStringExtra("CheckoutID"));
		data = new Date();
		
		TextView txtTitulo = (TextView)findViewById(R.id.idCheckout_PesquisaCheckout_Titulo);
		txtTitulo.setText("Checkout no. " + String.valueOf(checkoutID));
		
		lista = new ArrayList<TBResposta>();
	}
	
	public void Enviar(View v){
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta1_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 1, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 1, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta2_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 2, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 2, "Não"));		
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta3_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 3, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 3, "Não"));		

		if (((RadioButton)findViewById(R.id.idCheckout_Resposta4_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 4, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 4, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta5_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 5, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 5, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta6_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 6, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 6, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta7_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 7, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 7, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta8_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 8, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 8, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta9_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 9, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 9, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta10_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 10, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 10, "Não"));		
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta11_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 11, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 11, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta12_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 12, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 12, "Não"));
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta13_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 13, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 13, "Não"));		

		if (((RadioButton)findViewById(R.id.idCheckout_Resposta14_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 14, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 14, "Não"));			
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta15_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 15, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 15, "Não"));			
		
		if (((RadioButton)findViewById(R.id.idCheckout_Resposta16_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 16, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 16, "Não"));

		if (((RadioButton)findViewById(R.id.idCheckout_Resposta17_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 17, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 17, "Não"));

		if (((RadioButton)findViewById(R.id.idCheckout_Resposta18_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 18, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 18, "Não"));

		if (((RadioButton)findViewById(R.id.idCheckout_Resposta19_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 19, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 19, "Não"));

		if (((RadioButton)findViewById(R.id.idCheckout_Resposta20_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 20, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 20, "Não"));

		if (((RadioButton)findViewById(R.id.idCheckout_Resposta21_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 21, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 21, "Não"));

		if (((RadioButton)findViewById(R.id.idCheckout_Resposta22_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 22, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 3, checkouts, checkoutID, 22, "Não"));
		
		for (TBResposta resposta : lista){
			resposta.save();
		}
		
		finish();
			
	}
	
	public void Cancelar(View v){
		finish();
	}
}

