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
import br.com.lynx.model.FlashDevolucao;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashDevolucaoAdapter extends RecyclerView.Adapter<FlashDevolucaoAdapter.MyViewHolder> {

    private List<FlashDevolucao> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashDevolucaoAdapter(Context c, List<FlashDevolucao> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashDevolucaoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_devolucao, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashDevolucaoAdapter.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat percentFormat = new DecimalFormat("#,##0,00%", new DecimalFormatSymbols(local));
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        holder.txtMotivo.setText(list.get(position).getMotivo());
        holder.txtQuantidade.setText(String.valueOf(list.get(position).getQuantidade()));
        holder.txtCaixas.setText(decimalFormat.format(list.get(position).getCaixas()));
        holder.txtParticipacao.setText(percentFormat.format(list.get(position).getParticipacao()));

        if (list.get(position).getQuantidade() == 0)
            holder.txtQuantidade.setText("");

        if (list.get(position).getCaixas() == 0)
            holder.txtCaixas.setText("");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }


    public void addListItem(FlashDevolucao c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtMotivo;
        public TextView txtQuantidade;
        public TextView txtCaixas;
        public TextView txtParticipacao;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtMotivo = (TextView) itemView.findViewById(R.id.flash_devolucao_motivo);
            txtQuantidade = (TextView) itemView.findViewById(R.id.flash_devolucao_quantidade);
            txtCaixas = (TextView) itemView.findViewById(R.id.flash_devolucao_caixas);
            txtParticipacao = (TextView) itemView.findViewById(R.id.flash_devolucao_participacao);

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
