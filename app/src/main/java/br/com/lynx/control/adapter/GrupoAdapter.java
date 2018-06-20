package br.com.lynx.control.adapter;

import br.com.lynx.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder> {

    ArrayList<String> grupos;

    public GrupoAdapter(ArrayList<String> lista, Context context) {
        this.grupos = lista;
    }

    @Override
    public GrupoAdapter.GrupoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_grupo_sugestao ,parent,false);
        GrupoViewHolder viewHolder = new GrupoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GrupoAdapter.GrupoViewHolder holder, int position) {
        holder.text.setText(grupos.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return grupos.size();
    }

    public static class GrupoViewHolder extends RecyclerView.ViewHolder{

        protected TextView text;

        public GrupoViewHolder(View itemView) {
            super(itemView);

            text= (TextView) itemView.findViewById(R.id.txt_grupo_sugestao_faltante);
        }
    }
}
