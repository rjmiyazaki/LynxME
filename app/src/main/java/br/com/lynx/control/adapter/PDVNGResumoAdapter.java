package br.com.lynx.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.RecyclerViewOnClickListenerHack;
import br.com.lynx.control.RecyclerViewOnLongClickListernerHack;
import br.com.lynx.domain.Questao;

/**
 * Created by Rogerio on 31/07/2017.
 */

public class PDVNGResumoAdapter extends RecyclerView.Adapter<PDVNGResumoAdapter.MyViewHolder> {

    private List<Questao> lista;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
    private RecyclerViewOnLongClickListernerHack recyclerViewOnLongClickListernerHack;
    private Questao questao;

    public PDVNGResumoAdapter(Context c, List<Questao> lista) {
        this.lista = lista;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.row_avaliacao_pdvng_resumo, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        questao = lista.get(position);

        myViewHolder.txtPergunta.setText(lista.get(position).getPergunta());
        myViewHolder.txtResposta.setText(lista.get(position).getResposta());

        if (position % 2 == 0)
            myViewHolder.layout.setBackgroundColor(Color.LTGRAY);
        else
            myViewHolder.layout.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        recyclerViewOnClickListenerHack = r;
    }

    public void setRecyclerViewOnLongClickListernerHack(RecyclerViewOnLongClickListernerHack r){
        recyclerViewOnLongClickListernerHack = r;
    }


    public void addListItem(Questao c, int position) {
        lista.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position) {
        lista.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView txtPergunta;
        public TextView txtResposta;
        public RelativeLayout layout;


        public MyViewHolder(View itemView) {
            super(itemView);

            txtPergunta = (TextView) itemView.findViewById(R.id.rowAvaliacaoPDVNGResumo_Pergunta);
            txtResposta = (TextView) itemView.findViewById(R.id.rowAvaliacaoPDVNGResumo_Resposta);
            layout = (RelativeLayout)  itemView.findViewById(R.id.rowAvaliacaoPDVNGResumo);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerViewOnClickListenerHack != null) {
                recyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }


        @Override
        public boolean onLongClick(View v) {
            if (recyclerViewOnLongClickListernerHack != null){
                recyclerViewOnLongClickListernerHack.onLongClickListener(v, getPosition());
            }

            return true;
        }
    }
}
