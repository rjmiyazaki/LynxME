package br.com.lynx.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverFlexXGPS extends BroadcastReceiver{

  public void onReceive(Context context, Intent intent) {
	  
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("FlexXGPS");
		context.startService(serviceIntent);
  }
}