package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import br.com.lynx.dao.HistoricoVendaDAO;
import br.com.lynx.vo.HistoricoVendaItemVO;

public class HistoricoVendaItem extends ListActivity{
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		List<HistoricoVendaItemVO> itens = new ArrayList<HistoricoVendaItemVO>();
		HistoricoVendaDAO historicoDAO = new HistoricoVendaDAO(this);
		
		itens = historicoDAO.retornaHistoricoVendaItem(intent.getIntExtra("PedidoID", 0));
		
		//setListAdapter(new HistoricoVendaItemAdapter(this, itens));
	}

}
