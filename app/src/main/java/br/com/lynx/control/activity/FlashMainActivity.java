package br.com.lynx.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.misc.ListaCampanha;
import br.com.lynx.control.misc.MenuItemAdapter;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.model.Configuracao;
import br.com.lynx.model.ItemMenu;

/**
 * Created by Rogerio on 20/04/2016.
 */
public class FlashMainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private RecyclerView recyclerView;
    private List<ItemMenu> list;
    private Configuracao configuracao;

    private enum COCACOLA_FLASH {Metas, Cobertura, Devolucoes, Top10, Quedas, Projetos, Arrastao, Femsa, Desafio, Equipamentos, Positivacao, UltimasDevolucoes, BigFour;}

    private enum MONDELEZ_FLASH {Metas, Cobertura, Devolucoes, Top10, Quedas, Arrastao, Positivacao, UltimasDevolucoes;}

    private enum ALIMENTAR_FLASH {Metas, Cobertura, Devolucoes, Top10, Quedas, Arrastao, Positivacao, UltimasDevolucoes;}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_main);

        configuracao = Configuracao.getInstance(getApplicationContext());
        configuracao.load();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_flash_main);
        toolbar.setTitle("Flash");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_flash_main);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager llm = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        list = getSetItemMenuList();
        MenuItemAdapter adapter = new MenuItemAdapter(this, list);
        adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter);
    }

    private List<ItemMenu> getSetItemMenuList() {
        List<ItemMenu> listAux = new ArrayList<>();

        if (configuracao.getDivisaoID() == 2 || configuracao.getDivisaoID() == 14) {
            listAux.add(new ItemMenu("Metas por categoria", R.drawable.document_graph, COCACOLA_FLASH.Metas.ordinal()));
            listAux.add(new ItemMenu("Cobertura", R.drawable.cobertura, COCACOLA_FLASH.Cobertura.ordinal()));
            listAux.add(new ItemMenu("Clientes TOP 10", R.drawable.vip, COCACOLA_FLASH.Top10.ordinal()));
            listAux.add(new ItemMenu("Maiores quedas", R.drawable.quedas, COCACOLA_FLASH.Quedas.ordinal()));
            listAux.add(new ItemMenu("Projetos", R.drawable.projects, COCACOLA_FLASH.Projetos.ordinal()));
            listAux.add(new ItemMenu("Coberturas prioritárias", R.drawable.arrastao, COCACOLA_FLASH.Arrastao.ordinal()));
            listAux.add(new ItemMenu("Prazo Femsa", R.drawable.femsa, COCACOLA_FLASH.Femsa.ordinal()));
            listAux.add(new ItemMenu("Desafio", R.drawable.desafio, COCACOLA_FLASH.Desafio.ordinal()));
            listAux.add(new ItemMenu("Big 4", R.drawable.big4, COCACOLA_FLASH.BigFour.ordinal()));
            listAux.add(new ItemMenu("Equipamentos", R.drawable.equipamento, COCACOLA_FLASH.Equipamentos.ordinal()));
            listAux.add(new ItemMenu("Positivação", R.drawable.positivacao, COCACOLA_FLASH.Positivacao.ordinal()));
            listAux.add(new ItemMenu("Devoluções", R.drawable.devolucoes, COCACOLA_FLASH.Devolucoes.ordinal()));
            listAux.add(new ItemMenu("Últimas devoluções", R.drawable.ultimasdevolucoes, COCACOLA_FLASH.UltimasDevolucoes.ordinal()));
        } else if (configuracao.getDivisaoID() == 9) {
            listAux.add(new ItemMenu("Metas por categoria", R.drawable.document_graph, MONDELEZ_FLASH.Metas.ordinal()));
            listAux.add(new ItemMenu("Cobertura", R.drawable.cobertura, MONDELEZ_FLASH.Cobertura.ordinal()));
            listAux.add(new ItemMenu("Devoluções", R.drawable.devolucoes, MONDELEZ_FLASH.Devolucoes.ordinal()));
            listAux.add(new ItemMenu("Últimas devoluções", R.drawable.ultimasdevolucoes, MONDELEZ_FLASH.UltimasDevolucoes.ordinal()));
            listAux.add(new ItemMenu("Clientes TOP 10", R.drawable.vip, MONDELEZ_FLASH.Top10.ordinal()));
            listAux.add(new ItemMenu("Maiores quedas", R.drawable.quedas, MONDELEZ_FLASH.Quedas.ordinal()));
            listAux.add(new ItemMenu("MSL", R.drawable.arrastao, MONDELEZ_FLASH.Arrastao.ordinal()));
            listAux.add(new ItemMenu("Positivação", R.drawable.positivacao, MONDELEZ_FLASH.Positivacao.ordinal()));
        } else if (configuracao.getDivisaoID() == 10) {
            listAux.add(new ItemMenu("Metas por categoria", R.drawable.document_graph, ALIMENTAR_FLASH.Metas.ordinal()));
            listAux.add(new ItemMenu("Cobertura", R.drawable.cobertura, ALIMENTAR_FLASH.Cobertura.ordinal()));
            listAux.add(new ItemMenu("Devoluções", R.drawable.devolucoes, ALIMENTAR_FLASH.Devolucoes.ordinal()));
            listAux.add(new ItemMenu("Últimas devoluções", R.drawable.ultimasdevolucoes, ALIMENTAR_FLASH.UltimasDevolucoes.ordinal()));
            listAux.add(new ItemMenu("Clientes TOP 10", R.drawable.vip, ALIMENTAR_FLASH.Top10.ordinal()));
            listAux.add(new ItemMenu("Maiores quedas", R.drawable.quedas, ALIMENTAR_FLASH.Quedas.ordinal()));
            listAux.add(new ItemMenu("Desafios", R.drawable.arrastao, ALIMENTAR_FLASH.Arrastao.ordinal()));
            listAux.add(new ItemMenu("Positivação", R.drawable.positivacao, ALIMENTAR_FLASH.Positivacao.ordinal()));
        } else if (configuracao.getDivisaoID() == 12) {
            listAux.add(new ItemMenu("Metas por categoria", R.drawable.document_graph, ALIMENTAR_FLASH.Metas.ordinal()));
            listAux.add(new ItemMenu("Cobertura", R.drawable.cobertura, ALIMENTAR_FLASH.Cobertura.ordinal()));
            listAux.add(new ItemMenu("Devoluções", R.drawable.devolucoes, ALIMENTAR_FLASH.Devolucoes.ordinal()));
            listAux.add(new ItemMenu("Últimas devoluções", R.drawable.ultimasdevolucoes, ALIMENTAR_FLASH.UltimasDevolucoes.ordinal()));
            listAux.add(new ItemMenu("Clientes TOP 10", R.drawable.vip, ALIMENTAR_FLASH.Top10.ordinal()));
            listAux.add(new ItemMenu("Maiores quedas", R.drawable.quedas, ALIMENTAR_FLASH.Quedas.ordinal()));
            listAux.add(new ItemMenu("Desafios", R.drawable.arrastao, ALIMENTAR_FLASH.Arrastao.ordinal()));
            listAux.add(new ItemMenu("Positivação", R.drawable.positivacao, ALIMENTAR_FLASH.Positivacao.ordinal()));
        }

        return (listAux);
    }

    @Override
    public void onClickListener(View view, int position) {
        int opcao = list.get(position).getID();

        if (configuracao.getDivisaoID() == 2 || configuracao.getDivisaoID() == 14) {
            if (opcao == COCACOLA_FLASH.Metas.ordinal())
                exibirMetasPorCategoria();
            else if (opcao == COCACOLA_FLASH.Cobertura.ordinal())
                exibirFlashCobertura();
            else if (opcao == COCACOLA_FLASH.Devolucoes.ordinal())
                exibirFlashDevolucao();
            else if (opcao == COCACOLA_FLASH.Top10.ordinal())
                exibirFlashTop10();
            else if (opcao == COCACOLA_FLASH.Quedas.ordinal())
                exibirFlashMaioresQuedas();
            else if (opcao == COCACOLA_FLASH.Projetos.ordinal())
                exibirFlashProjetos();
            else if (opcao == COCACOLA_FLASH.Arrastao.ordinal())
                exibirFlashArrastao();
            else if (opcao == COCACOLA_FLASH.Femsa.ordinal())
                exibirFlashPrazoFemsa();
            else if (opcao == COCACOLA_FLASH.Desafio.ordinal())
                exibirFlashDesafio();
            else if (opcao == COCACOLA_FLASH.Equipamentos.ordinal())
                exibirFlashEquipamento();
            else if (opcao == COCACOLA_FLASH.Positivacao.ordinal())
                exibirFlashPositivacao();
            else if (opcao == COCACOLA_FLASH.UltimasDevolucoes.ordinal())
                exibirFlashUltimasDevolucoes();
            else if (opcao == COCACOLA_FLASH.BigFour.ordinal())
                exibirFlashBig4();
        } else if (configuracao.getDivisaoID() == 9) {
            if (opcao == MONDELEZ_FLASH.Metas.ordinal())
                exibirMetasPorCategoria();
            else if (opcao == MONDELEZ_FLASH.Cobertura.ordinal())
                exibirFlashCobertura();
            else if (opcao == MONDELEZ_FLASH.Devolucoes.ordinal())
                exibirFlashDevolucao();
            else if (opcao == MONDELEZ_FLASH.Top10.ordinal())
                exibirFlashTop10();
            else if (opcao == MONDELEZ_FLASH.Quedas.ordinal())
                exibirFlashMaioresQuedas();
            else if (opcao == MONDELEZ_FLASH.Arrastao.ordinal())
                exibirFlashArrastao();
            else if (opcao == MONDELEZ_FLASH.Positivacao.ordinal())
                exibirFlashPositivacao();
            else if (opcao == MONDELEZ_FLASH.UltimasDevolucoes.ordinal())
                exibirFlashUltimasDevolucoes();
        } else if (configuracao.getDivisaoID() == 10) {
            if (opcao == ALIMENTAR_FLASH.Metas.ordinal())
                exibirMetasPorCategoria();
            else if (opcao == ALIMENTAR_FLASH.Cobertura.ordinal())
                exibirFlashCobertura();
            else if (opcao == ALIMENTAR_FLASH.Devolucoes.ordinal())
                exibirFlashDevolucao();
            else if (opcao == ALIMENTAR_FLASH.Top10.ordinal())
                exibirFlashTop10();
            else if (opcao == ALIMENTAR_FLASH.Quedas.ordinal())
                exibirFlashMaioresQuedas();
            else if (opcao == ALIMENTAR_FLASH.Arrastao.ordinal())
                exibirFlashArrastao();
            else if (opcao == ALIMENTAR_FLASH.Positivacao.ordinal())
                exibirFlashPositivacao();
            else if (opcao == ALIMENTAR_FLASH.UltimasDevolucoes.ordinal())
                exibirFlashUltimasDevolucoes();
        } else if (configuracao.getDivisaoID() == 12) {
            if (opcao == ALIMENTAR_FLASH.Metas.ordinal())
                exibirMetasPorCategoria();
            else if (opcao == ALIMENTAR_FLASH.Cobertura.ordinal())
                exibirFlashCobertura();
            else if (opcao == ALIMENTAR_FLASH.Devolucoes.ordinal())
                exibirFlashDevolucao();
            else if (opcao == ALIMENTAR_FLASH.Top10.ordinal())
                exibirFlashTop10();
            else if (opcao == ALIMENTAR_FLASH.Quedas.ordinal())
                exibirFlashMaioresQuedas();
            else if (opcao == ALIMENTAR_FLASH.Arrastao.ordinal())
                exibirFlashArrastao();
            else if (opcao == ALIMENTAR_FLASH.Positivacao.ordinal())
                exibirFlashPositivacao();
            else if (opcao == ALIMENTAR_FLASH.UltimasDevolucoes.ordinal())
                exibirFlashUltimasDevolucoes();
        }
    }

    private void exibirMetasPorCategoria() {
        startActivity(new Intent(this, FlashMetaCategoriaActivity.class));
    }

    private void exibirFlashCobertura() {
        startActivity(new Intent(this, FlashCoberturaActivity.class));
    }

    private void exibirFlashDevolucao() {
        startActivity(new Intent(this, FlashDevolucaoActivity.class));
    }

    private void exibirFlashTop10() {
        startActivity(new Intent(this, FlashTop10Activity.class));
    }

    private void exibirFlashMaioresQuedas() {
        startActivity(new Intent(this, FlashQuedasActivity.class));
    }

    private void exibirFlashProjetos() {
        startActivity(new Intent(this, FlashProjetosActivity.class));
    }

    private void exibirFlashPrazoFemsa() {
        startActivity(new Intent(this, FlashPrazoFemsaActivity.class));
    }

    private void exibirFlashArrastao() {
        startActivity(new Intent(this, FlashArrastaoActivity.class));
    }

    private void exibirFlashDesafio() {
        startActivity(new Intent(this, FlashDesafioActivity.class));
    }

    private void exibirFlashEquipamento() {
        startActivity(new Intent(this, FlashEquipamentoActivity.class));
    }

    private void exibirFlashPositivacao() {
        startActivity(new Intent(this, FlashPositivacaoActivity.class));
    }

    private void exibirFlashUltimasDevolucoes() {
        startActivity(new Intent(this, FlashUltimasDevolucoesActivity.class));
    }

    private void exibirFlashBig4() {
        startActivity(new Intent(this, FlashBig4Activity.class));
    }
}
