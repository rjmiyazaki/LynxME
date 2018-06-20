package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashQuedasAdapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashQueda;

/**
 * Created by Rogerio on 22/04/2016.
 */
public class FlashQuedasActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private List<FlashQueda> lista;
    private FlashQuedasAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_quedas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_quedas);
        toolbar.setTitle("Maiores quedas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_queda);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashQuedas();

        adapter = new FlashQuedasAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }

}
