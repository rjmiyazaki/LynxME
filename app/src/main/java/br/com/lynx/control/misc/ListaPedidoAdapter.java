package br.com.lynx.control.misc;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.domain.Pedido;

public class ListaPedidoAdapter extends BaseAdapter {
	
	private Context context;
	private List<Pedido> pedidos;

	public ListaPedidoAdapter(Context context, List<Pedido> pedidos){
		this.context = context;
		this.pedidos = pedidos;
	}
	
	public int getCount(){
		return pedidos.size();
	}
	
	public Object getItem(int position){
		return pedidos.get(position);
	}
	
	public long getItemId(int position){
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		Pedido pedido = pedidos.get(position);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.listviewpedido, null);
		
		TextView txtOpcao = (TextView)view.findViewById(R.id.txtIdentificacao);
		TextView txtTipoPedido = (TextView)view.findViewById(R.id.txtTipoPedido);
		TextView txtValorPedido = (TextView)view.findViewById(R.id.txtValor);
		TextView txtSituacao = (TextView)view.findViewById(R.id.txtSituacao);
		
		txtOpcao.setText(String.valueOf(pedido.getCliente().getClienteID()) + " - " + pedido.getCliente().getRazaoSocial());
		txtTipoPedido.setText(pedido.getTipoPedido().getDescricao());
		txtValorPedido.setText(String.valueOf(pedido.getValorPedido()));
		txtSituacao.setText(String.valueOf(pedido.getSituacao()));
		
		return view;
	}
}
