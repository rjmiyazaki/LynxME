package br.com.lynx.control.misc;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.model.ItemHistoricoVenda;

public class HistoricoVendaItemAdapter extends BaseAdapter{

	private List<ItemHistoricoVenda> itens;
	private Context context;
	
	public HistoricoVendaItemAdapter(Context context, List<ItemHistoricoVenda> itens){
		
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

		ItemHistoricoVenda item = this.itens.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.listview_row_itemhistoricopedido, null);
		
		TextView txtItem = (TextView) view.findViewById(R.id.idRowHistoricoPedidoItem_Item);
		TextView txtQuantidade = (TextView) view.findViewById(R.id.idRowHistoricoPedidoItem_Quantidade);
		TextView txtValorUnitario = (TextView) view.findViewById(R.id.idRowHistoricoPedidoItem_ValorUnitario);
		TextView txtDesconto = (TextView) view.findViewById(R.id.idRowHistoricoPedidoItem_Desconto);
		TextView txtValorLiquido = (TextView) view.findViewById(R.id.idRowHistoricoPedidoItem_ValorLiquido);
		TextView txtValorTotal = (TextView) view.findViewById(R.id.idRowHistoricoPedidoItem_ValorTotal);
		
		txtItem.setText(item.getFormattedItem());
		txtQuantidade.setText(item.getFormattedQuantidade());
		txtValorUnitario.setText(item.getFormattedValorUnitario());
		txtDesconto.setText(item.getFormattedDesconto());
		txtValorLiquido.setText(item.getFormattedValorLiquido());
		txtValorTotal.setText(item.getFormattedValorTotal());
		
	  return view;
  }
}