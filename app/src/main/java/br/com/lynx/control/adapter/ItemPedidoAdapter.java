package br.com.lynx.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import br.com.lynx.R;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.domain.PedidoItem;

/**
 * Created by rogerio on 27/10/2016.
 */

public class ItemPedidoAdapter extends RecyclerView.Adapter<ItemPedidoAdapter.MyViewHolder> {

    private List<PedidoItem> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public ItemPedidoAdapter(Context c, List<PedidoItem> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ItemPedidoAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.listview_row_itempedido, viewGroup, false);
        ItemPedidoAdapter.MyViewHolder mvh = new ItemPedidoAdapter.MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PedidoItem pedidoItem = mList.get(position);
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));

        holder.txtProduto.setText(pedidoItem.getItem().getDescricao());
        holder.txtValorUnitario.setText(decimalFormat.format(pedidoItem.getPrecoUnitario()));

        if (pedidoItem.getDesconto() > 0)
          holder.txtDesconto.setText(decimalFormat.format((pedidoItem.getDesconto() + pedidoItem.getDescontoAdicional()) * -1));
        else if (pedidoItem.getAcrescimo() > 0)
          holder.txtDesconto.setText(decimalFormat.format(pedidoItem.getAcrescimo()));
        else
          holder.txtDesconto.setText(decimalFormat.format(0));

        holder.txtQuantidade.setText(pedidoItem.getFormattedQuantidade());
        holder.txtValorLiquido.setText(decimalFormat.format(pedidoItem.getPrecoLiquido()));
        holder.txtValorTotal.setText(decimalFormat.format(pedidoItem.getValorTotal()));

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(Color.WHITE);
        else
            holder.layout.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addListItem(PedidoItem c, int position) {
        mList.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtProduto;
        TextView txtValorUnitario;
        TextView txtDesconto;
        TextView txtQuantidade;
        TextView txtValorLiquido;
        TextView txtValorTotal;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtProduto = (TextView) itemView.findViewById(R.id.idRowPedidoItem_Item);
            txtValorUnitario = (TextView) itemView.findViewById(R.id.idRowPedidoItem_ValorUnitario);
            txtDesconto = (TextView) itemView.findViewById(R.id.idRowPedidoItem_Desconto);
            txtQuantidade = (TextView) itemView.findViewById(R.id.idRowPedidoItem_Quantidade);
            txtValorLiquido = (TextView) itemView.findViewById(R.id.idRowPedidoItem_ValorLiquido);
            txtValorTotal = (TextView) itemView.findViewById(R.id.idRowPedidoItem_ValorTotal);
            layout = (LinearLayout) itemView.findViewById(R.id.idRowPedidoItem);

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
