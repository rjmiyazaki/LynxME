package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashDevolucaoAdapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashDevolucao;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashDevolucaoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FlashDevolucao> lista;
    private FlashDevolucaoAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_devolucao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_devolucao);
        toolbar.setTitle("Devoluções");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_devolucao);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashDevolucao();

        adapter = new FlashDevolucaoAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
