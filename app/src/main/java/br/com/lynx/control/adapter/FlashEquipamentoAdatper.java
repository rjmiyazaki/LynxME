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
import br.com.lynx.model.FlashEquipamento;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashEquipamentoAdatper extends RecyclerView.Adapter<FlashEquipamentoAdatper.MyViewHolder> {

    private List<FlashEquipamento> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashEquipamentoAdatper(Context c, List<FlashEquipamento> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashEquipamentoAdatper.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_equipamento, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashEquipamentoAdatper.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("##0,00%", new DecimalFormatSymbols(local));

        holder.txtCategoria.setText(list.get(position).getTipo());
        holder.txtQuantidade.setText(toString().valueOf(list.get(position).getQuantidade()));
        holder.txtBatidos.setText(toString().valueOf(list.get(position).getMetaBatida()));
        holder.txtPercentual.setText(decimalFormat.format(list.get(position).getPercentual()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(FlashEquipamento c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtCategoria;
        public TextView txtQuantidade;
        public TextView txtBatidos;
        public TextView txtPercentual;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCategoria = (TextView) itemView.findViewById(R.id.flash_equipamento_categoria);
            txtQuantidade = (TextView) itemView.findViewById(R.id.flash_equipamento_quantidade);
            txtBatidos = (TextView) itemView.findViewById(R.id.flash_equipamento_batidos);
            txtPercentual = (TextView) itemView.findViewById(R.id.flash_equipamento_percentual);

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
