package br.com.lynx.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReceiverFlexXFTP extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		
		Log.i("LynxME", "Receiver para fazer o envio dos arquivos para o FTP");
		context.startService(new Intent(context, FlexXFTP.class));
	}
}
