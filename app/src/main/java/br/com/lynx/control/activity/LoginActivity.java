package br.com.lynx.control.activity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.lynx.R;
import br.com.lynx.control.misc.PreferenciaActivity;
import br.com.lynx.dao.PedidoDAO;
import br.com.lynx.dbutility.SQLiteHelper;
import br.com.lynx.misc.Communication;
import br.com.lynx.misc.MessageDlg;
import br.com.lynx.misc.Command;
import br.com.lynx.misc.Permissoes;
import br.com.lynx.model.Configuracao;
import br.com.lynx.misc.MessageBox;
import br.com.lynx.domain.Pedido;
import br.com.lynx.util.LynxMEUtil;

import static br.com.lynx.misc.SystemLib.copyFile;
import static br.com.lynx.util.LynxMEUtil.HorarioValido;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "Preferences";

    private Configuracao configuracao;
    private ProgressDialog dialog;
    private Activity context;
    private AutoCompleteTextView edtVendedor;
    private SharedPreferences settings;
    private String vendedorID;
    private String rota;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configuracao = Configuracao.getInstance(this);

        // Solicita as permissões
        String[] permissoes = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        Permissoes.validate(this, 0, permissoes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle(getString(R.string.app_nome));
        toolbar.setSubtitle("Versão" + " " + getString(R.string.app_versao));
        setSupportActionBar(toolbar);

        settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getString("Conexao", "").isEmpty()) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Conexao", "FTPNG");
            editor.putString("Host", "189.114.224.122");
            editor.putString("User", "cotenge");
            editor.putString("Password", "abc123_");
            editor.commit();
        }

        File arquivo = new File("/data/data/br.com.lynx/databases/lynxme.db");
        File arquivoDestino = new File(Environment.getExternalStorageDirectory(), "/lynxme.db");

        if (!arquivo.exists()) {
            try {
                SQLiteHelper.initDB(this);
            } catch (Exception e) {

                Command close = new Command() {
                    public void execute() {
                        finish();
                    }
                };

                AlertDialog dialog = MessageDlg
                        .createAlertDialog(this, "Banco de dados",
                                "Não foi possível criar o banco de dados.", close);
                dialog.show();
            }
        }


        context = this;
        edtVendedor = (AutoCompleteTextView) findViewById(R.id.idLogin_edtVendedorID);

        edtVendedor.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button btnLogin = (Button) findViewById(R.id.idLogin_btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        apagaArquivosAntigos();

        String startTime = configuracao.getStartTime();
        String endTime = configuracao.getEndTime();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_login, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_login_atualizar:
                atualizar();
                return true;

            case R.id.menu_login_inicializar:
                inicializar();
                return true;

            case R.id.menu_login_copiarbd:
                copiarBD();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void copiarBD(){
        File arquivo = new File("/data/data/br.com.lynx/databases/lynxme.db");
        File destino = new File(Environment.getExternalStorageDirectory() + "/lynxme.db");

        try {
            copyFile(arquivo, destino);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void attemptLogin() {
        // Reset errors.
        edtVendedor.setError(null);

        // Store values at the time of the login attempt.
        rota = edtVendedor.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(rota)) {
            edtVendedor.setError(getString(R.string.error_field_required));
            focusView = edtVendedor;
            cancel = true;
        } else if (!isRotaValid(rota)) {
            edtVendedor.setError(getString(R.string.error_invalid_rota));
            focusView = edtVendedor;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            doLogin();
        }
    }

    private boolean isRotaValid(String rota) {
        return rota.length() == 4;
    }

    private void doLogin() {

        Command salvaConfiguracao = new Command() {
            public void execute() {
                configuracao.setVendedorID(rota);
                configuracao.save();
                carregarMenuPrincipal();
            }
        };

        configuracao = Configuracao.getInstance(this);

        if (configuracao.isEmpty()) {
            configuracao.setVendedorID(rota);
            configuracao.save();
            carregarMenuPrincipal();
        } else if (configuracao.getVendedorID().equals(rota)) {
            carregarMenuPrincipal();
        } else if (!configuracao.getVendedorID().equals(rota)) {
            AlertDialog dialog = MessageDlg.createConfirmationDialog(
                    this,
                    "Alteração de Vendedor",
                    "O código informado é diferente do último informado. Deseja utilizar o novo código?",
                    salvaConfiguracao);

            dialog.show();
        }
    }

    private void inicializar() {

        if (verificaPedidoAberto()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("LynxME");
            alertDialog
                    .setMessage("Você possui pedidos em aberto, caso queira continuar, todos os seus dados serão apagados. Continua?");

            alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    SQLiteHelper.initDB(LoginActivity.this);
                    MessageBox.show(LoginActivity.this, "LynxME",
                            "Banco de dados recriado com sucesso.");
                }
            });

            alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    return;
                }
            });
            alertDialog.show();
        } else {
            SQLiteHelper.initDB(this);
            MessageBox.show(this, "LynxME", "Banco de dados recriado com sucesso.");
        }
    }

    private void atualizar() {
        dialog = ProgressDialog.show(this, "Atualização",
                "Baixando uma nova versão, aguarde...", false, true);
        executeDownloadAtualizacao();
    }

    private void executeDownloadAtualizacao(){
        new Thread() {
            @Override
            public void run() {
                String caminho = Environment.getExternalStorageDirectory().toString();

                try {
                    Communication.executeDownload("Sistema", "lynxme.apk", "189.114.224.122", "cotenge", "abc123_", 21);
                    dialog.dismiss();

                    File apkFile = new File(Environment.getExternalStorageDirectory(), "lynxme.apk");

                    if (apkFile.exists()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(apkFile),
                                "application/vnd.android.package-archive");
                        startActivity(intent);

                        Uri packageURI = Uri.parse("package:br.com.lynx");
                        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void preferencias() {
        startActivity(new Intent(this, PreferenciaActivity.class));
    }

    private void carregarMenuPrincipal() {

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void apagaArquivosAntigos(){
        new Thread() {
            @Override
            public void run() {
                File path = new File(Environment.getExternalStorageDirectory(), "/lynxme/orders");

                if (!path.exists())
                    return;

                File files[] = path.listFiles();
                DateTime data = new DateTime();

                for (int i = files.length - 1; i >= 0; i--) {
                    DateTime dataArquivo = new DateTime(files[i].lastModified());

                    if ((Days.daysBetween(dataArquivo, data).getDays() >= 7) || (files[i].length() == 0))
                      files[i].delete();
                }
            }
        }.start();
    }

    private boolean verificaPedidoAberto() {

        boolean pedidoAberto = false;

        try {
            PedidoDAO pedidoDAO = new PedidoDAO(context);
            List<Pedido> pedidos = pedidoDAO.listaPedidos();

            for (Pedido pedido : pedidos)
                if (pedido.getSituacao().equals("Finalizado")) {
                    pedidoAberto = true;
                    break;
                }
        } catch (Exception e) {
            pedidoAberto = false;
        }

        return pedidoAberto;
    }
}