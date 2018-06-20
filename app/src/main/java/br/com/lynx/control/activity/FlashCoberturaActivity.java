package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashCoberturaAdapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashCobertura;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashCoberturaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FlashCobertura> lista;
    private FlashCoberturaAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_cobertura);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_cobertura);
        toolbar.setTitle("Cobertura");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_cobertura);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashMetaCobertura();

        adapter = new FlashCoberturaAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
