package br.com.lynx.control.adapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.model.DivisaoPedidoInfo;
import br.com.lynx.model.Equipamento;

public class EquipamentoAdapter extends RecyclerView.Adapter<EquipamentoAdapter.MyViewHolder>  {

	private List<Equipamento> list;
	private LayoutInflater layoutInflater;
	private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

	public EquipamentoAdapter(Context c, List<Equipamento> l) {
		list = l;
		layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public EquipamentoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = layoutInflater.inflate(R.layout.listview_row_equipamento, parent, false);
        EquipamentoAdapter.MyViewHolder mvh = new EquipamentoAdapter.MyViewHolder(v);
		return mvh;
	}

	@Override
	public void onBindViewHolder(EquipamentoAdapter.MyViewHolder holder, int position) {
		Locale local = new Locale("pt", "BR");
		DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

		holder.txtModelo.setText(list.get(position).getModelo());
		holder.txtGeko.setText(list.get(position).getGeko());
        holder.txtLogomarca.setText(list.get(position).getLogomarca());
        holder.txtVoltagem.setText(String.valueOf(list.get(position).getVoltagem()));
		holder.txtMeta.setText(decimalFormat.format(list.get(position).getMeta()));
		holder.txtRealizado.setText(decimalFormat.format(list.get(position).getRealizado()));
        holder.txtDiferenca.setText(decimalFormat.format(list.get(position).getDiferenca()));

		if (position % 2 == 0)
			holder.layout.setBackgroundColor(Color.LTGRAY);
		else
			holder.layout.setBackgroundColor(Color.WHITE);
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
		recyclerViewOnClickListenerHack = r;
	}

	public void addListItem(Equipamento c, int position) {
		list.add(c);
		notifyItemInserted(position);
	}

	public void removeListItem(int position) {
		list.remove(position);
		notifyItemRemoved(position);
	}

	public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public TextView txtModelo;
		public TextView txtGeko;
		public TextView txtLogomarca;
		public TextView txtVoltagem;
		public TextView txtMeta;
		public TextView txtRealizado;
		public TextView txtDiferenca;
		public RelativeLayout layout;

		public MyViewHolder(View itemView) {
			super(itemView);

			txtModelo = (TextView) itemView.findViewById(R.id.idRowEquipamento_Modelo);
			txtGeko = (TextView) itemView.findViewById(R.id.idRowEquipamento_Geko);
			txtLogomarca = (TextView) itemView.findViewById(R.id.idRowEquipamento_Logomarca);
			txtVoltagem = (TextView) itemView.findViewById(R.id.idRowEquipamento_Voltagem);
			txtMeta = (TextView) itemView.findViewById(R.id.idRowEquipamento_Meta);
			txtRealizado = (TextView) itemView.findViewById(R.id.idRowEquipamento_Realizado);
			txtDiferenca = (TextView) itemView.findViewById(R.id.idRowEquipamento_Diferenca);
			layout = (RelativeLayout) itemView.findViewById(R.id.idRowEquipamento);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (recyclerViewOnClickListenerHack != null) {
				recyclerViewOnClickListenerHack.onClickListener(v, getPosition());
			}
		}
	}
}
