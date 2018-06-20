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
import br.com.lynx.model.FlashQueda;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashQuedasAdapter extends RecyclerView.Adapter<FlashQuedasAdapter.MyViewHolder> {

    private List<FlashQueda> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashQuedasAdapter(Context c, List<FlashQueda> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashQuedasAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_queda, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashQuedasAdapter.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        holder.txtCliente.setText(list.get(position).getCliente());
        holder.txtMesAtual.setText(decimalFormat.format(list.get(position).getMestAtual()));
        holder.txtQueda.setText(decimalFormat.format(list.get(position).getQueda()));
        holder.txtAno.setText(decimalFormat.format(list.get(position).getAnoAnterior()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(FlashQueda c, int position) {
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
        public TextView txtQueda;
        public TextView txtAno;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCliente = (TextView) itemView.findViewById(R.id.flash_queda_cliente);
            txtMesAtual = (TextView) itemView.findViewById(R.id.flash_queda_valor_mesAtual);
            txtAno = (TextView) itemView.findViewById(R.id.flash_queda_valor_anoAnterior);
            txtQueda = (TextView) itemView.findViewById(R.id.flash_queda_valor_queda);

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
