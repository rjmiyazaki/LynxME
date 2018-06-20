package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.domain.PedidoItem;

public class DetalhePedidoAdapter extends BaseAdapter {

	private Context context;
	private List<PedidoItem> itens = new ArrayList<PedidoItem>();

	public DetalhePedidoAdapter(Context context, List<PedidoItem> itens) {

		this.context = context;
		this.itens = itens;
	}

	public int getCount() {

		return itens.size();
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		/*
		 * PedidoItem pedidoItem = itens.get(position);
		 * 
		 * LayoutInflater inflater = (LayoutInflater)
		 * context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view
		 * = inflater.inflate(R.layout.pedido_detalhe, null);
		 * 
		 * TextView item = (TextView)
		 * view.findViewById(R.idPedidoDetalhe.txtItem); TextView quantidade =
		 * (TextView) view.findViewById(R.idPedidoDetalhe.txtQuantidade);
		 * TextView valor = (TextView)
		 * view.findViewById(R.idPedidoDetalhe.txtValor); TextView repasse =
		 * (TextView) view.findViewById(R.idPedidoDetalhe.txtRepasse);
		 * 
		 * item.setText(pedidoItem.getItem().getProdutoID() + " - " +
		 * pedidoItem.getItem().getDescricao());
		 * quantidade.setText("Quantidade: " + pedidoItem.getQuantidade());
		 * valor.setText("Valor: R$" + pedidoItem.getValorTotal());
		 * if(pedidoItem.isRepasse()) repasse.setText("Repasse: Sim"); else
		 * repasse.setText("Repasse: Não");
		 * 
		 * return view;
		 */

		PedidoItem item = this.itens.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.listview_row_itemhistoricopedido,
				null);

		TextView txtItem = (TextView) view
				.findViewById(R.id.idRowHistoricoPedidoItem_Item);
		TextView txtQuantidade = (TextView) view
				.findViewById(R.id.idRowHistoricoPedidoItem_Quantidade);
		TextView txtValorUnitario = (TextView) view
				.findViewById(R.id.idRowHistoricoPedidoItem_ValorUnitario);
		TextView txtDesconto = (TextView) view
				.findViewById(R.id.idRowHistoricoPedidoItem_Desconto);
		TextView txtValorLiquido = (TextView) view
				.findViewById(R.id.idRowHistoricoPedidoItem_ValorLiquido);
		TextView txtValorTotal = (TextView) view
				.findViewById(R.id.idRowHistoricoPedidoItem_ValorTotal);

		txtItem.setText(item.getFormattedItem());
		txtQuantidade.setText(item.getFormattedQuantidade());
		txtValorUnitario.setText(item.getFormattedValorUnitario());
		txtDesconto.setText(item.getFormattedDesconto());
		txtValorLiquido.setText(item.getFormattedValorLiquido());
		txtValorTotal.setText(item.getFormattedValorTotal());
		
		return view;
	}

}