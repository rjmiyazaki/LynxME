package br.com.lynx.control.adapter;

/**
 * Created by rogerio on 24/10/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import br.com.lynx.R;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.model.DivisaoPedidoInfo;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class DivisaoPedidoAdapter extends RecyclerView.Adapter<DivisaoPedidoAdapter.MyViewHolder> {

    private List<DivisaoPedidoInfo> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public DivisaoPedidoAdapter(Context c, List<DivisaoPedidoInfo> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public DivisaoPedidoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_row_pedidos_divisao, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(DivisaoPedidoAdapter.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        holder.txtDivisao.setText(list.get(position).getNome());
        holder.txtFaturamento.setText(decimalFormat.format(list.get(position).getFaturamento()));
        holder.txtVolume.setText(decimalFormat.format(list.get(position).getVolume()));
        holder.txtCobertura.setText(decimalFormat.format(list.get(position).getCobertura()));

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

    public void addListItem(DivisaoPedidoInfo c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtDivisao;
        public TextView txtFaturamento;
        public TextView txtVolume;
        public TextView txtCobertura;
        public RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtDivisao = (TextView) itemView.findViewById(R.id.pedido_divisao_nome);
            txtFaturamento = (TextView) itemView.findViewById(R.id.pedido_divisao_faturamento_valor);
            txtVolume = (TextView) itemView.findViewById(R.id.pedido_divisao_volume_valor);
            txtCobertura = (TextView) itemView.findViewById(R.id.pedido_divisao_cobertura_valor);
            layout = (RelativeLayout) itemView.findViewById(R.id.row_divisao);

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
