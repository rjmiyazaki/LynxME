package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashBig4Adapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashBig4;

/**
 * Created by Rogerio on 06/05/2016.
 */
public class FlashBig4Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<FlashBig4> lista;
    private FlashBig4Adapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_big4);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_big4);
        toolbar.setTitle("Big 4");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_big4);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashBig4Item();

        adapter = new FlashBig4Adapter(this, lista);
        recyclerView.setAdapter(adapter);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_big4_total);
        recyclerView.setHasFixedSize(true);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        lista = flashDAO.getFlashBig4Total();

        adapter = new FlashBig4Adapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
