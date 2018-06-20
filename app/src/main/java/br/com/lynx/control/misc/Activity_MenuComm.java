package br.com.lynx.control.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.GridView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import br.com.lynx.model.Configuracao;
import br.com.lynx.util.FTP;
import br.com.lynx.domain.Integracao;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.R;

/**
 * 
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class Activity_MenuComm extends Activity {
	private GridView gridView;
	private GridViewAdapter customGridAdapter;
	private Configuracao configuracao;
	private ProgressDialog dialog;
	private Integracao integracao;
	private Activity context;
	private Handler handler;
	private String eMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_temp);

		context = this;
		handler = new Handler();

		configuracao = Configuracao.getInstance(this);
		configuracao.load();

		integracao = new Integracao(this);

		gridView = (GridView) findViewById(R.id.gridView);
		customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position,
			    long id) {
				if (position == 0)
					download();
				else if (position == 1)
					upload();
			}

		});
	}

	private ArrayList<ImageItem> getData() {
		final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();

		imageItems.add(new ImageItem(BitmapFactory.decodeResource(
		    this.getResources(),
		    this.getResources().getIdentifier("download", "drawable",
		        getPackageName())), "Receber"));
		imageItems.add(new ImageItem(BitmapFactory.decodeResource(
		    this.getResources(),
		    this.getResources().getIdentifier("upload", "drawable",
		        getPackageName())), "Enviar"));

		return imageItems;
	}

	private void download() {
		dialog = ProgressDialog.show(this, "Download",
		    "Fazendo download do arquivo e integrando tabelas, aguarde...", false,
		    true);

		/*
		 * if (verificaPedidoAberto()) { dialog.dismiss(); MessageBox.show(this,
		 * "LynxME",
		 * "VocÃª possui pedidos em aberto, favor envia-los antes de receber a nova base"
		 * ); } else
		 */

		executeDownload();
	}

	private void executeDownload() {
		new Thread() {
			@Override
			public void run() {
				String arquivoDestino, diretorioOrigem, arquivoOrigem;

				FTP ftp = new FTP();
				try {
					arquivoDestino = "/lynxme.dat";
					diretorioOrigem = "/" + configuracao.getVendedorID();
					arquivoOrigem = configuracao.getVendedorID() + ".dbi";

					File arquivo = new File(Environment.getExternalStorageDirectory(),
					    arquivoDestino);
					File arquivoIntegracao = new File(
					    Environment.getExternalStorageDirectory(), "lynxme.dat");
					
					if (arquivoIntegracao.exists())
						arquivoIntegracao.delete();
					  
					try {

						if (!arquivoIntegracao.exists()) {
							if (ftp.connect(configuracao.getftpHost(),
							    configuracao.getftpUsuario(), configuracao.getftpSenha(), 21)) {
								ftp.download(diretorioOrigem, arquivoOrigem, arquivo.toString());
								ftp.disconnect();
							}
						}

						integracao.executar(Environment.getExternalStorageDirectory()
						    .toString(), "/lynxme.dat");

						arquivoIntegracao.delete();

					} catch (SocketException e) {
						// TODO Auto-generated catch block

						eMessage = e.getMessage();
						handler.post(new Runnable() {
							public void run() {
								MessageBox.show(context, "Socket", eMessage);
							}
						});

						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						eMessage = e.getMessage();
						handler.post(new Runnable() {
							public void run() {
								MessageBox.show(context, "Arquivo nÃ£o encontrado", eMessage);
							}
						});

						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						eMessage = e.getMessage();
						handler.post(new Runnable() {
							public void run() {
								MessageBox.show(context, "I/O", eMessage);
							}
						});

						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						eMessage = e.getMessage();
						handler.post(new Runnable() {
							public void run() {
								MessageBox.show(context, "Exception", eMessage);
							}
						});

						e.printStackTrace();
					}
				} finally {
					dialog.dismiss();
				}
			}
		}.start();
	}

	private void upload() {

	}

}