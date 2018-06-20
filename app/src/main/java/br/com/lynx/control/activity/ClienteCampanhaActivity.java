package br.com.lynx.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.ItemCampanhaAdapter;
import br.com.lynx.dao.ClienteDAO;
import br.com.lynx.model.ItemCampanha;

/**
 * Created by rogerio on 18/07/2016.
 */
public class ClienteCampanhaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ItemCampanha> lista;
    private ItemCampanhaAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_campanha);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cliente_campanha_categoria);
        toolbar.setTitle("Campanhas não positivadas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_cliente_campanha_categoria);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        Intent intent = getIntent();
        int clienteID = intent.getIntExtra("ClienteID", 0);

        ClienteDAO clienteDAO = new ClienteDAO(this);
        lista = clienteDAO.carregaListaArrastao(clienteID);

        adapter = new ItemCampanhaAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
