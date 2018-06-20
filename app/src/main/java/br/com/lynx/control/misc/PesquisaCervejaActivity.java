package br.com.lynx.control.misc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.lynx.R;
import br.com.lynx.model.PesquisaCerveja;
import br.com.lynx.model.RespostaCerveja;
import br.com.lynx.util.Command;
import br.com.lynx.util.MathUtil;
import br.com.lynx.misc.MessageBox;

/**
 * Created by Rogerio on 12/04/2016.
 */
public class PesquisaCervejaActivity extends AppCompatActivity {

    private PesquisaCerveja pesquisaCerveja;
    private Dialog dlgEnvioArquivo;
    private Activity context;
    private String messageError;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_cerveja);

        Toolbar toolbar = (Toolbar) findViewById(R.id.pesquisa_cerveja_toolbar);
        toolbar.setTitle("Pesquisa de cervejas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handler = new Handler();

        context = this;
        Intent intent = getIntent();
        int clienteID = intent.getIntExtra("ClienteID", 0);

        pesquisaCerveja = new PesquisaCerveja(this, clienteID);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_pesquisa_cerveja, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_preferences_confirmar:
                confirmar();
                finish();
                return true;

            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }

    private void confirmar() {
        float caixas, preco;
        RespostaCerveja resposta;

        // Kaiser
        EditText txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Kaiser_Caixa);
        EditText txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Kaiser_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Kaiser", caixas, preco));

        //Bohemia
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Bohemia_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Bohemia_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Bohemia", caixas, preco));

        //Skol
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Skol1L_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Skol1L_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Skol", caixas, preco));

        //Skol 600
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Skol600_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Skol600_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Skol 600", caixas, preco));

        //Brahma
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Brahma1L_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Brahma1L_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Brahma", caixas, preco));

        //Brahma 600
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Brahma600_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Brahma600_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Brahma 600", caixas, preco));

        //Antartica
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Antartica_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Antartica_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Antartica", caixas, preco));

        //Schin
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Schin_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Schin_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Schin", caixas, preco));

        //Bavaria
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Bavaria_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Bavaria_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Bavária", caixas, preco));

        //Original
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Original_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Original_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Original", caixas, preco));

        //Itaipava
        txtCaixa = (EditText) findViewById(R.id.pesquisaCerveja_Itaipava_Caixa);
        txtPreco = (EditText) findViewById(R.id.pesquisaCerveja_Itaipava_Preco);

        caixas = MathUtil.stringToFloat(txtCaixa.getText().toString());
        preco = MathUtil.stringToFloat(txtPreco.getText().toString());
        pesquisaCerveja.getRespostas().add(new RespostaCerveja("Itaipava", caixas, preco));

        pesquisaCerveja.save();

        dlgEnvioArquivo = ProgressDialog.show(this, "Aguarde", "Enviando pesquisa de cerveja...", false, true);
        enviarArquivo();
    }

    private void enviarArquivo() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //CommUtil.enviarPesquisaCerveja(context, pesquisaCerveja);

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
                                    PesquisaCervejaActivity.this, "Comunicação",
                                    "Erro ao enviar a pesquisa.\r\n" + messageError, close);
                            dialog.show();
                        }
                    });
                } finally {
                }
            }
        }.start();
    }
}
