package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.lynx.R;
import br.com.lynx.model.TBResposta;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;

public class PesquisaCheckoutMais5Activity extends AppCompatActivity {
	
	//private Button btnEnviar;
	//private Button btnCancelar;
	private int clienteID;
	private int checkouts;
	private int checkoutID;
	private Date data;
	private ArrayList<TBResposta> lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisa_5_mais_checkout);

		Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
		toolbar.setTitle("Trem Bala");
		setSupportActionBar(toolbar);
		
		clienteID = getIntent().getIntExtra("ClienteID", 0);
		checkouts = getIntent().getIntExtra("Checkouts", 0);
		checkoutID = getIntent().getIntExtra("CheckoutID", 0);
		data = new Date();
		
		lista = new ArrayList<TBResposta>();
	}
	
	public void Enviar(View v){
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta1_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 1, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 1, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta2_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 2, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 2, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta3_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 3, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 3, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta4_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 4, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 4, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta5_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 5, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 5, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta6_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 6, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 6, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta7_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 7, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 7, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta8_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 8, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 8, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta9_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 9, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 9, "Não"));

		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 10, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta10)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 11, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta11)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 12, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta12)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 13, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta13)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 14, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta14)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 15, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta15)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 16, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta16)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 17, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta17)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 18, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta18)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 19, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta19)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 20, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta20)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 21, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta21)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 22, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta22)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 23, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta23)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 24, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta24)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 25, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta25)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 26, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta26)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 27, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta27)).getText().toString()));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta28_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 28, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 28, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta29_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 29, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 29, "Não"));	
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta30_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 30, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 30, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta31_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 31, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 31, "Não"));	
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta32_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 32, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 32, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta33_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 33, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 33, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta34_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 34, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 34, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta35_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 35, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 35, "Não"));

		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 36, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta36)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 37, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta37)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 38, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta38)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 39, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta39)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 40, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta40)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 41, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta41)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 42, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta42)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 43, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta43)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 44, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta44)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 45, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta45)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 46, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta46)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 47, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta47)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 48, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta48)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 49, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta49)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 50, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta50)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 51, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta51)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 52, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta52)).getText().toString()));
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta53_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 53, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 53, "Não"));		
		
		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta54_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 54, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 54, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta55_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 55, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 55, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta56_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 56, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 56, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta57_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 57, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 57, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta58_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 58, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 58, "Não"));

		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 59, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta59)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 60, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta60)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 61, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta61)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 62, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta62)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 63, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta63)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 64, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta64)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 65, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta65)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 66, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta66)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 67, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta67)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 68, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta68)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 69, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta68)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 70, ((EditText)findViewById(R.id.idGondola_5_Chk_Resposta68)).getText().toString()));


		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta71_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 71, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 71, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta72_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 72, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 72, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_5_Chk_Resposta73_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 73, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 1, checkouts, checkoutID, 73, "Não"));

		for (TBResposta resposta : lista){
			resposta.save();
		}
		
		finish();
	}
	
	public void Cancelar(View v){
		finish();
	}	
}

