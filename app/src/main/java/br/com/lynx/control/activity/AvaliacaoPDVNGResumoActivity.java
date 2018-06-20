package br.com.lynx.control.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.lynx.R;
import br.com.lynx.control.adapter.PDVNGResumoAdapter;
import br.com.lynx.control.RecyclerViewOnLongClickListernerHack;
import br.com.lynx.dao.PDVNGDAO;
import br.com.lynx.domain.Integracao;
import br.com.lynx.domain.Questao;
import br.com.lynx.misc.Communication;


/**
 * Created by Rogerio on 31/07/2017.
 */

public class AvaliacaoPDVNGResumoActivity extends AppCompatActivity implements RecyclerViewOnLongClickListernerHack {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Questao> perguntas;
    private PDVNGResumoAdapter adapter;
    private int clienteID;
    private int tipo;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_pdvng_resumo);

        toolbar = (Toolbar) findViewById(R.id.resumoAvaliacaoPDVNG_Toolbar);
        toolbar.setTitle("PDVNG");
        toolbar.setSubtitle("Resumo");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.resumoAvaliacaoPDVNG_RecyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        perguntas = getIntent().getParcelableArrayListExtra("Respostas");
        clienteID = getIntent().getIntExtra("ClienteID", 0);
        tipo = getIntent().getIntExtra("TipoAvaliacaoID", 0);

        adapter = new PDVNGResumoAdapter(this, perguntas);
        adapter.setRecyclerViewOnLongClickListernerHack(this);

        recyclerView.setAdapter(adapter);
        exibeNota();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_avaliacao_pdvng_resumo, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuAvaliacaoPDVNGResumo_Enviar:
                salvar();
                finish();
                return (true);
            case R.id.MenuAvaliacaoPDVNGResumo_Cancelar:
                finish();
                return (true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLongClickListener(View view, int position) {
        Questao item = perguntas.get(position);
        item.inverteResposta();
        adapter.notifyDataSetChanged();
        exibeNota();
    }

    private void exibeNota(){
        double nota = 0;

        for (Questao item : perguntas){
            //if (item.getResposta().equals("Sim"))
              //  nota = nota + item.getValor();
            nota = nota + item.getValor();
        }

        TextView txtNota = (TextView) findViewById(R.id.resumoAvaliacaoPDVNG_Nota);
        txtNota.setText(String.valueOf(nota));
    }

    private void salvar(){
        PDVNGDAO daoObject = new PDVNGDAO(this);
        daoObject.savePDVNG(clienteID, tipo, perguntas);
        gerarArquivo();
    }

    private void gerarArquivo(){
        String path = "/lynxme/pdvng";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = dateFormat.format(new Date());
        String nomeArquivo = (String) DateFormat.format("ddMMyyyykkmms", new Date());
        nomeArquivo = "PDVNG-" + String.valueOf(clienteID) + nomeArquivo + ".out";

        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            file.mkdirs();
        }

        path = file.toString();

        try {
            FileWriter arquivo = new FileWriter(path + "/" + nomeArquivo);
            for (Questao questao : perguntas){
                String linha = String.valueOf(questao.getQuestionarioID()) + "|" +
                        String.valueOf(tipo) + "|" +
                        String.valueOf(clienteID) + "|" +
                        String.valueOf(questao.getID()) + "|" +
                        String.valueOf(questao.getResposta()) + "|" +
                        data;

                arquivo.append(linha);
                arquivo.append("\r\n");
            }

            arquivo.flush();
            arquivo.close();

            Communication.enviarArquivo(file.toString(), "Pdvng", nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
