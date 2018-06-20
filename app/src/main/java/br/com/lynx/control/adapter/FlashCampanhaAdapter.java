package br.com.lynx.control.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import br.com.lynx.R;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.model.FlashCampanha;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashCampanhaAdapter extends RecyclerView.Adapter<FlashCampanhaAdapter.MyViewHolder> {

    private List<FlashCampanha> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashCampanhaAdapter(Context c, List<FlashCampanha> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashCampanhaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_campanha, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashCampanhaAdapter.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("##0,00%", new DecimalFormatSymbols(local));

        holder.txtCategoria.setText(list.get(position).getCategoria());
        holder.txtMeta.setText(String.valueOf(list.get(position).getMeta()));
        holder.txtRealizado.setText(String.valueOf(list.get(position).getRealizado()));
        holder.txtFalta.setText(String.valueOf(list.get(position).getFalta()));
        holder.txtDiario.setText(String.valueOf(list.get(position).getDiario()));
        holder.txtPercentual.setText(decimalFormat.format(list.get(position).getPercentual()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(FlashCampanha c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtCategoria;
        public TextView txtMeta;
        public TextView txtRealizado;
        public TextView txtFalta;
        public TextView txtDiario;
        public TextView txtPercentual;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCategoria = (TextView) itemView.findViewById(R.id.flash_campanha_categoria);
            txtMeta = (TextView) itemView.findViewById(R.id.flash_campanha_meta);
            txtRealizado = (TextView) itemView.findViewById(R.id.flash_campanha_realizado);
            txtFalta = (TextView) itemView.findViewById(R.id.flash_campanha_falta);
            txtDiario = (TextView) itemView.findViewById(R.id.flash_campanha_diario);
            txtPercentual = (TextView) itemView.findViewById(R.id.flash_campanha_percentual);

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
