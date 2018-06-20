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
import br.com.lynx.domain.Produto;
import br.com.lynx.domain.Cliente;

/**
 * Created by rogerio on 27/10/2016.
 */

public class ListaProdutoAdapter extends RecyclerView.Adapter<ListaProdutoAdapter.MyViewHolder> {

    private List<Produto> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Cliente cliente;

    public ListaProdutoAdapter(Context c, List<Produto> l, Cliente cliente) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.cliente = cliente;
    }

    @Override
    public ListaProdutoAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.listview_row_lista_produto, viewGroup, false);
        ListaProdutoAdapter.MyViewHolder mvh = new ListaProdutoAdapter.MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Produto produto = mList.get(position);
        Locale local = new Locale("pt", "BR");
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(local));
        int corFundo, corTexto;

        holder.txtNome.setText(produto.getDescricao());
        holder.txtCodigo.setText(produto.getProdutoID());
        holder.txtUnidade.setText(produto.getUnidade());
        holder.txtEstoque.setText(String.valueOf(produto.getEstoque()));
        holder.txtPreco.setText(decimalFormat.format(produto.retornaValorPorTabela(cliente.getTabelaPadraoID(), cliente.getTabelaSecundariaID())));

        if (position % 2 == 0)
          corFundo = Color.LTGRAY;
        else
          corFundo = Color.WHITE;

        if (produto.getDestacado()){
            corTexto = Color.BLUE;
            corFundo = Color.YELLOW;
        }
        else {
            corTexto = Color.BLACK;
        }

        holder.layout.setBackgroundColor(corFundo);
        holder.txtNome.setTextColor(corTexto);
        holder.txtCodigo.setTextColor(corTexto);
        holder.txtUnidade.setTextColor(corTexto);
        holder.txtEstoque.setTextColor(corTexto);
        holder.txtPreco.setTextColor(corTexto);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addListItem(Produto c, int position) {
        mList.add(c);
        notifyItemInserted(position);
    }

    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtNome;
        public TextView txtCodigo;
        public TextView txtUnidade;
        public TextView txtEstoque;
        public TextView txtPreco;
        public RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtNome = (TextView) itemView.findViewById(R.id.lista_produto_nome);
            txtCodigo = (TextView) itemView.findViewById(R.id.lista_produto_info_codigo);
            txtUnidade = (TextView) itemView.findViewById(R.id.lista_produto_info_unidade);
            txtEstoque = (TextView) itemView.findViewById(R.id.lista_produto_info_estoque);
            txtPreco = (TextView) itemView.findViewById(R.id.lista_produto_info_preco);
            layout = (RelativeLayout) itemView.findViewById(R.id.lista_produto_layout);

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
