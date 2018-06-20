package br.com.lynx.control.misc;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.domain.Produto;

public class PesquisaProdutoAdapter extends BaseAdapter {

	private List<Produto> produtos;
	private Context context;
	
	public PesquisaProdutoAdapter (Context context, List<Produto> produtos){
		this.context = context;
		this.produtos = produtos;
	}
	
	public int getCount() {
		return produtos.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {		
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Produto produto = produtos.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.listview_row_produto, null);
		
		TextView txtProdutoID = (TextView) view.findViewById(R.id.idRowProduto_ProdutoID);
		TextView txtProduto = (TextView) view.findViewById(R.id.idRowProduto_NomeProduto);
		TextView txtUnidade = (TextView) view.findViewById(R.id.idRowProduto_Unidade);
		TextView txtEstoque = (TextView)view.findViewById(R.id.idRowProduto_Estoque);
		
		txtProdutoID.setText(String.valueOf(produto.getProdutoID()));
		txtProduto.setText(produto.getDescricao());
		txtUnidade.setText(produto.getUnidade());
		txtEstoque.setText(String.valueOf(produto.getEstoque()));
		
		return view;
	}
}

