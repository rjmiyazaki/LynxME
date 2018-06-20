package br.com.lynx.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
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
import br.com.lynx.model.FlashDevolucaoCliente;
import br.com.lynx.model.FlashTop10;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashUltimasDevolucoesAdapter extends RecyclerView.Adapter<FlashUltimasDevolucoesAdapter.MyViewHolder> {

    private List<FlashDevolucaoCliente> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashUltimasDevolucoesAdapter(Context c, List<FlashDevolucaoCliente> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashUltimasDevolucoesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_ultimas_devolucoes, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashUltimasDevolucoesAdapter.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        holder.txtCliente.setText(list.get(position).getCliente());
        holder.txtRota.setText(list.get(position).getRota());
        holder.txtMotivo.setText(list.get(position).getMotivo());
        holder.txtDataPedido.setText(DateFormat.format("dd/MM/yyyy", list.get(position).getDataPedido()));
        holder.txtDataDevolucao.setText(DateFormat.format("dd/MM/yyyy", list.get(position).getDataDevolucao()));
        holder.txtTipo.setText(list.get(position).getTipo());
        holder.txtCaixas.setText(decimalFormat.format(list.get(position).getCaixas()));
        holder.txtValor.setText(decimalFormat.format(list.get(position).getValor()));

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

    public void addListItem(FlashDevolucaoCliente c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtCliente;
        public TextView txtRota;
        public TextView txtMotivo;
        public TextView txtDataPedido;
        public TextView txtDataDevolucao;
        public TextView txtTipo;
        public TextView txtCaixas;
        public TextView txtValor;
        public RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCliente = (TextView) itemView.findViewById(R.id.flash_ultimasDevolucoes_cliente);
            txtRota = (TextView) itemView.findViewById(R.id.flash_ultimasDevolucoes_rota);
            txtMotivo = (TextView) itemView.findViewById(R.id.flash_ultimasDevolucoes_motivo);
            txtDataPedido = (TextView) itemView.findViewById(R.id.flash_ultimasDevolucoes_datapedido);
            txtDataDevolucao = (TextView) itemView.findViewById(R.id.flash_ultimasDevolucoes_datadevolucao);
            txtTipo = (TextView) itemView.findViewById(R.id.flash_ultimasDevolucoes_tipo);
            txtCaixas = (TextView) itemView.findViewById(R.id.flash_ultimasDevolucoes_caixas);
            txtValor = (TextView) itemView.findViewById(R.id.flash_ultimasDevolucoes_valor);
            layout = (RelativeLayout) itemView.findViewById(R.id.flash_ultimasDevolucoes_layout);

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
