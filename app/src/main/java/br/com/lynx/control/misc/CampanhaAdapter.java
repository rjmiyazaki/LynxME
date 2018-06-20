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
import br.com.lynx.model.Campanha;

public class CampanhaAdapter extends BaseAdapter {

	private Context context;
	private List<Campanha> campanhas = new ArrayList<Campanha>();

	public CampanhaAdapter(Context context, List<Campanha> campanhas) {
		this.context = context;
		this.campanhas = campanhas;
	}

	public int getCount() {
		return campanhas.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Campanha campanha = campanhas.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview_row_campanha, null);

		TextView txtLinha = (TextView) rowView.findViewById(R.id.idRowCampanha_NomeLinha);
		TextView txtMeta = (TextView) rowView.findViewById(R.id.idRowCampanha_Meta);
		TextView txtRealizado = (TextView) rowView.findViewById(R.id.idRowCampanha_Realizado);
		TextView txtFaltante = (TextView) rowView.findViewById(R.id.idRowCampanha_Faltantes);
								
		txtLinha.setText(campanha.getLinha());		
		txtMeta.setText(String.valueOf(campanha.getMeta()));
		txtRealizado.setText(String.valueOf(campanha.getRealizado()));
		txtFaltante.setText(String.valueOf(campanha.getFaltantes()));
		
		return rowView;
	}

}
