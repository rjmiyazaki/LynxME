package br.com.lynx.control.misc;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.vo.ResumoPedidoVO;

public class ResumoPedidoAdapter extends BaseAdapter {
	
	private Context context;
	private List<ResumoPedidoVO> resumoPedido;
	
	public ResumoPedidoAdapter(Context context, List<ResumoPedidoVO> resumoPedido){
		this.context = context;
		this.resumoPedido = resumoPedido;
	}
	
	public int getCount(){
		return resumoPedido.size();
	}
	
	public Object getItem(int position){
		return resumoPedido.get(position);
	}
	
	public long getItemId(int position){
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		ResumoPedidoVO resumoPedidoVO = resumoPedido.get(position);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.activity_resumo, null);
		
		TextView txtInformacao = (TextView)view.findViewById(R.id.txtInformacao);
		TextView txtValor = (TextView)view.findViewById(R.id.txtValor);
		
		txtInformacao.setText(resumoPedidoVO.getDescricao());
		txtValor.setText(String.valueOf(resumoPedidoVO.getValor()));

		return view;
	}

}
