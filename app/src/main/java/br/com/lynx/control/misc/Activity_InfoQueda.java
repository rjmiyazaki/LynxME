package br.com.lynx.control.misc;

import br.com.lynx.R;
import br.com.lynx.domain.Cliente;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Activity_InfoQueda extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_infoqueda);

		TextView txtVolumeAtual = (TextView) findViewById(R.id.idInfoQueda_VolumeAtual);
		TextView txtVolumeAnterior = (TextView) findViewById(R.id.idInfoQueda_VolumeAnterior);
		TextView txtVolumeDiferenca = (TextView) findViewById(R.id.idInfoQueda_Diferenca);

		Intent intent = getIntent();
		int clienteID = intent.getIntExtra("ClienteID", 0);
		
		Cliente cliente = new Cliente(this);
		cliente.load(clienteID);
		
		txtVolumeAnterior.setText(String.valueOf(cliente.getVolumeAnterior()));
		txtVolumeAtual.setText(String.valueOf(cliente.getVolumeAtual()));
		txtVolumeDiferenca.setText(String.valueOf(cliente.getVariacao()));
	}

}
