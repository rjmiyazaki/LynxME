package br.com.lynx.control.misc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.model.Devolucao;

public class DevolucaoAdapter extends BaseAdapter {

	private Context context;
	private List<Devolucao> devolucoes = new ArrayList<Devolucao>();

	public DevolucaoAdapter(Context context, List<Devolucao> devolucoes) {
		this.context = context;
		this.devolucoes = devolucoes;
	}

	public int getCount() {
		return devolucoes.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Devolucao devolucao = devolucoes.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview_row_devolucao, null);

		TextView txtProduto = (TextView) rowView.findViewById(R.id.idRowDevolucao_Produto);
		TextView txtData = (TextView) rowView.findViewById(R.id.idRowDevolucao_Data);
		TextView txtFaturada = (TextView) rowView.findViewById(R.id.idRowDevolucao_Faturado);
		TextView txtDevolvida = (TextView) rowView.findViewById(R.id.idRowDevolucao_Devolvido);
		TextView txtMotivo = (TextView) rowView.findViewById(R.id.idRowDevolucao_Motivo);
						
		txtProduto.setText(devolucao.getItem());
		txtData.setText(DateFormat.format("dd/MM/yyyy", devolucao.getData()));
		txtFaturada.setText(String.valueOf(devolucao.getDevolvida()));
		txtDevolvida.setText(String.valueOf(devolucao.getDevolvida()));
		txtMotivo.setText(devolucao.getMotivo());
		
		return rowView;
	}

}
