package br.com.lynx.control.misc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.lynx.R;
import br.com.lynx.model.Titulo;

public class ATituloAdapter extends BaseAdapter {

    private Context context;
    private List<Titulo> titulos = new ArrayList<Titulo>();

    public ATituloAdapter(Context context, List<Titulo> titulos) {
        this.context = context;
        this.titulos = titulos;
    }

    public int getCount() {
        return titulos.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Titulo titulo = titulos.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_row_titulo, null);

        TextView txtTipo = (TextView) rowView.findViewById(R.id.idRowTitulo_Tipo);
        TextView txtNumero = (TextView) rowView.findViewById(R.id.idRowTitulo_Numero);
        TextView txtValorTitulo = (TextView) rowView.findViewById(R.id.idRowTitulo_ValorTitulo);
        TextView txtSaldoTitulo = (TextView) rowView.findViewById(R.id.idRowTitulo_SaldoTitulo);
        TextView txtDataEmissao = (TextView) rowView.findViewById(R.id.idRowTitulo_DataEmissao);
        TextView txtDataVencimento = (TextView) rowView.findViewById(R.id.idRowTitulo_DataVencimento);
        TextView txtDivisao = (TextView) rowView.findViewById(R.id.idRowTitulo_Divisao);

        txtTipo.setText(titulo.getTipo());
        txtNumero.setText(String.valueOf(titulo.getNumero()) + "/" + String.valueOf(titulo.getParcela()));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        txtDivisao.setText(titulo.getDivisao());
        txtValorTitulo.setText(currencyFormat.format(titulo.getValor()));
        txtSaldoTitulo.setText(currencyFormat.format(titulo.getValorAtual()));

        txtDataEmissao.setText(DateFormat.format("dd/MM/yyyy", titulo.getDataEmissao()));
        txtDataVencimento.setText(DateFormat.format("dd/MM/yyyy", titulo.getDataVencimento()));


        if (titulo.isVencido()){
            txtTipo.setTextColor(Color.RED);
            txtNumero.setTextColor(Color.RED);
            txtValorTitulo.setTextColor(Color.RED);
            txtSaldoTitulo.setTextColor(Color.RED);
            txtDataEmissao.setTextColor(Color.RED);
            txtDataVencimento.setTextColor(Color.RED);
            txtDivisao.setTextColor(Color.RED);

            ((TextView) rowView.findViewById(R.id.idRowTitulo_DataVencimento)).setTextColor(Color.RED);
            ((TextView) rowView.findViewById(R.id.idRowTitulo_TextoValorTitulo)).setTextColor(Color.RED);
            ((TextView) rowView.findViewById(R.id.idRowTitulo_TextoSaldoAtualizado)).setTextColor(Color.RED);
            ((TextView) rowView.findViewById(R.id.idRowTitulo_TextoDataEmissao)).setTextColor(Color.RED);
            ((TextView) rowView.findViewById(R.id.idRowTitulo_TextoDataVencimento)).setTextColor(Color.RED);
            ((TextView) rowView.findViewById(R.id.idRowTitulo_TextoDataVencimento)).setTextColor(Color.RED);
            ((TextView) rowView.findViewById(R.id.idRowTitulo_TextoDivisao)).setTextColor(Color.RED);
        }

        return rowView;
    }

}
