package br.com.lynx.control.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.com.lynx.R;
import br.com.lynx.control.adapter.ListaClientesAdapter;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.dao.ClienteDAO;
import br.com.lynx.domain.Cliente;
import br.com.lynx.control.adapter.TituloAdapter;
import br.com.lynx.model.Titulo;

public class TituloActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

	private Cliente cliente;
    private List<Titulo> titulos;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_titulo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.listaTitulos_Toolbar);
        toolbar.setTitle("Títulos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerview_titulos);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

		Intent intent = getIntent();
		cliente = new Cliente(this);
		int clienteID = intent.getIntExtra("ClienteID", 0);
		cliente.load(clienteID);

        try {
            titulos = cliente.getTitulos();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TituloAdapter adapter = new TituloAdapter(this, titulos);
        adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter);
	}

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }
}
