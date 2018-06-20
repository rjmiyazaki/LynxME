package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.vo.ResumoPedidoVO;
import br.com.lynx.R;

public class Activity_OrdersMain extends Activity {
	private GridView gridView;
	private GridViewAdapter customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordersmain);

		gridView = (GridView) findViewById(R.id.gridView);
		customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position,
			    long id) {
				Toast.makeText(getApplicationContext(),
				    ((TextView) v.findViewById(R.id.text)).getText(),
				    Toast.LENGTH_SHORT).show();
			}

		});
	}

	private ArrayList<ImageItem> getData() {
		final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();

		PedidoDAO pedido = new PedidoDAO(this);
		List<ResumoPedidoVO> itens = pedido.resumoPedido();

		for (ResumoPedidoVO item : itens) {

			imageItems.add(new ImageItem(BitmapFactory.decodeResource(
			    this.getResources(),
			    this.getResources().getIdentifier(item.getNomeImagem(), "drawable",
			        getPackageName())), item.getValor()));

		}

		return imageItems;
	}
}