package br.com.lynx.control.misc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.content.SharedPreferences;
import br.com.lynx.R;

public class PreferenciaActivity extends AppCompatActivity {
	
	private static final String PREFS_NAME = "Preferences";
	private RadioButton radLocaweb;
	private RadioButton radFTPNG;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);

		Toolbar toolbar = (Toolbar) findViewById(R.id.preferences_toolbar);
		toolbar.setTitle("Preferências");
		setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radLocaweb = (RadioButton) findViewById(R.id.idPreferencias_radLocaweb);
		radFTPNG = (RadioButton) findViewById(R.id.idPreferencias_radFTPNG);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		
		if (settings.getString("Conexao", "").equals("FTPLocaweb"))
			radLocaweb.setChecked(true);
		else
			radFTPNG.setChecked(true);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_preferences, menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_preferences_confirmar:
			confirmar();
			finish();
			return true;
		
		default:
			finish();
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void confirmar(){
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		if (radLocaweb.isChecked()){
		  editor.putString("Conexao", "FTPLocaweb");
		  editor.putString("Host", "ftp.distribuidorang.com.br");
		  editor.putString("User", "distribuidorang");
		  editor.putString("Password", "dng2k15");
		}
		else {
			editor.putString("Conexao", "FTPNG");
		  editor.putString("Host", "189.114.224.122");
		  editor.putString("User", "cotenge");
		  editor.putString("Password", "abc123_");
		}
		
		editor.commit();
	}
}
