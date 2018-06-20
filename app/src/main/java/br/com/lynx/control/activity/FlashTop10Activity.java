package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashTop10Adapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashTop10;

/**
 * Created by Rogerio on 22/04/2016.
 */
public class FlashTop10Activity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private List<FlashTop10> lista;
    private FlashTop10Adapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_top10);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_top10);
        toolbar.setTitle("Clientes TOP 10");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_top10);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashTop10();

        adapter = new FlashTop10Adapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
