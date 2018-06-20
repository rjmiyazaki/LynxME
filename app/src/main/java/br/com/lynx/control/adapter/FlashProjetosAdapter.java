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
import br.com.lynx.model.FlashProjeto;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashProjetosAdapter extends RecyclerView.Adapter<FlashProjetosAdapter.MyViewHolder> {

    private List<FlashProjeto> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashProjetosAdapter(Context c, List<FlashProjeto> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashProjetosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_projetos, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashProjetosAdapter.MyViewHolder holder, int position) {
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        holder.txtCliente.setText(list.get(position).getCliente());
        holder.txtVolume.setText(decimalFormat.format(list.get(position).getVolume()));
        holder.txtAnoAnterior.setText(decimalFormat.format(list.get(position).getAnoAnterior()));
        holder.txtVariacao.setText(decimalFormat.format(list.get(position).getVariacao()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(FlashProjeto c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtCliente;
        public TextView txtVolume;
        public TextView txtAnoAnterior;
        public TextView txtVariacao;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCliente = (TextView) itemView.findViewById(R.id.flash_projeto_cliente);
            txtVolume = (TextView) itemView.findViewById(R.id.flash_projeto_valor_volume);
            txtAnoAnterior = (TextView) itemView.findViewById(R.id.flash_projeto_valor_antoAnterior);
            txtVariacao = (TextView) itemView.findViewById(R.id.flash_projeto_valor_variacao);

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
