package br.com.lynx.control.activity;

/**
 * Created by rogerio on 24/10/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import br.com.lynx.R;
import br.com.lynx.control.adapter.DivisaoPedidoAdapter;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.dao.SupervisorDAO;
import br.com.lynx.misc.SupervisorIntegracao;
import br.com.lynx.model.DivisaoPedidoInfo;
import br.com.lynx.util.FTP;

public class AcompanhamentoPedidosActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private RecyclerView recyclerView;
    private SupervisorDAO supervisorDAO;
    public Context context;
    public ArrayList<DivisaoPedidoInfo> divisoes;
    public DivisaoPedidoAdapter divisaoAdapter;
    private ProgressDialog dialog;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acompanhamento_pedidos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_acompanhamento_pedidos);
        toolbar.setTitle("Acompanhamento de pedidos");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_divisao);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        supervisorDAO = new SupervisorDAO(this);
        divisoes = supervisorDAO.pedidosPorDivisao();

        divisaoAdapter = new DivisaoPedidoAdapter(this, divisoes);
        divisaoAdapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(divisaoAdapter);

        context = this;
        handler = new Handler();
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent;

        DivisaoPedidoInfo divisaoSelecionada = divisoes.get(position);

        intent = new Intent(this, AcompanhamentoPedidosSetorActivity.class);
        intent.putExtra("Divisao", divisaoSelecionada.getNome());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.acompanhamento_pedidos, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.main_menu_reload: {

                dialog = ProgressDialog.show(this, "Download",
                        "Aguardo, atualizando informações...", false,
                        true);
                executeDownload();
            }
        }

        refreshList();

        return true;
    }

    public void refreshList(){
        ArrayList<DivisaoPedidoInfo> lista;

        lista = supervisorDAO.pedidosPorDivisao();


        divisoes.clear();
        for (DivisaoPedidoInfo divisao : lista ){
            divisoes.add(divisao);
        }

        divisaoAdapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }

    private void executeDownload(){
        new Thread(){
            @Override
            public void run(){
                String arquivoDestino, diretorioOrigem, arquivoOrigem;

                FTP ftp = new FTP("189.114.224.122", "cotenge", "abc123_", 21);
                try {
                    arquivoDestino = "/lynxmespv.dat";
                    diretorioOrigem = "/Vendas";
                    arquivoOrigem = "Vendas.dbi";

                    File arquivo = new File(Environment.getExternalStorageDirectory(), arquivoDestino);
                    File arquivoIntegracao = new File(Environment.getExternalStorageDirectory(), "lynxmespv.dat");

                    if (arquivoIntegracao.exists())
                        arquivoIntegracao.delete();

                    if (!arquivoIntegracao.exists()) {
                        if (ftp.connect()) {
                            ftp.download(diretorioOrigem, arquivoOrigem, arquivo.toString());
                            ftp.disconnect();
                        }
                    }

                    SupervisorIntegracao integracao = new SupervisorIntegracao(context);
                    integracao.importaArquivo(Environment.getExternalStorageDirectory().toString(), "/lynxmespv.dat");

                    handler.post(new Runnable() {
                        public void run() {
                            refreshList();
                        }
                    });
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    dialog.dismiss();
                }
            }
        }.start();
    }
}

