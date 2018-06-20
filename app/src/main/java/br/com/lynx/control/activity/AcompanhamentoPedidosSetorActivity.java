package br.com.lynx.control.activity;

/**
 * Created by rogerio on 25/10/2016.
 */

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

public class AcompanhamentoPedidosSetorActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

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
        toolbar.setTitle("Setores");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_divisao);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        supervisorDAO = new SupervisorDAO(this);

        Intent intent = getIntent();
        String divisao = intent.getStringExtra("Divisao");

        if (divisao.equals("Coca-Cola"))
          divisoes = supervisorDAO.pedidosSetorCocaCola();
        else if (divisao.equals("Mondelez"))
          divisoes = supervisorDAO.pedidosSetorMondelez();
        else if (divisao.equals("Alimentar"))
          divisoes = supervisorDAO.pedidosSetorAlimentar();
        else if (divisao.equals("RM Distribuição"))
          divisoes = supervisorDAO.pedidosSetorRM();

        divisaoAdapter = new DivisaoPedidoAdapter(this, divisoes);
        divisaoAdapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(divisaoAdapter);

        context = this;
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent;

        DivisaoPedidoInfo divisaoSelecionada = divisoes.get(position);
        intent = new Intent(this, AcompanhamentoPedidosVendedorActivity.class);
        intent.putExtra("Setor", divisaoSelecionada.getNome().substring(0, 2));
        startActivity(intent);
    }
}


