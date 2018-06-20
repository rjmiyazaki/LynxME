package br.com.lynx.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.lynx.R;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.domain.Cliente;

public class ListaClientesAdapter extends RecyclerView.Adapter<ListaClientesAdapter.MyViewHolder> {

    private List<Cliente> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Cliente cliente;


    public ListaClientesAdapter(Context c, List<Cliente> l) {
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.listviewcliente, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        cliente = mList.get(position);

        myViewHolder.txtIdentificacao.setText(mList.get(position).getIdentificacao());
        myViewHolder.txtClassificacao.setText(mList.get(position).getClassificacao());
        myViewHolder.txtFantasia.setText(mList.get(position).getFantasia());
        myViewHolder.txtSituacao.setText(mList.get(position).getStatus());
        myViewHolder.txtTipo.setText(mList.get(position).getNaturezaJuridica());
        myViewHolder.txtFormaPagamento.setText(mList.get(position).getFormaPagamento().getDescricao());
        myViewHolder.txtTelefone.setText(mList.get(position).getFone());
        myViewHolder.txtEndereco.setText(mList.get(position).getEndereco());
        myViewHolder.txtBairro.setText(mList.get(position).getBairro());
        myViewHolder.txtCidade.setText(mList.get(position).getCidade());
        myViewHolder.txtComplementoPontoReferencia.setText(mList.get(position).getPontodeReferencia());

        if (cliente.getAtendido()) {
            myViewHolder.txtAtendido.setText("ATENDIDO");
            myViewHolder.txtAtendido.setTextColor(Color.BLACK);
        }
        else {
            myViewHolder.txtAtendido.setText("NÃO ATENDIDO");
            myViewHolder.txtAtendido.setTextColor(Color.RED);
        }

        if (cliente.isExibeMedalha()){
            myViewHolder.imgMedal.setVisibility(View.VISIBLE);
        }
        else {
            myViewHolder.imgMedal.setVisibility(View.INVISIBLE);
        }

        if (position % 2 == 0)
            myViewHolder.layout.setBackgroundColor(Color.LTGRAY);
        else
            myViewHolder.layout.setBackgroundColor(Color.WHITE);

        if (cliente.isPositivado()) {
            myViewHolder.txtIdentificacao.setTextColor(Color.BLUE);
            myViewHolder.txtClassificacao.setTextColor(Color.BLUE);
            myViewHolder.txtFantasia.setTextColor(Color.BLUE);
            myViewHolder.txtSituacao.setTextColor(Color.BLUE);
            myViewHolder.txtTipo.setTextColor(Color.BLUE);
            myViewHolder.txtFormaPagamento.setTextColor(Color.BLUE);
            myViewHolder.txtEndereco.setTextColor(Color.BLUE);
            myViewHolder.txtBairro.setTextColor(Color.BLUE);
            myViewHolder.txtCidade.setTextColor(Color.BLUE);
            myViewHolder.txtTelefone.setTextColor(Color.BLUE);
            myViewHolder.txtComplementoPontoReferencia.setTextColor(Color.BLUE);
            myViewHolder.txtAtendido.setTextColor(Color.BLUE);
        } else if (cliente.isNaoVenda()) {
            myViewHolder.txtIdentificacao.setTextColor(Color.MAGENTA);
            myViewHolder.txtClassificacao.setTextColor(Color.MAGENTA);
            myViewHolder.txtFantasia.setTextColor(Color.MAGENTA);
            myViewHolder.txtSituacao.setTextColor(Color.MAGENTA);
            myViewHolder.txtTipo.setTextColor(Color.MAGENTA);
            myViewHolder.txtFormaPagamento.setTextColor(Color.MAGENTA);
            myViewHolder.txtEndereco.setTextColor(Color.MAGENTA);
            myViewHolder.txtBairro.setTextColor(Color.MAGENTA);
            myViewHolder.txtCidade.setTextColor(Color.MAGENTA);
            myViewHolder.txtTelefone.setTextColor(Color.MAGENTA);
            myViewHolder.txtComplementoPontoReferencia.setTextColor(Color.MAGENTA);
            myViewHolder.txtAtendido.setTextColor(Color.MAGENTA);
        } else if (cliente.isVendaZero()) {
            myViewHolder.txtIdentificacao.setTextColor(Color.RED);
            myViewHolder.txtClassificacao.setTextColor(Color.RED);
            myViewHolder.txtFantasia.setTextColor(Color.RED);
            myViewHolder.txtSituacao.setTextColor(Color.RED);
            myViewHolder.txtTipo.setTextColor(Color.RED);
            myViewHolder.txtFormaPagamento.setTextColor(Color.RED);
            myViewHolder.txtEndereco.setTextColor(Color.RED);
            myViewHolder.txtBairro.setTextColor(Color.RED);
            myViewHolder.txtCidade.setTextColor(Color.RED);
            myViewHolder.txtTelefone.setTextColor(Color.RED);
            myViewHolder.txtComplementoPontoReferencia.setTextColor(Color.RED);
            myViewHolder.txtAtendido.setTextColor(Color.RED);
        } else {
            myViewHolder.txtIdentificacao.setTextColor(Color.BLACK);
            myViewHolder.txtClassificacao.setTextColor(Color.BLACK);
            myViewHolder.txtFantasia.setTextColor(Color.BLACK);
            myViewHolder.txtSituacao.setTextColor(Color.BLACK);
            myViewHolder.txtTipo.setTextColor(Color.BLACK);
            myViewHolder.txtFormaPagamento.setTextColor(Color.BLACK);
            myViewHolder.txtEndereco.setTextColor(Color.BLACK);
            myViewHolder.txtBairro.setTextColor(Color.BLACK);
            myViewHolder.txtCidade.setTextColor(Color.BLACK);
            myViewHolder.txtTelefone.setTextColor(Color.BLACK);
            myViewHolder.txtComplementoPontoReferencia.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(Cliente c, int position) {
        mList.add(c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtIdentificacao;
        public TextView txtFantasia;
        public TextView txtSituacao;
        public TextView txtTipo;
        public TextView txtTelefone;
        public TextView txtFormaPagamento;
        public TextView txtEndereco;
        public TextView txtBairro;
        public TextView txtCidade;
        public TextView txtComplementoPontoReferencia;
        public TextView txtClassificacao;
        public TextView txtAtendido;
        public RelativeLayout layout;
        public ImageView imgMedal;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtIdentificacao = (TextView) itemView.findViewById(R.id.idListaCliente_txtIdentificacao);
            txtClassificacao = (TextView) itemView.findViewById(R.id.idListaCliente_txtClassificacao);
            txtFantasia = (TextView) itemView.findViewById(R.id.idListaCliente_txtFantasia);
            txtSituacao = (TextView) itemView.findViewById(R.id.idListaCliente_txtSituacao);
            txtTipo = (TextView) itemView.findViewById(R.id.idListaCliente_txtTipo);
            txtTelefone = (TextView) itemView.findViewById(R.id.idListaCliente_txtTelefone);
            txtFormaPagamento = (TextView) itemView.findViewById(R.id.idListaCliente_txtFormaPagamento);
            txtEndereco = (TextView) itemView.findViewById(R.id.idListaCliente_txtEndereco);
            txtBairro = (TextView) itemView.findViewById(R.id.idListaCliente_txtBairro);
            txtCidade = (TextView) itemView.findViewById(R.id.idListaCliente_txtCidade);
            txtComplementoPontoReferencia = (TextView) itemView.findViewById(R.id.idListaCliente_txtComplementoPontoReferencia);
            txtAtendido = (TextView) itemView.findViewById(R.id.idListaCliente_txtAtendido);
            imgMedal = (ImageView) itemView.findViewById(R.id.imgMedal);
            layout = (RelativeLayout) itemView.findViewById(R.id.idListaCliente_layout);

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
