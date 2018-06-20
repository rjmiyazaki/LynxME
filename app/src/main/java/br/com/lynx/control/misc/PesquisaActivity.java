package br.com.lynx.control.misc;

import br.com.lynx.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PesquisaActivity extends AppCompatActivity {

    EditText edtNumCheckouts;
    Button btnConfirma;
    Button btnCancela;
    int clienteID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle("Trem Bala");
        setSupportActionBar(toolbar);

        edtNumCheckouts = (EditText) findViewById(R.id.idCheckouts_Quantidade);

        clienteID = getIntent().getIntExtra("ClienteID", 0);

        edtNumCheckouts.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    selecionarQuestionario();
                    return true;
                }

                return false;

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_pesquisa, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_pesquisa_confirmar:
                selecionarQuestionario();
                return true;

            case R.id.menu_pesquisa_cancelar:
                voltar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selecionarQuestionario() {

        if (Integer.parseInt(edtNumCheckouts.getText().toString()) <= 4) {
            Intent intent = new Intent(this, PesquisaCheckout1A4Activity.class);

            intent.putExtra("ClienteID", clienteID);
            intent.putExtra("Checkouts", Integer.parseInt(edtNumCheckouts.getText().toString()));
            startActivity(intent);
            finish();
        } else {
            // Exibe as perguntas por checkout
            for (int i = Integer.parseInt(edtNumCheckouts.getText().toString()); i >= 1; i--) {
                Intent intent = new Intent(this, PesquisaCheckoutActivity.class);

                intent.putExtra("CheckoutID", String.valueOf(i));
                intent.putExtra("ClienteID", clienteID);
                intent.putExtra("Checkouts", Integer.parseInt(edtNumCheckouts.getText().toString()));

                startActivity(intent);
                finish();
            }

            // Exibe a tela de pesquisa de preço de balas
            Intent intent = new Intent(this, PesquisaPrecoActivity.class);

            intent.putExtra("ClienteID", clienteID);
            intent.putExtra("Checkouts", Integer.parseInt(edtNumCheckouts.getText().toString()));
            startActivity(intent);
            finish();

            // Exibe a tela de pesquisa de gondola
            //intent = new Intent(this, PesquisaCheckout1A4Activity.class);
            intent = new Intent(this, PesquisaCheckoutMais5Activity.class);

            intent.putExtra("ClienteID", clienteID);
            intent.putExtra("Checkouts", Integer.parseInt(edtNumCheckouts.getText().toString()));
            startActivity(intent);
            finish();
        }
    }

    public void voltar() {
        finish();
    }
}


