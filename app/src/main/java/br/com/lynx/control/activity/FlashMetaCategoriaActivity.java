package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashMetaCategoriaAdapter;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashMeta;

/**
 * Created by Rogerio on 22/04/2016.
 */
public class FlashMetaCategoriaActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private List<FlashMeta> lista;
    private FlashMetaCategoriaAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_meta_por_categoria);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_meta_por_categoria);
        toolbar.setTitle("Metas por categoria");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_meta_por_categoria);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        lista = flashDAO.getFlashMetaCategoria();

        adapter = new FlashMetaCategoriaAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
