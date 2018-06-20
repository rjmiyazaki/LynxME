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
import br.com.lynx.model.FlashPositivacao;
import br.com.lynx.model.FlashProjeto;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashPositivacaoAdapter extends RecyclerView.Adapter<FlashPositivacaoAdapter.MyViewHolder> {

    private List<FlashPositivacao> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashPositivacaoAdapter(Context c, List<FlashPositivacao> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashPositivacaoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_positivacao, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashPositivacaoAdapter.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("##0,00%", new DecimalFormatSymbols(local));

        holder.txtDia.setText(String.valueOf(list.get(position).getDia()));
        holder.txtDiaSemana.setText(list.get(position).getDiaSemana());
        holder.txtClientes.setText(String.valueOf(list.get(position).getClientes()));
        holder.txtPositivados.setText(String.valueOf(list.get(position).getPositivados()));
        holder.txtVendaZero.setText(String.valueOf(list.get(position).getVendaZero()));
        holder.txtPercentual.setText(decimalFormat.format(list.get(position).getPercentual()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(FlashPositivacao c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtDia;
        public TextView txtDiaSemana;
        public TextView txtClientes;
        public TextView txtPositivados;
        public TextView txtVendaZero;
        public TextView txtPercentual;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtDia = (TextView) itemView.findViewById(R.id.flash_positivacao_dia);
            txtDiaSemana = (TextView) itemView.findViewById(R.id.flash_positivacao_diaSemana);
            txtClientes = (TextView) itemView.findViewById(R.id.flash_positivacao_clientes);
            txtPositivados = (TextView) itemView.findViewById(R.id.flash_positivacao_positivados);
            txtVendaZero = (TextView) itemView.findViewById(R.id.flash_positivacao_vendaZero);
            txtPercentual = (TextView) itemView.findViewById(R.id.flash_positivacao_percentual);

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
