package br.com.lynx.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.lynx.R;
import br.com.lynx.control.adapter.EquipamentoAdapter;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.domain.Cliente;

public class EquipamentoActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

	private Toolbar toolbar;
	private Cliente cliente;
	private RecyclerView recyclerView;
    private EquipamentoAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipamentos);

		toolbar = (Toolbar) findViewById(R.id.listaEquipamentos_Toolbar);
		toolbar.setTitle("Lista de equipamentos");
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		recyclerView = (RecyclerView) findViewById(R.id.recylerview_equipamentos);
		recyclerView.setHasFixedSize(true);

		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(llm);

		Intent intent = getIntent();
		cliente = new Cliente(this);
		int clienteID = intent.getIntExtra("ClienteID", 0);
		cliente.load(clienteID);

        adapter = new EquipamentoAdapter(this, cliente.getEquipamentos());
        adapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListener(View view, int position) {

    }
	
	/*
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			MessageBox.show(this, "LynxME", "Informe todos os GEKOs e clique em finalizar.");
			return false;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	public void save(View v) {

		equipamento.setGeko(edtGeko.getText().toString());
		equipamentoDAO.save(equipamento);

		edtGeko.setText("");

		loadEquipamentos();
	}

	public void loadEquipamentos() {

		equipamentos = equipamentoDAO.carregaEquipamentos(intent.getIntExtra("ClienteID", 0));
		setListAdapter(new EquipamentoAdapter(this, equipamentos));
	}

	public void reloadEquipamentos() {

		equipamentos = equipamentoDAO.carregaEquipamentos(intent.getIntExtra("ClienteID", 0));
		EquipamentoAdapter adapter = new EquipamentoAdapter(EquipamentoActivity.this, equipamentos);

		adapter.notifyDataSetChanged();
		setListAdapter(adapter);
	}

	protected void onLongListItemClick(AdapterView<?> l, View v, final int position, long id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setItems(new CharSequence[] { "Remover" }, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				equipamentoDAO.delete(equipamentos.get(position));
				reloadEquipamentos();

			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private void vibrar() {
		
		Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long milliseconds = 30;
		rr.vibrate(milliseconds);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, 1, 1, "Finalizar");

		return true;
	}
	
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		dlEquipamento = ProgressDialog.show(EquipamentoActivity.this, "Envio", "Enviando arquivo de equipamentos, aguarde...", false, true);
		finalizarEquipamentos();
		return false;
	}
	
	public void finalizarEquipamentos() {

		new Thread() {

			public void run() {

				try {

					CommUtil.enviarEquipamento(EquipamentoActivity.this, equipamentos);
					dlEquipamento.dismiss();
					finish();
				} catch (IOException e) {
					messageError = e.getMessage();
					handler.post(new Runnable() {
						public void run() {
							Command close = new Command() {
								public void execute() {
									finish();
								}
							};
							AlertDialog dialog = MessageBox.createAlertDialog(EquipamentoActivity.this, "ComunicaÃ§Ã£o", "Erro ao enviar o pedido\r\n" + messageError, close);
							dialog.show();
						}
					});
				} finally {
				}
			}
		}.start();
	}
	*/
}