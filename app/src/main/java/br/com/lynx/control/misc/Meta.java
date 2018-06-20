package br.com.lynx.control.misc;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import br.com.lynx.R;
import br.com.lynx.dao.RelatorioDAO;
import br.com.lynx.vo.MetaVO;

public class Meta extends ListActivity {

	List<MetaVO> meta = new ArrayList<MetaVO>();

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_viewmeta);

		RelatorioDAO relatorioDAO = new RelatorioDAO(this);
		setListAdapter(new MetaAdapter(this, relatorioDAO.listaMeta()));
	}

}
