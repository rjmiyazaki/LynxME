package br.com.lynx.model;

import java.util.List;

import android.content.Context;
import br.com.lynx.dao.MotivoNaoVendaDAO;
import br.com.lynx.dao.TipoPedidoDAO;
import br.com.lynx.vo.MotivoNaoVendaVO;
import br.com.lynx.domain.TipoPedido;

public class Listas {
	
	private static TipoPedidoDAO daoTipoPedido;
	private static MotivoNaoVendaDAO daoMotivoNaoVenda;
	
	public static List<TipoPedido> GetListTipoPedido(Context context){
		List<TipoPedido> result;
		
		if (daoTipoPedido == null)
			daoTipoPedido = new TipoPedidoDAO(context);
		
		result = daoTipoPedido.getList();
		
		return result;
	}
	
	public static List<MotivoNaoVendaVO> GetListMotivoNaoVenda(Context context){
		List<MotivoNaoVendaVO> result;
		
		if (daoMotivoNaoVenda == null)
			daoMotivoNaoVenda = new MotivoNaoVendaDAO(context);
		
		result = daoMotivoNaoVenda.getList();
		
		return result;
	}
}
