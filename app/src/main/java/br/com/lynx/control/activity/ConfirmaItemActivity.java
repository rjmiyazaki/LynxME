package br.com.lynx.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import br.com.lynx.R;
import br.com.lynx.dao.ClienteDAO;
import br.com.lynx.dao.ProdutoDAO;
import br.com.lynx.domain.Produto;
import br.com.lynx.model.TabelaPreco;
import br.com.lynx.util.MathUtil;
import br.com.lynx.domain.Cliente;

/**
 * Created by Rogerio on 17/11/2016.
 */

public class ConfirmaItemActivity extends AppCompatActivity {

    private String produtoID;
    private Produto produto;
    private ProdutoDAO daoProduto;
    private ClienteDAO daoCliente;
    private int clienteID;
    private Cliente cliente;

    private TextView txtProdutoID;
    private TextView txtProduto;
    private TextView txtEstoque;
    private TextView txtValorUnitario;
    private TextView txtUnidade;
    private TextView txtSubtotal;
    private EditText edtDesconto;
    private EditText edtQuantidade;

    private Spinner spnTabelaPreco;
    private Spinner spnTipoDesconto;
    private List<TabelaPreco> tabelasPreco;
    private DecimalFormat numberFormat;
    private DecimalFormat currencyFormat;

    private String[] tiposDesconto = new String[]{"Por percentagem",
            "Por valor", "Acréscimo"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_confirma_item);
        toolbar.setTitle("Confirmação de item");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        produtoID = intent.getStringExtra("ProdutoID");
        clienteID = intent.getIntExtra("ClienteID", 0);

        produto = new Produto(this);
        produto.setProdutoID(produtoID);
        produto.load();

        cliente = new Cliente(this);
        cliente.load(clienteID);

        txtProdutoID = (TextView) findViewById(R.id.txt_confirma_item_codigo_info);
        txtProduto = (TextView) findViewById(R.id.txt_confirma_item_descricao_info);
        txtEstoque = (TextView) findViewById(R.id.txt_confirma_item_estoque_info);
        txtValorUnitario = (TextView) findViewById(R.id.txt_confirma_item_valor_unitario_info);
        txtUnidade = (TextView) findViewById(R.id.txt_confirma_item_unidade_info);
        spnTabelaPreco = (Spinner) findViewById(R.id.spn_confirma_item_tabelapreco_info);
        spnTipoDesconto = (Spinner) findViewById(R.id.spn_confirma_item_tipodesconto);
        edtDesconto = (EditText) findViewById(R.id.txt_confirma_item_desconto_info);
        edtQuantidade = (EditText) findViewById(R.id.txt_confirma_item_quantidade_info);
        txtSubtotal = (TextView) findViewById(R.id.txt_confirma_item_subtotal_info);

        Locale local = new Locale("pt", "BR");
        numberFormat = new DecimalFormat("###,##0", new DecimalFormatSymbols(local));
        currencyFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        txtProdutoID.setText(produtoID);
        txtProduto.setText(produto.getDescricao());
        txtEstoque.setText(numberFormat.format(produto.getEstoque()));
        txtUnidade.setText(produto.getUnidade());

        tabelasPreco = cliente.tabelasDisponiveis(produto);
        String[] tabelas = new String[tabelasPreco.size()];

        for (int i = 0; i < tabelasPreco.size(); i++)
            tabelas[i] = tabelasPreco.get(i).getDescricao();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_tabelapreco, tabelas);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        spnTabelaPreco.setAdapter(adapter);

        spnTabelaPreco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtValorUnitario.setText(currencyFormat.format(tabelasPreco.get(position).getValor()));
                ajustaValores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> tipoDescontoAdapter = new ArrayAdapter<String>(this, R.layout.spinner_tabelapreco, tiposDesconto);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spnTipoDesconto.setAdapter(tipoDescontoAdapter);

        spnTipoDesconto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtDesconto.setText("");
                ajustaValores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtQuantidade.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    ajustaValores();
                    return true;
                }
                return false;
            }
        });

        edtDesconto.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    ajustaValores();
                    return true;
                }
                return false;
            }
        });
    }

    private void ajustaValores(){
        float valorUnitario, quantidade, desconto, valorDesconto, subtotal;

        Locale local = new Locale("pt", "BR");
        currencyFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        if (txtValorUnitario.getText().toString().isEmpty())
            valorUnitario = 0;
        else
            valorUnitario = MathUtil.stringToFloat(txtValorUnitario.getText().toString());

        if (edtQuantidade.getText().toString().isEmpty())
            quantidade = 0;
        else
            quantidade = MathUtil.stringToFloat(edtQuantidade.getText().toString());

        subtotal = valorUnitario * quantidade;

        if (edtDesconto.getText().toString().isEmpty())
            desconto = 0;
        else
            desconto = MathUtil.stringToFloat(edtDesconto.getText().toString());

        if (spnTipoDesconto.getSelectedItemPosition() == 0) { // Desconto por Percentagem
            desconto = valorUnitario * desconto / 100;
            subtotal = (valorUnitario - desconto) * quantidade;
        }
        else if (spnTipoDesconto.getSelectedItemPosition() == 1) { // Desconto por valor
            subtotal = (valorUnitario - desconto) * quantidade;
        }
        else if (spnTipoDesconto.getSelectedItemPosition() == 2) { // Acréscimo
            subtotal = (valorUnitario + desconto) * quantidade;
        }

        txtSubtotal.setText(currencyFormat.format(subtotal));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_confirma_item, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            /*case R.id.menu_login_atualizar:
                atualizar();
                return true;

            case R.id.menu_login_inicializar:
                inicializar();
                return true;

            case R.id.menu_login_preferencias:
                preferencias();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
