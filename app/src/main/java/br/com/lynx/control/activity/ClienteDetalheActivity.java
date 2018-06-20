package br.com.lynx.control.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.misc.HistoricoVenda;
import br.com.lynx.control.misc.ListaDevolucao;
import br.com.lynx.control.misc.PesquisaActivity;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.dao.RelatorioDAO;
import br.com.lynx.domain.Cliente;
import br.com.lynx.domain.TipoPedido;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.model.Configuracao;
import br.com.lynx.model.ItemMenu;
import br.com.lynx.model.Listas;

public class ClienteDetalheActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private Cliente cliente;
    private List<TipoPedido> tiposPedido;
    private boolean quedas = false;
    private boolean equipamentos = false;
    private RecyclerView recyclerView;
    private List<ItemMenu> list;
    private GridView gridview;
    private Configuracao configuracao;
    private int clienteID;

    private String[] labels = {
            "Novo pedido",
            "Financeiro",
            "Equipamentos",
            "Devoluções",
            "Histórico",
            "Pesquisa"
    };

    private int[] images = {
            R.drawable.if_invoice,
            R.drawable.financeiro,
            R.drawable.ic_freezer,
            R.drawable.if_cargo,
            R.drawable.ic_movimento,
            R.drawable.questionaire
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalhe);

        configuracao = Configuracao.getInstance(this);

        Intent intent = getIntent();
        clienteID = intent.getIntExtra("ClienteID", 0);

        cliente = new Cliente(this);
        cliente.load(clienteID);

        if (!cliente.getExibePesquisa() && configuracao.getDivisaoID() != 2 && configuracao.getDivisaoID() != 14){
            labels[5] = null;
            images[5] = 0;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.clienteDetalhe_Toolbar);
        toolbar.setTitle("Atendimento");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView) findViewById(R.id.idCliente_RazaoSocial)).setText(cliente.getRazaoSocial());
        ((TextView) findViewById(R.id.idCliente_CodigoCliente)).setText(String.valueOf(cliente.getClienteID()) + '/' + cliente.getSubcanal());
        ((TextView) findViewById(R.id.idCliente_FormaPagamento)).setText(cliente.getFormaPagamento().getDescricao());

        exibirInformacoes();

        gridview = (GridView) findViewById(R.id.clienteDetalhe_customGrid);
        gridview.setAdapter(new CustomAdapter(this, labels, images));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                avaliarOnItemClickListener(position);

            }
        });
    }

    private void avaliarOnItemClickListener(int position) {
        switch (position){
            case 0:
                abrirNovoPedido();
                break;

            case 1:
                try {
                    listarTitulosCliente();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case 2:
                listarEquipamentos();
                break;

            case 3:
                listarDevolucoes();
                break;

            case 4:
                listarHistoricoPedidos();
                break;

            case 5:
                pesquisa();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }

    @Override
    public void onClickListener(View view, int position) {
        if (position == 0)
            abrirNovoPedido();
        else if (position == 1)
            try {
                listarTitulosCliente();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        else if (position == 2)
            listarEquipamentos();
        else if (position == 3)
            listarDevolucoes();
        else if (position == 4)
            listarHistoricoPedidos();
        else if (position == 5)
            pesquisa();
    }

    private void abrirNovoPedido() {
        carregaTiposPedido();

        CharSequence[] opcoes = new CharSequence[tiposPedido.size()];
        for (int i = 0; i < tiposPedido.size(); i++)
            opcoes[i] = tiposPedido.get(i).getDescricao();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Operação");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                novoPedido(tiposPedido.get(item));
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void listarTitulosCliente() throws ParseException {
        Intent intent = new Intent(this, TituloActivity.class);

        if (cliente.getTitulos().isEmpty()) {
            MessageBox.show(this, "Títulos",
                    "O cliente não possui títulos para exibição.");

        } else {
            intent.putExtra("ClienteID", cliente.getClienteID());

            startActivity(intent);
        }
    }

    private void listarEquipamentos() {
        Intent intent = new Intent(this, EquipamentoActivity.class);

        if (cliente.getEquipamentos().isEmpty()) {
            MessageBox.show(this, "Equipamentos",
                    "O cliente não possui equipamentos para exibição.");

        } else {
            intent.putExtra("ClienteID", cliente.getClienteID());

            startActivity(intent);
        }
    }

    private void listarDevolucoes() {
        Intent intent = new Intent(this, ListaDevolucao.class);

        if (cliente.getDevolucoes().isEmpty()) {
            MessageBox.show(this, "Devoluções",
                    "O cliente não possui devoluções para exibição.");

        } else {
            intent.putExtra("ClienteID", cliente.getClienteID());

            startActivity(intent);
        }
    }

    private void listarHistoricoPedidos(){
        Intent intent = new Intent(this, HistoricoVenda.class);

        intent.putExtra("ClienteID",  cliente.getClienteID());
        startActivity(intent);
    }

    private void carregaTiposPedido() {
        tiposPedido = Listas.GetListTipoPedido(this);
    }

    private void novoPedido(TipoPedido tipoPedido) {
        Intent intent = new Intent(this, ManutencaoPedidoActivity.class);

        if (tipoPedido.getTipoPedidoID() == 5 && !cliente.isNovaCampanhaColgate()) {
            MessageBox.show(this, "Colgate", "Ação não disponível para esse cliente.");
        }
        else {
            intent.putExtra("ClienteID", cliente.getClienteID());
            intent.putExtra("TipoPedidoID", tipoPedido.getTipoPedidoID());

            startActivity(intent);
            finish();
        }
    }

    private void pesquisa(){

        if (configuracao.getDivisaoID() == 2 || configuracao.getDivisaoID() == 14) {
            Intent intent = new Intent(this, AvaliacaoPDVNGActivity.class);

            intent.putExtra("ClienteID", clienteID);
            intent.putExtra("TipoAvaliacaoID", 5);
            startActivity(intent);
        }
        else if (cliente.getExibePesquisa()) {
            Intent intent = new Intent(this, PesquisaActivity.class);
            intent.putExtra("ClienteID", cliente.getClienteID());
            startActivity(intent);
        }
    }

    private void exibirInformacoes() {
        TextView txtVolumeAnterior = (TextView) findViewById(R.id.idCliente_ComparativoPeriodoAnterior);
        TextView txtVolumeAtual = (TextView) findViewById(R.id.idCliente_ComparativoPeriodoAtual);
        TextView txtVariacao = (TextView) findViewById(R.id.idCliente_ComparativoVariacao);

        txtVolumeAnterior.setText(String.valueOf(cliente.getVolumeAnterior()));
        txtVolumeAtual.setText(String.valueOf(cliente.getVolumeAtual()));
        txtVariacao.setText(String.valueOf(cliente.getVariacao()));

        LinearLayout infoExtraLayout = (LinearLayout) findViewById(R.id.idCliente_ExtraInfoLayout);

        if (cliente.getPossuiCondicao1x1() || cliente.getPossuiAtendimentoTelevendas() || cliente.getRepasseRefPet() || cliente.getRepasseLS() || cliente.getNotaPDVNG() > -1){
            TextView txtClienteCondicao1x1 = (TextView) findViewById(R.id.idCliente_Condicao1x1);
            TextView txtClienteTelevenda = (TextView) findViewById(R.id.idCliente_AtendidoTelevendas);
            TextView txtClienteRepasseRefPet = (TextView) findViewById(R.id.idCliente_RepasseREFPET);
            TextView txtClienteRepasseLS = (TextView) findViewById(R.id.idCliente_RepasseLS);
            TextView txtNotaPDVNG = (TextView) findViewById(R.id.idCliente_NotaPDVNG);

            if (cliente.getPossuiCondicao1x1())
                txtClienteCondicao1x1.setText("Cliente com condição de boleto 1x1");
            else
                txtClienteCondicao1x1.setVisibility(View.GONE);

            if (cliente.getPossuiAtendimentoTelevendas())
                txtClienteTelevenda.setText("Cliente atendido pelo televendas");
            else
                txtClienteTelevenda.setVisibility(View.GONE);

            if (cliente.getRepasseRefPet())
                txtClienteRepasseRefPet.setText("Cliente com REPASSE REFPET");
            else
                txtClienteRepasseRefPet.setVisibility(View.GONE);

            if (cliente.getRepasseLS())
                txtClienteRepasseLS.setText("Cliente com REPASSE LS");
            else
                txtClienteRepasseLS.setVisibility(View.GONE);

            if (cliente.getNotaPDVNG() > -1)
                txtNotaPDVNG.setText("Nota última avaliação: " + String.valueOf(cliente.getNotaPDVNG()));
            else
                txtNotaPDVNG.setVisibility(View.GONE);
        }
        else
            infoExtraLayout.setVisibility(View.GONE);
    }

    private boolean possuiMaioresQuedas() {

        boolean retorno = false;
        RelatorioDAO relatorioDAO = new RelatorioDAO(ClienteDetalheActivity.this);

        if (relatorioDAO.listaMaioresQuedas(cliente.getClienteID()).size() > 0)
            retorno = true;

        return retorno;
    }
}