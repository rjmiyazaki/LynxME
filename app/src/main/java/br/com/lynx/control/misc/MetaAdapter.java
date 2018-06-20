package br.com.lynx.control.misc;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.vo.MetaVO;

public class MetaAdapter extends BaseAdapter{
	
	private Context context;
	private List<MetaVO> meta;
	
	public MetaAdapter(Context context, List<MetaVO> meta){
		
		this.context = context;
		this.meta = meta;
	}
	
	public int getCount(){
		return meta.size();
	}
	
	public Object getItem(int position){
		return position;
	}
	
	public long getItemId(int position){
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		int cor;
		Locale local = new Locale("pt", "BR");
		MetaVO metaVO = meta.get(position);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.listview_row_resultado, null);
		
		TextView txtCategoria = (TextView)view.findViewById(R.id.idRowResultado_NomeCategoria);
		TextView txtMeta = (TextView)view.findViewById(R.id.idRowResultado_Meta);
		TextView txtRealizado = (TextView)view.findViewById(R.id.idRowResultado_Realizado);
		TextView txtTendencia = (TextView)view.findViewById(R.id.idRowResultado_Tendencia);
		TextView txtResultado = (TextView)view.findViewById(R.id.idRowResultado_Percentual);
		
		DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));
		DecimalFormat percentFormat = new DecimalFormat("#,##0,00%", new DecimalFormatSymbols(local));
		
		txtCategoria.setText(String.valueOf(metaVO.getCategoria()));
		txtMeta.setText(decimalFormat.format(metaVO.getMeta()));
		txtRealizado.setText(decimalFormat.format(metaVO.getRealizado()));
		txtTendencia.setText(decimalFormat.format(metaVO.getTendencia()));
		txtResultado.setText(percentFormat.format(metaVO.getPorcentagem()));
		
		if (metaVO.getPorcentagem() < 0)
			cor = Color.RED;
		else
			cor = Color.BLACK;
		
		txtResultado.setTextColor(cor);
				
		return view;
	}

}
