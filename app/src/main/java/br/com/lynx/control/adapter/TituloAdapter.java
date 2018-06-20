package br.com.lynx.control.adapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.control.misc.RecyclerViewOnClickListenerHack;
import br.com.lynx.model.Titulo;

public class TituloAdapter extends RecyclerView.Adapter<TituloAdapter.MyViewHolder> {

	private List<Titulo> titulos = new ArrayList<Titulo>();
	private LayoutInflater layoutInflater;
	private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

	public TituloAdapter(Context context, List<Titulo> titulos) {
		this.titulos = titulos;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View v = layoutInflater.inflate(R.layout.listview_row_titulo, viewGroup, false);
		MyViewHolder mvh = new MyViewHolder(v);

		return mvh;
	}

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        myViewHolder.txtTipo.setText(titulos.get(position).getTipo());
        myViewHolder.txtNumero.setText(titulos.get(position).getNumero() + "/" + titulos.get(position).getParcela());
        myViewHolder.txtValorTitulo.setText(currencyFormat.format(titulos.get(position).getValor()));
        myViewHolder.txtSaldoTitulo.setText(currencyFormat.format(titulos.get(position).getValorAtual()));
        myViewHolder.txtDataEmissao.setText(DateFormat.format("dd/MM/yyyy", titulos.get(position).getDataEmissao()));
        myViewHolder.txtDataVencimento.setText(DateFormat.format("dd/MM/yyyy", titulos.get(position).getDataVencimento()));
        myViewHolder.txtDivisao.setText(titulos.get(position).getDivisao());
    }

    public int getItemCount() {
        return titulos.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

	public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		public TextView txtTipo;
		public TextView txtNumero;
		public TextView txtValorTitulo;
		public TextView txtSaldoTitulo;
		public TextView txtDataEmissao;
		public TextView txtDataVencimento;
		public TextView txtDivisao;

		public MyViewHolder(View itemView) {
			super(itemView);

			txtTipo = (TextView) itemView.findViewById(R.id.idRowTitulo_Tipo);
			txtNumero = (TextView) itemView.findViewById(R.id.idRowTitulo_Numero);
			txtValorTitulo = (TextView) itemView.findViewById(R.id.idRowTitulo_ValorTitulo);
			txtSaldoTitulo = (TextView) itemView.findViewById(R.id.idRowTitulo_SaldoTitulo);
			txtDataEmissao = (TextView) itemView.findViewById(R.id.idRowTitulo_DataEmissao);
			txtDataVencimento = (TextView) itemView.findViewById(R.id.idRowTitulo_DataVencimento);
			txtDivisao = (TextView) itemView.findViewById(R.id.idRowTitulo_Divisao);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if(mRecyclerViewOnClickListenerHack != null){
				mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
			}
		}
	}
}
