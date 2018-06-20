package br.com.lynx.util;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;

public class FlexXFTP extends Service {

	private FTP ftp;
	TelephonyManager telephonyManager;

	public void onStart(Intent intent, int startId) {

		ftp = new FTP("189.114.224.122", "cotenge", "abc123_", 21);

		telephonyManager = (TelephonyManager) this
		    .getSystemService(Context.TELEPHONY_SERVICE);

		new Thread() {
			public void run() {

				for (File file : new File(Environment.getExternalStorageDirectory()
				    .getPath() + "/Gps/").listFiles()) {
					if (file.getName().contains(telephonyManager.getDeviceId()))
						enviarArquivo(file);
				}
			}
		}.start();
	}

	public void enviarArquivo(File file) {
					
			try {
	      if (ftp.connect()){			
	      	ftp.upload(file.getAbsolutePath(), file.getName(), "FlexxGPS");
	      	ftp.disconnect();
	      }
      } catch (SocketException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
	
	}

	public IBinder onBind(Intent intent) {

		return null;
	}

}
