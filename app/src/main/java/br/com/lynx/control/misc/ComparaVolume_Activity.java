package br.com.lynx.control.misc;

import br.com.lynx.R;
import br.com.lynx.domain.Cliente;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ComparaVolume_Activity extends Activity {

	private Cliente cliente;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comparavolume);

		Intent intent = getIntent();
		int clienteID = intent.getIntExtra("ClienteID", 0);

		cliente = new Cliente(this);
		cliente.load(clienteID);

		RelativeLayout quedasLayout = (RelativeLayout) findViewById(R.id.idComparaVolume_MaioresQuedasLayout);
		RelativeLayout top10Layout = (RelativeLayout) findViewById(R.id.idComparaVolume_Top10Layout);
		
		TextView txtVolumeAnterior = (TextView) findViewById(R.id.idComparaVolume_VolumeAnterior);
		TextView txtVolumeAtual = (TextView) findViewById(R.id.idComparaVolume_VolumeAtual);
		TextView txtDiferenca = (TextView) findViewById(R.id.idComparaVolume_Diferenca);
		
		TextView txtVolumeAnteriorTop10 = (TextView) findViewById(R.id.idComparaVolume_VolumeAnteriorTop10);
		TextView txtVolumeAtualTop10 = (TextView) findViewById(R.id.idComparaVolume_VolumeAtualTop10);
		TextView txtDiferencaTop10 = (TextView) findViewById(R.id.idComparaVolume_DiferencaTop10);


		if (cliente.getVariacao() >= 0)
			quedasLayout.setVisibility(View.GONE);
		else {
			txtVolumeAnterior.setText(String.valueOf(cliente.getVolumeAnterior()));
			txtVolumeAtual.setText(String.valueOf(cliente.getVolumeAtual()));
			txtDiferenca.setText(String.valueOf(cliente.getVariacao()));
		}

		if (cliente.getVolumeTop10() <= 0)
			top10Layout.setVisibility(View.GONE);
		else {
			txtVolumeAnteriorTop10.setText(String.valueOf(cliente.getVolumeTop10()));
			txtVolumeAtualTop10.setText(String.valueOf(cliente.getVolumeAtual()));
			txtDiferencaTop10.setText(String.valueOf(cliente.getVariacaoVolumeTop10()));
		}

		exibirInformacoes();
	}

	private void exibirInformacoes() {
		TextView txtIdentificacao = (TextView) findViewById(R.id.idComparaVolume_Cliente);
		TextView txtSubcanal = (TextView) findViewById(R.id.idComparaVolume_Subcanal);

		txtIdentificacao.setText(String.valueOf(cliente.getClienteID()) + " - "
		    + cliente.getRazaoSocial());
		txtSubcanal.setText(cliente.getSubcanal());
	}

}
