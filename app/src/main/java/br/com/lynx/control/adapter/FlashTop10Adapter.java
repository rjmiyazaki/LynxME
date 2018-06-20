package br.com.lynx.control.adapter;

import android.content.Context;
import android.graphics.Color;
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
import br.com.lynx.model.FlashTop10;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashTop10Adapter extends RecyclerView.Adapter<FlashTop10Adapter.MyViewHolder> {

    private List<FlashTop10> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashTop10Adapter(Context c, List<FlashTop10> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashTop10Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_top10, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashTop10Adapter.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        holder.txtCliente.setText(list.get(position).getCliente());
        holder.txtMesAtual.setText(decimalFormat.format(list.get(position).getMestAtual()));
        holder.txtTrimestre.setText(decimalFormat.format(list.get(position).getTrimestreAnterior()));
        holder.txtAno.setText(decimalFormat.format(list.get(position).getAnoAnterior()));

        if (list.get(position).getTrimestreAnterior() > list.get(position).getMestAtual()) {
            holder.txtTrimestre.setTextColor(Color.WHITE);
            holder.txtTrimestre.setBackgroundColor(Color.RED);
        }
        else{
            holder.txtTrimestre.setTextColor(Color.BLACK);
            holder.txtTrimestre.setBackgroundColor(Color.WHITE);
        }

        if (list.get(position).getAnoAnterior() > list.get(position).getMestAtual()) {
            holder.txtAno.setTextColor(Color.WHITE);
            holder.txtAno.setBackgroundColor(Color.RED);
        }
        else{
            holder.txtAno.setTextColor(Color.BLACK);
            holder.txtAno.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(FlashTop10 c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtCliente;
        public TextView txtMesAtual;
        public TextView txtTrimestre;
        public TextView txtAno;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCliente = (TextView) itemView.findViewById(R.id.flash_top10_cliente);
            txtMesAtual = (TextView) itemView.findViewById(R.id.flash_top10_valor_mesAtual);
            txtTrimestre = (TextView) itemView.findViewById(R.id.flash_top10_valor_trimestreAnterior);
            txtAno = (TextView) itemView.findViewById(R.id.flash_top10_valor_anoAnterior);

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
