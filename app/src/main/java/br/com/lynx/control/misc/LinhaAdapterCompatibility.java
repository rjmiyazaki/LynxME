package br.com.lynx.control.misc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.model.Campanha;

public class LinhaAdapterCompatibility extends BaseAdapter {

    private Context context;
    private List<String> linhas = new ArrayList<String>();

    public LinhaAdapterCompatibility(Context context, List<String> linhas) {
        this.context = context;
        this.linhas = linhas;
    }

    public int getCount() {
        return linhas.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        String linha = linhas.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row_item_arrastao, null);

        TextView txtLinha = (TextView) rowView.findViewById(R.id.idRowFaltaCampanha_NomeLinha);

        txtLinha.setText(linha);

        return rowView;
    }

}
