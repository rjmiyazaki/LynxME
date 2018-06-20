package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashCampanhaAdapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashCampanha;

/**
 * Created by Rogerio on 03/05/2016.
 */
public class FlashPrazoFemsaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<FlashCampanha> lista;
    private FlashCampanhaAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_campanha);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_campanha);
        toolbar.setTitle("Prazo Femsa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_campanha);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashPrazoFemsa();

        adapter = new FlashCampanhaAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
