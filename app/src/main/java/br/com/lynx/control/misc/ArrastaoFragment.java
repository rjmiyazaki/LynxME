package br.com.lynx.control.misc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.lynx.R;

/**
 * Created by rogerio on 15/03/2016.
 */
public class ArrastaoFragment extends Fragment implements RecyclerViewOnClickListenerHack, View.OnClickListener {

    private RecyclerView recyclerView;
    private List<String> lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //lista = ((ClienteVerificacaoActivity) getActivity()).retornaItensFaltantesArrastao();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_titulos_cliente, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view_lista_titulos_cliente);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        LinhaAdapter adapter = new LinhaAdapter(getActivity(), lista);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onClick(View v) {

    }
}
