package br.com.lynx.control.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.model.FlashCobertura;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class FlashCoberturaAdapter extends RecyclerView.Adapter<FlashCoberturaAdapter.MyViewHolder> {

    private List<FlashCobertura> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public FlashCoberturaAdapter(Context c, List<FlashCobertura> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlashCoberturaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_flash_cobertura, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FlashCoberturaAdapter.MyViewHolder holder, int position) {
        FlashCobertura itemCobertura = list.get(position);

        holder.txtTitulo.setText(list.get(position).getTitulo());
        holder.txtValor.setText(list.get(position).getValor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }


    public void addListItem(FlashCobertura c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtTitulo;
        public TextView txtValor;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView) itemView.findViewById(R.id.flash_cobertura_titulo);
            txtValor = (TextView) itemView.findViewById(R.id.flash_cobertura_valor);

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
