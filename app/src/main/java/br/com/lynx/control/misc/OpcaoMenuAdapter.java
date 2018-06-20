package br.com.lynx.control.misc;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.lynx.R;

public class OpcaoMenuAdapter extends BaseAdapter {
	private final Context context;
	private List<OpcaoMenu> itens;

	public OpcaoMenuAdapter(Context context, List<OpcaoMenu> itens) {
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
	

	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview_row_menu, null);
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.item_menu_image);
		TextView textView = (TextView) rowView.findViewById(R.id.item_menu_caption);
		
		imageView.setImageResource(itens.get(position).getImageIndex());
		textView.setText(itens.get(position).getCaption());
				
		return rowView;
	}
}
