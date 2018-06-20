package br.com.lynx.misc;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by rogerio on 18/02/2016.
 */
public class MessageDlg {

    public static AlertDialog createConfirmationDialog(final Context context, final String title, final String message, final Command command){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Sim", new CommandWrapper(command));
        builder.setNegativeButton("Não", new CommandWrapper(Command.NO_OP));
        return builder.create();
    }

    public static AlertDialog createAlertDialog(final Context context, final String title, final String message, final Command command){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setInverseBackgroundForced(true);
        builder.setNeutralButton("Ok", new CommandWrapper(command));
        return builder.create();
    }
}
