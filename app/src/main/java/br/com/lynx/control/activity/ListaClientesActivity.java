package br.com.lynx.control.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.lynx.R;
import br.com.lynx.control.misc.ComparaVolume_Activity;
import br.com.lynx.control.adapter.ListaClientesAdapter;
import br.com.lynx.control.misc.PesquisaCervejaActivity;
import br.com.lynx.control.misc.PesquisaEquipamentoActivity;
import br.com.lynx.control.misc.PesquisaSubcanalActivity;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.dao.ClienteDAO;
import br.com.lynx.dao.RotaDAO;
import br.com.lynx.model.Configuracao;
import br.com.lynx.model.InfoPanel;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.domain.Cliente;
import br.com.lynx.vo.RotaVO;

public class ListaClientesActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private InfoPanel infoPanel;
    private RecyclerView recyclerView;

    private ClienteDAO clienteDAO;
    private List<Cliente> listaCli = new ArrayList<Cliente>();
    private List<RotaVO> rotas = new ArrayList<RotaVO>();
    private Cliente clienteSelecionado;
    private EditText edtPesquisa;
    private Configuracao configuracao;
    private Dialog dialogTelefone;
    private Handler handler;
    private ImageButton btnConfirmar;
    private ImageButton btnCancelar;
    private EditText edtTelefone;
    private Dialog enviarArquivo;
    private Toolbar toolbar;
    private ListaClientesAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaclientes);

        toolbar = (Toolbar) findViewById(R.id.tb_listaclientes);
        toolbar.setTitle("Lista de clientes");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_lista_cliente);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        clienteDAO = new ClienteDAO(this);
        listaCli = clienteDAO.listaClientes();

        adapter = new ListaClientesAdapter(this, listaCli);
        adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter);

        carregaRotas();

        handler = new Handler();
        handleSearch(getIntent());
        ajustaPainelInformacoes();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearch(intent);
    }

    public void handleSearch(Intent intent) {
        if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
            String q = intent.getStringExtra(SearchManager.QUERY);

            toolbar.setTitle(q);
            localizar(q);
        }
    }

    public void onRestart() {
        List<Cliente> listaClientes = new ArrayList<Cliente>();

        super.onRestart();

        listaClientes = clienteDAO.listaClientes();
        listaCli.clear();

        for (Cliente cliente: listaClientes)
          listaCli.add(cliente);

        adapter.notifyDataSetChanged();

        ajustaPainelInformacoes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_listaclientes, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.action_searchable_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) item.getActionView();
        } else {
            searchView = (SearchView) MenuItemCompat.getActionView(item);
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Buscar");

        for (int i = 0; i < rotas.size(); i++) {
            menu.add(0, rotas.get(i).getRotaID(), i, rotas.get(i).getDescricao());
        }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        String textoRota = item.getTitle().toString();
        try {
            int rota = Integer.parseInt(textoRota.substring(5, 8));

            ClienteDAO clienteDAO = new ClienteDAO(this);
            listaCli = clienteDAO.pesquisaRota(rota);

            adapter = new ListaClientesAdapter(this, listaCli);
            adapter.setRecyclerViewOnClickListenerHack(this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {

        }

        return true;
    }

    public void localizar(String q) {
        boolean isNumero;
        int codigo = 0;
        ClienteDAO clienteDAO = new ClienteDAO(this);

        List<Cliente> listaClienteLocal = new ArrayList<Cliente>();
        listaCli.clear();

        try {
            codigo = Integer.parseInt(q);
            isNumero = true;
        } catch (NumberFormatException e) {
            isNumero = false;
        }

        if (isNumero)
            listaClienteLocal = clienteDAO.pesquisaCodigo(codigo);
        else
            listaClienteLocal = clienteDAO.pesquisaNome(q);

        for (Cliente cliente : listaClienteLocal)
            listaCli.add(cliente);

        adapter.notifyDataSetChanged();
    }

    private void ajustaPainelInformacoes() {
        configuracao = Configuracao.getInstance(this);
        configuracao.load();

        infoPanel = InfoPanel.getInstance(this);
        infoPanel.load();

        ((TextView) findViewById(R.id.panel_info_cliente)).setText(infoPanel.getClientes());
        ((TextView) findViewById(R.id.panel_info_positivacao)).setText(infoPanel.getPositivacao());
        ((TextView) findViewById(R.id.panel_info_pedidos)).setText(infoPanel.getNumeroPedidos());
        ((TextView) findViewById(R.id.panel_info_aenviar)).setText(infoPanel.getNaoEnviados());
        ((TextView) findViewById(R.id.panel_info_caixas)).setText(infoPanel.getCaixas());
    }

    private void carregaRotas() {
        RotaDAO rotasDAO = new RotaDAO(this);
        rotas = rotasDAO.retornaListaRota();
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent;

        clienteSelecionado = listaCli.get(position);

        // Verifica se o activity_cliente_detalhe anterior foi atendido
        if (clienteSelecionado.getOrdem() > 1) {
            ClienteDAO clienteDAO = new ClienteDAO(this);
            Cliente clienteAnterior = clienteDAO
                    .retornaClientePorOrdem(clienteSelecionado.getOrdem() - 1);

            if (!clienteAnterior.getAtendido() && !clienteAnterior.isPositivado() && !clienteAnterior.isNaoVenda()) {
                MessageBox.show(this, "Atendimento não permitido",
                        "Para atender esse cliente, você deve antes, atender o cliente anterior.");
                return;
            }
        }

        intent = new Intent(this, ClienteDetalheActivity.class);
        intent.putExtra("ClienteID", clienteSelecionado.getClienteID());
        startActivity(intent);

        if (clienteSelecionado.getVolumeTop10() > 0
                || clienteSelecionado.getVariacao() < 0) {
            intent = new Intent(this, ComparaVolume_Activity.class);
            intent.putExtra("ClienteID", clienteSelecionado.getClienteID());
            startActivity(intent);
        }

        if (clienteSelecionado.getItensArrastao().size() > 0) {
            intent = new Intent(this, ClienteCampanhaActivity.class);
            intent.putExtra("ClienteID", clienteSelecionado.getClienteID());
            startActivity(intent);
        }

        if (!clienteSelecionado.getEquipamentos().isEmpty()) {
            intent = new Intent(this, EquipamentoActivity.class);
            intent.putExtra("ClienteID", clienteSelecionado.getClienteID());
            startActivity(intent);
        }

        try {
            if (!clienteSelecionado.getTitulos().isEmpty()) {
                intent = new Intent(this, TituloActivity.class);
                intent.putExtra("ClienteID", clienteSelecionado.getClienteID());
                startActivity(intent);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (clienteSelecionado.getEquipamentosPesquisa().size() > 0 && !clienteSelecionado.getPesquisaEquipamentoEnviada()){
            intent = new Intent(this, PesquisaEquipamentoActivity.class);
            intent.putExtra("ClienteID", clienteSelecionado.getClienteID());
            startActivity(intent);
        }

        if (!clienteSelecionado.getSubcanalConfirmado() && !clienteSelecionado.getPesquisaSubcanalEnviada()){
            intent = new Intent(this, PesquisaSubcanalActivity.class);
            intent.putExtra("ClienteID", clienteSelecionado.getClienteID());
            startActivity(intent);
        }

        if (clienteSelecionado.getPesquisaCerveja() && !clienteSelecionado.getPesquisaCervejaEnviada()) {
            intent = new Intent(this, PesquisaCervejaActivity.class);
            intent.putExtra("ClienteID", clienteSelecionado.getClienteID());
            startActivity(intent);
        }
    }
}
