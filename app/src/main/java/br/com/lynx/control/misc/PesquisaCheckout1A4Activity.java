package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.Date;

import br.com.lynx.R;
import br.com.lynx.model.TBResposta;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.EditText;

public class PesquisaCheckout1A4Activity extends AppCompatActivity {
	
	private int clienteID;
	private int checkouts;
	private Date data;
	private ArrayList<TBResposta> lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisa_1_4_checkout);

		Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
		toolbar.setTitle("Trem Bala");
		setSupportActionBar(toolbar);

		clienteID = getIntent().getIntExtra("ClienteID", 0);
		checkouts = getIntent().getIntExtra("Checkouts", 0);
		data = new Date();
		
		lista = new ArrayList<TBResposta>();
	}
	
	public void Enviar(View v){
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta1_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 1, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 1, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta2_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 2, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 2, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta3_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 3, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 3, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta4_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 4, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 4, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta5_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 5, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 5, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta6_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 6, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 6, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta7_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 7, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 7, "Não"));

		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 8, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta8)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 9, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta9)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 10, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta10)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 11, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta11)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 12, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta12)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 13, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta13)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 14, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta14)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 15, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta15)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 16, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta16)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 17, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta17)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 18, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta18)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 19, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta19)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 20, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta20)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 21, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta21)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 22, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta22)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 23, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta23)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 24, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta24)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 25, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta25)).getText().toString()));

		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta26_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 26, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 26, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta27_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 27, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 27, "Não"));		
		
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta28_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 28, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 28, "Não"));
				
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta29_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 29, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 29, "Não"));
		
		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta30_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 30, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 30, "Não"));

        if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta31_Sim)).isChecked())
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 31, "Sim"));
        else
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 31, "Não"));

        if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta32_Sim)).isChecked())
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 32, "Sim"));
        else
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 32, "Não"));

        if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta33_Sim)).isChecked())
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 33, "Sim"));
        else
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 33, "Não"));

        lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 34, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta34)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 35, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta35)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 36, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta36)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 37, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta37)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 38, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta38)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 39, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta39)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 40, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta40)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 41, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta41)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 42, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta42)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 43, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta43)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 44, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta44)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 45, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta45)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 46, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta46)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 47, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta47)).getText().toString()));
        lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 48, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta48)).getText().toString()));
        lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 49, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta49)).getText().toString()));
        lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 50, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta50)).getText().toString()));


		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta51_Sim)).isChecked())
		  lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 51, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 51, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta52_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 52, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 52, "Não"));

        if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta53_Sim)).isChecked())
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 53, "Sim"));
        else
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 53, "Não"));

        if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta54_Sim)).isChecked())
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 54, "Sim"));
        else
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 54, "Não"));

		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 55, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta55)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 56, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta56)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 57, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta57)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 58, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta58)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 59, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta59)).getText().toString()));		
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 60, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta60)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 61, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta61)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 62, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta62)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 63, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta63)).getText().toString()));
		lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 64, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta64)).getText().toString()));
        lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 65, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta63)).getText().toString()));
        lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 66, ((EditText)findViewById(R.id.idGondola_1_4_Chk_Resposta64)).getText().toString()));

        if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta67_Sim)).isChecked())
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 67, "Sim"));
        else
            lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 67, "Não"));

		if (((RadioButton)findViewById(R.id.idGondola_1_4_Chk_Resposta68_Sim)).isChecked())
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 68, "Sim"));
		else
			lista.add(new TBResposta(this, clienteID, data, 0, checkouts, 0, 68, "Não"));

        for (TBResposta resposta : lista){
			resposta.save();
		}
		
		finish();
			
	}
	
	public void Cancelar(View v){
		finish();
	}	
}

