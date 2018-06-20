package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.lynx.R;

public class LinhaAdapter extends RecyclerView.Adapter<LinhaAdapter.MyViewHolder> {

	private List<String> lista = new ArrayList<String>();
	private LayoutInflater layoutInflater;
	private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

	public LinhaAdapter(Context context, List<String> lista) {
		this.lista = lista;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View v = layoutInflater.inflate(R.layout.list_row_item_arrastao, viewGroup, false);
		MyViewHolder mvh = new MyViewHolder(v);

		return mvh;
	}

	@Override
	public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.txtLinha.setText(lista.get(position));
	}

    public int getItemCount() {
        return lista.size();
    }

	public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		public TextView txtLinha;

		public MyViewHolder(View itemView) {
			super(itemView);

			txtLinha = (TextView) itemView.findViewById(R.id.idRowFaltaCampanha_NomeLinha);

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
