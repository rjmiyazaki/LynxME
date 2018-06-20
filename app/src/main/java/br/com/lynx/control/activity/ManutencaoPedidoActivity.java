package br.com.lynx.control.activity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.scalified.fab.ActionButton;

import br.com.lynx.R;
import br.com.lynx.control.adapter.GrupoAdapter;
import br.com.lynx.control.adapter.ItemPedidoAdapter;
import br.com.lynx.control.misc.ConfirmacaoItem;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.dao.ProdutoDAO;
import br.com.lynx.domain.FormaPagamento;
import br.com.lynx.misc.Communication;
import br.com.lynx.model.Configuracao;
import br.com.lynx.model.Listas;
import br.com.lynx.model.TabelaPreco;
import br.com.lynx.util.Command;
import br.com.lynx.util.FlexxGPS;
import br.com.lynx.util.MathUtil;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.domain.Cliente;
import br.com.lynx.vo.MotivoNaoVendaVO;
import br.com.lynx.domain.PedidoItem;
import br.com.lynx.domain.Pedido;
import br.com.lynx.domain.Produto;
import br.com.lynx.vo.RegistroNaoVendaVO;
import br.com.lynx.domain.TipoPedido;

public class ManutencaoPedidoActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {
    private CharSequence[] motivos;
    private List<MotivoNaoVendaVO> listaMotivosNaoVenda;
    private List<Produto> listaProdutos;
    private Cliente cliente;
    private List<TabelaPreco> tabelasPreco;
    private TipoPedido tipoPedido;
    private Pedido pedido;
    private TextView txtProduto;
    private Spinner spnTabelaPreco;
    private Spinner spnTipoDesconto;
    private TextView edtValorUnitario;
    private EditText edtDesconto;
    private TextView edtValorLiquido;
    private EditText edtQuantidade;
    private TextView edtValorTotal;
    private Dialog dlgConfirmaItem;
    private Dialog dlgEnvioArquivo;
    private Button btnInsere;
    private Button btnExclui;
    private PedidoItem itemSelecionado;
    private EditText edtPesquisa;
    private EditText edtValorPedido;
    private EditText edtObservacao;
    private Spinner spnFormaPagamento;
    private Button btnConfirmaFinalizacao;
    private Button btnCancelaFinalizacao;
    private Activity context;
    private String messageError;
    private Handler handler;
    private int motivoNaoVenda;
    private Configuracao configuracao;
    private int vendedorID;
    private int clienteID;
    private int tipoPedidoID;
    private AlertDialog alertDialog;
    private RecyclerView recyclerView;
    private ItemPedidoAdapter adapter;
    private Dialog finalizaPedido;
    private String[] tiposDesconto = new String[]{"Por percentagem",
            "Por valor", "Acréscimo"};

    private ConfirmacaoItem confirmacaoItem;
    private Boolean sugestaoJaValidada;
    private Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manutencao_pedido);

        toolbar = (Toolbar) findViewById(R.id.toolbar_pedido);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configuracao = Configuracao.getInstance(this);
        configuracao.load();

        confirmacaoItem = new ConfirmacaoItem(this);

        vendedorID = Integer.parseInt(configuracao.getVendedorID());

        Intent intent = getIntent();
        clienteID = intent.getIntExtra("ClienteID", 0);
        tipoPedidoID = intent.getIntExtra("TipoPedidoID", 0);

        cliente = new Cliente(this);
        cliente.load(clienteID);

        tipoPedido = new TipoPedido(this);
        tipoPedido.load(tipoPedidoID);

        toolbar.setTitle(tipoPedido.getDescricao());

        pedido = new Pedido(this, tipoPedido, cliente, vendedorID);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_lista_itens_pedido);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new ItemPedidoAdapter(this, pedido.getItens());
        adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter);

        carregaMotivosNaoVenda();
        criaDialogConfirmacaoProduto();
        criaDialogFinalizacaoPedido();

        context = this;
        handler = new Handler();
        edtPesquisa = (EditText) findViewById(R.id.idManutencaoPedido_edtPesquisa);

        this.alertDialog = criaAlertDialog();
        sugestaoJaValidada = false;

        edtPesquisa.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    pesquisarProduto(v);
                    return true;
                }

                return false;

            }
        });

        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.fab_material_blue_500));

        actionButton.setImageDrawable(getResources().getDrawable(R.drawable.fab_plus_icon));
        actionButton.setImageResource(R.drawable.fab_plus_icon);

        actionButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ListaProdutoActivity.class);

                        String text = "";
                        intent.putExtra("value", text);

                        intent.putExtra("clienteID", clienteID);
                        intent.putExtra("tipoPedidoID", tipoPedidoID);
                        intent.putExtra("vendedorID", vendedorID);
                        startActivityForResult(intent, 0);
                    }
                }
        );
    }

    @Override
    public void onClickListener(View view, int position) {
        itemSelecionado = pedido.getItens().get(position);
        alteraItem(itemSelecionado);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            alertDialog.show();
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            alertDialog.show();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int codigo, int result, Intent it) {
        String produtoID;

        if (it != null) {
            produtoID = it.getStringExtra("ProdutoID");

            if (!validaExistencia(produtoID))
                confirmarNovoItem(produtoID);
            else
                Toast.makeText(context, "Produto já existe no pedido", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tipoPedidoID == 5)
            getMenuInflater().inflate(R.menu.menu_activity_manutencao_pedido, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_activity_manutencao_pedido_aplicarPromocaoColgate:
                aplicarPromocaoColgate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pesquisarProduto(View v) {
        Intent intent;
        String text;
        boolean exists = false;
        TextView txtPesquisa = (TextView) findViewById(R.id.idManutencaoPedido_edtPesquisa);
        ProdutoDAO produtoDAO = new ProdutoDAO(this);

        try {
            Integer.parseInt(txtPesquisa.getText().toString());
            listaProdutos = produtoDAO.pesquisaCodigo(txtPesquisa.getText().toString(), cliente.getClienteID());
        } catch (NumberFormatException e) {
            listaProdutos = produtoDAO.pesquisaNome(txtPesquisa.getText().toString(), cliente.getClienteID());
        }

        if (listaProdutos.size() == 1) {
            exists = validaExistencia(listaProdutos.get(0).getProdutoID());
            if (!exists)
                confirmarNovoItem(listaProdutos.get(0).getProdutoID());
            else
                Toast.makeText(context, "Produto já existe no pedido", Toast.LENGTH_LONG).show();
        } else {
            intent = new Intent(this, ListaProdutoActivity.class);

            text = txtPesquisa.getText().toString();
            intent.putExtra("value", text);

            intent.putExtra("clienteID", cliente.getClienteID());
            startActivityForResult(intent, 0);
        }
    }

    /* Métodos utilizadas na janela de confirmação de item */
    private void criaDialogConfirmacaoProduto() {
        if (dlgConfirmaItem != null)
            return;

        dlgConfirmaItem = new Dialog(this);
        dlgConfirmaItem.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgConfirmaItem.setContentView(R.layout.confirmaitem);

        txtProduto = (TextView) dlgConfirmaItem.findViewById(R.id.txtProduto);
        spnTabelaPreco = (Spinner) dlgConfirmaItem.findViewById(R.id.spnTabelaPreco);
        spnTipoDesconto = (Spinner) dlgConfirmaItem.findViewById(R.id.spnTipoDesconto);
        edtValorUnitario = (TextView) dlgConfirmaItem.findViewById(R.id.edtValorUnitario);
        edtDesconto = (EditText) dlgConfirmaItem.findViewById(R.id.edtDesconto);
        edtValorLiquido = (TextView) dlgConfirmaItem.findViewById(R.id.edtValorLiquido);
        edtQuantidade = (EditText) dlgConfirmaItem.findViewById(R.id.edtQuantidade);
        edtValorTotal = (TextView) dlgConfirmaItem.findViewById(R.id.edtValorTotal);
        btnInsere = (Button) dlgConfirmaItem.findViewById(R.id.btnInsere);
        btnExclui = (Button) dlgConfirmaItem.findViewById(R.id.btnExclui);

        ArrayAdapter<String> tipoDescontoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tiposDesconto);
        tipoDescontoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoDesconto.setAdapter(tipoDescontoAdapter);

        spnTabelaPreco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int posicao,
                                       long id) {
                alteraTabelaPreco(posicao);
                atualizaCampos(itemSelecionado);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnTipoDesconto
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent, View v, int posicao,
                                               long id) {
                        atualizaCampos(itemSelecionado);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        edtQuantidade.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    int quantidade = Integer.parseInt(edtQuantidade.getText().toString());
                    itemSelecionado.setQuantidade(quantidade);
                } catch (Exception ex) {
                    itemSelecionado.setQuantidade(0);
                }

                atualizaCampos(itemSelecionado);
            }
        });

        edtQuantidade.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    try {
                        int quantidade = Integer.parseInt(edtQuantidade.getText().toString());
                        itemSelecionado.setQuantidade(quantidade);
                    } catch (Exception ex) {
                        itemSelecionado.setQuantidade(0);
                    }

                    atualizaCampos(itemSelecionado);
                    return true;
                }

                return false;
            }
        });


        edtDesconto.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    try {
                        float desconto, valorDesconto, valorAcrescimo;

                        if (spnTipoDesconto.getSelectedItemPosition() == 0) {
                            desconto = MathUtil.stringToFloat(edtDesconto.getText().toString());

                            itemSelecionado.setAcrescimo(0);
                            itemSelecionado.setDesconto(desconto);
                        } else if (spnTipoDesconto.getSelectedItemPosition() == 1) {
                            valorDesconto = MathUtil.stringToFloat(edtDesconto.getText().toString());
                            desconto = (float) MathUtil.round((valorDesconto / itemSelecionado.getPrecoUnitario()) * 100, 2);

                            itemSelecionado.setAcrescimo(0);
                            itemSelecionado.setDesconto(desconto);
                        } else if (spnTipoDesconto.getSelectedItemPosition() == 2) {
                            valorAcrescimo = MathUtil.stringToFloat(edtDesconto.getText().toString());
                            desconto = (float) MathUtil.round((valorAcrescimo / itemSelecionado.getPrecoUnitario()) * 100, 2);

                            itemSelecionado.setAcrescimo(desconto);
                            itemSelecionado.setDesconto(0);
                        }

                    } catch (Exception ex) {
                        itemSelecionado.setDesconto(0);
                        itemSelecionado.setAcrescimo(0);
                    }

                    atualizaCampos(itemSelecionado);
                    return true;
                }

                return false;
            }
        });

        btnInsere.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                try {
                    if (tipoPedidoID != 5)
                        itemSelecionado.validaDesconto(clienteID);
                    else {
                        itemSelecionado.validaItemCampanhaColgate();
                        itemSelecionado.validaDescontoCampanhaColgate();
                    }

                } catch (Exception e) {
                    MessageBox.show(ManutencaoPedidoActivity.this, "LynxME", e.getMessage());
                    return;
                }

                String mensagem = itemSelecionado.validar(spnTipoDesconto.getSelectedItemPosition());

                if (!mensagem.equals(""))
                    MessageBox.show(ManutencaoPedidoActivity.this, "LynxME", mensagem);
                else {
                    inserirItem();
                    dlgConfirmaItem.dismiss();
                }
            }
        });

        btnExclui.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                removerItem();
                dlgConfirmaItem.dismiss();
            }
        });

    }

    private void exibeValores(PedidoItem item) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));
        String descricao;
        float desconto;

        descricao = item.getItem().getDescricao() + " - " + item.getItem().getUnidade();
        txtProduto.setText(descricao);

        if (item.getQuantidade() == 0)
            edtQuantidade.setText("");
        else
            edtQuantidade.setText(String.valueOf(item.getQuantidade()));

        desconto = item.getDesconto();
        if (desconto > 0)
            edtDesconto.setText(decimalFormat.format(item.getDesconto()));
        else if (item.getAcrescimo() > 0)
            edtDesconto.setText(decimalFormat.format(item.getAcrescimo()));
        else
            edtDesconto.setText("");

        edtValorUnitario.setText(decimalFormat.format(item.getPrecoUnitario()));
        edtValorLiquido.setText(decimalFormat.format(item.getPrecoLiquido()));
        edtValorTotal.setText(decimalFormat.format(item.getValorTotal()));
    }

    private void confirmarNovoItem(String produtoID) {
        itemSelecionado = new PedidoItem(this);
        itemSelecionado.getItem().setProdutoID(produtoID);
        itemSelecionado.getItem().load();
        itemSelecionado.getItem().carregaTabelasPreco();

        btnExclui.setEnabled(false);

        tabelasPreco = cliente.tabelasDisponiveis(itemSelecionado.getItem());
        String[] tabelas = new String[tabelasPreco.size()];

        for (int i = 0; i < tabelasPreco.size(); i++)
            tabelas[i] = tabelasPreco.get(i).getDescricao();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabelas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTabelaPreco.setAdapter(adapter);

        itemSelecionado.setPrecoUnitario(tabelasPreco.get(spnTabelaPreco.getSelectedItemPosition()).getValor());
        itemSelecionado.setDesconto(0);
        itemSelecionado.setAcrescimo(0);
        itemSelecionado.setQuantidade(0);
        itemSelecionado.setRepasse(false);

        exibeValores(itemSelecionado);
        dlgConfirmaItem.show();
    }

    private void alteraItem(PedidoItem item) {
        tabelasPreco = cliente.tabelasDisponiveis(item.getItem());
        String[] tabelas = new String[tabelasPreco.size()];

        btnExclui.setEnabled(true);

        for (int i = 0; i < tabelasPreco.size(); i++)
            tabelas[i] = tabelasPreco.get(i).getDescricao();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabelas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTabelaPreco.setAdapter(adapter);

        exibeValores(item);
        dlgConfirmaItem.show();
    }

    private void atualizaCampos(PedidoItem item) {
        if (item.getQuantidade() > 0 || item.getDesconto() > 0 || item.getAcrescimo() > 0)
            exibeValores(item);

        int quantidade = (int) MathUtil.stringToFloat(edtQuantidade.getText().toString());
        float desconto, valorDesconto, valorAcrescimo;

        item.setQuantidade(quantidade);
        exibeValores(item);
    }

    private void alteraTabelaPreco(int posicao) {

        itemSelecionado.setPrecoUnitario(tabelasPreco.get(posicao).getValor());

        edtValorUnitario.setText(String.valueOf(itemSelecionado.getPrecoUnitario()));

        if (tabelasPreco.get(spnTabelaPreco.getSelectedItemPosition())
                .getID() == 8) {
            edtDesconto.setText("0");
            edtDesconto.setFocusable(false);

            itemSelecionado.setRepasse(true);
        } else {
            edtDesconto.setText("");
            edtDesconto.setFocusableInTouchMode(true);
            edtDesconto.setFocusable(true);
            itemSelecionado.setRepasse(false);
        }
    }

    private void carregaMotivosNaoVenda() {
        listaMotivosNaoVenda = Listas.GetListMotivoNaoVenda(this);

        motivos = new CharSequence[listaMotivosNaoVenda.size()];

        for (int i = 0; i < listaMotivosNaoVenda.size(); i++)
            motivos[i] = listaMotivosNaoVenda.get(i).getDescricao();
    }

    private boolean validaExistencia(String produtoID) {
        boolean exists = false;

        for (PedidoItem pe : pedido.getItens()) {
            if (pe.getItem().getProdutoID() == produtoID)
                exists = true;
        }

        return exists;
    }

    private void inserirItem() {
        edtQuantidade.clearFocus();
        edtDesconto.clearFocus();

        try {
            pedido.adicionaItem(itemSelecionado);
        } catch (Exception ex) {
            MessageBox.show(this, "Validação", ex.getMessage());
            return;
        }

        adapter.notifyDataSetChanged();
        atualizaBarraInfo();
    }

    private void atualizaBarraInfo() {
        TextView txtValorPedido = (TextView) findViewById(
                R.id.idManutencaoPedido_TotalPedido);
        EditText edtPesquisa = (EditText) findViewById(
                R.id.idManutencaoPedido_edtPesquisa);
        NumberFormat currencyFormat = NumberFormat
                .getCurrencyInstance(new Locale("pt", "BR"));

        edtPesquisa.setText("");
        txtValorPedido.setText(currencyFormat.format(pedido.getValorPedido()));
    }

    private void removerItem() {
        pedido.removeItem(itemSelecionado);
        adapter.notifyDataSetChanged();
        atualizaBarraInfo();

        edtPesquisa.setText("");
    }

    private void finalizarNaoVenda() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Não venda");

        builder.setItems(motivos, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                for (MotivoNaoVendaVO motivo : listaMotivosNaoVenda) {
                    if (motivos[item] == motivo.getDescricao())
                        motivoNaoVenda = motivo.getID();
                }

                pedido.finalizarNaoVenda(motivoNaoVenda, String.valueOf(motivos[item]));
                FlexxGPS.saveNaoVenda(pedido, String.valueOf(motivos[item]));
                dlgEnvioArquivo = ProgressDialog.show(ManutencaoPedidoActivity.this, "Envio",
                        "Enviando arquivo de não venda, aguarde...", false, true);

                enviarArquivoNaoVenda();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void enviarArquivoNaoVenda() {
        final RegistroNaoVendaVO registro = new RegistroNaoVendaVO(context, 1,
                pedido.getCliente().getClienteID(), motivoNaoVenda, new Date(),
                new Date());

        new Thread() {
            @Override
            public void run() {
                try {
                    Communication.enviarNaoVenda(context, registro);
                    dlgEnvioArquivo.dismiss();
                    finish();
                } catch (Exception e) {
                    dlgEnvioArquivo.dismiss();
                    messageError = e.getMessage();
                    handler.post(new Runnable() {
                        public void run() {
                            Command close = new Command() {
                                public void execute() {
                                    finish();
                                }
                            };

                            AlertDialog dialog = MessageBox.createAlertDialog(
                                    ManutencaoPedidoActivity.this, "Comunicação",
                                    "Erro ao enviar o pedido\r\n" + messageError, close);
                            dialog.show();
                        }
                    });
                } finally {
                }
            }
        }.start();
    }

    private void criaDialogFinalizacaoPedido() {
        finalizaPedido = new Dialog(this);

        finalizaPedido.setContentView(R.layout.finalizapedido);
        finalizaPedido.setTitle("Finalizar pedido");

        edtObservacao = (EditText) finalizaPedido.findViewById(R.id.edtObservacao);
        edtValorPedido = (EditText) finalizaPedido
                .findViewById(R.id.edtValorPedido);
        spnFormaPagamento = (Spinner) finalizaPedido
                .findViewById(R.id.spnTabelaPreco);
        btnConfirmaFinalizacao = (Button) finalizaPedido
                .findViewById(R.id.btnConfirma);
        btnCancelaFinalizacao = (Button) finalizaPedido
                .findViewById(R.id.btnCancela);

        edtValorPedido.setEnabled(false);

        spnFormaPagamento
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent, View v, int posicao,
                                               long id) {
                        ajustaTelaFinalizacao();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        btnConfirmaFinalizacao.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                salvarPedido();
                finalizaPedido.dismiss();
            }
        });

        btnCancelaFinalizacao.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                finalizaPedido.dismiss();
            }
        });

        edtObservacao.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) context
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtObservacao.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    public void ajustaTelaFinalizacao() {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));
        float valor;

        if (pedido != null) {
            pedido.setFormaPagamento(pedido.getCliente().getFormasPagamento().get((int) spnFormaPagamento.getSelectedItemId()));
            valor = pedido.getValorPedidoCorrigido();
            edtValorPedido.setText(decimalFormat.format(valor));
        }
    }

    private void aplicarPromocaoColgate() {
        if (!pedido.validaValorMinimoCampanhaColgate()) {
            MessageBox.show(this, "Colgate",
                    "O valor mínimo do pedido não foi atingido.");

            return;
        }

        if (!pedido.validarCampanhaColgatePedagio()) {
            MessageBox.show(this, "Colgate",
                    "Os itens do pedágio não estão presentes.");

            return;
        }

        pedido.aplicarAcaoCampanhaColgate();
        adapter.notifyDataSetChanged();
        atualizaBarraInfo();
    }

    private void finalizarPedido() {
        List<FormaPagamento> formasPagamentoCliente;
        String[] formasPagamento;
        int k = 0;

        if (pedido.getItens().size() == 0) {
            MessageBox.show(this, "LynxME",
                    "O pedido não possui nenhum item.");

            return;
        }

        if (tipoPedidoID == 5)
            aplicarPromocaoColgate();

        /* Verifica se os itens da sugestão foram inseridos */
        if (validaSugestaoPedido() && !sugestaoJaValidada)
            return;

        formasPagamentoCliente = pedido.getCliente().getFormasPagamento();
        formasPagamento = new String[formasPagamentoCliente.size()];

        for (int i = 0; i < pedido.getCliente().getFormasPagamento().size(); i++) {
            formasPagamento[i] = formasPagamentoCliente.get(i).getDescricao();

            if (formasPagamento[i].equals(pedido.getCliente().getFormaPagamento().getDescricao()))
                k = i;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, formasPagamento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnFormaPagamento.setAdapter(adapter);
        spnFormaPagamento.setSelection(k);

        edtObservacao.setText(pedido.getObs());
        ajustaTelaFinalizacao();

        finalizaPedido.show();

    }

    private void salvarPedido() {
        pedido.setObs(edtObservacao.getText().toString());
        pedido.finalizar(pedido.getCliente().getFormasPagamento()
                .get((int) spnFormaPagamento.getSelectedItemId()));
        FlexxGPS.saveVenda(pedido);

        dlgEnvioArquivo = ProgressDialog.show(this, "Envio",
                "Enviando arquivo de pedido, aguarde...", false, true);


        enviarArquivo();
    }

    private void enviarArquivo() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Communication.enviarPedido(context, pedido);

                    dlgEnvioArquivo.dismiss();
                    finish();
                } catch (Exception e) {
                    dlgEnvioArquivo.dismiss();
                    messageError = e.getMessage();
                    handler.post(new Runnable() {
                        public void run() {
                            dlgEnvioArquivo.dismiss();
                            Command close = new Command() {
                                public void execute() {
                                    finish();
                                }
                            };

                            AlertDialog dialog = MessageBox.createAlertDialog(
                                    ManutencaoPedidoActivity.this, "Comunicação",
                                    "Erro ao enviar o pedido\r\n" + messageError, close);
                            dialog.show();
                        }
                    });
                } finally {
                }
            }
        }.start();
    }

    private AlertDialog criaAlertDialog() {
        final CharSequence[] opcoes = {"Finalizar", "Não venda", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Encerrar atendimento").setItems(opcoes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0)
                    finalizarPedido();
                else if (which == 1)
                    finalizarNaoVenda();
            }
        });

        return builder.create();
    }

    private boolean validaSugestaoPedido() {
        List<Produto> itensSugestao = new ArrayList<Produto>();
        ArrayList<String> grupos = new ArrayList<String>();
        String grupo;
        boolean existe, result;

        result = false;

        listaProdutos = cliente.retornaListaProdutos(tipoPedidoID, vendedorID);

        // Insere os itens da sugestão
        for (Produto produto : listaProdutos) {
            if (produto.getDestacado()) {
                itensSugestao.add(produto);
            }
        }

        // Insere os grupos
        for (Produto produto : itensSugestao) {
            existe = false;

            for (int i = 0; i < grupos.size(); i++) {
                if (grupos.get(i).equals(produto.getGrupo())) {
                    existe = true;
                    break;
                }
            }

            if (!existe)
                grupos.add(produto.getGrupo());
        }

        for (PedidoItem item : pedido.getItens()) {
            for (int i = grupos.size() - 1; i >= 0; i--) {
                grupo = "";

                for (Produto produto : listaProdutos) {
                    if (produto.getProdutoID().equals(item.getItem().getProdutoID())) {
                        grupo = produto.getGrupo();
                        break;
                    }
                }

                if (grupos.get(i).equals(grupo))
                    grupos.remove(i);
            }
        }

        if (!grupos.isEmpty()) {
            result = true;
            criaJanelaItensFaltantes(grupos);
        }

        return result;
    }

    private void criaJanelaItensFaltantes(ArrayList<String> itens) {
        final Dialog itensFaltantes = new Dialog(this);

        itensFaltantes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        itensFaltantes.setContentView(R.layout.dialog_itens_faltantes);

        RecyclerView lista = (RecyclerView) itensFaltantes.findViewById(R.id.recyler_view_lista_grupo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new GrupoAdapter(itens, getApplicationContext());
        lista.setAdapter(adapter);

        Button btnContinuar = (Button) itensFaltantes.findViewById(R.id.btnContinuarFechamentoPedido);
        Button btnVoltar = (Button) itensFaltantes.findViewById(R.id.btnCancelarFechamentoPedido);

        btnContinuar.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                sugestaoJaValidada = true;

                finalizarPedido();
                itensFaltantes.dismiss();
            }
        });

        btnVoltar.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                sugestaoJaValidada = false;
                itensFaltantes.dismiss();
            }
        });

        itensFaltantes.show();
    }
}