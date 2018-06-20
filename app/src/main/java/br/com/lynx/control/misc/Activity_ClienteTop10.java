package br.com.lynx.control.misc;

import br.com.lynx.R;
import br.com.lynx.domain.Cliente;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Activity_ClienteTop10 extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_top10);

		TextView txtVolumeAtual = (TextView) findViewById(R.id.idTop10_VolumeAtual);
		TextView txtVolumeAnterior = (TextView) findViewById(R.id.idTop10_VolumeAnterior);
		TextView txtVolumeDiferenca = (TextView) findViewById(R.id.idTop10_Diferenca);

		Intent intent = getIntent();
		int clienteID = intent.getIntExtra("ClienteID", 0);
		
		Cliente cliente = new Cliente(this);
		cliente.load(clienteID);
		
		txtVolumeAnterior.setText(String.valueOf(cliente.getVolumeTop10()));
		txtVolumeAtual.setText(String.valueOf(cliente.getVolumeAtual()));
		txtVolumeDiferenca.setText(String.valueOf(cliente.getVariacaoVolumeTop10()));
	}
}