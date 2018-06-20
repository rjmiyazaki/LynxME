package br.com.lynx.misc;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

import br.com.lynx.dao.ClienteDAO;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.dao.PesquisaGeralDAO;
import br.com.lynx.model.PesquisaCerveja;
import br.com.lynx.model.RespostaSubcanal;
import br.com.lynx.domain.Cliente;
import br.com.lynx.domain.Pedido;
import br.com.lynx.vo.RegistroNaoVendaVO;

/**
 * Created by Rogerio on 15/04/2016.
 */
public class CommService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    sendFiles();
                } catch (Exception e){}
            }
        }, 0, 300000);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void sendFiles() {
        Context context;

        context = getApplicationContext();

        // Faz o envio de pedidos
        PedidoDAO daoPedido = new PedidoDAO(context);

        for (Pedido pedido : daoPedido.retornaListaPedidoNaoTransmitidos()) {
            try {
                Communication.enviarPedido(context, pedido);
            } catch (Exception e) {

            }
        }

        // Faz o envio dos registros de não venda
        for (RegistroNaoVendaVO naoVenda : daoPedido.retornaListaNaoVendaNaoTransmitidos()) {
            try {
                Communication.enviarNaoVenda(context, naoVenda);
            } catch (Exception e) {
            }
        }


        // Faz o envio dos arquivos de pesquisa de subcanal
        PesquisaGeralDAO daoPesquisa = new PesquisaGeralDAO(context);
        for (RespostaSubcanal resposta : daoPesquisa.carregaListaRespostas()) {
            ClienteDAO daoCliente = new ClienteDAO(context);
            Cliente cliente = daoCliente.retornaCliente(resposta.getCliente());

            try {
                Communication.enviarPesquisaSubcanal(context, cliente, resposta.getSubcanal());
            }
            catch (Exception e) {
            }
        }

        // Faz o envio dos arquivos de pesquisa de equipamento
        for (Cliente cliente : daoPesquisa.carregaPesquisaEquipamentosNaoEnviado()){
            try {
                Communication.enviarPesquisaEquipamento(context, cliente);
            }
            catch (Exception e) {
            }
        }

        // Faz o envio dos arquivos de pesquisa de cerveja
        for (Cliente cliente : daoPesquisa.carregaPesquisaCervejaNaoEnviado()){
            try {
                PesquisaCerveja pesquisa = cliente.retornaPesquisaCerveja();
                Communication.enviarPesquisaCerveja(context, pesquisa);
            } catch (Exception e) {
            }
        }

        // TODO Faz o envio dos arquivos de pesquisa de trem bala
    }
}
