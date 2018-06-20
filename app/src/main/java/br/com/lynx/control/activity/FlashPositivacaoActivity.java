package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashPositivacaoAdapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashPositivacao;

/**
 * Created by Rogerio on 05/05/2016.
 */
public class FlashPositivacaoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FlashPositivacao> lista;
    private FlashPositivacaoAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_positivacao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_positivacao);
        toolbar.setTitle("Positivação");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_positivacao);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashPositivacao();

        adapter = new FlashPositivacaoAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
