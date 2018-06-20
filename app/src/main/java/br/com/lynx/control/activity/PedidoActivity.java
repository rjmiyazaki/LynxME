package br.com.lynx.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.scalified.fab.ActionButton;

import br.com.lynx.R;
import br.com.lynx.model.Configuracao;

/**
 * Created by rogerio on 09/11/2016.
 */

public class PedidoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Configuracao configuracao;
    private int vendedorID;
    private int clienteID;
    private int tipoPedidoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        toolbar = (Toolbar) findViewById(R.id.toolbar_pedido);
        toolbar.setTitle("Pedido");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configuracao = Configuracao.getInstance(this);
        configuracao.load();

        vendedorID = Integer.parseInt(configuracao.getVendedorID());

        Intent intent = getIntent();
        clienteID = intent.getIntExtra("ClienteID", 0);
        tipoPedidoID = intent.getIntExtra("TipoPedidoID", 0);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_itens_pedido);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager llm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(llm);

        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.fab_material_blue_500));

        actionButton.setImageDrawable(getResources().getDrawable(R.drawable.fab_plus_icon));
        actionButton.setImageResource(R.drawable.fab_plus_icon);

        actionButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), ListaProdutoActivity.class);

                        String text = "";
                        intent.putExtra("value", text);

                        intent.putExtra("clienteID", clienteID);
                        startActivityForResult(intent, 0);
                    }
                }
        );
    }
}
