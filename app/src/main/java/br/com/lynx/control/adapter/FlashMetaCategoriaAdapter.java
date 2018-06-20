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
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.lynx.R;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.model.FlashMeta;

/**
 * Created by Rogerio on 22/04/2016.
 */
public class FlashMetaCategoriaAdapter extends RecyclerView.Adapter<FlashMetaCategoriaAdapter.MyViewHolder> {

    private List<FlashMeta> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private FlashMeta meta;
    private NumberFormat currencyFormat;
    private DecimalFormat percentFormat;
    private DecimalFormat decimalFormat;


    public FlashMetaCategoriaAdapter(Context c, List<FlashMeta> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Locale local = new Locale("pt", "BR");
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        percentFormat = new DecimalFormat("#,##0,00%", new DecimalFormatSymbols(local));
        decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.listview_flash_meta_por_categoria, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        meta = mList.get(position);

        myViewHolder.txtCategoria.setText(mList.get(position).getCategoria());
        myViewHolder.txtPercentual.setText(percentFormat.format(mList.get(position).getPercentual()));
        myViewHolder.txtMeta.setText(decimalFormat.format(mList.get(position).getMeta()));
        myViewHolder.txtRealizado.setText(decimalFormat.format(mList.get(position).getRealizado()));
        myViewHolder.txtFalta.setText(decimalFormat.format(mList.get(position).getFalta()));
        myViewHolder.txtTendencia.setText(decimalFormat.format(mList.get(position).getTendencia()));
        myViewHolder.txtMetaDiaria.setText(decimalFormat.format(mList.get(position).getMetaDiaria()));
        myViewHolder.txtVariavel.setText(currencyFormat.format(mList.get(position).getVariavel()));

        if (mList.get(position).getVariavel() > 0)
            myViewHolder.layout.setBackgroundColor(Color.CYAN);
        else
            myViewHolder.layout.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(FlashMeta c, int position) {
        mList.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtMeta;
        public TextView txtRealizado;
        public TextView txtFalta;
        public TextView txtTendencia;
        public TextView txtMetaDiaria;
        public TextView txtVariavel;
        public TextView txtPercentual;
        public TextView txtCategoria;
        public RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCategoria = (TextView) itemView.findViewById(R.id.flash_meta_categoria_label_categoria);
            txtMeta = (TextView) itemView.findViewById(R.id.flash_meta_categoria_prompt_meta);
            txtRealizado = (TextView) itemView.findViewById(R.id.flash_meta_categoria_prompt_realizado);
            txtFalta = (TextView) itemView.findViewById(R.id.flash_meta_categoria_prompt_falta);
            txtTendencia = (TextView) itemView.findViewById(R.id.flash_meta_categoria_prompt_tendencia);
            txtMetaDiaria = (TextView) itemView.findViewById(R.id.flash_meta_categoria_prompt_diaria);
            txtVariavel = (TextView) itemView.findViewById(R.id.flash_meta_categoria_prompt_variavel);
            txtPercentual = (TextView) itemView.findViewById(R.id.flash_meta_categoria_prompt_percentual);
            layout = (RelativeLayout) itemView.findViewById(R.id.flash_meta_categoria_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }
}
