package br.com.lynx.control.misc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import br.com.lynx.R;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.misc.Communication;
import br.com.lynx.model.Configuracao;
import br.com.lynx.util.FTP;
import br.com.lynx.domain.Integracao;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.domain.Pedido;
import br.com.lynx.vo.RegistroNaoVendaVO;

public class Comunicacao extends ListActivity {

	private Configuracao configuracao;
	private Integracao integracao;
	private FTP ftp;
	private ProgressDialog dialog;
	private Activity context;
	private Handler handler;
	private String messageError;
	
	private String[] opcoes = new String[] { "Recebimento da base de trabalho", "Envio de pedidos", "Salvar os pedidos no cartÃ£o de memÃ³ria" };
	private int[] imagens = new int[] { R.drawable.download, R.drawable.upload, R.drawable.save };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;
		configuracao = Configuracao.getInstance(this);
		integracao = new Integracao(this);
		handler = new Handler();
	
		List<OpcaoMenu> opcoesMenu = new ArrayList<OpcaoMenu>();

		for (int i = 0; i < opcoes.length; i++) {
			//OpcaoMenu opcao = new OpcaoMenu(opcoes[i], imagens[i]);
			//opcoesMenu.add(opcao);
		}

		//setListAdapter(new OpcaoMenuAdapter(this, opcoesMenu));
		
		Configuracao configuracao = Configuracao.getInstance(this);
		configuracao.load();
		
		setTitle("Rota: " + configuracao.getVendedorID());	
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (position == 0)
			download();
		else if (position == 1)
			upload();
		else
			salvar();
	}

	private void download() {
		dialog = ProgressDialog.show(this, "IntegraÃ§Ã£o", "Baixando e integrando arquivos, aguarde...", false, true);

		if (verificaPedidoAberto()) {
			dialog.dismiss();
			MessageBox.show(this, "LynxME", "VocÃª possui pedidos em aberto, favor envia-los antes de receber a nova base");
		} else
			executeDownload();
	}

	private void upload() {
		dialog = ProgressDialog.show(this, "IntegraÃ§Ã£o", "Enviando arquivos de pedido e nÃ£o venda, aguarde...", false, true);
		executeUpload();
	}

	private void salvar() {
		dialog = ProgressDialog.show(this, "IntegraÃ§Ã£o", "Salvando os pedidos no cartÃ£o de memÃ³ria, aguarde...", false, true);
		executeSave();
	}

	private void executeDownload() {
		new Thread() {
			@Override
			public void run() {
				ftp = new FTP(); 
				try {
					ftp.connect(configuracao.getftpHost(), configuracao.getftpUsuario(), configuracao.getftpSenha(), 21);
					ftp.download(configuracao.getVendedorID(), configuracao.getVendedorID() + ".dbi", "/data/data/br.com.lynx/files/lynx.dbi");
					ftp.disconnect();
					
					integracao.executar("/data/data/br.com.lynx/files/", "lynx.dbi");
				} catch (SocketException e) {

					messageError = "NÃ£o foi possÃ­vel abrir uma conexÃ£o de internet." + e.getMessage();
					handler.post(new Runnable() {
						public void run() {
							MessageBox.show(context, "ComunicaÃ§Ã£o", messageError);
						}
					});

				} catch (Exception e) {

					messageError = e.getMessage();
					handler.post(new Runnable() {
						public void run() {
							MessageBox.show(context, "ComunicaÃ§Ã£o", messageError);
						}
					});

				} finally {
					dialog.dismiss();
				}
			}
		}.start();

	}

	private void executeUpload() {
		new Thread() {
			@Override
			public void run() {
				try {
					// Carrega a lista de pedidos
					PedidoDAO pedidoDAO = new PedidoDAO(context);
					List<Pedido> pedidos = pedidoDAO.listaPedidos();

					for (Pedido pedido : pedidos)
						//if (pedido.getSituacao().equals("Finalizado"))
							Communication.enviarPedido(context, pedido);

					List<RegistroNaoVendaVO> naoVendas = pedidoDAO.listaNaoVenda();

					try {
						for (RegistroNaoVendaVO registro : naoVendas)
							if (!registro.isEnviado())
								Communication.enviarNaoVenda(context, registro);
					} catch (Exception e) {

						messageError = e.getMessage();
						handler.post(new Runnable() {
							public void run() {
								MessageBox.show(context, "ComunicaÃ§Ã£o", messageError);
							}
						});

					}				
				} catch (IOException e) {
					messageError = e.getMessage();
					handler.post(new Runnable() {
						public void run() {
							MessageBox.show(context, "ComunicaÃ§Ã£o", messageError);
						}
					});
				} finally {
					dialog.dismiss();
					finish();
				}
			}
		}.start();
	}
	
	private boolean zipfile(String filename, String zipFile){
		try{
			final int BUFFER = 8192;
			
			BufferedInputStream origin = null;
			
			File outzip = new File(zipFile);
			if (outzip.exists())
				outzip.delete();
			
			FileOutputStream dest = new FileOutputStream(zipFile);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			
			byte data[] = new byte[BUFFER];
			String nomeArquivo = filename.substring(filename.lastIndexOf("/") + 1);
			FileInputStream fi = new FileInputStream(filename);
			
			origin = new BufferedInputStream(fi, BUFFER);
			
			ZipEntry entry = new ZipEntry(nomeArquivo);
			out.putNextEntry(entry);
		
			int count;
			
			while ((count = origin.read(data, 0, BUFFER)) != -1)
				out.write(data, 0, count);
			
			origin.close();
			fi.close();
			out.flush();
			out.finish();
			out.close();
		}
		catch (Exception ex) {
			return false;
		}
		
		return true;
	}

	private void executeSave() {
		new Thread() {
			public void run() {
				try {
					Communication.limpaPasta("/sdcard/LynxME/");
					// Carrega a lista de pedidos
					PedidoDAO pedidoDAO = new PedidoDAO(context);
					List<Pedido> pedidos = pedidoDAO.listaPedidos();

					for (Pedido pedido : pedidos)
						Communication.geraArquivo(context, pedido, "/sdcard/LynxME/");

					List<RegistroNaoVendaVO> naoVendas = pedidoDAO.listaNaoVenda();

					try {
						for (RegistroNaoVendaVO registro : naoVendas)
							Communication.geraArquivo(context, registro, "/sdcard/LynxME/");
					} catch (Exception e) {

						messageError = e.getMessage();
						handler.post(new Runnable() {
							public void run() {
								MessageBox.show(context, "ComunicaÃ§Ã£o", messageError);
							}
						});
					}
				} catch (IOException e) {
					messageError = e.getMessage();
					handler.post(new Runnable() {
						public void run() {
							MessageBox.show(context, "ComunicaÃ§Ã£o", messageError);
						}
					});
				} finally {
					dialog.dismiss();
					finish();
				}
			}
		}.start();
	}

	public boolean verificaPedidoAberto() {

		boolean pedidoAberto = false;
		PedidoDAO pedidoDAO = new PedidoDAO(context);
		List<Pedido> pedidos = pedidoDAO.listaPedidos();

		for (Pedido pedido : pedidos)
			if (pedido.getSituacao().equals("Finalizado")) {
				pedidoAberto = true;
				break;
			}

		return pedidoAberto;
	}

	public void onDestroy() {
		super.onDestroy();
	}
}