package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashCoberturaAdapter;
import br.com.lynx.control.adapter.FlashUltimasDevolucoesAdapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashCobertura;
import br.com.lynx.model.FlashDevolucaoCliente;

/**
 * Created by Rogerio on 05/05/2016.
 */
public class FlashUltimasDevolucoesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FlashDevolucaoCliente> lista;
    private FlashUltimasDevolucoesAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_ultimas_devolucoes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_ultimasDevolucoes);
        toolbar.setTitle("Últimas devoluções");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_ultimasDevolucoes);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashUltimasDevolucoes();

        adapter = new FlashUltimasDevolucoesAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
