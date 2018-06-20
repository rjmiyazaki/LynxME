package br.com.lynx.control.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import br.com.lynx.R;
import br.com.lynx.control.adapter.DivisaoPedidoAdapter;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.dao.SupervisorDAO;
import br.com.lynx.model.DivisaoPedidoInfo;

/**
 * Created by rogerio on 25/10/2016.
 */

public class AcompanhamentoPedidosVendedorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SupervisorDAO supervisorDAO;
    public Context context;
    public ArrayList<DivisaoPedidoInfo> divisoes;
    public DivisaoPedidoAdapter divisaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acompanhamento_pedidos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_acompanhamento_pedidos);
        toolbar.setTitle("Vendedor");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_divisao);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        supervisorDAO = new SupervisorDAO(this);

        Intent intent = getIntent();
        String setor = intent.getStringExtra("Setor");

        divisoes = supervisorDAO.pedidosVendedor(Integer.parseInt(setor));
        divisaoAdapter = new DivisaoPedidoAdapter(this, divisoes);
        recyclerView.setAdapter(divisaoAdapter);

        context = this;
    }
}
