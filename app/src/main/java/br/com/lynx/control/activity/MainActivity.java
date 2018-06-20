package br.com.lynx.control.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.view.View;

import br.com.lynx.R;
import br.com.lynx.control.misc.ListaCampanha;
import br.com.lynx.control.misc.ListaPedidos;
import br.com.lynx.control.misc.OpcaoMenu;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.dao.PesquisaDAO;
import br.com.lynx.misc.CommService;
import br.com.lynx.misc.Command;
import br.com.lynx.misc.Communication;
import br.com.lynx.misc.MessageDlg;
import br.com.lynx.model.Configuracao;
import br.com.lynx.model.InfoPanel;
import br.com.lynx.model.ItemMenu;
import br.com.lynx.model.PesquisaCerveja;
import br.com.lynx.util.FTP;
import br.com.lynx.domain.Integracao;
import br.com.lynx.domain.Cliente;
import br.com.lynx.domain.Pedido;
import br.com.lynx.vo.RegistroNaoVendaVO;
import br.com.lynx.dao.ClienteDAO;
import br.com.lynx.misc.MessageBox;

import static br.com.lynx.util.LynxMEUtil.HorarioValido;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private RecyclerView recyclerView;
    private List<ItemMenu> list;

    private ArrayList<OpcaoMenu> opcoes;
    private Configuracao configuracao;
    private InfoPanel infoPanel;
    private ProgressDialog dialog;
    private Integracao integracao;
    private String eMessage;
    private Handler handler;
    private Activity context;
    private String messageError;

    private GridView gridview;

    private String[] labels = {
            "Clientes",
            "Envio",
            "Recebimentos",
            "Flash",
            "Pedidos"
    };

    private int[] images = {
            R.drawable.customers,
            R.drawable.upload,
            R.drawable.download,
            R.drawable.graph,
            R.drawable.lists,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        integracao = new Integracao(this);
        context = this;
        handler = new Handler();

        ajustaPainelInformacoes();

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainForm_Toolbar);
        toolbar.setTitle(getString(R.string.app_nome));
        toolbar.setSubtitle("Versão" + " " + getString(R.string.app_versao));
        setSupportActionBar(toolbar);

        context.startService(new Intent(context, CommService.class));


        gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new CustomAdapter(this, labels, images));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                avaliarOnItemClickListener(position);

            }
        });
    }

    private void avaliarOnItemClickListener(int position) {

        if (!validaHorario())
            return;

        switch (position){
            case 0:
                clientes();
                break;

            case 1:
                upload();
                break;

            case 2:
                download();
                break;

            case 3:
                metas();
                break;

            case 4:
                listaPedidos();
                break;
        }
    }

    private Boolean validaHorario(){
        Boolean result = false;
        String startTime = configuracao.getStartTime();
        String endTime = configuracao.getEndTime();

        if (!HorarioValido(startTime, endTime)){
            Command closeApp = new Command() {
                public void execute() {
                    finish();
                }
            };

            AlertDialog dialog = MessageDlg.createAlertDialog(
                    this,
                    "LynxME",
                    "Horário não permitido para acesso ao sistema.",
                    closeApp);

            dialog.show();
        } else
            result = true;

        return result;
    }

    @Override
    public void onClickListener(View view, int position) {
        if (position == 0)
            clientes();
        else if (position == 1)
            upload();
        else if (position == 2)
            download();
        else if (position == 3)
            metas();
        else if (position == 4)
            listaPedidos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private List<ItemMenu> getSetItemMenuList() {
        List<ItemMenu> listAux = new ArrayList<>();

        listAux.add(new ItemMenu(getString(R.string.item_menu_cliente), R.drawable.customers));
        listAux.add(new ItemMenu(getString(R.string.item_menu_upload), R.drawable.upload));
        listAux.add(new ItemMenu(getString(R.string.item_menu_download), R.drawable.download));
        listAux.add(new ItemMenu(getString(R.string.item_menu_flash), R.drawable.graph));
        listAux.add(new ItemMenu(getString(R.string.item_menu_pedidos), R.drawable.lists));

        return (listAux);
    }

    private void ajustaPainelInformacoes() {
        configuracao = Configuracao.getInstance(this);
        configuracao.load();

        infoPanel = InfoPanel.getInstance(this);
        infoPanel.load();

        ((TextView) findViewById(R.id.identificacaoVendedor)).setText(configuracao.getVendedorID() + " - " + configuracao.getNomeVendedor());

        ((TextView) findViewById(R.id.panel_info_cliente)).setText(infoPanel.getClientes());
        ((TextView) findViewById(R.id.panel_info_positivacao)).setText(infoPanel.getPositivacao());
        ((TextView) findViewById(R.id.panel_info_pedidos)).setText(infoPanel.getNumeroPedidos());
        ((TextView) findViewById(R.id.panel_info_aenviar)).setText(infoPanel.getNaoEnviados());
        ((TextView) findViewById(R.id.panel_info_caixas)).setText(infoPanel.getCaixas());
    }

    private void clientes() {
        startActivity(new Intent(this, ListaClientesActivity.class));
    }

    private void campanhas() {
        startActivity(new Intent(this, ListaCampanha.class));
    }

    private void listaPedidos() {
        startActivity(new Intent(this, ListaPedidos.class));
    }

    private void download() {
        dialog = ProgressDialog.show(this, "Download",
                "Fazendo download do arquivo e integrando tabelas, aguarde...", false,
                true);

        if (verificaPedidoAberto()) {
            dialog.dismiss();
            MessageBox.show(this, "LynxME",
                            "Você possui pedidos em aberto, favor envia-los antes de receber a nova base.");
        } else
            executeDownload();
    }

    private void upload() {
        dialog = ProgressDialog.show(this, "Integração",
                "Enviando arquivos de pedido e não venda, aguarde...", false, true);
        executeUpload();
    }

    private void metas() {
        startActivity(new Intent(this, FlashMainActivity.class));
    }

    private boolean verificaPedidoAberto() {

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

    @SuppressLint("MissingPermission")
    private String retornaIMEI(Context context) {
        String IMEI = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();

        return IMEI;
    }

    private void executeDownload() {
        new Thread() {
            @Override
            public void run() {
                String arquivoDestino, diretorioOrigem, arquivoOrigem;
                String caminho = Environment.getExternalStorageDirectory().toString();

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

                        if (!arquivoIntegracao.exists()){
                            Communication.executeDownload(diretorioOrigem, arquivoOrigem, arquivoIntegracao.toString(), "189.114.224.122", "cotenge", "abc123_", 21);
                        }

                        integracao.executar(Environment.getExternalStorageDirectory().toString(), "/lynxme.dat");


/*
                        if (!arquivoIntegracao.exists()) {
                            if (ftp.connect(configuracao.getftpHost(),
                                    configuracao.getftpUsuario(), configuracao.getftpSenha(), 21)) {
                                ftp.download(diretorioOrigem, arquivoOrigem, arquivo.toString());
                                ftp.disconnect();
                            }
                        }

                        integracao.executar(Environment.getExternalStorageDirectory()
                                .toString(), "/lynxme.dat");
*/

                        arquivoIntegracao.delete();

                        Communication.executeDownload("Gerencial", "PDVNG.dbi", "189.114.224.122", "cotenge", "abc123_", 21);
                        integracao.carregarArquivo(caminho, "/PDVNG.dbi");
                        integracao.integraPDVQuestionario();
                        integracao.integraPDVPergunta();

                        handler.post(new Runnable() {
                            public void run() {
                                ajustaPainelInformacoes();
                            }
                        });

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
                                MessageBox.show(context, "Arquivo não encontrado", eMessage);
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

                        e.printStackTrace();

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

    private void executeUpload() {
        new Thread() {
            @Override
            public void run() {
                try {
                    // Carrega a lista de pedidos
                    PedidoDAO pedidoDAO = new PedidoDAO(context);
                    List<Pedido> pedidos = pedidoDAO.listaPedidos();

                    for (Pedido pedido : pedidos)
                        Communication.enviarPedido(context, pedido);

                    // Carrega as respostas dos questionário
                    PesquisaDAO pesquisaDAO = new PesquisaDAO(context);
                    List<String> respostas = pesquisaDAO.listaRespostasParaExportacao();

                    if (respostas.size() > 0)
                        Communication.enviarTBPesquisa(context, respostas);

                    ClienteDAO clienteDAO = new ClienteDAO(context);
                    List<Cliente> clientes = clienteDAO.listaClientes();

                    for (Cliente cliente : clientes) {
                        if (cliente.getEquipamentosPesquisa().size() > 0)
                            Communication.enviarPesquisaEquipamento(context, cliente);

                        PesquisaCerveja pesquisaCerveja = cliente.retornaPesquisaCerveja();
                        if (pesquisaCerveja.getRespostas().size() > 0)
                            Communication.enviarPesquisaCerveja(context, pesquisaCerveja);
                    }


                    List<RegistroNaoVendaVO> naoVendas = pedidoDAO.listaNaoVenda();

                    try {
                        for (RegistroNaoVendaVO registro : naoVendas)
                            Communication.enviarNaoVenda(context, registro);
                    } catch (Exception e) {

                        messageError = e.getMessage();
                        handler.post(new Runnable() {
                            public void run() {
                                MessageBox.show(context, "Comunicação", messageError);
                            }
                        });

                    }


                } catch (IOException e) {
                    messageError = e.getMessage();
                    handler.post(new Runnable() {
                        public void run() {
                            MessageBox.show(context, "Comunicação", messageError);
                        }
                    });
                } finally {
                    dialog.dismiss();
                }
            }
        }.start();
    }
}