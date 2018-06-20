package br.com.lynx.control.misc;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Spinner;

import br.com.lynx.R;
import br.com.lynx.dao.SubcanalDAO;
import br.com.lynx.misc.Communication;
import br.com.lynx.util.Command;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.domain.Cliente;
import br.com.lynx.vo.SubcanalVO;

public class PesquisaSubcanalActivity extends AppCompatActivity {

    private Cliente cliente;
    private RadioButton radSubcanalAtual;
    private RadioButton radSubcanalNovo;
    private Spinner sp;
    private ArrayList<SubcanalVO> subcanais;
    private ArrayList<String> listaSubcanais;
    private Dialog dlgEnvioArquivo;
    private String messageError;
    private Handler handler;
    private Activity context;
    private String subcanalResposta;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_subcanal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pesquisa_subcanal);
        toolbar.setTitle("Subcanal");
        setSupportActionBar(toolbar);

        context = this;
        Intent intent = getIntent();
        cliente = new Cliente(this);
        int clienteID = intent.getIntExtra("ClienteID", 0);
        cliente.load(clienteID);

        handler = new Handler();

        radSubcanalAtual = (RadioButton) findViewById(R.id.radConfirmaSubcanal);
        radSubcanalNovo = (RadioButton) findViewById(R.id.radNovoSubcanal);

        radSubcanalNovo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked)
                    sp.setVisibility(View.VISIBLE);
                else
                    sp.setVisibility(View.INVISIBLE);
            }
        });

        radSubcanalAtual.setText(cliente.getSubcanal());

        carregaSubcanais();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                listaSubcanais);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp = (Spinner) findViewById(R.id.spinner);
        sp.setAdapter(adapter);
        sp.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_pesquisa_subcanal, menu);

        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) || (keyCode == KeyEvent.KEYCODE_HOME)) {
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_pesquisa_subcanal_confirmar:
                confirmarSubcanal();
                return true;

            case R.id.menu_pesquisa_subcanal_cancelar:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void carregaSubcanais() {
        SubcanalDAO dao = new SubcanalDAO(this);

        subcanais = new ArrayList<SubcanalVO>();
        listaSubcanais = new ArrayList<String>();
        dao.carregaItensParaPesquisa(subcanais);

        for (SubcanalVO item : subcanais) {
            listaSubcanais.add(item.getDescricao());
        }
    }

    private void confirmarSubcanal() {
        if (radSubcanalAtual.isChecked()) {
            subcanalResposta = cliente.getSubcanal();
        } else {
            subcanalResposta = sp.getSelectedItem().toString();
        }

        cliente.confirmaSubcanal(subcanalResposta);

        //dlgEnvioArquivo = ProgressDialog.show(this, "Envio", "Enviando arquivo de pedido, aguarde...", false, true);
        //enviarArquivo();

        finish();
    }

    private void enviarArquivo() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Communication.enviarPesquisaSubcanal(context, cliente, subcanalResposta);

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
                                    PesquisaSubcanalActivity.this, "Comunicação",
                                    "Erro ao enviar o pedido\r\n" + messageError, close);
                            dialog.show();
                        }
                    });
                } finally {
                }
            }
        }.start();
    }

}
