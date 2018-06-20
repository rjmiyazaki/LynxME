package br.com.lynx.control.misc;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import br.com.lynx.R;
import br.com.lynx.dao.RelatorioDAO;

public class ListaCampanha extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listacampanha);

		RelatorioDAO relatorioDAO = new RelatorioDAO(this);
		setListAdapter(new CampanhaAdapter(this, relatorioDAO.listaCampanha()));
	}

}
