package br.com.lynx.control.misc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.domain.PedidoItem;

public class PedidoItemAdapter extends BaseAdapter {
	private Context context;
	private List<PedidoItem> itens;

	public PedidoItemAdapter(Context context, List<PedidoItem> itens) {
		this.context = context;
		this.itens = itens;
	}

	public int getCount() {
		return itens.size();
	}

	public Object getItem(int position) {
		return itens.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PedidoItem pedidoItem = itens.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.listview_row_itempedido, null);

		TextView txtProduto = (TextView) view
				.findViewById(R.id.idRowPedidoItem_Item);
		TextView txtValorUnitario = (TextView) view
				.findViewById(R.id.idRowPedidoItem_ValorUnitario);
		TextView txtDesconto = (TextView) view
				.findViewById(R.id.idRowPedidoItem_Desconto);
		TextView txtQuantidade = (TextView) view
				.findViewById(R.id.idRowPedidoItem_Quantidade);
		TextView txtValorLiquido = (TextView) view
				.findViewById(R.id.idRowPedidoItem_ValorLiquido);
		TextView txtValorTotal = (TextView) view
				.findViewById(R.id.idRowPedidoItem_ValorTotal);

		NumberFormat currencyFormat = NumberFormat
				.getCurrencyInstance(new Locale("pt", "BR"));
		DecimalFormat percentFormat = new DecimalFormat("#0,00%");

		txtQuantidade.setText(String.valueOf(pedidoItem.getQuantidade()));
		txtProduto.setText(pedidoItem.getItem().getDescricao());
		txtValorUnitario.setText(currencyFormat.format(pedidoItem
				.getPrecoUnitario()));
		txtDesconto.setText(percentFormat.format(pedidoItem.getDesconto()));
		txtValorLiquido.setText(currencyFormat.format(pedidoItem
				.getPrecoLiquido()));
		txtValorTotal
				.setText(currencyFormat.format(pedidoItem.getValorTotal()));

		return view;
	}
}
