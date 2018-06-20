package br.com.lynx.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.adapter.FlashEquipamentoAdatper;
import br.com.lynx.dao.FlashDAO;
import br.com.lynx.model.FlashEquipamento;


/**
 * Created by Rogerio on 04/05/2016.
 */
public class FlashEquipamentoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSegunda;
    private RecyclerView recyclerViewTerca;
    private RecyclerView recyclerViewQuarta;
    private RecyclerView recyclerViewQuinta;
    private RecyclerView recyclerViewSexta;
    private RecyclerView recyclerViewSabado;
    private RecyclerView recyclerViewTotal;

    private List<FlashEquipamento> listaSegunda;
    private List<FlashEquipamento> listaTerca;
    private List<FlashEquipamento> listaQuarta;
    private List<FlashEquipamento> listaQuinta;
    private List<FlashEquipamento> listaSexta;
    private List<FlashEquipamento> listaSabado;
    private List<FlashEquipamento> listaTotal;

    private FlashEquipamentoAdatper adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_equipamento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_equipamento);
        toolbar.setTitle("Equipamentos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewSegunda = (RecyclerView) findViewById(R.id.recyler_view_flash_equipamento_segunda);
        recyclerViewSegunda.setHasFixedSize(true);

        recyclerViewTerca = (RecyclerView) findViewById(R.id.recyler_view_flash_equipamento_terca);
        recyclerViewTerca.setHasFixedSize(true);

        recyclerViewQuarta = (RecyclerView) findViewById(R.id.recyler_view_flash_equipamento_quarta);
        recyclerViewQuarta.setHasFixedSize(true);

        recyclerViewQuinta = (RecyclerView) findViewById(R.id.recyler_view_flash_equipamento_quinta);
        recyclerViewQuinta.setHasFixedSize(true);

        recyclerViewSexta = (RecyclerView) findViewById(R.id.recyler_view_flash_equipamento_sexta);
        recyclerViewSexta.setHasFixedSize(true);

        recyclerViewSabado = (RecyclerView) findViewById(R.id.recyler_view_flash_equipamento_sabado);
        recyclerViewSabado.setHasFixedSize(true);

        recyclerViewTotal = (RecyclerView) findViewById(R.id.recyler_view_flash_equipamento_total);
        recyclerViewTotal.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSegunda.setLayoutManager(llm);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTerca.setLayoutManager(llm);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewQuarta.setLayoutManager(llm);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewQuinta.setLayoutManager(llm);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSexta.setLayoutManager(llm);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSabado.setLayoutManager(llm);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTotal.setLayoutManager(llm);

        FlashDAO flashDAO = new FlashDAO(this);
        listaSegunda = flashDAO.getFlashEquipamentoPorDia("Segunda-Feira");
        listaTerca = flashDAO.getFlashEquipamentoPorDia("Terça-Feira");
        listaQuarta = flashDAO.getFlashEquipamentoPorDia("Quarta-Feira");
        listaQuinta = flashDAO.getFlashEquipamentoPorDia("Quinta-Feira");
        listaSexta = flashDAO.getFlashEquipamentoPorDia("Sexta-Feira");
        listaSabado = flashDAO.getFlashEquipamentoPorDia("Sábado");
        listaTotal = flashDAO.getFlashEquipamentoPorDia("TOTAL");

        adapter = new FlashEquipamentoAdatper(this, listaSegunda);
        recyclerViewSegunda.setAdapter(adapter);

        adapter = new FlashEquipamentoAdatper(this, listaTerca);
        recyclerViewTerca.setAdapter(adapter);

        adapter = new FlashEquipamentoAdatper(this, listaQuarta);
        recyclerViewQuarta.setAdapter(adapter);

        adapter = new FlashEquipamentoAdatper(this, listaQuinta);
        recyclerViewQuinta.setAdapter(adapter);

        adapter = new FlashEquipamentoAdatper(this, listaSexta);
        recyclerViewSexta.setAdapter(adapter);

        adapter = new FlashEquipamentoAdatper(this, listaSabado);
        recyclerViewSabado.setAdapter(adapter);

        adapter = new FlashEquipamentoAdatper(this, listaTotal);
        recyclerViewTotal.setAdapter(adapter);
    }
}
