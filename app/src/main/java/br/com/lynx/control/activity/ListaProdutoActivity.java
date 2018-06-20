package br.com.lynx.control.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.ListaProdutoAdapter;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.domain.Produto;
import br.com.lynx.domain.Cliente;

/**
 * Created by rogerio on 26/10/2016.
 */

public class ListaProdutoActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Handler handler;
    private ListaProdutoAdapter adapter;
    private List<Produto> listaProdutos;
    private String texto;
    private int clienteID;
    private int tipoPedidoID;
    private int vendedorID;
    private Cliente cliente;
    private SearchManager searchManager;
    private SearchView searchView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);

        Intent intent = getIntent();
        texto = intent.getStringExtra("value");
        clienteID = intent.getIntExtra("clienteID", 0);
        vendedorID = intent.getIntExtra("vendedorID", 0);
        tipoPedidoID = intent.getIntExtra("tipoPedidoID", 0);

        cliente = new Cliente(this);
        cliente.setClienteID(clienteID);
        cliente.load();

        toolbar = (Toolbar) findViewById(R.id.toolbar_listaproduto);
        toolbar.setTitle("Lista de produtos 3");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_lista_produto);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        listaProdutos = cliente.retornaListaProdutos(tipoPedidoID, vendedorID);

        adapter = new ListaProdutoAdapter(this, listaProdutos, cliente);
        adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter);

        handleSearch(getIntent());

        if (texto != "") {
            localizar(texto);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearch(intent);
    }

    @Override
    public void onClickListener(View view, int position) {
        Produto produtoSelecionado = listaProdutos.get(position);

        Intent intent = new Intent();
        intent.putExtra("ProdutoID", produtoSelecionado.getProdutoID());
        setResult(1, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_listaclientes, menu);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem item = menu.findItem(R.id.action_searchable_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) item.getActionView();
        } else {
            searchView = (SearchView) MenuItemCompat.getActionView(item);
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Buscar");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                localizar(newText);
                return false;
            }
        });

        return true;
    }

    private void localizar(String texto) {
        List<Produto> produtosLocal = new ArrayList<Produto>();
        produtosLocal = cliente.retornaListaProdutos(tipoPedidoID, vendedorID);
        listaProdutos.clear();

        for (Produto produto : produtosLocal)
            if (produto.getProdutoID().contains(texto.toUpperCase()) || produto.getDescricao().contains(texto.toUpperCase()))
                listaProdutos.add(produto);

        adapter.notifyDataSetChanged();
    }

    private void handleSearch(Intent intent) {
        if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
            String texto = intent.getStringExtra(SearchManager.QUERY);

            toolbar.setTitle(texto);
            localizar(texto);
        }
    }
}
