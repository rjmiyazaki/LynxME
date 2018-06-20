package br.com.lynx.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateFormat;
import br.com.lynx.R;
import br.com.lynx.model.Configuracao;

public class GpsService extends Service {

	int bateria = 0;

	public void onStart(Intent intent, int startId) {

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {

			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

			}

			public void onProviderEnabled(String arg0) {

			}

			public void onProviderDisabled(String arg0) {

			}

			public void onLocationChanged(Location location) {
				atualizar(location);
			}
		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, locationListener);
	}

	public void atualizar(Location location) {

		try {
			registraBateria();
			FileWriter filePosicoes = new FileWriter("/sdcard/Gps/Posicoes.txt", true);
			FileWriter filePosAtual = new FileWriter("/sdcard/Gps/PosAtual.txt");
			
			filePosicoes.write(
					DateFormat.format("yyyyMMdd", new Date()) + ";" + 
			    DateFormat.format("kkmmss", new Date()) + ";" + 
					location.getLatitude() + ";" + 
			    location.getLongitude() + ";" + 
					(int) location.getSpeed() * 3.6 + 
					";LynxME(" + 
					this.getString(R.string.app_versao) + ");" +
					bateria + "\r\n");
			
			filePosicoes.flush();
			filePosicoes.close();
			
			filePosAtual.write(location.getLatitude() + ";" + location.getLongitude());			
			filePosAtual.flush();
			filePosAtual.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registraBateria() {

		BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				try {
					context.unregisterReceiver(this);
					int rawlevel = intent.getIntExtra("level", -1);
					int scale = intent.getIntExtra("scale", -1);
					int level = -1;
					if (rawlevel >= 0 && scale > 0)
						level = (rawlevel * 100) / scale;
					bateria = level;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		};
		
		IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryLevelReceiver, batteryLevelFilter);
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}
}