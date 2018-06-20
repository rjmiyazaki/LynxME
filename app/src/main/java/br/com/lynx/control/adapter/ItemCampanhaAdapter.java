package br.com.lynx.control.adapter;

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
import br.com.lynx.model.ItemCampanha;

/**
 * Created by Rogerio on 26/04/2016.
 */
public class ItemCampanhaAdapter extends RecyclerView.Adapter<ItemCampanhaAdapter.MyViewHolder> {

    private List<ItemCampanha> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public ItemCampanhaAdapter(Context c, List<ItemCampanha> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ItemCampanhaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listview_item_campanha, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ItemCampanhaAdapter.MyViewHolder holder, int position) {
        holder.txtCampanha.setText(list.get(position).getCampanha());
        holder.txtCategoria.setText(list.get(position).getCategoria());

        if (position % 2 == 0){
            holder.layout.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.layout.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(ItemCampanha c, int position) {
        list.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtCampanha;
        public TextView txtCategoria;
        public RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCampanha = (TextView) itemView.findViewById(R.id.cliente_campanha);
            txtCategoria = (TextView) itemView.findViewById(R.id.cliente_campanha_categoria);
            layout = (RelativeLayout) itemView.findViewById(R.id.cliente_campanha_layout);

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
