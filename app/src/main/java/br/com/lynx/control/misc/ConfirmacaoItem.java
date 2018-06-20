package br.com.lynx.control.misc;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.lynx.R;

/**
 * Created by Rogerio on 06/12/2016.
 */

public class ConfirmacaoItem {

    private Dialog dialog;
    private TextView txtProduto;
    private Spinner spnTabelaPreco;
    private Spinner spnTipoDesconto;
    private TextView edtValorUnitario;
    private EditText edtDesconto;
    private TextView edtValorLiquido;
    private EditText edtQuantidade;
    private TextView edtValorTotal;
    private Button btnInsere;
    private Button btnExclui;
    private Context context;

    public ConfirmacaoItem(Context context){
        this.context = context;
    }

    public void criaDialogConfirmacao(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirmaitem);
        dialog.setTitle("Confirmação de Item");

        txtProduto = (TextView) dialog.findViewById(R.id.txtProduto);
        spnTabelaPreco = (Spinner) dialog.findViewById(R.id.spnTabelaPreco);
        spnTipoDesconto = (Spinner) dialog.findViewById(R.id.spnTipoDesconto);
        edtValorUnitario = (TextView) dialog.findViewById(R.id.edtValorUnitario);
        edtDesconto = (EditText) dialog.findViewById(R.id.edtDesconto);
        edtValorLiquido = (TextView) dialog.findViewById(R.id.edtValorLiquido);
        edtQuantidade = (EditText) dialog.findViewById(R.id.edtQuantidade);
        edtValorTotal = (TextView) dialog.findViewById(R.id.edtValorTotal);
        btnInsere = (Button) dialog.findViewById(R.id.btnInsere);
        btnExclui = (Button) dialog.findViewById(R.id.btnExclui);

        edtValorUnitario.setFocusable(false);
        edtValorLiquido.setFocusable(false);
        edtValorTotal.setFocusable(false);

        dialog.show();
    }

}
