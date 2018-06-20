package br.com.lynx.misc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import br.com.lynx.util.*;
import br.com.lynx.util.CommandWrapper;

public class MessageBox {

	public static void show(Activity owner, String titulo, String mensagem) {
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(owner).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensagem);
		alertDialog.setButton("Ok", new br.com.lynx.util.CommandWrapper(br.com.lynx.util.Command.NO_OP));
		alertDialog.show();
	}
	
	public static AlertDialog createConfirmationDialog(final Context context, final String title, final String message, final br.com.lynx.util.Command command){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	  builder.setCancelable(true);
	  builder.setTitle(title);
	  builder.setMessage(message);
	  builder.setInverseBackgroundForced(true);
	  builder.setPositiveButton("Sim", new br.com.lynx.util.CommandWrapper(command));
	  builder.setNegativeButton("Não", new br.com.lynx.util.CommandWrapper(br.com.lynx.util.Command.NO_OP));
	  return builder.create();
	}
	
	public static AlertDialog createAlertDialog(final Context context, final String title, final String message, final br.com.lynx.util.Command command){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	  builder.setCancelable(true);
	  builder.setTitle(title);
	  builder.setMessage(message);
	  builder.setInverseBackgroundForced(true);
	  builder.setNeutralButton("Ok", new CommandWrapper(command));
	  return builder.create();
	}
}
