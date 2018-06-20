package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.model.EquipamentoPesquisa;
import br.com.lynx.util.Command;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.domain.Cliente;

public class PesquisaEquipamentoActivity extends AppCompatActivity {

	private Cliente cliente;
	private List<PesquisaEquipamentoControle> controles;
	private int k;
	private Dialog dlgEnvioArquivo;
	private String messageError;
	private Handler handler;
	private Activity context;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pesquisaequipamento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.pesquisa_equipamento_toolbar);
        toolbar.setTitle("Pesquisa de equipamentos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		context = this;

		Intent intent = getIntent();
		cliente = new Cliente(this);
		int clienteID = intent.getIntExtra("ClienteID", 0);
		cliente.load(clienteID);
		
		handler = new Handler();

		controles = new ArrayList<PesquisaEquipamentoControle>();

		LinearLayout ll = (LinearLayout) findViewById(R.id.pesquisaequipamento);

		k = 0;
		for (int i = 0; i < cliente.getEquipamentosPesquisa().size(); i++) {
			TextView pergunta = new TextView(this);
			pergunta.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
			    LayoutParams.WRAP_CONTENT));
			pergunta.setId(k++);
			pergunta.setText(
			    "O equipamento " + cliente.getEquipamentosPesquisa().get(i).getGeko()
			        + " - " + cliente.getEquipamentosPesquisa().get(i).getLogomarca()
			        + " está presente?");
            pergunta.setTextColor(getResources().getColor(android.R.color.black));

			RadioButton radSim = new RadioButton(this);
			radSim.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
			    LayoutParams.WRAP_CONTENT));
			radSim.setId(k++);
			radSim.setText("Sim");
            radSim.setTextColor(getResources().getColor(android.R.color.black));

			RadioButton radNao = new RadioButton(this);
			radNao.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
			    LayoutParams.WRAP_CONTENT));
			radNao.setId(k++);
			radNao.setText("Não");
            radNao.setTextColor(getResources().getColor(android.R.color.black));

			RadioGroup radResposta = new RadioGroup(this);
			radResposta.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
			    LayoutParams.WRAP_CONTENT));
			radResposta.setId(k++);

			controles.add(new PesquisaEquipamentoControle(
			    cliente.getEquipamentosPesquisa().get(i).getGeko(), radSim.getId(),
			    radNao.getId()));

			radNao.setChecked(true);

			radResposta.addView(radSim);
			radResposta.addView(radNao);

			ll.addView(pergunta);
			ll.addView(radResposta);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_pesquisaequipamento, menu);

		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		    || (keyCode == KeyEvent.KEYCODE_HOME)) {
			if (existePerguntaEmAberto()) {
				MessageBox.show(this, "Pesquisa",
				    "Você deve confirmar todos os equipamentos.");

				return false;
			} else {
				MessageBox.show(this, "Pesquisa",
				    "Faça o envio do resultado da pesquisa.");

				return false;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_pesquisaequipamento_enviar:
			salvarPesquisa();
			return true;

		case R.id.menu_pesquisaequipamento_adiar:
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private Boolean salvarPesquisa() {
		RadioButton radSim;
		String resposta;

		if (existePerguntaEmAberto()) {
			MessageBox.show(this, "Pesquisa",
			    "Você deve confirmar todos os equipamentos.");

			return false;

		} else {

			for (PesquisaEquipamentoControle controle : controles) {
				radSim = (RadioButton) findViewById(controle.getradSim());

				if (radSim.isChecked())
					resposta = "Sim";
				else
					resposta = "Não";

				for (EquipamentoPesquisa equipamentoPesquisado : cliente
				    .getEquipamentosPesquisa()) {
					if (equipamentoPesquisado.getGeko() == controle.getGeko()) {
						equipamentoPesquisado.setPresente(resposta);
						equipamentoPesquisado.setData(new Date());
					}
				}
			}

			cliente.salvarPesquisa();

			dlgEnvioArquivo = ProgressDialog.show(this, "Envio",
			    "Salvando a pesquisa, aguarde...", false, true);
			enviarArquivo();

			finish();

			return true;
		}
	}

	private void enviarArquivo() {
		new Thread() {
			@Override
			public void run() {
				try {
					//CommUtil.enviarPesquisaEquipamento(context, activity_cliente_detalhe);

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
		              PesquisaEquipamentoActivity.this, "Comunicação",
		              "Erro ao enviar o pedido\r\n" + messageError, close);
							dialog.show();
						}
					});
				} finally {
				}
			}
		}.start();
	}

	private boolean existePerguntaEmAberto() {
		boolean retorno = false;

		for (PesquisaEquipamentoControle controle : controles) {
			RadioButton radSim = (RadioButton) findViewById(controle.getradSim());
			RadioButton radNao = (RadioButton) findViewById(controle.getradNao());

			if ((!radSim.isChecked()) && (!radNao.isChecked())) {
				retorno = true;
				break;
			}
		}

		return retorno;
	}
}