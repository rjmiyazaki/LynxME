package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashProjetosAdapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashProjeto;

/**
 * Created by Rogerio on 29/04/2016.
 */
public class FlashProjetosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPontoEconomico;
    private RecyclerView recyclerViewZat;
    private RecyclerView recyclerViewGondola;

    private List<FlashProjeto> listaPontoEconomico;
    private List<FlashProjeto> listaZat;
    private List<FlashProjeto> listaGondola;

    private FlashProjetosAdapter adapterPontoEconomico;
    private FlashProjetosAdapter adapterZat;
    private FlashProjetosAdapter adapterGondola;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_projetos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_projeto);
        toolbar.setTitle("Projetos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewPontoEconomico = (RecyclerView) findViewById(R.id.recyler_view_flash_pontoeconomico);
        recyclerViewPontoEconomico.setHasFixedSize(true);

        recyclerViewZat = (RecyclerView) findViewById(R.id.recyler_view_flash_zat);
        recyclerViewZat.setHasFixedSize(true);

        recyclerViewGondola = (RecyclerView) findViewById(R.id.recyler_view_flash_gondola);
        recyclerViewGondola.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPontoEconomico.setLayoutManager(llm);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewZat.setLayoutManager(llm);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewGondola.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        listaPontoEconomico = flashDAO.getFlashPontoEconomico();
        listaZat = flashDAO.getFlashZat();
        listaGondola = flashDAO.getFlashGondola();

        adapterPontoEconomico = new FlashProjetosAdapter(this, listaPontoEconomico);
        recyclerViewPontoEconomico.setAdapter(adapterPontoEconomico);

        adapterZat = new FlashProjetosAdapter(this, listaZat);
        recyclerViewZat.setAdapter(adapterZat);

        adapterGondola = new FlashProjetosAdapter(this, listaGondola);
        recyclerViewGondola.setAdapter(adapterGondola);
    }
}
