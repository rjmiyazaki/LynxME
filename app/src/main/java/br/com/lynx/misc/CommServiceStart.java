package br.com.lynx.misc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rogerio on 15/04/2016.
 */
public class CommServiceStart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      context.startService(new Intent(context, CommService.class));
    }
}
